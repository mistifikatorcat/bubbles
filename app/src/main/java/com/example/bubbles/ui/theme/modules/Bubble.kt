package com.example.bubbles.ui.theme.modules

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.Canvas
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.clickable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun Bubble(color: Color, onCLick: () -> Unit) {
    Canvas(
        modifier = Modifier
            .size(37.dp)
            .padding(2.dp)
            .clickable { onCLick()}
    ) {
        if (size.minDimension <= 0f) return@Canvas

        val radius = size.minDimension / 2f

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(color.lighten(0.3f), color.darken(0.3f)),
                center = center,
                radius = radius * 1.2f
            ),
            radius = radius,
            center = center
        )

        // Subtle border stroke
        drawCircle(
            color = Color.Black.copy(alpha = 0.45f),
            radius = radius,
            center = center,
            style = Stroke(width = 3f)
        )

        drawArc(
            color = Color.White.copy(alpha = 0.18f),
            startAngle = -110f,
            sweepAngle = 100f,
            useCenter = false,
            topLeft = center - Offset(radius * 0.7f, radius * 0.7f),
            size = Size(radius * 1.4f, radius * 1.0f),
            style = Stroke(width = 4f)
        )

    }
}

fun Color.lighten(amount: Float): Color =
    copy(red = (red + amount).coerceAtMost(1f), green = (green + amount).coerceAtMost(1f), blue = (blue + amount).coerceAtMost(1f))

fun Color.darken(amount: Float): Color =
    copy(red = (red - amount).coerceAtLeast(0f), green = (green - amount).coerceAtLeast(0f), blue = (blue - amount).coerceAtLeast(0f))
