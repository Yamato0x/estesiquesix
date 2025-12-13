package com.example.frontendandroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.frontendandroid.data.UserSession
import com.example.frontendandroid.ui.components.ProductCard
import com.example.frontendandroid.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
) {
    val products by viewModel.products
    val isLoading by viewModel.isLoading
    val user = UserSession.getUser()
    val snackbarMessage by viewModel.snackbarMessage
    
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearSnackbar()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Header
            Text(
                text = "Bienvenido, ${user?.nombreUsuario ?: "Gamer"}",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Featured Section
            Text(
                text = "Destacados",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.fillMaxWidth().wrapContentWidth())
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(products) { product ->
                        ProductCard(
                            product = product,
                            modifier = Modifier.width(200.dp),
                            onAddToCart = { selectedProduct ->
                                viewModel.addToCart(selectedProduct)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            
            // Explore Section
            Text(
                text = "Explora Más",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { navController.navigate("blogs") },
                    modifier = Modifier.weight(1f)
                ) { Text("Blogs") }
                
                Button(
                    onClick = { navController.navigate("about_us") },
                    modifier = Modifier.weight(1f)
                ) { Text("Nosotros") }
                
                Button(
                    onClick = { navController.navigate("contact") },
                    modifier = Modifier.weight(1f)
                ) { Text("Contacto") }
            }
        }
    }
}
