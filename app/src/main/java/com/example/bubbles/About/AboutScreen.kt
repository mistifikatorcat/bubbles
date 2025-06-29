package com.example.bubbles.About

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bubbles.utils.GameVersion


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About", fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            SectionTitle("How to Play")
            BulletPoint("Tap a group of 2 or more same-colored bubbles to pop them.")
            BulletPoint("The more you pop in one tap, the more points you earn.")
            BulletPoint("The board doesn't refill — plan your moves.")
            BulletPoint("Game ends when no more moves are possible.")
            BulletPoint("You can pause and resume the game anytime.")

            Spacer(modifier = Modifier.height(24.dp))

            SectionTitle("About the Game")
            Text(
                text = """
                    This version of Bubble Breaker is a modern tribute to the original Windows Mobile classic — a simple, relaxing, and ad-free experience.

                    The idea for this game came during quiet hours in a shelter, where connectivity was limited and distractions were many. It helped pass the time and stay calm — and maybe now, it can do the same for you.
                    
                    No ads. No in-app purchases. Just you and the bubbles.
                """.trimIndent(),
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 20.sp,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(32.dp))


            Text(
                text = "Version ${GameVersion.VERSION}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun BulletPoint(text: String) {
    Row(modifier = Modifier.padding(bottom = 6.dp)) {
        Text("• ", fontSize = 16.sp)
        Text(text, fontSize = 16.sp)
    }
}