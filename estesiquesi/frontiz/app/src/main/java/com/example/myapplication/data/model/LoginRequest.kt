package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("nombre_usuario")
    val nombreUsuario: String,
    val contrasena: String
)
