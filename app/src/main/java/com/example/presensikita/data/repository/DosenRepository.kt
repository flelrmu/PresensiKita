package com.example.presensikita.data.repository

import com.example.presensikita.configs.RetrofitClient
import com.example.presensikita.data.api.ApiService
import com.example.presensikita.data.model.Dosen
import retrofit2.Response

class DosenRepository {
    private val apiService = RetrofitClient.instance.create(ApiService::class.java)

    // Mendapatkan daftar semua dosen
    suspend fun getAllDosen(): Response<List<Dosen>> {
        return apiService.getAllDosen()
    }

    // Mendapatkan detail dosen berdasarkan NIP
    suspend fun getDosenByNip(nip: String): Response<Dosen> {
        return apiService.getDosenByNip(nip)
    }

    // Menambahkan dosen baru
    suspend fun createDosen(dosen: Dosen): Response<Dosen> {
        return apiService.createDosen(dosen)
    }

    // Mengupdate data dosen berdasarkan NIP
    suspend fun updateDosen(nip: String, updatedDosen: Dosen): Response<Dosen> {
        return apiService.updateDosen(nip, updatedDosen)
    }

    // Menghapus data dosen berdasarkan NIP
    suspend fun deleteDosen(nip: String): Response<Unit> {
        return apiService.deleteDosen(nip)
    }
}
