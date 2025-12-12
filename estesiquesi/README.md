# 🎮 Level-Up Gamer

**Level-Up Gamer** es una plataforma integral de comercio electrónico para hardware y periféricos gaming. El sistema se compone de una aplicación móvil nativa Android de última generación y un backend robusto basado en microservicios Spring Boot, diseñado para ofrecer rendimiento, escalabilidad y una experiencia de usuario premium.

---

## � Arquitectura del Proyecto

El sistema sigue una arquitectura cliente-servidor desacoplada:

### 📱 Frontend: `FrontendAndroid`
Una aplicación móvil nativa construida desde cero con las tecnologías más recientes del ecosistema Android.

*   **Lenguaje**: Kotlin.
*   **UI Toolkit**: Jetpack Compose (Declarativo).
*   **Patrón de Arquitectura**: **MVVM (Model-View-ViewModel)**.
    *   *ViewModel*: Gestiona el estado de la UI y la lógica de negocio, sobreviviendo a cambios de configuración.
    *   *View (Compose)*: Pantallas reactivas que observan el estado del ViewModel.
    *   *Model*: Repositorios y fuentes de datos.
*   **Networking**: Retrofit 2 + Gson para consumo de API REST asíncrono.
*   **Gestión de Imágenes**: Coil para carga, caché y transformación de imágenes.
*   **Navegación**: Jetpack Navigation Compose.
*   **Funcionalidades Nativas**:
    *   **Sensores (Acelerómetro)**: Detección de gestos físicos ("Shake" para limpiar carrito).
    *   **Cámara (CameraX/Result API)**: Captura y procesamiento de imágenes de perfil en tiempo real.

### ⚙️ Backend: `demo`
Servicio RESTful que centraliza la lógica de negocio y la persistencia de datos.

*   **Lenguaje**: Java 17+.
*   **Framework**: Spring Boot.
*   **Gestor de Dependencias**: Maven.
*   **Base de Datos**: **PostgreSQL** (Alojada en AWS RDS) para producción.
*   **ORM**: Hibernate / Spring Data JPA.
*   **Seguridad**: Validación de usuarios y manejo de sesiones.

---

## ✨ Características Principales

### 👤 Gestión de Usuarios
*   **Registro Seguro**: Validación en tiempo real de RUT Chileno (Algoritmo Módulo 11) y formatos de correo, preveniendo datos corruptos desde el origen.
*   **Autenticación**: Login persistente mediante Singleton de Sesión.
*   **Perfil de Usuario**: Edición de datos personales y actualización de avatar mediante cámara.

### 📦 Catálogo y Productos
*   **Listado Dinámico**: Scroll infinito y renderizado eficiente de listas de productos.
*   **Búsqueda**: Filtrado de productos en tiempo real contra la API.
*   **Panel de Administración (Rol ADMIN)**:
    *   Interfaz exclusiva para administradores.
    *   CRUD Completo: Crear, Editar y Eliminar productos del catálogo global.

### 🛒 Carrito de Compras Avanzado
*   **Lógica de Negocio Local**: Cálculo de totales y gestión de cantidades en el cliente.
*   **Integración de Hardware**:
    *   **"Shake to Clear"**: Algoritmo que utiliza el acelerómetro para detectar una sacudida del dispositivo (> 1.5G) y vaciar el carrito automáticamente.
*   **Checkout**: Procesamiento de compra y asignación de puntos de fidelidad.

---

## 🛠 Guía de Instalación y Ejecución

### Prerrequisitos
*   **JDK 17** o superior.
*   **Android Studio** (Versión recomendada: Ladybug o superior).
*   **IntelliJ IDEA** (Para el Backend).

### 1. Despliegue del Backend (`/demo`)
El backend debe estar ejecutándose para que la app móvil funcione.

1.  Navegar a la carpeta `estesiquesi/demo`.
2.  Si usas terminal:
    ```bash
    ./mvnw spring-boot:run
    ```
3.  Si usas IntelliJ IDEA: Ejecutar la clase `DemoApplication.java`.
4.  El servidor iniciará en el puerto **8080**.
    *   *Database*: Conectado automáticamente a instancia AWS RDS PostgreSQL.

### 2. Ejecución del Frontend (`/FrontendAndroid`)

1.  Abrir **Android Studio**.
2.  Seleccionar "Open" y buscar la carpeta `estesiquesi/FrontendAndroid`.
3.  Esperar la sincronización de Gradle.
4.  Seleccionar un emulador (Recomendado API 30+) o dispositivo físico.
    *   **Nota para Emulador**: La app apunta a `10.0.2.2:8080` (localhost del host).
    *   **Nota para Físico**: Asegúrese de que ambos dispositivos estén en la misma red y actualice la IP en `RetrofitClient.kt`.
5.  Presionar **Run (Shift+F10)**.

---

## 🧪 Testing

El proyecto cuenta con una batería de pruebas unitarias en el módulo Android para asegurar la calidad de componentes críticos.

*   `UserSessionTest`: Verifica la integridad de la sesión y roles.
*   `ValidatorsTest`: Pruebas exhaustivas para algoritmos de validación (RUT, Email, Passwords).
*   `ProductTest`: Validación de integridad de modelos.

Para ejecutar los tests:
```bash
cd FrontendAndroid
./gradlew testDebugUnitTest
```

---

## 👨‍� Autores
Proyecto desarrollado para la asignatura de Desarrollo de Aplicaciones Móviles.

*   **Equipo Level-Up Gamer**
