package com.example.presensikita.data.model

data class Class(
    val kode_kelas: String,
    val nip: String,
    val nama_kelas: String,
    val jumlah_sks: Int,
    val semester: String,
)

data class Presensi(
    val presensi_id: Int,
    val pertemuan: Int,
    val jml_hadir: Int,
    val jml_alpha: Int,
    val jml_izin: Int,
    val jml_sakit: Int,
)

data class Lecturer(
    val nip: String,
    val nama_dosen: String,
    val email: String,
)
