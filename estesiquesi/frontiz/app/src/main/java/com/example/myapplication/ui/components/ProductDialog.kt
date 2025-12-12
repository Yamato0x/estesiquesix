package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.data.model.Product
import java.math.BigDecimal

@Composable
fun ProductDialog(
    product: Product? = null,
    onDismiss: () -> Unit,
    onSave: (Product) -> Unit
) {
    var nombre by remember { mutableStateOf(product?.nombreproducto ?: "") }
    var precio by remember { mutableStateOf(product?.precioproducto?.toString() ?: "") }
    var categoria by remember { mutableStateOf(product?.categoria ?: "") }
    var imagenUrl by remember { mutableStateOf(product?.imagen_url ?: "") }
    var precioPuntos by remember { mutableStateOf(product?.precio_puntos?.toString() ?: "") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = if (product == null) "Crear Producto" else "Editar Producto",
                    style = MaterialTheme.typography.titleLarge
                )

                TextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del Producto") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = categoria,
                    onValueChange = { categoria = it },
                    label = { Text("Categoría") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = imagenUrl,
                    onValueChange = { imagenUrl = it },
                    label = { Text("URL de la Imagen") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = precioPuntos,
                    onValueChange = { precioPuntos = it },
                    label = { Text("Precio en Puntos (Opcional)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancelar")
                    }
                    Button(
                        onClick = {
                            try {
                                val newProduct = Product(
                                    idproducto = product?.idproducto,
                                    nombreproducto = nombre,
                                    precioproducto = BigDecimal(precio),
                                    categoria = categoria,
                                    imagen_url = imagenUrl,
                                    precio_puntos = precioPuntos.toIntOrNull()
                                )
                                onSave(newProduct)
                            } catch (e: Exception) {
                                // Handle invalid input
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}
