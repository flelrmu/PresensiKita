package com.example.presensikita.configs

import com.example.presensikita.data.api.ApiService
import com.example.presensikita.data.api.JadwalService
import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "https://api-presensi-production.up.railway.app/api/"

//    private const val BASE_URL = "https://8qgllgnr-5000.asse.devtunnels.ms/api/"
//    private const val BASE_URL = "http://10.0.2.2:5000/api/"
//    private const val BASE_URL = "http://192.168.1.5:5000/api/"
    lateinit var applicationContext: Context // Tambahkan context dari aplikasi

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor { chain ->
            // Ambil token dari SharedPreferences
            val sharedPreferences = applicationContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val token = sharedPreferences.getString("access_token", null)

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

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun getApiService(): ApiService = retrofit.create(ApiService::class.java)

    val jadwalService: JadwalService = retrofit.create(JadwalService::class.java)

}
