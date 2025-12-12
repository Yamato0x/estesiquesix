package com.example.frontendandroid.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontendandroid.data.UserSession
import com.example.frontendandroid.data.model.Cart
import com.example.frontendandroid.data.network.RetrofitClient
import kotlinx.coroutines.launch
import java.math.BigDecimal

class CartViewModel : ViewModel() {
    var cartItems = mutableStateOf<List<Cart>>(emptyList())
    var total = mutableStateOf(BigDecimal.ZERO)
    var snackbarMessage = mutableStateOf<String?>(null)
    var showSuccessDialog = mutableStateOf(false)
    var pointsEarned = mutableStateOf(0)
    
    // User ID is retrieved from session
    private val userId = UserSession.getUser()?.userid

    init {
        refreshCart()
    }

    fun refreshCart() {
        viewModelScope.launch {
            try {
                userId?.let {
                    cartItems.value = RetrofitClient.instance.getCartItems(it)
                    total.value = RetrofitClient.instance.getCartTotal(it)
                }
            } catch (e: Exception) {
                snackbarMessage.value = "Error al cargar carrito: ${e.message}"
            }
        }
    }

    fun clearCart(fromShake: Boolean = false) {
          viewModelScope.launch {
            try {
                userId?.let { uid ->
                    RetrofitClient.instance.clearCart(uid)
                    refreshCart()
                    if(fromShake) snackbarMessage.value = "¡Carrito vaciado al agitar!"
                    else snackbarMessage.value = "Carrito vaciado"
                }
            } catch (e: Exception) {
                 snackbarMessage.value = "Error: ${e.message}" // Fixed assignment
            }
        }
    }

    fun decrementItem(productId: Long) {
         viewModelScope.launch {
            try {
                userId?.let {
                    RetrofitClient.instance.decrementCart(it, productId)
                    refreshCart()
                }
            } catch (e: Exception) {
                snackbarMessage.value = "Error: ${e.message}"
            }
        }
    }

    fun incrementItem(productId: Long) {
         viewModelScope.launch {
            try {
                userId?.let {
                    RetrofitClient.instance.addToCart(it, productId, 1)
                    refreshCart()
                }
            } catch (e: Exception) {
                snackbarMessage.value = "Error: ${e.message}"
            }
        }
    }

    fun checkout() {
        viewModelScope.launch {
            try {
                userId?.let {
                    val points = RetrofitClient.instance.checkout(it)
                    refreshCart()
                    pointsEarned.value = points
                    showSuccessDialog.value = true
                }
            } catch (e: Exception) {
                snackbarMessage.value = "Error al realizar compra: ${e.message}"
            }
        }
    }

    fun dismissSuccessDialog() {
        showSuccessDialog.value = false
    }

    fun clearSnackbar() {
        snackbarMessage.value = null
    }
}
