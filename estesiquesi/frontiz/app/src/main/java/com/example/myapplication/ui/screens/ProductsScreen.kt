package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.UserSession
import com.example.myapplication.data.model.Product
import com.example.myapplication.data.network.RetrofitClient
import com.example.myapplication.ui.components.ConfirmationDialog
import com.example.myapplication.ui.components.ProductCard
import com.example.myapplication.ui.components.ProductDialog
import kotlinx.coroutines.launch

@Composable
fun ProductsScreen(navController: NavController) {
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var searchQuery by remember { mutableStateOf("") }
    var showProductDialog by remember { mutableStateOf(false) }
    var productToEdit by remember { mutableStateOf<Product?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var productToDelete by remember { mutableStateOf<Product?>(null) }
    var snackbarMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    val isAdmin = UserSession.isAdmin()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(searchQuery) {
        try {
            if (searchQuery.isBlank()) {
                products = RetrofitClient.instance.getProducts()
            } else {
                products = RetrofitClient.instance.searchProducts(searchQuery)
            }
        } catch (e: Exception) {
            snackbarMessage = "Error al cargar productos: ${e.message}"
        }
    }

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let {
            snackbarHostState.showSnackbar(it)
            snackbarMessage = null
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            if (isAdmin) {
                FloatingActionButton(
                    onClick = {
                        productToEdit = null
                        showProductDialog = true
                    }
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Crear Producto")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar productos") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(products) { product ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ProductCard(
                                product = product,
                                modifier = Modifier.weight(1f),
                                onAddToCart = { selectedProduct ->
                                    scope.launch {
                                        try {
                                            val userId = UserSession.getUser()?.userid
                                            if (userId != null && selectedProduct.idproducto != null) {
                                                RetrofitClient.instance.addToCart(
                                                    userId = userId,
                                                    productId = selectedProduct.idproducto,
                                                    quantity = 1
                                                )
                                                snackbarMessage = "Producto agregado al carrito"
                                            } else {
                                                snackbarMessage = "Error: Usuario no identificado"
                                            }
                                        } catch (e: Exception) {
                                            snackbarMessage = "Error al agregar: ${e.message}"
                                        }
                                    }
                                }
                            )
                            if (isAdmin) {
                                Column {
                                    IconButton(onClick = {
                                        productToEdit = product
                                        showProductDialog = true
                                    }) {
                                        Icon(Icons.Filled.Edit, contentDescription = "Editar")
                                    }
                                    IconButton(onClick = {
                                        productToDelete = product
                                        showDeleteDialog = true
                                    }) {
                                        Icon(Icons.Filled.Delete, contentDescription = "Borrar")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Product Dialog
    if (showProductDialog) {
        ProductDialog(
            product = productToEdit,
            onDismiss = { showProductDialog = false },
            onSave = { product ->
                scope.launch {
                    try {
                        UserSession.getUser()?.userid?.let { userId ->
                            if (product.idproducto == null) {
                                RetrofitClient.instance.createProduct(product, userId)
                            } else {
                                RetrofitClient.instance.updateProduct(product, userId)
                            }
                            // Refresh products
                            products = RetrofitClient.instance.getProducts()
                        }
                        showProductDialog = false
                    } catch (e: Exception) {
                        // Handle error
                    }
                }
            }
        )
    }

    // Delete Confirmation Dialog
    if (showDeleteDialog && productToDelete != null) {
        ConfirmationDialog(
            title = "Confirmar Eliminación",
            message = "¿Estás seguro de que deseas eliminar el producto '${productToDelete?.nombreproducto}'?",
            onConfirm = {
                scope.launch {
                    try {
                        UserSession.getUser()?.userid?.let { userId ->
                            RetrofitClient.instance.deleteProduct(productToDelete!!.idproducto!!, userId)
                            // Refresh products
                            products = RetrofitClient.instance.getProducts()
                        }
                        showDeleteDialog = false
                        productToDelete = null
                    } catch (e: Exception) {
                        // Handle error
                    }
                }
            },
            onDismiss = {
                showDeleteDialog = false
                productToDelete = null
            }
        )
    }
}
