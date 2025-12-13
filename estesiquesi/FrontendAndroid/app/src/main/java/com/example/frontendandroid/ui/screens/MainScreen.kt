package com.example.frontendandroid.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.frontendandroid.data.UserSession

sealed class Screen(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector? = null) {
    object Login : Screen("login", "Login")
    object Register : Screen("register", "Registro")
    object Home : Screen("home", "Inicio", Icons.Filled.Home)
    object Products : Screen("products", "Productos", Icons.Filled.List)
    object Cart : Screen("cart", "Carrito", Icons.Filled.ShoppingCart)
    object Profile : Screen("profile", "Perfil", Icons.Filled.Person)
    
    // Sub-screens
    object Blogs : Screen("blogs", "Blogs")
    object Blog1 : Screen("blog1", "Blog Anime")
    object Blog2 : Screen("blog2", "Blog eSports")
    object AboutUs : Screen("about_us", "Nosotros")
    object Contact : Screen("contact", "Contacto")
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    // Determine start destination based on session, but for now simple login
    val startDestination = if (UserSession.isLoggedIn()) Screen.Home.route else Screen.Login.route

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Define sub-routes that belong to "Home" visually
    val homeSubRoutes = listOf(
        Screen.Blogs.route,
        Screen.Blog1.route,
        Screen.Blog2.route,
        Screen.AboutUs.route,
        Screen.Contact.route
    )

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Login.route && currentRoute != Screen.Register.route) {
                NavigationBar {
                    val items = listOf(Screen.Home, Screen.Products, Screen.Cart, Screen.Profile)
                    items.forEach { screen ->
                        
                        // Special selection logic for Home to encompass sub-screens
                        val isSelected = if (screen == Screen.Home) {
                            currentRoute == Screen.Home.route || currentRoute in homeSubRoutes
                        } else {
                            currentRoute == screen.route
                        }

                        NavigationBarItem(
                            icon = { Icon(screen.icon!!, contentDescription = screen.title) },
                            label = { Text(screen.title) },
                            selected = isSelected,
                            onClick = {
                                if (screen == Screen.Home && currentRoute in homeSubRoutes) {
                                    // FORCE RESET TO HOME: Don't save state, just go there
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Home.route) { inclusive = true }
                                        launchSingleTop = true
                                    }
                                } else {
                                    // STANDARD NAVIGATION
                                    navController.navigate(screen.route) {
                                        popUpTo(Screen.Home.route) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Login.route) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate(Screen.Register.route)
                    }
                )
            }
            composable(Screen.Register.route) {
                RegisterScreen(
                    onRegisterSuccess = {
                         navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Register.route) { inclusive = true }
                        }
                    },
                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route)
                    }
                )
            }
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Products.route) { ProductsScreen() }
            composable(Screen.Cart.route) { CartScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }
            
            composable(Screen.Blogs.route) { BlogScreen(navController) }
            composable(Screen.Blog1.route) { Blog1Screen() }
            composable(Screen.Blog2.route) { Blog2Screen() }
            composable(Screen.AboutUs.route) { AboutUsScreen() }
            composable(Screen.Contact.route) { ContactScreen() }
        }
    }
}
