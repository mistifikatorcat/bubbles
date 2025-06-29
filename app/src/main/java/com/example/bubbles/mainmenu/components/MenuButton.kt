package com.example.bubbles.mainmenu.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuButton(text: String, onClick: () -> Unit){
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .width(200.dp)
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            contentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
        )
    ) {
        Text(text = text)
    }
}