package com.example.presensikita.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.presensikita.data.model.Class
import com.example.presensikita.data.repository.ClassRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddClassViewModel : ViewModel() {
    private val repository = ClassRepository()

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> get() = _isSuccess

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun addClass(newClass: Class) {
        viewModelScope.launch {
            val response = repository.addClass(newClass)
            if (response.isSuccessful) {
                _isSuccess.value = true
            } else {
                _errorMessage.value = response.message()
            }
        }
    }
}
