package com.iesfernandoaguilar.solsonafuentes.appmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.iesfernandoaguilar.solsonafuentes.appmobile.ui.screens.GrupoScreen
import com.iesfernandoaguilar.solsonafuentes.appmobile.ui.screens.LoginScreen
import com.iesfernandoaguilar.solsonafuentes.appmobile.ui.screens.auth.RegisterScreen
import com.iesfernandoaguilar.solsonafuentes.appmobile.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                var currentScreen by remember { mutableStateOf("login") }

                when (currentScreen) {
                    "login" -> LoginScreen(
                        onNavigateToHome = { currentScreen = "home" },
                        onNavigateToRegister = { currentScreen = "register" }
                    )
                    "register" -> RegisterScreen(
                        onBack = { currentScreen = "login" },
                        onRegisterSuccess = { currentScreen = "home" }
                    )
                    "home" -> GrupoScreen()
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
    AppTheme {
        Greeting("Android")
    }
}