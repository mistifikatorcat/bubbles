package com.example.bubbles.mainmenu.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.random.Random


data class SnowBubble(
    var x: Float,
    var y: Float,
    val radius: Float,
    val speed: Float,
    val color: Color,
    val alpha: Float
)

@Composable
fun Snowfall(
    modifier: Modifier = Modifier,
    bubbleColors: List<Color>,
    bubbleCount: Int = 30
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val density = LocalDensity.current
    val screenHeightPx = with(density) { screenHeight.toPx()}
    val screenWidthPx = with(density) { screenWidth.toPx()}

    val bubbles = remember {
        List(bubbleCount) {
            SnowBubble(
                x = Random.nextFloat() * screenWidthPx,
                y = Random.nextFloat() * screenHeightPx,
                radius = Random.nextInt(14,56).toFloat(),
                speed = Random.nextFloat() * 1.2f + 0.4f,
                color = bubbleColors.random(),
                alpha = Random.nextFloat() * 0.3f + 0.1f
            )
        }
    }

    val tick = remember { mutableStateOf(0L) }
    LaunchedEffect(Unit) {
        while (true){
            delay(16)
            tick.value = System.currentTimeMillis()
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val forceRecompose = tick.value

        bubbles.forEach { bubble ->
            drawCircle(
                color = bubble.color.copy(alpha = bubble.alpha),
                radius = bubble.radius,
                center = Offset(bubble.x, bubble.y)
            )

            bubble.y += bubble.speed

            if (bubble.y > size.height){
                bubble.y = -bubble.radius
                bubble.x = Random.nextFloat() * size.width
            }
        }
    }
}