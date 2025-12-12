package com.example.myapplication.ui.screens

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
import com.example.myapplication.data.UserSession
import com.example.myapplication.data.model.Cart
import com.example.myapplication.data.network.RetrofitClient
import kotlinx.coroutines.launch
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
fun CartScreen() {
    var cartItems by remember { mutableStateOf<List<Cart>>(emptyList()) }
    var total by remember { mutableStateOf(BigDecimal.ZERO) }
    var snackbarMessage by remember { mutableStateOf<String?>(null) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var pointsEarned by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val userId = UserSession.getUser()?.userid

    fun refreshCart() {
        scope.launch {
            try {
                userId?.let {
                    cartItems = RetrofitClient.instance.getCartItems(it)
                    total = RetrofitClient.instance.getCartTotal(it)
                }
            } catch (e: Exception) {
                snackbarMessage = "Error al cargar carrito: ${e.message}"
            }
        }
    }

    LaunchedEffect(Unit) {
        refreshCart()
    }

    // Shake Detection
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val accelerometer = remember { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }
    var lastShakeTime by remember { mutableStateOf(0L) }

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
                    
                    if (gForce > 2.7f) { // Shake threshold
                        val currentTime = System.currentTimeMillis()
                        if (currentTime - lastShakeTime > 500) { // Debounce 500ms
                            lastShakeTime = currentTime
                            if (cartItems.isNotEmpty()) {
                                scope.launch {
                                    try {
                                        userId?.let { uid ->
                                            RetrofitClient.instance.clearCart(uid)
                                            refreshCart()
                                            snackbarMessage = "¡Carrito vaciado al agitar!"
                                        }
                                    } catch (e: Exception) {
                                        // Ignore or log
                                    }
                                }
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
            snackbarMessage = null
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
                                        scope.launch {
                                            try {
                                                userId?.let {
                                                    RetrofitClient.instance.decrementCart(it, item.producto.idproducto!!)
                                                    refreshCart()
                                                }
                                            } catch (e: Exception) {
                                                snackbarMessage = "Error: ${e.message}"
                                            }
                                        }
                                    }) {
                                        Icon(Icons.Filled.Delete, contentDescription = "Decrementar")
                                    }
                                    Text(
                                        text = "${item.cantidad}",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    IconButton(onClick = {
                                        scope.launch {
                                            try {
                                                userId?.let {
                                                    RetrofitClient.instance.addToCart(it, item.producto.idproducto!!, 1)
                                                    refreshCart()
                                                }
                                            } catch (e: Exception) {
                                                snackbarMessage = "Error: ${e.message}"
                                            }
                                        }
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
                onClick = {
                    scope.launch {
                        try {
                            userId?.let {
                                val points = RetrofitClient.instance.checkout(it)
                                refreshCart()
                                pointsEarned = points
                                showSuccessDialog = true
                            }
                        } catch (e: Exception) {
                            snackbarMessage = "Error al realizar compra: ${e.message}"
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = cartItems.isNotEmpty()
            ) {
                Text("Realizar Compra")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    scope.launch {
                        try {
                            userId?.let {
                                RetrofitClient.instance.clearCart(it)
                                refreshCart()
                                snackbarMessage = "Carrito vaciado"
                            }
                        } catch (e: Exception) {
                            snackbarMessage = "Error: ${e.message}"
                        }
                    }
                },
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
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Compra Exitosa") },
            text = { Text("Se ha realizado la compra con éxito.\nHas ganado $pointsEarned puntos.") },
            confirmButton = {
                Button(onClick = { showSuccessDialog = false }) {
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
