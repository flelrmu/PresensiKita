package com.example.presensikita.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.presensikita.data.model.Dosen
import com.example.presensikita.data.repository.DosenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DosenViewModel : ViewModel() {
    private val repository = DosenRepository()

    private val _dosens = MutableStateFlow<List<Dosen>>(emptyList())
    val dosens: StateFlow<List<Dosen>> get() = _dosens

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> get() = _isSuccess

    fun fetchDosens() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.getAllDosen()
                if (response.isSuccessful) {
                    _dosens.value = response.body() ?: emptyList()
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

    fun addDosen(newDosen: Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.createDosen(newDosen)
                if (response.isSuccessful) {
                    _isSuccess.value = true
                    _error.value = null
                    fetchDosens() // Perbarui daftar dosen setelah berhasil menambahkan
                } else {
                    _error.value = "Gagal menambahkan dosen: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateDosen(nama: String, email: String, nip: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.updateDosen(nip, updatedDosen)
                if (response.isSuccessful) {
                    _error.value = null
                    fetchDosens()
                } else {
                    _error.value = "Gagal memperbarui dosen: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteDosen(nip: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.deleteDosen(nip)
                if (response.isSuccessful) {
                    _error.value = null
                    fetchDosens() // Refresh daftar dosen
                } else {
                    _error.value = "Gagal menghapus dosen: ${response.errorBody()?.string() ?: "Unknown error"}"
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
