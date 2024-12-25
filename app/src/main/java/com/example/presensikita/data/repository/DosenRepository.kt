package com.example.presensikita.data.repository

import com.example.presensikita.configs.RetrofitClient
import com.example.presensikita.data.api.ApiService
import com.example.presensikita.data.model.Dosen
import retrofit2.Response

class DosenRepository {
    private val apiService = RetrofitClient.instance.create(ApiService::class.java)

    // Mendapatkan semua data dosen
    suspend fun getAllDosen(): Response<List<Dosen>> {
        return apiService.getAllDosen()
    }

    // Mendapatkan data dosen berdasarkan NIP
    suspend fun getDosenByNip(nip: String): Response<Dosen> {
        return apiService.getDosenByNip(nip)
    }

    // Menambahkan data dosen baru
    suspend fun createDosen(dosen: Dosen): Response<Dosen> {
        return apiService.createDosen(dosen)
    }

    // Memperbarui data dosen
    suspend fun updateDosen(nip: String, updatedDosen: Dosen): Response<Dosen> {
        // Validasi apakah NIP ada di daftar dosen
        val existingDosen = getAllDosen().body() ?: emptyList()
        if (existingDosen.none { it.nip == nip }) {
            throw IllegalArgumentException("NIP dosen tidak ditemukan!")
        }

        return apiService.updateDosen(nip, updatedDosen)
    }

    // Menghapus data dosen berdasarkan NIP
    suspend fun deleteDosen(nip: String): Response<Unit> {
        return try {
            apiService.deleteDosen(nip)
        } catch (e: Exception) {
            Response.error(500, okhttp3.ResponseBody.create(null, "Gagal menghapus dosen"))
        }
    }
}
