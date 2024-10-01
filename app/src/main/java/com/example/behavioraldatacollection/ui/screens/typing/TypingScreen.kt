package com.example.behavioraldatacollection.ui.screens.typing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.behavioraldatacollection.ui.custom.keyboard.KeyboardView
import com.example.behavioraldatacollection.ui.custom.textfield.CustomTextField

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TypingScreen(navController: NavController) {
    // Keep inputText as mutable state for UI updates
    var inputText by remember { mutableStateOf("") }

    // Use regular variables for other values
    var pressTimestamp: Long = 0L
    var lastKeyReleaseTime: Long = System.currentTimeMillis()
    var firstKeyEvent: Boolean = true
    var flightTime: Long = 0L
    val flightTimeThreshold = 3000L // After three seconds, the event wouldn't be considered flight time

    val keystrokeUseCase = KeystrokeUseCase()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
//            .pointerInteropFilter {
//                when (it.action) {
//                    android.view.MotionEvent.ACTION_DOWN -> {
//                        pressTimestamp = System.currentTimeMillis()
//
//                        if (pressTimestamp - lastKeyReleaseTime > flightTimeThreshold) {
//                            firstKeyEvent = true
//                        }
//
//                        if (firstKeyEvent) {
//                            firstKeyEvent = false
//                        } else {
//                            flightTime = pressTimestamp - lastKeyReleaseTime
//                        }
//                    }
//
//                    android.view.MotionEvent.ACTION_UP -> {
//                        val keyPressed =
//                            inputText.lastOrNull() ?: return@pointerInteropFilter false
//
//                        val releaseTimeStamp = System.currentTimeMillis()
//                        val holdTime = releaseTimeStamp - pressTimestamp
//
//                        keystrokeUseCase.addKeystroke(
//                            key = keyPressed,
//                            pressTimestamp = pressTimestamp,
//                            releaseTimestamp = releaseTimeStamp,
//                            holdTime = holdTime,
//                            flightTime = flightTime,
//                            typingPattern = inputText
//                        )
//
//                        lastKeyReleaseTime = releaseTimeStamp
//                    }
//
//                    else -> {
//
//                    }
//                }
//                true
//            }
        ,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            val username = remember { mutableStateOf(TextFieldValue(text = "")) }
            CustomTextField(textState = username)
            Spacer(modifier = Modifier.height(24.dp))
            KeyboardView(
                textFieldState = username,
                focusFirstKey = true,
                modifier = Modifier.shadow(8.dp),
                onAction = {

                }
            ) {}
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview
@Composable
fun TypingScreenPreview() {
    TypingScreen(navController = rememberNavController())
}
