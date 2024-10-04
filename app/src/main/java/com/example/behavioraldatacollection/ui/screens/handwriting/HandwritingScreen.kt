package com.example.behavioraldatacollection.ui.screens.handwriting

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.behavioraldatacollection.data.model.TouchEventType

@Composable
fun HandwritingScreen(navController: NavController) {
    val handwritingUseCase = HandwritingUseCase()
    var strokes by remember { mutableStateOf(listOf<Pair<List<Offset>, Color>>()) }  // List of strokes (each stroke has points and a color)
    var currentStroke by remember { mutableStateOf(listOf<Offset>()) }  // Current stroke being drawn
    var selectedColor by remember { mutableStateOf(Color.Black) }  // Default color is black
    var strokeID by remember { mutableIntStateOf(0) }  // To track stroke IDs
    var lastTimeStamp by remember { mutableLongStateOf(-1L)} // To track point speed
    var nMaxPressurePoints by remember { mutableIntStateOf(0) }
    var pressureSupported by remember { mutableStateOf(true) }

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Canvas for handwriting gestures
        Canvas(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .pointerInput(Unit) {
                    awaitEachGesture {
                        // Wait for the first down event
                        val down = awaitFirstDown()
                        val position = down.position
                        val pressure = down.pressure

                        strokeID++  // Increment stroke ID for each new stroke
                        currentStroke = listOf(position)  // Start new stroke
                        addPointData(position, TouchEventType.DOWN, strokeID, -1L, pressure, handwritingUseCase)
                        lastTimeStamp = 0 // Reset timestamp
                        nMaxPressurePoints++

                        // Continue to handle movement
                        var lastPosition = position
                        while (true) {
                            val event = awaitPointerEvent() // Await the next pointer event
                            val changes = event.changes.firstOrNull() ?: continue
                            val currentPressure = changes.pressure

                            if (changes.changedToUp()) {
                                // Touch ended
                                addPointData(lastPosition, TouchEventType.UP, strokeID, -2L, currentPressure, handwritingUseCase)
                                strokes = strokes + Pair(currentStroke, selectedColor)
                                currentStroke = emptyList()  // Reset stroke
                                if (currentPressure == 1f) {
                                    nMaxPressurePoints++
                                }
                                break
                            } else if (changes.positionChange() != Offset.Zero) {
                                // Movement detected
                                currentStroke = currentStroke + changes.position  // Add to stroke
                                addPointData(changes.position, TouchEventType.MOVE, strokeID, lastTimeStamp, currentPressure, handwritingUseCase)
                                lastTimeStamp = System.currentTimeMillis()
                                lastPosition = changes.position
                                if (currentPressure == 1f) {
                                    nMaxPressurePoints++
                                }
                            }
                        }

                        if (nMaxPressurePoints > 30 && pressureSupported) {
                            pressureSupported = false
                            Toast.makeText(context, "Your device doesn't support Touch Pressure Sensitivity", Toast.LENGTH_LONG).show()
                        }
                    }
                }
        ) {
            // Draw previous strokes with their corresponding colors
            strokes.forEach { (stroke, color) ->
                for (i in 1 until stroke.size) {
                    drawLine(
                        color = color,
                        start = stroke[i - 1],
                        end = stroke[i],
                        strokeWidth = 5f
                    )
                }
            }
            // Draw current stroke
            for (i in 1 until currentStroke.size) {
                drawLine(
                    color = selectedColor,
                    start = currentStroke[i - 1],
                    end = currentStroke[i],
                    strokeWidth = 5f
                )
            }
        }

        // Spacer for separation
        Spacer(modifier = Modifier.height(20.dp))

        // Row for color picker
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val colors = listOf(Color.Black, Color.Red, Color.Green, Color.Blue)
            colors.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(color, shape = CircleShape)
                        .clickable {
                            selectedColor = color  // Change the selected color for the next stroke
                        }
                )
            }
        }

        // Spacer for separation
        Spacer(modifier = Modifier.height(20.dp))

        // Button for navigation
        Button(
            onClick = { navController.navigate("home") },
            modifier = Modifier.padding(48.dp)
        ) {
            Text("Back to Home")
        }
    }
}


@Preview
@Composable
fun HandwritingScreenPreview() {
    HandwritingScreen(navController = rememberNavController())
}