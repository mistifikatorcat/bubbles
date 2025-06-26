package com.example.bubbles.ui.theme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThemeViewModel(app: Application) : AndroidViewModel(app) {

    private val prefs = ThemePrefs(app)

    val theme: StateFlow<AppTheme> = prefs.theme
        .stateIn(viewModelScope, SharingStarted.Eagerly, AppTheme.System)

    fun setTheme(theme: AppTheme) = viewModelScope.launch {
        prefs.setTheme(theme)
    }
}