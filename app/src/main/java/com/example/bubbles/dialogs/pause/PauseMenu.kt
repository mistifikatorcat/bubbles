package com.example.bubbles.dialogs.pause

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.bubbles.mainmenu.components.MenuButton

@Composable
fun PauseMenu(
    onResume: () -> Unit,
    onReturnToTitleScreen: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f), RoundedCornerShape(16.dp))
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Paused", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)

            Spacer(modifier = Modifier.height(24.dp))

           MenuButton(text = "Resume") {
               onResume()
           }

            Spacer(modifier = Modifier.height(24.dp))

            MenuButton("Return to Main Menu") {
                onReturnToTitleScreen()
            }
        }
    }
}