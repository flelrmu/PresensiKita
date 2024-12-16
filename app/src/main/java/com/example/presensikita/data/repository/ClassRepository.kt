package com.example.presensikita.data.repository

import com.example.presensikita.configs.RetrofitClient
import com.example.presensikita.data.api.ApiService
import com.example.presensikita.data.model.Class
import retrofit2.Response

class ClassRepository {
    private val apiService = RetrofitClient.instance.create(ApiService::class.java)

    suspend fun getClasses(): Response<List<Class>> {
        return apiService.getClasses()
    }

    suspend fun addClass(newClass: Class): Response<Class> {
        return apiService.addClass(newClass)
    }

    suspend fun deleteClass(id: Int): Response<Unit> {
        return apiService.deleteClass(id)
    }
}
