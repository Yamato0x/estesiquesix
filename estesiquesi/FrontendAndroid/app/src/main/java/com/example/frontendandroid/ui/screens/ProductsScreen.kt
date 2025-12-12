package com.example.frontendandroid.ui.screens

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frontendandroid.ui.components.ConfirmationDialog
import com.example.frontendandroid.ui.components.ProductCard
import com.example.frontendandroid.ui.components.ProductDialog
import com.example.frontendandroid.ui.viewmodel.ProductsViewModel

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel = viewModel()
) {
    val products by viewModel.products
    val searchQuery by viewModel.searchQuery
    val showProductDialog by viewModel.showProductDialog
    val productToEdit by viewModel.productToEdit
    val showDeleteDialog by viewModel.showDeleteDialog
    val productToDelete by viewModel.productToDelete
    val snackbarMessage by viewModel.snackbarMessage
    
    val isAdmin = viewModel.isAdmin
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearSnackbar()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            if (isAdmin) {
                FloatingActionButton(
                    onClick = { viewModel.startCreateProduct() }
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
                onValueChange = { viewModel.onSearchQueryChanged(it) },
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
                                    viewModel.addToCart(selectedProduct)
                                }
                            )
                            if (isAdmin) {
                                Column {
                                    IconButton(onClick = {
                                        viewModel.startEditProduct(product)
                                    }) {
                                        Icon(Icons.Filled.Edit, contentDescription = "Editar")
                                    }
                                    IconButton(onClick = {
                                        viewModel.startDeleteProduct(product)
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
            onDismiss = { viewModel.cancelDialogs() },
            onSave = { product ->
                viewModel.saveProduct(product)
            }
        )
    }

    // Delete Confirmation Dialog
    if (showDeleteDialog && productToDelete != null) {
        ConfirmationDialog(
            title = "Confirmar Eliminación",
            message = "¿Estás seguro de que deseas eliminar el producto '${productToDelete?.nombreproducto}'?",
            onConfirm = {
                viewModel.deleteProduct()
            },
            onDismiss = {
                viewModel.cancelDialogs()
            }
        )
    }
}
