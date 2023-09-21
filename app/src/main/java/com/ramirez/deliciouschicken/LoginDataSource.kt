package com.ramirez.deliciouschicken

interface LoginDataSource {
    fun getUser(username: String, password: String): User?
}