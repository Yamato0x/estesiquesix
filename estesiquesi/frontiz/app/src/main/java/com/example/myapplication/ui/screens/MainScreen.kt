package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = listOf(
        Screen.Home,
        Screen.Products,
        Screen.Cart,
        Screen.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.resourceId) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Products.route) { ProductsScreen(navController) }
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

sealed class Screen(val route: String, val resourceId: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Home : Screen("home", "Home", Icons.Filled.Home)
    object Products : Screen("products", "Productos", Icons.Filled.List)
    object Cart : Screen("cart", "Carrito", Icons.Filled.ShoppingCart)
    object Profile : Screen("profile", "Perfil", Icons.Filled.Person)
    object Blogs : Screen("blogs", "Blogs", Icons.Filled.List)
    object Blog1 : Screen("blog1", "Anime", Icons.Filled.List)
    object Blog2 : Screen("blog2", "Gaming", Icons.Filled.List)
    object AboutUs : Screen("about_us", "Nosotros", Icons.Filled.Person)
    object Contact : Screen("contact", "Contacto", Icons.Filled.Person)
}
