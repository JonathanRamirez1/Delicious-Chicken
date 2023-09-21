package com.ramirez.deliciouschicken.data.datasource.impl

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ramirez.deliciouschicken.domain.model.User
import com.ramirez.deliciouschicken.data.datasource.LoginDataSource
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(private val context: Context) : LoginDataSource {

    private val users: List<User> by lazy {
        loadUsersFromJson()
    }

    override fun getUser(username: String, password: String): User? {
        if (username.isBlank() || password.isBlank()) {
            // Validación básica para asegurarse de que no estén vacíos
            return null
        }
        val user = users.find { it.username == username }
        if (user != null && password == user.password) {
            return user
        }
        return null
    }

    private fun loadUsersFromJson(): List<User> {
        val userType = object : TypeToken<List<User>>() {}.type
        val json = context.assets.open("users.json").bufferedReader().use { it.readText() }
        return Gson().fromJson(json, userType)
    }
}
