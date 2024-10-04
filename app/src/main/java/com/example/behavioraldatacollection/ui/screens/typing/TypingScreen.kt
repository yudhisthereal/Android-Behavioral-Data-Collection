package com.example.behavioraldatacollection.ui.screens.typing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.behavioraldatacollection.data.model.TypingPattern
import com.example.behavioraldatacollection.ui.custom.keyboard.KeyboardView
import com.example.behavioraldatacollection.ui.custom.textfield.CustomTextField

@Composable
fun TypingScreen(navController: NavController) {
    // Keep inputText as mutable state for UI updates
    val inputText = remember { mutableStateOf(TextFieldValue(text = "")) }
    val typingPattern = remember { mutableStateOf(TypingPattern(0, 0f, 0f)) }

    // Use regular variables for other values
    var pressTimestamp = 0L
    var lastKeyReleaseTime: Long = System.currentTimeMillis()
    var firstKeyEvent = true
    var typingStartTimestamp = 0L
    var flightTime = 0L
    var totalFlightTime = 0L
    var totalKeyStrokes = 0
    var pauseDuration = 0L
    var nPauses = 0
    var typingSpeed = 0f
    val flightTimeThreshold = 10000L // 10 seconds for a long pause
    val pauseThreshold = 1000L // 1 second for a short pause

    val keystrokeUseCase = KeystrokeUseCase()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(bottom = 64.dp),
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
                                pressTimestamp = System.currentTimeMillis()

                                // Capture typing start timestamp on the first character press
                                if (inputText.value.text.isEmpty()) {
                                    typingStartTimestamp = pressTimestamp
                                }

                                val currentPauseDuration = pressTimestamp - lastKeyReleaseTime

                                // reset flight time (the pause has been long enough)
                                if (currentPauseDuration > flightTimeThreshold) {
                                    firstKeyEvent = true
                                }

                                // Update Pauses
                                else if (currentPauseDuration > pauseThreshold) {
                                    nPauses++
                                    pauseDuration += flightTime
                                }

                                if (firstKeyEvent) {
                                    firstKeyEvent = false
                                } else {
                                    flightTime = pressTimestamp - lastKeyReleaseTime
                                    totalFlightTime += flightTime
                                }

                                // Consume the DOWN event so it doesn't propagate to child components
                                event.changes.forEach { it.consume() }
                            }

                            // Handle UP event
                            if (event.changes.any { !it.pressed }) {
                                val keyPressed = inputText.value.text.lastOrNull() ?: continue

                                val releaseTimestamp = System.currentTimeMillis()
                                val holdTime = releaseTimestamp - pressTimestamp

                                // Update total keystrokes
                                totalKeyStrokes += 1

                                // Calculate typing speed in characters per minute
                                val totalTime = (releaseTimestamp - typingStartTimestamp) / 1000f // in seconds
                                typingSpeed = if (totalTime > 0) {
                                    (totalKeyStrokes / totalTime) * 60 // convert to CPM
                                } else {
                                    0f
                                }

                                // Update typing pattern
                                typingPattern.value.typingSpeed = typingSpeed.toInt()
                                typingPattern.value.pauseDuration = (pauseDuration.toFloat() / nPauses.toFloat()) / 1000f
                                typingPattern.value.transitionDuration = if (totalKeyStrokes > 1) totalFlightTime.toFloat() / (totalKeyStrokes - 1) else 0f

                                // Send data to keystroke use case
                                keystrokeUseCase.addKeystroke(
                                    key = keyPressed,
                                    pressTimestamp = pressTimestamp,
                                    releaseTimestamp = releaseTimestamp,
                                    holdTime = holdTime,
                                    flightTime = flightTime,
                                    typingPattern = typingPattern.value.copy()
                                )

                                lastKeyReleaseTime = releaseTimestamp

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
