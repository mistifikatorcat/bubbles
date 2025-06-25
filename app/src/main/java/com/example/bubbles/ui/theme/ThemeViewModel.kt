package com.example.bubbles.ui.theme

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ThemeViewModel : ViewModel() {

    private val _theme = MutableStateFlow(AppTheme.Light)
    val theme: StateFlow<AppTheme> = _theme

    fun setTheme(theme: AppTheme) { _theme.value = theme }
}