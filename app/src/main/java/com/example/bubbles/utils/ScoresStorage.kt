package com.example.bubbles.utils

import android.content.Context
import android.os.Build
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.bubbles.scores.HighScore
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "high_scores")

val HIGH_SCORES_KEY = stringPreferencesKey("high_scores")
val LAST_NAME_KEY = stringPreferencesKey("last_name")

val moshi = Moshi.Builder()
    .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
    .build()
val highScoreListAdapter = moshi.adapter<List<HighScore>>(Types.newParameterizedType(List::class.java,
    HighScore::class.java))


suspend fun saveHighScore(context: Context, newName: String, newScore: Int){
    val prefs = context.dataStore.data.first()
    val json = prefs[HIGH_SCORES_KEY] ?: "[]"

    val existingScores = highScoreListAdapter.fromJson(json) ?: emptyList()

    val nowDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.now().toString()
    } else {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }


    val updatedList = (existingScores + HighScore(newName, nowDate, newScore))
        .sortedByDescending { it.score }
        .take(3)

    val updatedJson = highScoreListAdapter.toJson(updatedList)

    context.dataStore.edit { it[HIGH_SCORES_KEY] = updatedJson }
}

suspend fun loadHighScores(context: Context): List<HighScore>{
    val prefs = context.dataStore.data.first()
    val json = prefs[HIGH_SCORES_KEY] ?: "[]"
    return highScoreListAdapter.fromJson(json) ?: emptyList()
}

suspend fun calculatePlayerRank(context: Context, score: Int): Int? {
    val scores = loadHighScores(context)

    if (scores.size >= 3 && score <= scores.last().score) {
        return null // not a high score
    }

    val tempList = (scores + HighScore("", "", score))
        .sortedByDescending { it.score }

    return tempList.indexOfFirst { it.score == score } + 1
}


suspend fun saveLastNameUsed(context: Context, name: String){
    context.dataStore.edit { prefs ->
        prefs[LAST_NAME_KEY] = name
    }
}

suspend fun loadLastNameUsed(context: Context): String{
    val prefs = context.dataStore.data.first()
    return prefs[LAST_NAME_KEY] ?: ""
}