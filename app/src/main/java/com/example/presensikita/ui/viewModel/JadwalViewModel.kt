package com.example.presensikita.ui.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.presensikita.data.api.JadwalService
import com.example.presensikita.data.model.JadwalItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class JadwalUiModel(
    val kode: String,
    val nama: String,
    val hari: String,
    val jamMulai: String,
    val ruang: String
)

sealed class JadwalUiState {
    object Loading : JadwalUiState()
    data class Success(val data: List<JadwalUiModel>) : JadwalUiState()
    data class Error(val message: String) : JadwalUiState()
}

class JadwalViewModel(
    private val jadwalService: JadwalService,
    private val context: Context
) : ViewModel() {
    private val _uiState = MutableStateFlow<JadwalUiState>(JadwalUiState.Loading)
    val uiState: StateFlow<JadwalUiState> = _uiState


    fun loadJadwal() {
        viewModelScope.launch {
            try {
                _uiState.value = JadwalUiState.Loading

                // Ambil token dari SharedPreferences

                val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
                val token = sharedPreferences.getString("access_token", null)

                if (token == null) {
                    _uiState.value = JadwalUiState.Error("Token tidak ditemukan")
                    return@launch
                }
                val response = jadwalService.getJadwal("Bearer $token")

                // Tambahkan logging
                Log.d("JadwalViewModel", "Response: ${response.raw()}")
                Log.d("JadwalViewModel", "Response body: ${response.body()}")

                if (response.isSuccessful) {
                    val jadwalResponse = response.body()
                    Log.d("JadwalViewModel", "Jadwal list: ${jadwalResponse?.jadwal}")

                    val jadwalList = jadwalResponse?.jadwal?.mapNotNull { jadwalItem ->
                        jadwalItem?.let {
                            JadwalUiModel(
                                kode = it.kodeKelas ?: "",
                                nama = it.kela?.namaKelas ?: "",
                                hari = it.hari ?: "",
                                jamMulai = it.jamMulai?.substring(0, 5) ?: "",
                                ruang = it.ruangan?.namaRuangan ?: ""
                            )
                        }
                    } ?: emptyList()

                    Log.d("JadwalViewModel", "Mapped jadwal: $jadwalList")
                    _uiState.value = JadwalUiState.Success(jadwalList)
                } else {
                    Log.e("JadwalViewModel", "Error: ${response.errorBody()?.string()}")
                    _uiState.value = JadwalUiState.Error("Failed to load jadwal: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("JadwalViewModel", "Exception: ${e.message}", e)
                _uiState.value = JadwalUiState.Error("Error loading jadwal: ${e.message}")
            }
        }
    }
}

class JadwalViewModelFactory(
    private val jadwalService: JadwalService,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JadwalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JadwalViewModel(jadwalService, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}