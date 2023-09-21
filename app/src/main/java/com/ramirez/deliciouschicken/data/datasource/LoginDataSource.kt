package com.ramirez.deliciouschicken.data.datasource

import com.ramirez.deliciouschicken.domain.model.User

interface LoginDataSource {
    fun getUser(username: String, password: String): User?
}