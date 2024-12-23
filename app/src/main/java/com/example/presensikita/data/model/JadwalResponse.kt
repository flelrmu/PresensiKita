package com.example.presensikita.data.model

import com.google.gson.annotations.SerializedName

data class JadwalResponse(

	@field:SerializedName("jadwal")
	val jadwal: List<JadwalItem?>? = emptyList(),

	@field:SerializedName("message")
	val message: String? = null
)

data class JadwalItem(

	@field:SerializedName("hari")
	val hari: String? = null,

	@field:SerializedName("jam_mulai")
	val jamMulai: String? = null,

	@field:SerializedName("kode_kelas")
	val kodeKelas: String? = null,

	@field:SerializedName("Ruangan")
	val ruangan: Ruangan? = null,

	@field:SerializedName("jadwal_kuliah_id")
	val jadwalKuliahId: Int? = null,

	@field:SerializedName("Kela")
	val kela: Kela? = null,

	@field:SerializedName("ruangan_id")
	val ruanganId: Int? = null
)

data class Ruangan(

	@field:SerializedName("ruang_id")
	val ruangId: Int? = null,

	@field:SerializedName("nama_ruangan")
	val namaRuangan: String? = null,

	@field:SerializedName("kapasitas")
	val kapasitas: Int? = null
)

data class Kela(

	@field:SerializedName("jumlah_sks")
	val jumlahSks: Int? = null,

	@field:SerializedName("kode_kelas")
	val kodeKelas: String? = null,

	@field:SerializedName("nip")
	val nip: String? = null,

	@field:SerializedName("nama_kelas")
	val namaKelas: String? = null,

	@field:SerializedName("departemen_id")
	val departemenId: Int? = null,

	@field:SerializedName("semester")
	val semester: String? = null
)
