package com.example.presensikita.data.model

data class LoginResponse(
    val message: String,
    val accessToken: String,
    val refreshToken: String,
    val admin: Admin
)

data class Admin(
    val id: Int,
    val email: String,
    val nama: String,
    val departemen_id: Int,
)