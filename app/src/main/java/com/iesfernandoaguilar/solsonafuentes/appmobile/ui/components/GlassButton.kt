package com.iesfernandoaguilar.solsonafuentes.appmobile.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.iesfernandoaguilar.solsonafuentes.appmobile.ui.theme.Colors

@Composable
fun GlassButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    val shape = RoundedCornerShape(12.dp)

    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(1.dp, Colors.GlassBorder),
        elevation = ButtonDefaults.buttonElevation(3.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Colors.AccentCyan.copy(alpha = 0.9f),
                            Colors.AccentLightPurple.copy(alpha = 0.9f)
                        )
                    ),
                    shape = shape
                )
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            content()
        }
    }
}