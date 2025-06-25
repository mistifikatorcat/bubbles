package com.example.bubbles.pause

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun PauseMenu(
    onResume: () -> Unit,
    onReturnToTitleScreen: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White.copy(alpha = 0.5f))
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(16.dp))
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Paused", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onResume) {
                Text("Resume")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onReturnToTitleScreen){
                Text("Return to Main Menu")
            }
        }
    }
}