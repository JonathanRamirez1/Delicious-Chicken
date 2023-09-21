package com.ramirez.deliciouschicken.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramirez.deliciouschicken.domain.model.User
import com.ramirez.deliciouschicken.domain.repository.LoginRepository
import com.ramirez.deliciouschicken.presentation.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginState = MutableLiveData<Resource<User>>()
    val loginState: LiveData<Resource<User>> = _loginState

    fun loginUser(username: String, password: String) {
        _loginState.value = Resource.loading(null)

        viewModelScope.launch {
            val user = loginRepository.loginUser(username, password)
            if (user != null) {
                _loginState.value = Resource.success(user)
            } else {
                _loginState.value = Resource.error("Username or password is incorrect.", null)
            }
        }
    }
}