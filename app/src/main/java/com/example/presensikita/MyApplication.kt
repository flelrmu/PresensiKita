package com.example.presensikita

import android.app.Application
import android.content.Context
import com.example.presensikita.configs.RetrofitClient

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitClient.applicationContext = applicationContext
    }
}