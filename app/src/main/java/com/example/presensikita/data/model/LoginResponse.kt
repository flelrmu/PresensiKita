package com.example.presensikita.data.model

data class LoginResponse(
    val message: String,
    val token: String,
    val refreshToken: String,
    val user: User
)

data class User(
    val id: Int,
    val email: String,
    val name: String,
    val departemen: String,
    val fakultas: String,
)