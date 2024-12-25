package com.example.presensikita.ui

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.presensikita.configs.RetrofitClient
import com.example.presensikita.data.api.ApiService
import com.example.presensikita.ui.components.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun logoutUser(context: Context) {
    // Ambil token dari SharedPreferences
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val refreshToken = sharedPreferences.getString("refresh_token", "")

    if (refreshToken.isNullOrEmpty()) {
        Toast.makeText(context, "Token tidak ditemukan", Toast.LENGTH_SHORT).show()
        return
    }

    // Buat API Service
    val apiService = RetrofitClient.instance.create(ApiService::class.java)

    // Lakukan permintaan logout
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = apiService.logout(mapOf("refresh_token" to refreshToken)) // Sesuaikan dengan key yang diharapkan server
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    // Hapus sesi lokal
                    with(sharedPreferences.edit()) {
                        clear()
                        apply()
                    }

                    Toast.makeText(context, "Logout berhasil", Toast.LENGTH_SHORT).show()

                    // Gunakan FLAG_ACTIVITY_NEW_TASK karena kita memulai activity dari context non-activity
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, "Logout gagal: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Terjadi kesalahan: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}