package com.example.frontendandroid.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frontendandroid.ui.components.ProductCard
import com.example.frontendandroid.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
) {
    val products by viewModel.products

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Bienvenido a Level-Up Gamer",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Productos Destacados",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(products) { product ->
                ProductCard(
                    product = product,
                    modifier = Modifier.width(200.dp),
                    onAddToCart = { selectedProduct ->
                        // Hardcoded userId for demo purposes
                        viewModel.addToCart(userId = 1, product = selectedProduct)
                    }
                )
            }
        }


        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Explora Más",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { navController.navigate("blogs") },
                modifier = Modifier.weight(1f)
            ) {
                Text("Blogs")
            }
            Button(
                onClick = { navController.navigate("about_us") },
                modifier = Modifier.weight(1f)
            ) {
                Text("Nosotros")
            }
            Button(
                onClick = { navController.navigate("contact") },
                modifier = Modifier.weight(1f)
            ) {
                Text("Contacto")
            }
        }
    }
}
