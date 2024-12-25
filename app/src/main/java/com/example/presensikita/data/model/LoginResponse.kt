package com.example.presensikita.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val message: String,
    val accessToken: String,
    val refreshToken: String,
    val admin: User,
)

data class User(

    @field:SerializedName("fakultas")
    val fakultas: String? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("departemen_id")
    val departemen_id: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("foto_profile")
    val foto_profile: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("nama_departemen")
    val nama_departemen: String? = null
)

data class ChangePasswordResponse(
    val message: String
)

// Data Classes
data class Departemen(
    val departemen_id: Int,
    val nama_departemen: String,
    val fakultas: String
)

// Data class untuk menyimpan data user
data class UserData(
    val id: Int,
    val email: String,
    val nama: String,
    val foto_profile: String? = null,
    val local_profile_image: String? = null,
    val server_profile_image: String? = null,
    val departemen_id: Int,
    val nama_departemen: String,
    val fakultas: String
)