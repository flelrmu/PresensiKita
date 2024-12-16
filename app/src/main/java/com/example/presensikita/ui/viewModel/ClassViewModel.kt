package com.example.presensikita.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.presensikita.data.model.Class
import com.example.presensikita.data.repository.ClassRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClassViewModel : ViewModel() {
    private val repository = ClassRepository()

    private val _classes = MutableStateFlow<List<Class>>(emptyList())
    val classes: StateFlow<List<Class>> get() = _classes

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun fetchClasses() {
        viewModelScope.launch {
            try {
                val response = repository.getClasses()
                if (response.isSuccessful) {
                    _classes.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Exception: ${e.message}"
            }
        }
    }

    fun deleteClass(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.deleteClass(id)
                if (response.isSuccessful) {
                    fetchClasses() // Perbarui daftar kelas
                } else {
                    _error.value = "Gagal menghapus kelas: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Terjadi kesalahan: ${e.message}"
            }
        }
    }

}
