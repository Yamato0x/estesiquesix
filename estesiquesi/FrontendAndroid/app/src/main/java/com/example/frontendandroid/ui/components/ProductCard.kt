package com.example.frontendandroid.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.frontendandroid.data.model.Product
import java.text.NumberFormat
import java.util.Locale
import coil.compose.AsyncImage
import com.example.frontendandroid.data.UserSession
import com.example.frontendandroid.data.network.RetrofitClient
import kotlinx.coroutines.launch

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
            
            product.precio_puntos?.let { points ->
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$points Puntos",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                val scope = rememberCoroutineScope()
                var showDialog by remember { mutableStateOf(false) }
                var dialogMessage by remember { mutableStateOf("") }
                var dialogTitle by remember { mutableStateOf("") }

                if (product.precio_puntos != null && product.precio_puntos > 0) {
                    Button(
                        onClick = {
                            val userPoints = UserSession.getUser()?.puntos ?: 0
                            if (userPoints >= product.precio_puntos) {
                                scope.launch {
                                    try {
                                        UserSession.getUser()?.userid?.let { uid ->
                                             val updatedUser = RetrofitClient.instance.redeemProduct(uid, product.idproducto!!)
                                             UserSession.setUser(updatedUser) // Update session with new points
                                             dialogTitle = "Canje Exitoso"
                                             dialogMessage = "Has canjeado ${product.nombreproducto}. Te quedan ${updatedUser.puntos} puntos."
                                             showDialog = true
                                        }
                                    } catch (e: Exception) {
                                        dialogTitle = "Error"
                                        dialogMessage = "Error al canjear: ${e.message}"
                                        showDialog = true
                                    }
                                }
                            } else {
                                dialogTitle = "Puntos Insuficientes"
                                dialogMessage = "No tienes suficientes puntos. Necesitas ${product.precio_puntos} y tienes $userPoints."
                                showDialog = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Canjear")
                    }
                }

                Button(
                    onClick = { onAddToCart(product) }
                ) {
                    Text("Agregar")
                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text(dialogTitle) },
                        text = { Text(dialogMessage) },
                        confirmButton = {
                            Button(onClick = { showDialog = false }) {
                                Text("Aceptar")
                            }
                        }
                    )
                }
            }
        }
    }
}

private fun formatCurrency(amount: java.math.BigDecimal): String {
    val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    return format.format(amount)
}
