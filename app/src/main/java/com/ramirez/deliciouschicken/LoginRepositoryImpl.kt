package com.ramirez.deliciouschicken

import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val dataSource: LoginDataSource): LoginRepository {

    override fun loginUser(username: String, password: String): User? {
        return dataSource.getUser(username, password)
    }
}