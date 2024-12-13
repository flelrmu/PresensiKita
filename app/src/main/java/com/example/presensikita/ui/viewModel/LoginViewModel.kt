package com.example.presensikita.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.presensikita.data.model.LoginRequest
import com.example.presensikita.data.model.LoginResponse
import com.example.presensikita.data.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    val loginResponse: MutableLiveData<Response<LoginResponse>> = MutableLiveData()

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            val response = authRepository.login(loginRequest)
            loginResponse.postValue(response)
        }
    }
}
