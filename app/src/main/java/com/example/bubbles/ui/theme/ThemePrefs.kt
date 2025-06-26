package com.example.bubbles.ui.theme

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.themeDataStore by preferencesDataStore("theme_prefs")

private val THEME_KEY = intPreferencesKey("selected_theme")

class ThemePrefs(private val context: Context) {

    val theme: Flow<AppTheme> = context.themeDataStore.data.map { prefs ->
        prefs[THEME_KEY]?.let { ordinal ->
            AppTheme.values().getOrNull(ordinal)
        } ?: AppTheme.System
    }

    suspend fun setTheme (theme: AppTheme){
        context.themeDataStore.edit { it[THEME_KEY] = theme.ordinal }
    }
}