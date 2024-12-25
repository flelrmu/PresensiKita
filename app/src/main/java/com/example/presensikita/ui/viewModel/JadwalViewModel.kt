package com.example.presensikita.ui.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.presensikita.configs.RetrofitClient.jadwalService
import com.example.presensikita.data.api.ApiService
import com.example.presensikita.data.api.JadwalService
import com.example.presensikita.data.model.JadwalItem
import com.example.presensikita.data.model.RuanganItem
import com.example.presensikita.data.model.TambahJadwalRequest
import com.example.presensikita.data.model.UpdateJadwalRequest
import com.example.presensikita.data.repository.ClassRepository
//import com.example.presensikita.data.repository.JadwalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class JadwalUiModel(
    val kode_jadwal: Int,
    val kode_kelas: String,
    val nama_kelas: String,
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

    private val _uiState = MutableLiveData<JadwalUiState>(JadwalUiState.Loading)
    val uiState: LiveData<JadwalUiState> = _uiState

    private val _deleteStatus = MutableLiveData<Boolean>()
    val deleteStatus: LiveData<Boolean> = _deleteStatus

    fun loadJadwal() {
        viewModelScope.launch {
            try {
                _uiState.value = JadwalUiState.Loading

                // Ambil token dari SharedPreferences

                val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val token = sharedPreferences.getString("access_token", null)

                if (token == null) {
                    _uiState.value = JadwalUiState.Error("Token tidak ditemukan")
                    return@launch
                }
                val response = jadwalService.getJadwal()

                // Tambahkan logging
                Log.d("JadwalViewModel", "Response: ${response.raw()}")
                Log.d("JadwalViewModel", "Response body: ${response.body()}")

                if (response.isSuccessful) {
                    val jadwalResponse = response.body()
                    Log.d("JadwalViewModel", "Jadwal list: ${jadwalResponse?.jadwal}")

                    val jadwalList = jadwalResponse?.jadwal?.mapNotNull { jadwalItem ->
                        jadwalItem?.let {
                            JadwalUiModel(
                                kode_jadwal = it.jadwalKuliahId ?: 0,
                                kode_kelas = it.kodeKelas ?: "",
                                nama_kelas = it.kela?.namaKelas ?: "",
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

    fun deleteJadwal(jadwalId: Int) {
        viewModelScope.launch {
            try {
                val response = jadwalService.deleteJadwal(jadwalId)
                if (response.isSuccessful) {
                    _deleteStatus.value = true
                    Toast.makeText(context, "Jadwal berhasil dihapus", Toast.LENGTH_SHORT).show()
                    loadJadwal() // Refresh data setelah menghapus
                } else {
                    _deleteStatus.value = false
                    Toast.makeText(context, "Gagal menghapus jadwal", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                _deleteStatus.value = false
                Log.e("JadwalViewModel", "Error deleting jadwal", e)
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
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


class EditJadwalViewModel(
    private val jadwalService: JadwalService,
    private val context: Context
) : ViewModel() {
    private val _uiState = MutableLiveData<EditJadwalUiState>(EditJadwalUiState.Initial)
    val uiState: LiveData<EditJadwalUiState> = _uiState

    private val _ruangan = MutableLiveData<List<RuanganItem>>(emptyList())
    val ruangan: LiveData<List<RuanganItem>> = _ruangan

    init {
        loadRuangan()
    }

    private fun loadRuangan() {
        viewModelScope.launch {
            try {
                Log.d("EditJadwalViewModel", "Memulai loading ruangan")
                val response = jadwalService.getRuangan()
                if (response.isSuccessful) {
                    _ruangan.value = response.body()?.data ?: emptyList()
                    Log.d("EditJadwalViewModel", "Berhasil load ruangan: ${response.body()?.data?.size} ruangan")
                } else {
                    Log.e("EditJadwalViewModel", "Gagal load ruangan: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "Gagal memuat data ruangan", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("EditJadwalViewModel", "Error loading ruangan", e)
                Toast.makeText(context, "Terjadi kesalahan: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun updateJadwal(jadwalId: Int, ruanganId: Int, hari: String, jamMulai: String) {
        viewModelScope.launch {
            try {
                _uiState.value = EditJadwalUiState.Loading

                if (jadwalId <= 0 || ruanganId <= 0 || hari.isBlank() || jamMulai.isBlank()) {
                    _uiState.value = EditJadwalUiState.Error("Semua field harus diisi")
                    return@launch
                }

                val request = UpdateJadwalRequest(ruanganId, hari, jamMulai)
                val response = jadwalService.updateJadwal(jadwalId, request)

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _uiState.value = EditJadwalUiState.Success(responseBody.message)
                    } else {
                        _uiState.value = EditJadwalUiState.Error("Response body kosong")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    _uiState.value = EditJadwalUiState.Error(
                        errorBody ?: "Gagal update jadwal: ${response.code()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("EditJadwalViewModel", "Error updating jadwal", e)
                _uiState.value = EditJadwalUiState.Error("Error: ${e.message}")
            }
        }
    }
}

sealed class EditJadwalUiState {
    object Initial : EditJadwalUiState()
    object Loading : EditJadwalUiState()
    data class Success(val message: String) : EditJadwalUiState()
    data class Error(val message: String) : EditJadwalUiState()
}

class EditJadwalViewModelFactory(
    private val jadwalService: JadwalService,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditJadwalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditJadwalViewModel(jadwalService, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class AddJadwalViewModel(
    private val jadwalService: JadwalService,
    private val apiService: ApiService,
    private val context: Context
) : ViewModel() {
    private val repository = ClassRepository()

    private val _classes = MutableStateFlow<List<com.example.presensikita.data.model.Class>>(emptyList())
    val classes: StateFlow<List<com.example.presensikita.data.model.Class>> get() = _classes

    private val _ruangan = MutableLiveData<List<RuanganItem>>()
    val ruangan: LiveData<List<RuanganItem>> = _ruangan

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    private val _navigateToList = MutableLiveData<Boolean>()
    val navigateToList: LiveData<Boolean> = _navigateToList

    init {
        loadClasses()
        loadRuangan()
    }

    private fun loadClasses() {
        viewModelScope.launch {
            try {
                Log.d("JadwalViewModel", "Memulai load daftar kelas")
                val response = apiService.getClasses()
                if (response.isSuccessful) {
                    _classes.value = response.body()!!
                    Log.d("JadwalViewModel", "Berhasil load ${response.body()?.size} kelas")
                } else {
                    Log.e("JadwalViewModel", "Gagal load kelas: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("JadwalViewModel", "Error loading classes", e)
            }
        }
    }

    private fun loadRuangan() {
        viewModelScope.launch {
            try {
                Log.d("JadwalViewModel", "Memulai load daftar ruangan")
                val response = jadwalService.getRuangan()
                if (response.isSuccessful) {
                    _ruangan.value = response.body()?.data
                    Log.d("JadwalViewModel", "Berhasil load ${response.body()?.data?.size} ruangan")
                } else {
                    Log.e("JadwalViewModel", "Gagal load ruangan: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("JadwalViewModel", "Error loading ruangan", e)
            }
        }
    }

    fun tambahJadwal(kodeKelas: String, ruanganId: Int, hari: String, jamMulai: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                Log.d("JadwalViewModel", "Mencoba menambah jadwal: $kodeKelas, $ruanganId, $hari, $jamMulai")

                val request = TambahJadwalRequest(kodeKelas, ruanganId, hari, jamMulai)
                val response = jadwalService.tambahJadwal(request)

                if (response.isSuccessful) {
                    Log.d("JadwalViewModel", "Berhasil menambah jadwal")
                    _toastMessage.value = "Berhasil menambah jadwal"
                    _navigateToList.value = true
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Gagal menambah jadwal"
                    Log.e("JadwalViewModel", "Gagal menambah jadwal: $errorMessage")
                    _toastMessage.value = errorMessage
                }
            } catch (e: Exception) {
                Log.e("JadwalViewModel", "Error menambah jadwal", e)
                _toastMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun doneNavigating() {
        _navigateToList.value = false
    }
}

class AddJadwalViewModelFactory(
    private val jadwalService: JadwalService,
    private val apiService: ApiService,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddJadwalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddJadwalViewModel(jadwalService, apiService, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}