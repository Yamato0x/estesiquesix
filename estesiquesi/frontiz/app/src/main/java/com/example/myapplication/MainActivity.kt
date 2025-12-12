package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.screens.LoginScreen
import com.example.myapplication.ui.screens.MainScreen
import com.example.myapplication.ui.screens.RegisterScreen
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(
                        modifier = Modifier.padding(innerPadding),
                        onLoginSuccess = {
                            // Navigate to MainScreen
                            setContent {
                                MyApplicationTheme {
                                    MainScreen()
                                }
                            }
                        },
                        onNavigateToRegister = {
                            // Navigate to RegisterScreen
                            setContent {
                                MyApplicationTheme {
                                    RegisterScreen(
                                        modifier = Modifier.padding(innerPadding),
                                        onRegisterSuccess = {
                                            // Go back to Login
                                            setContent {
                                                MyApplicationTheme {
                                                    // Re-create MainActivity content or navigate back
                                                    // For simplicity in this demo structure without a top-level NavHost for auth:
                                                    val intent = android.content.Intent(this@MainActivity, MainActivity::class.java)
                                                    startActivity(intent)
                                                    finish()
                                                }
                                            }
                                        },
                                        onNavigateToLogin = {
                                            val intent = android.content.Intent(this@MainActivity, MainActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                        }
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}