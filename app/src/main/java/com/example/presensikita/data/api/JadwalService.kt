package com.example.presensikita.data.api

import com.example.presensikita.data.model.JadwalResponse
import com.example.presensikita.data.model.RuanganResponse
import com.example.presensikita.data.model.TambahJadwalRequest
import com.example.presensikita.data.model.UpdateJadwalRequest
import com.example.presensikita.data.model.UpdateJadwalResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface JadwalService {

    @GET("jadwal")
    suspend fun getJadwal(): Response<JadwalResponse>

    @GET("ruangan")
    suspend fun getRuangan(): Response<RuanganResponse>

    @POST("jadwal")
    suspend fun tambahJadwal(@Body request: TambahJadwalRequest): Response<JadwalResponse>

    @PUT("jadwal/{id}")
    suspend fun updateJadwal(
        @Path("id") id: Int,
        @Body updateData: UpdateJadwalRequest
    ): Response<UpdateJadwalResponse>

    @DELETE("jadwal/{id}")
    suspend fun deleteJadwal(@Path("id") id: Int): Response<Unit>

}