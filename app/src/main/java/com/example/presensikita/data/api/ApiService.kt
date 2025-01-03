package com.example.presensikita.data.api

import com.example.presensikita.data.model.ChangePasswordRequest
import com.example.presensikita.data.model.ChangePasswordResponse
import com.example.presensikita.data.model.LoginRequest
import com.example.presensikita.data.model.LoginResponse
import com.example.presensikita.data.model.Class
import com.example.presensikita.data.model.Departemen
import okhttp3.MultipartBody
import okhttp3.RequestBody
import com.example.presensikita.data.model.Dosen
import com.example.presensikita.data.model.Lecturer
import com.example.presensikita.data.model.PertemuanResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @PUT("auth/change-password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body request: ChangePasswordRequest
    ): Response<ChangePasswordResponse>

    @GET("departemen")
    suspend fun getDepartments(): List<Departemen>

    @Multipart
    @PUT("auth/edit-profile")
    suspend fun editProfile(
        @Header("Authorization") token: String,
        @Part("nama") nama: RequestBody,
        @Part("email") email: RequestBody,
        @Part("departemen_id") departemenId: RequestBody,
        @Part foto: MultipartBody.Part?
    ): Response<LoginResponse>

    @POST("auth/logout")
    suspend fun logout(@Body refreshToken: Map<String, String>): Response<Unit>

    // Classes API methods
    @GET("classes")
    suspend fun getClasses(): Response<List<Class>>

    @POST("classes")
    suspend fun addClass(@Body newClass: Class): Response<Class>

    @DELETE("classes/{kode_kelas}")
    suspend fun deleteClass(@Path("kode_kelas") kode_kelas: String): Response<Unit>

    @PUT("classes/{kode_kelas}")
    suspend fun updateClass(
        @Path("kode_kelas") kodeKelas: String,
        @Body updatedClass: Class
    ): Response<Class>

    // Lecturers API method
    @GET("lecturers")
    suspend fun getAllLecturers(): Response<List<Lecturer>>

    // Pertemuan API method
    @GET("pertemuan")
    suspend fun getAllPertemuan(): Response<List<PertemuanResponse>>

    // Dosen API methods (CRUD operations)
    @GET("dosen")
    suspend fun getAllDosen(): Response<List<Dosen>>

    @GET("dosen/{nip}")
    suspend fun getDosenByNip(@Path("nip") nip: String): Response<Dosen>

    @POST("dosen")
    suspend fun createDosen(@Body dosen: Dosen): Response<Dosen>

    @PUT("dosen/{nip}")
    suspend fun updateDosen(
        @Path("nip") nip: String,
        @Body updatedDosen: Dosen
    ): Response<Dosen>

    @DELETE("dosen/{nip}")
    suspend fun deleteDosen(@Path("nip") nip: String): Response<Unit>
}
