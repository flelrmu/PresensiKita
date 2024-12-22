package com.example.presensikita.data.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class ChangePasswordRequest(
    val currentPassword: String,
    val newPassword: String
)


