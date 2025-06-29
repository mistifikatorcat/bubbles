package com.example.bubbles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bubbles.About.AboutScreen
import com.example.bubbles.mainmenu.MainMenuView
import com.example.bubbles.settings.SettingsScreen
import com.example.bubbles.ui.theme.BubbleTheme
import com.example.bubbles.ui.theme.ThemeViewModel
import com.example.bubbles.ui.theme.modules.GameScreen
import com.example.bubbles.viewmodel.GameViewModel
import com.example.bubbles.viewmodel.GameViewModelFactory


sealed class Screen{
    object MainMenu : Screen()
    object Game : Screen()
    object About: Screen()
    object Settings: Screen()
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val appTheme by themeViewModel.theme.collectAsState()

            BubbleTheme(appTheme = appTheme) {
                var currentScreen by remember { mutableStateOf<Screen>(Screen.MainMenu)}
                val context = LocalContext.current.applicationContext
                val gameViewModel: GameViewModel = viewModel(factory = GameViewModelFactory(context))


                when (currentScreen){
                    Screen.MainMenu -> MainMenuView(
                        viewModel = gameViewModel,
                        onStartGame = {currentScreen = Screen.Game},
                        onShowAbout = {currentScreen = Screen.About},
                        onShowSettings = {currentScreen = Screen.Settings}
                    )

                     Screen.Game -> GameScreen(
                        viewModel = gameViewModel,
                        onBackToMenu = { currentScreen = Screen.MainMenu }
                    )


                    Screen.About -> AboutScreen(
                        onBack = {currentScreen = Screen.MainMenu}
                    )

                     Screen.Settings -> SettingsScreen(
                        current = appTheme,
                        onThemeSelected = {themeViewModel.setTheme(it)},
                        onBack = {currentScreen = Screen.MainMenu}
                    )

                }

            }
        }
    }
}
