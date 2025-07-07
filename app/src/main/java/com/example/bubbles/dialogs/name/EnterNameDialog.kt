package com.example.bubbles.dialogs.name

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bubbles.mainmenu.components.MenuButton

@Composable
fun EnterNameDialog(
    score: Int,
    rank: Int,
    initialName: String,
    onSave: (String) -> Unit,
){
    var name by remember { mutableStateOf("") }

    LaunchedEffect(initialName) {
        if (initialName.isNotBlank()){
            name = initialName
        }
    }

    val titleText = if (rank == 1) "New Record" else "New High Score"

    AlertDialog(
        onDismissRequest = {
            onSave("Player")
        },
        title = {
            Text(titleText)
        },
        text = {
            Column {
                Text("Your final score is $score")
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = {name = it},
                    label = { Text("Enter your name")},
                    singleLine = true,
                )
            }
        },
        confirmButton = {
            MenuButton(
                text = "Save",
                onClick = {onSave(name.ifBlank { "Player" })}
            )
        }
    )
}