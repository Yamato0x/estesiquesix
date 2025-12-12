package com.example.myapplication.ui.screens

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import com.example.myapplication.data.UserSession
import com.example.myapplication.data.network.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen() {
    val user = UserSession.getUser()
    var isEditMode by remember { mutableStateOf(false) }
    var nombreCompleto by remember { mutableStateOf(user?.nombreCompleto ?: "") }
    var correo by remember { mutableStateOf(user?.correo ?: "") }
    var nombreUsuario by remember { mutableStateOf(user?.nombreUsuario ?: "") }

    var profileImage by remember { 
        mutableStateOf<Bitmap?>(
            if (!user?.foto_perfil.isNullOrEmpty()) {
                try {
                    val decodedString = Base64.decode(user!!.foto_perfil, Base64.DEFAULT)
                    BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                } catch (e: Exception) { null }
            } else { null }
        ) 
    }
    val scope = rememberCoroutineScope()

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        profileImage = bitmap
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Perfil de Usuario", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        // Profile Image
        if (profileImage != null) {
            Image(
                bitmap = profileImage!!.asImageBitmap(),
                contentDescription = "Profile Picture",
                modifier = Modifier.size(120.dp)
            )
        } else {
            Box(modifier = Modifier.size(120.dp), contentAlignment = Alignment.Center) {
                Text("Sin foto")
            }
        }
        Button(onClick = { 
            cameraLauncher.launch(null)
        }) {
            Text("Tomar Foto")
        }
        Spacer(modifier = Modifier.height(24.dp))

        if (isEditMode) {
            // Edit Mode - Show TextFields
            TextField(
                value = nombreCompleto,
                onValueChange = { nombreCompleto = it },
                label = { Text("Nombre Completo") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = nombreUsuario,
                onValueChange = { nombreUsuario = it },
                label = { Text("Nombre de Usuario") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        scope.launch {
                            try {
                                user?.let {
                                    // Convert Bitmap to Base64
                                    var fotoPerfilBase64: String? = user.foto_perfil
                                    profileImage?.let { bitmap ->
                                        val byteArrayOutputStream = ByteArrayOutputStream()
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
                                        val byteArray = byteArrayOutputStream.toByteArray()
                                        fotoPerfilBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT)
                                    }

                                    val updatedUser = it.copy(
                                        nombreCompleto = nombreCompleto,
                                        correo = correo,
                                        nombreUsuario = nombreUsuario,
                                        foto_perfil = fotoPerfilBase64
                                    )
                                    RetrofitClient.instance.updateUser(updatedUser)
                                    UserSession.setUser(updatedUser)
                                    isEditMode = false
                                }
                            } catch (e: Exception) {
                                // Handle error
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Guardar Cambios")
                }
                OutlinedButton(
                    onClick = {
                        // Reset values and exit edit mode
                        nombreCompleto = user?.nombreCompleto ?: ""
                        correo = user?.correo ?: ""
                        nombreUsuario = user?.nombreUsuario ?: ""
                        isEditMode = false
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }
            }
        } else {
            // View Mode - Display user info
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Nombre: ${user?.nombreCompleto ?: "N/A"}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Usuario: ${user?.nombreUsuario ?: "N/A"}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Correo: ${user?.correo ?: "N/A"}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "RUT: ${user?.rut ?: "N/A"}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Rol: ${user?.rol ?: "N/A"}", style = MaterialTheme.typography.bodyLarge)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { isEditMode = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Editar Perfil")
            }
        }
    }
}
