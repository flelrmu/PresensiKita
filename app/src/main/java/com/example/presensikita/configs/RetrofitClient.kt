package com.example.presensikita.configs

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:5000/api/"
    lateinit var applicationContext: Context // Tambahkan context dari aplikasi

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor { chain ->
            // Ambil token dari SharedPreferences
            val sharedPreferences = applicationContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val token = sharedPreferences.getString("accessToken", null)

            // Buat request baru dengan Authorization header
            val request = chain.request().newBuilder()
                .apply {
                    if (token != null) {
                        header("Authorization", "Bearer $token")
                    }
                }
                .build()

            // Lanjutkan request
            chain.proceed(request)
        }
        .build()

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}