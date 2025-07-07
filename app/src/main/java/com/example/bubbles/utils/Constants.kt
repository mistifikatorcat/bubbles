package com.example.bubbles.utils

import androidx.compose.ui.graphics.Color

const val ROW_COUNT = 15
const val COL_COUNT = 10

object GameVersion {
    const val VERSION = "0.4.2release"
}

val bubbleColors = listOf(
    Color.Red,
    Color.Green,
    Color.Blue,
    Color.Yellow,
    Color.Magenta
)

fun nameFromColor(color: Color): String {
    val index = bubbleColors.indexOf(color)
    return if (index != -1) index.toString() else "null"
}

fun colorFromName(name: String): Color {
    return name.toIntOrNull()?.let { bubbleColors.getOrNull(it) } ?: Color.Gray
}
