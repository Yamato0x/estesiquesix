package com.example.frontendandroid.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontendandroid.data.model.Product
import com.example.frontendandroid.data.network.RetrofitClient
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products: State<List<Product>> = _products

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

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
                // Handle error
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addToCart(userId: Long, product: Product) {
        viewModelScope.launch {
            try {
                RetrofitClient.instance.addToCart(userId, product.idproducto!!, 1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
