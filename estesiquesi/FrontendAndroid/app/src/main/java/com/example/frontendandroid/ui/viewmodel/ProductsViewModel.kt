package com.example.frontendandroid.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontendandroid.data.UserSession
import com.example.frontendandroid.data.model.Product
import com.example.frontendandroid.data.network.RetrofitClient
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {
    val products = mutableStateOf<List<Product>>(emptyList())
    val searchQuery = mutableStateOf("")
    val showProductDialog = mutableStateOf(false)
    val productToEdit = mutableStateOf<Product?>(null)
    val showDeleteDialog = mutableStateOf(false)
    val productToDelete = mutableStateOf<Product?>(null)
    val snackbarMessage = mutableStateOf<String?>(null)
    
    val isAdmin = UserSession.isAdmin()

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            try {
                if (searchQuery.value.isBlank()) {
                    products.value = RetrofitClient.instance.getProducts()
                } else {
                    products.value = RetrofitClient.instance.searchProducts(searchQuery.value)
                }
            } catch (e: Exception) {
                snackbarMessage.value = "Error al cargar productos: ${e.message}"
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
        fetchProducts()
    }

    fun startCreateProduct() {
        productToEdit.value = null
        showProductDialog.value = true
    }

    fun startEditProduct(product: Product) {
        productToEdit.value = product
        showProductDialog.value = true
    }
    
    fun startDeleteProduct(product: Product) {
        productToDelete.value = product
        showDeleteDialog.value = true
    }
    
    fun cancelDialogs() {
        showProductDialog.value = false
        showDeleteDialog.value = false
        productToEdit.value = null
        productToDelete.value = null
    }

    fun saveProduct(product: Product) {
        viewModelScope.launch {
            try {
                UserSession.getUser()?.userid?.let { userId ->
                    if (product.idproducto == null) {
                        RetrofitClient.instance.createProduct(product, userId)
                    } else {
                        RetrofitClient.instance.updateProduct(product, userId)
                    }
                    fetchProducts()
                    cancelDialogs()
                }
            } catch (e: Exception) {
                snackbarMessage.value = "Error al guardar: ${e.message}"
            }
        }
    }

    fun deleteProduct() {
         viewModelScope.launch {
            try {
                UserSession.getUser()?.userid?.let { userId ->
                    productToDelete.value?.idproducto?.let { id ->
                        RetrofitClient.instance.deleteProduct(id, userId)
                        fetchProducts()
                        cancelDialogs()
                    }
                }
            } catch (e: Exception) {
                snackbarMessage.value = "Error al eliminar: ${e.message}"
            }
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            try {
                val userId = UserSession.getUser()?.userid
                if (userId != null && product.idproducto != null) {
                    RetrofitClient.instance.addToCart(
                        userId = userId,
                        productId = product.idproducto,
                        quantity = 1
                    )
                    snackbarMessage.value = "Producto agregado al carrito"
                } else {
                    snackbarMessage.value = "Error: Usuario no identificado"
                }
            } catch (e: Exception) {
                snackbarMessage.value = "Error al agregar: ${e.message}"
            }
        }
    }
    
    fun clearSnackbar() {
        snackbarMessage.value = null
    }
}
