package com.example.frontendandroid.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontendandroid.data.UserSession
import com.example.frontendandroid.data.model.LoginRequest
import com.example.frontendandroid.data.network.RetrofitClient
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    var username = mutableStateOf("")
    var password = mutableStateOf("")
    var errorMessage = mutableStateOf<String?>(null)
    var isLoading = mutableStateOf(false)

    fun login(onSuccess: () -> Unit) {
        if (username.value.isBlank() || password.value.isBlank()) {
            errorMessage.value = "Por favor complete todos los campos"
            return
        }

        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            try {
                val response = RetrofitClient.instance.login(
                    LoginRequest(username.value, password.value)
                )
                UserSession.setUser(response)
                onSuccess()
            } catch (e: Exception) {
                errorMessage.value = "Error al iniciar sesión: Credenciales inválidas o error de conexión."
            } finally {
                isLoading.value = false
            }
        }
    }
}
