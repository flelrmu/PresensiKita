package com.example.presensikita.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.presensikita.data.model.Class
import com.example.presensikita.data.model.Lecturer
import com.example.presensikita.data.model.Presensi
import com.example.presensikita.data.repository.ClassRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClassViewModel : ViewModel() {
    private val repository = ClassRepository()

    private val _classes = MutableStateFlow<List<Class>>(emptyList())
    val classes: StateFlow<List<Class>> get() = _classes

    private val _lecturers = MutableStateFlow<List<Lecturer>>(emptyList())
    val lecturers: StateFlow<List<Lecturer>> get() = _lecturers

    private val _presensi = MutableStateFlow<List<Presensi>>(emptyList())
    val presensi: StateFlow<List<Presensi>> get() = _presensi

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> get() = _isSuccess

    fun fetchClasses() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.getClasses()
                if (response.isSuccessful) {
                    _classes.value = response.body() ?: emptyList()
                    _error.value = null
                } else {
                    _error.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Exception: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchPresensi() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.getPresensi()
                if (response.isSuccessful) {
                    _presensi.value = response.body() ?: emptyList()
                    _error.value = null
                } else {
                    _error.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Exception: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchLecturers() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.getAllLecturers()
                if (response.isSuccessful) {
                    _lecturers.value = response.body() ?: emptyList()
                    _error.value = null
                } else {
                    _error.value = "Gagal mengambil data dosen: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addClass(newClass: Class) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.addClass(newClass)
                if (response.isSuccessful) {
                    _isSuccess.value = true
                    _error.value = null
                    fetchClasses() // Perbarui daftar kelas setelah berhasil menambahkan
                } else {
                    _error.value = "Gagal menambahkan kelas: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }



    fun updateClass(
        kodeKelas: String,
        namaKelas: String,
        nip: String,
        jumlahSks: Int,
        semester: String,
    ) {
        val updatedClass = Class(
            kode_kelas = kodeKelas,
            nama_kelas = namaKelas,
            nip = nip,
            jumlah_sks = jumlahSks,
            semester = semester,
        )

        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.updateClass(kodeKelas, updatedClass)
                if (response.isSuccessful) {
                    _error.value = null
                    fetchClasses()
                } else {
                    _error.value = "Gagal memperbarui kelas: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteClass(kode_kelas: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.deleteClass(kode_kelas)
                if (response.isSuccessful) {
                    _error.value = null
                    fetchClasses() // Refresh daftar kelas
                } else {
                    _error.value = "Gagal menghapus kelas: ${response.errorBody()?.string() ?: "Unknown error"}"
                }
            } catch (e: Exception) {
                _error.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun setError(message: String?) {
        _error.value = message
    }
}