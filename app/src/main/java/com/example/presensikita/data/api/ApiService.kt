package com.example.presensikita.data.api

import com.example.presensikita.data.model.LoginRequest
import com.example.presensikita.data.model.LoginResponse
import com.example.presensikita.data.model.Class
import com.example.presensikita.data.model.Lecturer
import com.example.presensikita.data.model.Presensi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("classes")
    suspend fun getClasses(): Response<List<Class>>

    @GET("presensi")
    suspend fun getPresensi(): Response<List<Presensi>>

    @POST("classes")
    suspend fun addClass(@Body newClass: Class): Response<Class>

    @DELETE("classes/{kode_kelas}")
    suspend fun deleteClass(@Path("kode_kelas") kode_kelas: String): Response<Unit>

    @PUT("classes/{kode_kelas}")
    suspend fun updateClass(
        @Path("kode_kelas") kodeKelas: String,
        @Body updatedClass: Class
    ): Response<Class>

    @GET("lecturers")
    suspend fun getAllLecturers(): Response<List<Lecturer>>
}
