package com.iesfernandoaguilar.solsonafuentes.appmobile.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Colors.PrimaryBlue,
            secondary = Colors.PrimaryPurple,
            surface = Colors.DarkSurface,
            onSurface = Color.White,
            background = Colors.DarkBlue
        )
    } else {
        lightColorScheme(
            primary = Colors.PrimaryBlue,
            secondary = Colors.PrimaryPurple,
            surface = Color.White,
            onSurface = Color.Black,
            background = Color.White
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}