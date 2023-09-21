package com.ramirez.deliciouschicken

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ramirez.deliciouschicken.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.apply {
            LoginButton.setOnClickListener {
                val username = usernameEditText.text.toString().trim()
                val password = passwordEditText.text.toString().trim()
                if (isValidFields()) {
                    loginViewModel.loginUser(username, password)
                }
            }
            usernameEditText.addTextChangedListener {
                usernameTextInputLayout.error = null
            }
            passwordEditText.addTextChangedListener {
                passwordTextInputLayout.error = null
            }
        }
    }

    private fun isValidFields(): Boolean {
        var isValid = true
        binding.apply {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            if (username.isEmpty()) {
                usernameTextInputLayout.error = "Nombre de suario no puede estar en blanco"
                isValid = false
            }
            if (password.isEmpty()) {
                passwordTextInputLayout.error = "Contraseña no puede estar en blanco"
                isValid = false
            }
        }
        return isValid
    }

    private fun setupObservers() {
        loginViewModel.loginState.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Resource.Status.LOADING -> {
                    binding.LoginButton.isEnabled = false
                }
                Resource.Status.SUCCESS -> {
                    binding.LoginButton.isEnabled = true
                    Toast.makeText(context, "Login exitoso!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_loginFragment_to_productFragment)
                }
                Resource.Status.ERROR -> {
                    binding.LoginButton.isEnabled = true
                    Toast.makeText(context, resource.message ?: "Error desconocido", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}