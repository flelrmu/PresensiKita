package com.example.presensikita.ui.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.presensikita.configs.RetrofitClient
import com.example.presensikita.data.model.ChangePasswordRequest
import kotlinx.coroutines.launch

class ChangePasswordViewModel(private val context: Context) : ViewModel() {
    private val _changePasswordResult = MutableLiveData<Result<String>>()
    val changePasswordResult: LiveData<Result<String>> = _changePasswordResult

    fun changePassword(currentPassword: String, newPassword: String) {
        viewModelScope.launch {
            try {
                val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val token = sharedPreferences.getString("access_token", "") ?: ""

                val request = ChangePasswordRequest(currentPassword, newPassword)
                val response = RetrofitClient.getApiService()
                    .changePassword("Bearer $token", request)

                if (response.isSuccessful) {
                    _changePasswordResult.value = Result.success(response.body()?.message ?: "Password berhasil diubah")
                } else {
                    _changePasswordResult.value = Result.failure(Exception(response.body()?.message ?: "Gagal mengubah password"))
                }
            } catch (e: Exception) {
                _changePasswordResult.value = Result.failure(e)
            }
        }
    }
}

// Factory untuk ViewModel
class ChangePasswordViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChangePasswordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChangePasswordViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}