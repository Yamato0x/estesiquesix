package com.example.frontendandroid.data.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class Product(
    @SerializedName("idproducto")
    val idproducto: Long? = null,
    @SerializedName("nombreproducto")
    val nombreproducto: String,
    @SerializedName("precioproducto")
    val precioproducto: BigDecimal,
    val categoria: String? = null,
    @SerializedName("imagen_url")
    val imagen_url: String? = null,
    @SerializedName("precio_puntos")
    val precio_puntos: Int? = null
)
