package com.example.frontendandroid.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.frontendandroid.R

@Composable
fun BlogScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Nuestros Blogs",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("blog1") },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text("El Futuro del Anime", style = MaterialTheme.typography.titleMedium)
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("blog2") },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text("Revolución eSports", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun Blog1Screen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Image placeholder - in a real app add drawables
        // Image(painter = painterResource(id = R.drawable.anime_blog), contentDescription = null)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "El Futuro del Anime y la Tecnología",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "La industria del anime está experimentando una revolución tecnológica sin precedentes. " +
                   "Desde la integración de CGI avanzado hasta la distribución global simultánea, " +
                   "los fanáticos nunca habían tenido tanto acceso a contenido de alta calidad.\n\n" +
                   "Las nuevas tecnologías de renderizado permiten fusionar el 2D tradicional con entornos 3D " +
                   "inmersivos, creando experiencias visuales que definen el género en 2024.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun Blog2Screen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        
        Text(
            text = "La Era Dorada de los eSports",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Los deportes electrónicos han dejado de ser un nicho para convertirse en un fenómeno global. " +
                   "Estadios llenos, premios millonarios y una audiencia que supera a muchos deportes tradicionales.\n\n" +
                   "En nuestra tienda apoyamos esta pasión con el mejor equipamiento. Desde teclados mecánicos " +
                   "hasta monitores de 240Hz, entendemos que cada milisegundo cuenta para la victoria.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
