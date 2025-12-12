package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class Cart(
    @SerializedName("idcarrito")
    val idcarrito: Long? = null,
    val producto: Product,
    val cantidad: Int
)
