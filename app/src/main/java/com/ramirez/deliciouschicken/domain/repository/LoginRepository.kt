package com.ramirez.deliciouschicken.domain.repository

import com.ramirez.deliciouschicken.domain.model.User

interface LoginRepository {
    fun loginUser(username: String, password: String): User?
}