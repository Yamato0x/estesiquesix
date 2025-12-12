package com.example.frontendandroid.ui.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontendandroid.data.UserSession
import com.example.frontendandroid.data.network.RetrofitClient
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class ProfileViewModel : ViewModel() {
    // Current User Data
    val currentUser = mutableStateOf(UserSession.getUser())

    // Edit Mode State
    var isEditMode = mutableStateOf(false)
    var nombreCompleto = mutableStateOf("")
    var correo = mutableStateOf("")
    var nombreUsuario = mutableStateOf("")
    var profileImage = mutableStateOf<Bitmap?>(null)

    init {
        initializeData()
    }

    private fun initializeData() {
        val user = UserSession.getUser()
        currentUser.value = user
        nombreCompleto.value = user?.nombreCompleto ?: ""
        correo.value = user?.correo ?: ""
        nombreUsuario.value = user?.nombreUsuario ?: ""
        
        if (!user?.foto_perfil.isNullOrEmpty()) {
            try {
                val decodedString = Base64.decode(user!!.foto_perfil, Base64.DEFAULT)
                profileImage.value = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            } catch (e: Exception) { 
                profileImage.value = null 
            }
        }
    }

    fun onCameraResult(bitmap: Bitmap?) {
        bitmap?.let {
            profileImage.value = it
        }
    }

    fun startEditing() {
        initializeData() // Reset fields to current user data
        isEditMode.value = true
    }

    fun cancelEditing() {
        isEditMode.value = false
        initializeData() // Revert changes
    }
    
    fun saveProfile() {
        viewModelScope.launch {
            try {
                currentUser.value?.let { user ->
                    // Convert Bitmap to Base64
                    var fotoPerfilBase64: String? = user.foto_perfil
                    profileImage.value?.let { bitmap ->
                        val byteArrayOutputStream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
                        val byteArray = byteArrayOutputStream.toByteArray()
                        fotoPerfilBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT)
                    }

                    val updatedUser = user.copy(
                        nombreCompleto = nombreCompleto.value,
                        correo = correo.value,
                        nombreUsuario = nombreUsuario.value,
                        foto_perfil = fotoPerfilBase64
                    )
                    
                    RetrofitClient.instance.updateUser(updatedUser)
                    UserSession.setUser(updatedUser)
                    currentUser.value = updatedUser
                    isEditMode.value = false
                }
            } catch (e: Exception) { 
                e.printStackTrace()
            }
        }
    }
}
