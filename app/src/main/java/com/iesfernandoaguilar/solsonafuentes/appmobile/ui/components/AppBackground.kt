package com.iesfernandoaguilar.solsonafuentes.appmobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.iesfernandoaguilar.solsonafuentes.appmobile.ui.theme.Colors

@Composable
fun AppBackground(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = if (darkTheme) {
                        listOf(Colors.DarkBlue, Colors.DarkPurple)
                    } else {
                        listOf(Colors.PrimaryBlue, Colors.PrimaryPurple)
                    }
                )
            )
    ) {
        content()
    }
}