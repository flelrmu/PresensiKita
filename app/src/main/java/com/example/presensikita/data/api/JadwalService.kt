package com.example.presensikita.data.api

import com.example.presensikita.data.model.JadwalResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface JadwalService {
    @GET("jadwal")
    suspend fun getJadwal(@Header("Authorization") token: String): Response<JadwalResponse>
}