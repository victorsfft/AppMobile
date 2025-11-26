package com.iesfernandoaguilar.solsonafuentes.appmobile.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.iesfernandoaguilar.solsonafuentes.appmobile.data.repositories.LoginRepository
import com.iesfernandoaguilar.solsonafuentes.appmobile.network.SocketConnection
import com.iesfernandoaguilar.solsonafuentes.appmobile.ui.components.GlassButton
import com.iesfernandoaguilar.solsonafuentes.appmobile.ui.components.GlassCard
import com.iesfernandoaguilar.solsonafuentes.appmobile.ui.components.LoginTextField
import com.iesfernandoaguilar.solsonafuentes.appmobile.ui.components.PasswordTextField
import com.iesfernandoaguilar.solsonafuentes.appmobile.ui.theme.Colors
import com.iesfernandoaguilar.solsonafuentes.appmobile.ui.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val socketConnection = remember { SocketConnection }
    val loginRepository = remember { LoginRepository(socketConnection) }
    val viewModel = remember { LoginViewModel(loginRepository) }

    val state by viewModel.state.collectAsState()

    // Fondo degradado
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Colors.PrimaryBlue, Colors.PrimaryPurple)
                )
            )
    ) {
        // Capa de símbolos
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        )

        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Título TaskFlow
            Text(
                text = "TaskFlow",
                style = MaterialTheme.typography.displayMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 25.dp)
            )

            // Contenedor login
            GlassCard(
                modifier = Modifier
                    .widthIn(max = 420.dp)
                    .heightIn(max = 550.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    Text(
                        text = "Iniciar Sesión",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    // Campos
                    LoginTextField(
                        value = state.email,
                        onValueChange = viewModel::onEmailChange,
                        label = "Correo electrónico *",
                        icon = Icons.Outlined.Email,
                        keyboardType = KeyboardType.Email,
                        modifier = Modifier.fillMaxWidth()
                    )

                    PasswordTextField(
                        value = state.password,
                        onValueChange = viewModel::onPasswordChange,
                        label = "Contraseña *",
                        icon = Icons.Outlined.Lock,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Mensaje informativo
                    if (state.informationText.isNotEmpty()) {
                        Text(
                            text = state.informationText,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            maxLines = 2
                        )
                    }

                    // Botón
                    GlassButton(
                        onClick = viewModel::login,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        enabled = state.email.isNotEmpty() &&
                                state.password.isNotEmpty() &&
                                !state.isLoading
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Acceder", color = Color.White)
                        }
                    }

                    // Enlaces
                    Text(
                        text = "¿Has olvidado la contraseña?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Colors.AccentCyan,
                        modifier = Modifier
                            .clickable { /* TODO: Implementar recuperación */ }
                    )

                    Text(
                        text = "¿No tienes una cuenta?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Colors.AccentCyan,
                        modifier = Modifier
                            .clickable(onClick = onNavigateToRegister)
                    )
                }
            }
        }
    }

    // Navegación al éxito
    LaunchedEffect(state.loginSuccess) {
        if (state.loginSuccess) {
            onLoginSuccess()
        }
    }
}