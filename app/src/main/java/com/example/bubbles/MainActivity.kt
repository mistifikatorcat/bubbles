package com.example.bubbles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bubbles.mainmenu.MainMenuView
import com.example.bubbles.ui.theme.GameScreen
import com.example.bubbles.viewmodel.GameViewModel
import com.example.bubbles.viewmodel.GameViewModelFactory


sealed class Screen{
    object MainMenu : Screen()
    object Game : Screen()
//    object Instructions: Screen()
//    object Settings: Screen()
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                var currentScreen by remember { mutableStateOf<Screen>(Screen.MainMenu)}
                val context = LocalContext.current.applicationContext
                val gameViewModel: GameViewModel = viewModel(factory = GameViewModelFactory(context))


                when (currentScreen){
                    is Screen.MainMenu -> MainMenuView(
                        viewModel = gameViewModel,
                        onStartGame = {currentScreen = Screen.Game}
                    )

                    is Screen.Game -> GameScreen(
                        viewModel = gameViewModel,
                        onBackToMenu = { currentScreen = Screen.MainMenu }
                    )

//                    is Screen.Instructions -> ()
//
//                    is Screen.Settings -> ()

                }

            }
        }
    }
}
