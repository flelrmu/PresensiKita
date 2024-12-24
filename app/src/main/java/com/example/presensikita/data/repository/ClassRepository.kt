package com.example.presensikita.data.repository

import com.example.presensikita.configs.RetrofitClient
import com.example.presensikita.data.api.ApiService
import com.example.presensikita.data.model.Class
import com.example.presensikita.data.model.Presensi
import com.example.presensikita.data.model.Lecturer
import retrofit2.Response

class ClassRepository {
    private val apiService = RetrofitClient.instance.create(ApiService::class.java)

    suspend fun getClasses(): Response<List<Class>> {
        return apiService.getClasses()
    }

    suspend fun getPresensi(): Response<List<Presensi>> {
        return apiService.getPresensi()
    }

    suspend fun addClass(newClass: Class): Response<Class> {
        // Validasi NIP dosen dari daftar dosen
        val lecturers = getAllLecturers().body() ?: emptyList()
        if (lecturers.none { it.nip == newClass.nip }) {
            throw IllegalArgumentException("NIP dosen tidak valid!")
        }

        // Kirim data kelas tanpa properti dosen
        return apiService.addClass(newClass)
    }

    suspend fun updateClass(kode_kelas: String, updatedClass: Class): Response<Class> {
        // Validasi NIP dosen dari daftar dosen
        val lecturers = getAllLecturers().body() ?: emptyList()
        if (lecturers.none { it.nip == updatedClass.nip }) {
            throw IllegalArgumentException("NIP dosen tidak valid!")
        }

        // Kirim data kelas tanpa properti dosen
        return apiService.updateClass(kode_kelas, updatedClass)
    }

    suspend fun deleteClass(kode_kelas: String): Response<Unit> {
        return try {
            apiService.deleteClass(kode_kelas)
        } catch (e: Exception) {
            Response.error(500, okhttp3.ResponseBody.create(null, "Gagal menghapus kelas"))
        }
    }

    suspend fun getAllLecturers(): Response<List<Lecturer>> = apiService.getAllLecturers()
}
