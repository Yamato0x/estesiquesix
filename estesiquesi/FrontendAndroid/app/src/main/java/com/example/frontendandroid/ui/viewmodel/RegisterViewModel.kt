package com.example.frontendandroid.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontendandroid.data.model.User
import com.example.frontendandroid.data.network.RetrofitClient
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    var nombreCompleto = mutableStateOf("")
    var rut = mutableStateOf("")
    var nombreUsuario = mutableStateOf("")
    var contrasena = mutableStateOf("")
    var correo = mutableStateOf("")
    var codigoAdmin = mutableStateOf("")
    var errorMessage = mutableStateOf<String?>(null)
    var isLoading = mutableStateOf(false)

    fun register(onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            try {
                val newUser = User(
                    nombreCompleto = nombreCompleto.value,
                    rut = rut.value,
                    nombreUsuario = nombreUsuario.value,
                    contrasena = contrasena.value,
                    correo = correo.value,
                    rol = "usuario" // Default, backend/logic might upgrade with admin code
                )
                RetrofitClient.instance.createUser(
                    newUser, 
                    if (codigoAdmin.value.isBlank()) null else codigoAdmin.value
                )
                onSuccess()
            } catch (e: Exception) {
                errorMessage.value = "Error al registrar: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }
}
