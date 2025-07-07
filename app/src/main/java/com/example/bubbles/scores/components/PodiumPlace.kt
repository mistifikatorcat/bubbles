package com.example.bubbles.scores.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bubbles.scores.HighScore

@Composable
fun PodiumPlace( place: Int, score: HighScore, maxScore: Float){
    val targetHeightRatio = score.score / maxScore

    var animatedRatio by remember { mutableStateOf(0f) }

    val animatedHeightRatio by animateFloatAsState(
        targetValue = animatedRatio,
        animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
    )

    LaunchedEffect(Unit) {
        animatedRatio = targetHeightRatio
    }


    val color = when (place){
        1 -> Color(0xFFFFD700)
        2 -> Color(0xFFC0C0C0)
        3 -> Color(0xFFCD7F32)
        else -> Color.Gray
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.height(400.dp).padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .width(40.dp),
            contentAlignment = Alignment.BottomCenter
        ){
        Box(
            modifier = Modifier
                .width(40.dp)
                .fillMaxHeight(animatedHeightRatio)
                .background(color, shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)),
            contentAlignment = Alignment.TopCenter
        ) {
            if (animatedRatio > 0f) {
                Text(
                    text = "${score.score}",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
        }
        Text(
            text = when (place){
                1 ->  "🥇"
                2 ->  "🥈"
                3 ->  "🥉"
                else -> ""
            },
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = score.name,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun EmptyPlace(place: Int){
PodiumPlace(place, HighScore("-", "", 0), maxScore = 1f)
}