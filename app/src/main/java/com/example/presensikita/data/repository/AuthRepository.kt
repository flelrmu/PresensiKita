package com.example.presensikita.data.repository

import com.example.presensikita.configs.RetrofitClient
import com.example.presensikita.data.api.ApiService
import com.example.presensikita.data.model.LoginRequest
import com.example.presensikita.data.model.LoginResponse
import retrofit2.Response

class AuthRepository {
    private val apiService = RetrofitClient.instance.create(ApiService::class.java)

    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse> {
        return apiService.login(loginRequest)
    }
}