package com.example.frontendandroid.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frontendandroid.ui.viewmodel.CartViewModel
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.ui.platform.LocalContext
import kotlin.math.sqrt

@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel()
) {
    val cartItems by viewModel.cartItems
    val total by viewModel.total
    val snackbarMessage by viewModel.snackbarMessage
    val showSuccessDialog by viewModel.showSuccessDialog
    val pointsEarned by viewModel.pointsEarned
    
    val snackbarHostState = remember { SnackbarHostState() }

    // Shake Detection
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val accelerometer = remember { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }
    var lastShakeTime by remember { mutableStateOf(0L) }

    // Force refresh when screen is entered
    LaunchedEffect(Unit) {
        viewModel.refreshCart()
    }

    DisposableEffect(Unit) {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    val x = it.values[0]
                    val y = it.values[1]
                    val z = it.values[2]
                    
                    val gX = x / SensorManager.GRAVITY_EARTH
                    val gY = y / SensorManager.GRAVITY_EARTH
                    val gZ = z / SensorManager.GRAVITY_EARTH
                    
                    val gForce = sqrt(gX * gX + gY * gY + gZ * gZ)
                    
                    if (gForce > 1.5f) { // Shake threshold lowered for emulator
                        val currentTime = System.currentTimeMillis()
                        if (currentTime - lastShakeTime > 500) { // Debounce 500ms
                            lastShakeTime = currentTime
                            if (cartItems.isNotEmpty()) {
                                viewModel.clearCart(fromShake = true)
                            }
                        }
                    }
                }
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

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
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(text = "Carrito de Compras", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text("El carrito está vacío", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(cartItems) { item ->
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.producto.nombreproducto,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = formatCurrency(item.producto.precioproducto),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    IconButton(onClick = {
                                        viewModel.decrementItem(item.producto.idproducto!!)
                                    }) {
                                        Icon(Icons.Filled.Delete, contentDescription = "Decrementar")
                                    }
                                    Text(
                                        text = "${item.cantidad}",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    IconButton(onClick = {
                                        viewModel.incrementItem(item.producto.idproducto!!)
                                    }) {
                                        Icon(Icons.Filled.Add, contentDescription = "Incrementar")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Total: ${formatCurrency(total)}",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.checkout() },
                modifier = Modifier.fillMaxWidth(),
                enabled = cartItems.isNotEmpty()
            ) {
                Text("Realizar Compra")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { viewModel.clearCart() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                enabled = cartItems.isNotEmpty()
            ) {
                Text("Vaciar Carrito")
            }
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissSuccessDialog() },
            title = { Text("Compra Exitosa") },
            text = { Text("Se ha realizado la compra con éxito.\nHas ganado $pointsEarned puntos.") },
            confirmButton = {
                Button(onClick = { viewModel.dismissSuccessDialog() }) {
                    Text("Aceptar")
                }
            }
        )
    }
}

private fun formatCurrency(amount: BigDecimal): String {
    val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    return format.format(amount)
}
