package com.example.bubbles.scores

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bubbles.scores.components.EmptyPlace
import com.example.bubbles.scores.components.PodiumPlace

@Composable
fun ScoresScreen(
    highScores: List<HighScore>,
    onBack: () -> Unit
){

    val maxScore = highScores.maxOfOrNull { it.score }?.toFloat() ?: 1f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp), // nice margin
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween // 👈 evenly spaced
    ) {
        Text(
            "🏆 Top-3 High Scores",
            style = MaterialTheme.typography.headlineMedium
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp), // good height for podium
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            listOf(1, 0, 2).forEach { index ->
                val score = highScores.getOrNull(index)
                if (score != null) {
                    PodiumPlace(
                        place = index + 1,
                        score = score,
                        maxScore = maxScore
                    )
                } else {
                    EmptyPlace(index + 1)
                }
            }
        }

        Button(
            onClick = onBack,
            modifier = Modifier.padding(bottom = 8.dp) // 👈 ensures it’s not flush at bottom
        ) {
            Text("Back")
        }
    }


}