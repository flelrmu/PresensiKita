package com.example.presensikita.data


// Data class untuk menyimpan data profil
data class UserProfile(
    val nama: String = "",
    val email: String = "",
    val departemen: String = "",
    val fakultas: String = ""
)

// Data jadwal kuliah
data class JadwalKuliah(
    var kode: String,
    var kelas: String,
    var hari: String,
    var jam: String,
    var ruang: String
)