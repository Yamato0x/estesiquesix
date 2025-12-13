package com.example.frontendandroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun BlogScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "BLOGOSFERA GAMER",
            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 4.sp
            ),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        BlogCard(
            title = "EL FUTURO DEL ANIME",
            imageUrl = "https://images.unsplash.com/photo-1578632767115-351597cf2477?q=80&w=1000&auto=format&fit=crop",
            onClick = { navController.navigate("blog1") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        BlogCard(
            title = "REVOLUCIÓN ESPORTS",
            imageUrl = "https://images.unsplash.com/photo-1542751371-adc38448a05e?q=80&w=1000&auto=format&fit=crop",
            onClick = { navController.navigate("blog2") }
        )
    }
}

@Composable
fun BlogCard(
    title: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            // Gradient Overlay for text readability
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.8f)
                            ),
                            startY = 100f
                        )
                    )
            )
            
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp
                ),
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(24.dp)
            )
        }
    }
}

@Composable
fun Blog1Screen() {
    BlogPostLayout(
        title = "EL FUTURO DEL ANIME Y LA TECNOLOGÍA",
        imageUrl = "https://images.unsplash.com/photo-1578632767115-351597cf2477?q=80&w=1000&auto=format&fit=crop",
        content = """
            La industria del anime está experimentando una revolución tecnológica sin precedentes en este 2024.
            
            FUSIÓN 2D Y 3D
            Las nuevas tecnologías de renderizado permiten fusionar el 2D tradicional con entornos 3D inmersivos. Estudios como Ufotable y MAPPA están liderando esta carga, utilizando Unreal Engine para crear fondos dinámicos que reaccionan a la iluminación en tiempo real.
            
            DISTRIBUCIÓN GLOBAL
            El acceso simultáneo (Simulcast) ha eliminado las barreras geográficas. Hoy, un estreno en Tokyo se ve al mismo tiempo en Santiago, creando una comunidad global sincronizada.
            
            IA EN LA ANIMACIÓN
            Aunque controversial, la asistencia de IA para interpolación de cuadros (in-betweening) está permitiendo a los animadores enfocarse más en los keyframes artísticos y menos en el trabajo repetitivo, elevando la calidad visual promedio de las producciones.
        """.trimIndent()
    )
}

@Composable
fun Blog2Screen() {
    BlogPostLayout(
        title = "LA ERA DORADA DE LOS ESPORTS",
        imageUrl = "https://images.unsplash.com/photo-1542751371-adc38448a05e?q=80&w=1000&auto=format&fit=crop",
        content = """
            Los deportes electrónicos han dejado de ser un nicho de sótano para convertirse en un fenómeno de estadios llenos.
            
            RENDIMIENTO EXTREMO
            En Level-Up Gamer entendemos que cada milisegundo cuenta. La diferencia entre un monitor de 60Hz y uno de 240Hz puede ser la diferencia entre la victoria y la derrota en títulos como Valorant o CS2.
            
            PROFESIONALIZACIÓN
            Los atletas de eSports de hoy tienen regímenes de entrenamiento, psicólogos deportivos y nutricionistas. Es una disciplina de alto rendimiento que requiere el mejor hardware.
            
            EL EQUIPAMIENTO DEFINE AL GANADOR
            Periféricos con latencia ultrabaja, sensores ópticos de 30,000 DPI y switches magnéticos analógicos son el nuevo estándar. No te quedes atrás en la carrera armamentista digital.
        """.trimIndent()
    )
}

@Composable
fun BlogPostLayout(
    title: String,
    imageUrl: String,
    content: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        // Hero Image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            // Gradient at bottom of image to blend with content
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, MaterialTheme.colorScheme.background)
                        )
                    )
            )
        }

        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            HorizontalDivider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = content,
                style = MaterialTheme.typography.bodyLarge.copy(
                    lineHeight = 28.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
