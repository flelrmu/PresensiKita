package com.example.presensikita.data.model

data class Class(
    val id: Int = 0, // Default value to handle cases where ID is not provided
    val class_name: String,
    val class_code: String,
    val lecturer: String
)
