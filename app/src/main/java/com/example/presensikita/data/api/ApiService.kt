package com.example.presensikita.data.api

import com.example.presensikita.data.model.ChangePasswordRequest
import com.example.presensikita.data.model.ChangePasswordResponse
import com.example.presensikita.data.model.LoginRequest
import com.example.presensikita.data.model.LoginResponse
import com.example.presensikita.data.model.Class
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @PUT("auth/change-password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body request: ChangePasswordRequest
    ): Response<ChangePasswordResponse>



    @POST("auth/logout")
    suspend fun logout(@Body refreshToken: Map<String, String>): Response<Unit>

    @GET("classes")
    suspend fun getClasses(): Response<List<Class>>

    @POST("classes")
    suspend fun addClass(@Body newClass: Class): Response<Class>

    @DELETE("classes/{id}")
    suspend fun deleteClass(@Path("id") id: Int): Response<Unit>
}