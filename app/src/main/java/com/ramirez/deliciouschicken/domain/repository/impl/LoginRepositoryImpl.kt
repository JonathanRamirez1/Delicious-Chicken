package com.ramirez.deliciouschicken.domain.repository.impl

import com.ramirez.deliciouschicken.data.datasource.LoginDataSource
import com.ramirez.deliciouschicken.domain.model.User
import com.ramirez.deliciouschicken.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val dataSource: LoginDataSource):
    LoginRepository {

    override fun loginUser(username: String, password: String): User? {
        return dataSource.getUser(username, password)
    }
}