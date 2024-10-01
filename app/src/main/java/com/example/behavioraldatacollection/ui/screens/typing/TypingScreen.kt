package com.example.behavioraldatacollection.ui.screens.typing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.behavioraldatacollection.ui.custom.keyboard.KeyboardView
import com.example.behavioraldatacollection.ui.custom.textfield.CustomTextField

@Composable
fun TypingScreen(navController: NavController) {
    // Keep inputText as mutable state for UI updates
    val inputText = remember { mutableStateOf(TextFieldValue(text = "")) }

    // Use regular variables for other values
    var pressTimestamp: Long = 0L
    var lastKeyReleaseTime: Long = System.currentTimeMillis()
    var firstKeyEvent: Boolean = true
    var flightTime: Long = 0L
    val flightTimeThreshold = 3000L // After three seconds, the event wouldn't be considered flight time

    val keystrokeUseCase = KeystrokeUseCase()

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(bottom=64.dp)
        ,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(textState = inputText)
        Spacer(modifier = Modifier.height(24.dp))
        KeyboardView(
            textFieldState = inputText,
            focusFirstKey = true,
            modifier = Modifier
                .shadow(8.dp)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()

                            // Handle DOWN event
                            if (event.changes.any { it.pressed }) {
//                                Toast.makeText(context, "Event: DOWN", Toast.LENGTH_SHORT).show()
                                pressTimestamp = System.currentTimeMillis()

                                if (pressTimestamp - lastKeyReleaseTime > flightTimeThreshold) {
                                    firstKeyEvent = true
                                }

                                if (firstKeyEvent) {
                                    firstKeyEvent = false
                                } else {
                                    flightTime = pressTimestamp - lastKeyReleaseTime
                                }

                                // Consume the DOWN event so it doesn't propagate to child components
                                event.changes.forEach { it.consume() }
                            }

                            // Handle UP event
                            if (event.changes.any { !it.pressed }) {
//                                Toast.makeText(context, "Event: UP", Toast.LENGTH_SHORT).show()
                                val keyPressed = inputText.value.text.lastOrNull() ?: continue

                                val releaseTimeStamp = System.currentTimeMillis()
                                val holdTime = releaseTimeStamp - pressTimestamp

                                keystrokeUseCase.addKeystroke(
                                    key = keyPressed,
                                    pressTimestamp = pressTimestamp,
                                    releaseTimestamp = releaseTimeStamp,
                                    holdTime = holdTime,
                                    flightTime = flightTime,
                                    typingPattern = inputText.value.text
                                )

                                lastKeyReleaseTime = releaseTimeStamp

                                // Consume the UP event
                                event.changes.forEach { it.consume() }
                            }
                        }
                    }
                },
            onAction = {}
        ) {}
    }
}

@Preview
@Composable
fun TypingScreenPreview() {
    TypingScreen(navController = rememberNavController())
}
