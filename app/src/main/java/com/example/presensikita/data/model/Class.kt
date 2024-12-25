package com.example.presensikita.data.model

data class Class(
    val kode_kelas: String,
    val nip: String,
    val nama_kelas: String,
    val jumlah_sks: Int,
    val semester: String,
)

data class PertemuanResponse(
    val kode_kelas: String,
    val hari: String,
    val Presensis: List<TotalPertemuan>
)

data class TotalPertemuan(
    val total_pertemuan: String
)


data class Lecturer(
    val nip: String,
    val nama_dosen: String,
    val email: String,
)
