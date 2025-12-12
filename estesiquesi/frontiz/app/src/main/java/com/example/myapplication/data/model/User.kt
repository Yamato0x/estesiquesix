package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("userid")
    val userid: Long? = null,
    @SerializedName("nombre_completo")
    val nombreCompleto: String,
    val rut: String,
    @SerializedName("nombre_usuario")
    val nombreUsuario: String,
    val contrasena: String,
    val correo: String,
    val rol: String,
    @SerializedName("puntos")
    val puntos: Int? = 0
)
