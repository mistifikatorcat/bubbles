package com.example.bubbles.dialogs.gameover

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.bubbles.mainmenu.components.MenuButton

@Composable
fun GameOverDialog(
    score: Int,
    reset: () -> Unit
){

    AlertDialog(
        onDismissRequest = {},
        containerColor = (MaterialTheme.colorScheme.surface),
        title = { Text("Game over") },
        text = { Text("Your final score: $score") },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                MenuButton("Restart") {
                    reset()
                }
            }
        }
    )
}