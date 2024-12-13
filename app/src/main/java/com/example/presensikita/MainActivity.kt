package com.example.presensikita

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.presensikita.data.JadwalKuliah
import com.example.presensikita.data.UserProfile
import com.example.presensikita.ui.jadwal_kuliah.ScheduleTableScreen
import com.example.presensikita.ui.theme.PresensiKitaTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PresensiKitaTheme {
                Scaffold {
                    ScheduleTableScreen(
                        schedules = dummySchedules(),
                        onBack = { finish() } // Aksi untuk tombol kembali
                    )
                }

            }
        }
    }
}

// Fungsi untuk data dummy
fun dummySchedules(): List<JadwalKuliah> {
    return listOf(
        JadwalKuliah("Kalkulus", "Kamis", "13.30-15.00", "Ruang 101"),
        JadwalKuliah("Akuisisi Data", "Rabu", "09.20-11.00", "Ruang 203"),
        JadwalKuliah("Pemrograman", "Selasa", "10.00-12.00", "Ruang 105"),
        JadwalKuliah("Basis Data", "Jumat", "08.00-10.00", "Ruang 102")
    )
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    PresensiKitaTheme {

        ScheduleTableScreen(
            schedules = dummySchedules(),
            onBack = { finish() } // Aksi untuk tombol kembali
        )
    }
}

fun finish() {
    TODO("Not yet implemented")
}
