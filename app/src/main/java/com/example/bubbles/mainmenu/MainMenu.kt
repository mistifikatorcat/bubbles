package com.example.bubbles.mainmenu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bubbles.R
import com.example.bubbles.mainmenu.components.MenuButton
import com.example.bubbles.ui.theme.modules.AppBackground
import com.example.bubbles.utils.GameVersion
import com.example.bubbles.viewmodel.GameViewModel

@Composable
fun MainMenuView(
    viewModel: GameViewModel,
    onStartGame: () -> Unit,
    onShowAbout: () -> Unit = {},
    onShowSettings: () -> Unit = {},
    onShowHiScores: () -> Unit = {}
){

    var isStartGameMenuOpen by remember { mutableStateOf(false) }

    AppBackground{
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .wrapContentSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(56.dp)
                        .padding(end = 12.dp),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = "Bubbles",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            if (isStartGameMenuOpen){
                Box(
                    contentAlignment = Alignment.Center
                ){
                    Column(
                        modifier = Modifier
                            .background(
                                Color.Transparent,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Start Game", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(16.dp))

                        if (viewModel.hasSavedGame()){
                            MenuButton("Continue") {
                                viewModel.restoreGame()
                                isStartGameMenuOpen = false
                                onStartGame()
                            }
                            MenuButton("New Game") {
                                viewModel.startNewGame()
                                isStartGameMenuOpen = false
                                onStartGame()
                            }
                        } else {
                            MenuButton("New Game") {
                                viewModel.startNewGame()
                                isStartGameMenuOpen = false
                                onStartGame()
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        MenuButton("Cancel"){ isStartGameMenuOpen = false }
                    }
                }
            } else{
                MenuButton(text = "Start Game"){
                    isStartGameMenuOpen = true
                }
                MenuButton(text = "About", onClick = onShowAbout)
                MenuButton(text = "High Scores", onClick = onShowHiScores)
                MenuButton(text = "Settings", onClick = onShowSettings)
            }

            Spacer(Modifier.height(40.dp))
            Text("Version ${GameVersion.VERSION}", fontSize = 12.sp, color = Color.Red)
            Text("Created by Daniel Evgrafov", fontSize = 12.sp, color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f))
        }
    }
}