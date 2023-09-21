package com.ramirez.deliciouschicken

interface LoginRepository {
    fun loginUser(username: String, password: String): User?
}