package com.example.frontendandroid.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontendandroid.data.UserSession
import com.example.frontendandroid.data.model.Product
import com.example.frontendandroid.data.network.RetrofitClient
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products: State<List<Product>> = _products

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading
    
    // UI Feedback
    private val _snackbarMessage = mutableStateOf<String?>(null)
    val snackbarMessage: State<String?> = _snackbarMessage

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val fetchedProducts = RetrofitClient.instance.getProducts()
                _products.value = fetchedProducts
            } catch (e: Exception) {
                _snackbarMessage.value = "Error al cargar productos: ${e.message}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            try {
                val userId = UserSession.getUser()?.userid
                if (userId != null && product.idproducto != null) {
                    RetrofitClient.instance.addToCart(userId, product.idproducto, 1)
                     _snackbarMessage.value = "¡${product.nombreproducto} agregado al carrito!"
                } else {
                    _snackbarMessage.value = "Error: Debes iniciar sesión"
                }
            } catch (e: Exception) {
                 _snackbarMessage.value = "Error al agregar: ${e.message}"
                e.printStackTrace()
            }
        }
    }
    
    fun clearSnackbar() {
        _snackbarMessage.value = null
    }
}
