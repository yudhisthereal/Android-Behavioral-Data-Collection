package com.example.behavioraldatacollection.screens.typing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun TypingScreen(navController: NavController) {
    var inputText by remember { mutableStateOf("") }
    var lastKeyReleaseTime by remember { mutableLongStateOf(System.currentTimeMillis()) }
    val keystrokeUseCase = TypingUseCase()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicTextField(
            value = inputText,
            onValueChange = { newValue ->
                val keyPressed = newValue.lastOrNull() ?: return@BasicTextField
                val releaseTimeStamp = System.currentTimeMillis()

                // Simulate release event after some delay
                inputText = newValue
                keystrokeUseCase.addKeystroke(
                    key = keyPressed,
                    pressTimestamp = releaseTimeStamp - 100,
                    releaseTimestamp = releaseTimeStamp,
                    holdTime = 100,
                    flightTime = releaseTimeStamp - lastKeyReleaseTime
                )
                lastKeyReleaseTime = releaseTimeStamp
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { navController.navigate("home") }) {
            Text("Back to Home")
        }
    }
}

@Preview
@Composable
fun TypingScreenPreview() {
    TypingScreen(navController = rememberNavController())
}
