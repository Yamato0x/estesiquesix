package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.model.Product
import java.text.NumberFormat
import java.util.Locale

import coil.compose.AsyncImage

@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier,
    onAddToCart: (Product) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            product.imagen_url?.let { url ->
                AsyncImage(
                    model = url,
                    contentDescription = product.nombreproducto,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Text(
                text = product.nombreproducto,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            product.categoria?.let { category ->
                Text(
                    text = category,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            Text(
                text = formatCurrency(product.precioproducto),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            androidx.compose.material3.Button(
                onClick = { onAddToCart(product) },
                modifier = Modifier.align(androidx.compose.ui.Alignment.End)
            ) {
                Text("Agregar")
            }
        }
    }
}

private fun formatCurrency(amount: java.math.BigDecimal): String {
    val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    return format.format(amount)
}
