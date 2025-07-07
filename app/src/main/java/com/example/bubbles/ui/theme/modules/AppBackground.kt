package com.example.bubbles.ui.theme.modules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.bubbles.mainmenu.components.Snowfall
import com.example.bubbles.utils.bubbleColors

@Composable
fun AppBackground(
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = contentAlignment
    ){
        Snowfall(bubbleColors = bubbleColors)
        content()
    }
}