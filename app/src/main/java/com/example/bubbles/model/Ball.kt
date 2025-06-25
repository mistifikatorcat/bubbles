package com.example.bubbles.model

import androidx.compose.ui.graphics.Color
import java.util.UUID


data class Ball(
    val id: String = UUID.randomUUID().toString(),
    val color : Color
)