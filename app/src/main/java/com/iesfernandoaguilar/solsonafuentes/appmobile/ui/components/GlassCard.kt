package com.iesfernandoaguilar.solsonafuentes.appmobile.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.iesfernandoaguilar.solsonafuentes.appmobile.ui.theme.Colors

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = RoundedCornerShape(20.dp)

    OutlinedCard(
        modifier = modifier,
        shape = shape,
        border = BorderStroke(1.dp, Colors.GlassBorder),
        colors = CardDefaults.outlinedCardColors(
            containerColor = Colors.GlassWhite
        ),
        onClick = onClick ?: {}
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            content = content
        )
    }
}