package com.example.myapplication.ui.screens

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
import com.example.myapplication.data.model.Product
import com.example.myapplication.data.network.RetrofitClient
import com.example.myapplication.ui.components.ProductCard
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: com.example.myapplication.ui.viewmodel.HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val products by viewModel.products
    // val isLoading by viewModel.isLoading // Can be used for loading indicator

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
