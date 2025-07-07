package com.example.bubbles.ui.theme.modules


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bubbles.R
import com.example.bubbles.dialogs.gameover.GameOverDialog
import com.example.bubbles.dialogs.name.EnterNameDialog
import com.example.bubbles.dialogs.pause.PauseMenu
import com.example.bubbles.utils.calculatePlayerRank
import com.example.bubbles.viewmodel.GameViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GameScreen(viewModel: GameViewModel = viewModel(), onBackToMenu: () -> Unit, onShowScores: () -> Unit) {
    val grid by viewModel.state.collectAsState()
    val score by viewModel.score.collectAsState()
    val isGameOver by viewModel.isGameOver.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    var isPaused by remember { mutableStateOf(false) }
    var showNameDialog by remember {mutableStateOf(false)}
    var playerRank by remember {mutableStateOf<Int?>(null)}
    val lastName by viewModel.lastNameUsed.collectAsState()
    val lastNameLoaded by viewModel.lastNameLoaded.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(isGameOver) {
        if (isGameOver){
            val rank = calculatePlayerRank(context, score)
            if (rank != null) {
                playerRank = rank
                showNameDialog = true
                viewModel.loadLastName(context)
            }

        }
    }


    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver {
            _, event ->
            if (event == Lifecycle.Event.ON_STOP){
                viewModel.saveGame()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Outer box is now the true root with proper layering
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.surface)) {

        // Game UI (z-index = 0)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
//                .padding(10.dp)
                .zIndex(0f)
                .padding(top = 64.dp)
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Bubbles", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.secondary)
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { isPaused = true }, modifier = Modifier.size(32.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.burger),
                        contentDescription = "Pause",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }

                Text("Score: $score", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.secondary)

                IconButton(onClick = { viewModel.resetGame() }, modifier = Modifier.size(32.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.restart_icon),
                        contentDescription = "Restart",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            for ((y, row) in grid.withIndex()) {
                Row {
                    for ((x, ball) in row.withIndex()) {
                        if (ball != null) {
                            Bubble(color = ball.color) {
                                if (!isPaused) {
                                    viewModel.onCellClick(x, y)
                                }
                            }
                        } else {
                            Spacer(modifier = Modifier
                                .size(37.dp)
                                .padding(2.dp))
                        }
                    }
                }
            }

            if (isGameOver && !showNameDialog) {

                GameOverDialog(
                    score = score,
                    reset = { viewModel.resetGame() }
                )

            }
            if (showNameDialog && playerRank != null){
                EnterNameDialog(
                    score = score,
                    rank = playerRank!!,
                    initialName = lastName,
                    onSave = {
                        name ->
                        showNameDialog = false
                        viewModel.saveHighScore(context, name, score)
                        viewModel.resetGame()
                        onShowScores()
                    }
                )
            }
        }

        // Pause dim background layer (z-index = 1)
        if (isPaused) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xAAFFFFFF))
                    .pointerInput(Unit) {} // block input
                    .zIndex(1f)
            )
        }

        // Pause menu (z-index = 2)
        if (isPaused) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(2f),
                contentAlignment = Alignment.Center
            ) {
                PauseMenu(
                    onResume = { isPaused = false },
                    onReturnToTitleScreen = {
                        viewModel.saveGame()
                        onBackToMenu()
                    }
                )
            }
        }
    }
}
