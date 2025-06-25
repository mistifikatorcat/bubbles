package com.example.bubbles.utils

import android.content.Context
import com.example.bubbles.model.Ball

class GameRepository(context: Context) {
    private val prefs = context.getSharedPreferences("Bubbles", Context.MODE_PRIVATE)

    fun save( score: Int, grid: List<List<Ball?>>){
        val flatGrid = grid.flatten().map  { it?.let { nameFromColor(it.color) } ?: "null" }
        prefs.edit()
            .putInt("score", score)
            .putString("grid", flatGrid.joinToString (","))
            .apply()
    }

    fun load(): Pair<Int, List<List<Ball?>>>? {
        val score = prefs.getInt("score", -1)
        val gridString = prefs.getString("grid", null) ?: return null
        if (score == -1) return null

        val flat = gridString.split(",")
        val grid = flat.chunked(COL_COUNT).map { row ->
            row.map { colorName ->
                if (colorName == "null") null
                else Ball(color = colorFromName(colorName))
            }
        }

        return score to grid
    }

    fun hasSave(): Boolean {
        return prefs.contains("score") && prefs.contains("grid")
    }

    fun clear(){
        prefs.edit().clear().apply()
    }
}