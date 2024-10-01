package com.example.behavioraldatacollection.ui.screens.handwriting

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlin.math.atan2

@Composable
fun HandwritingScreen(navController: NavController) {
    val handwritingUseCase = HandwritingUseCase()
    var strokes by remember { mutableStateOf(listOf<Offset>()) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Box for handwriting gestures
        Box(
            modifier = Modifier
                .weight(1f)  // Let the box take available space but not overlay the button
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectDragGestures { change, _ ->
                        strokes = strokes + change.position
                        handwritingUseCase.addHandwritingData(
                            positions = strokes.map { it.x to it.y },
                            timestamps = strokes.map { System.currentTimeMillis() },
                            pressure = List(strokes.size) { 0.5f }, // Placeholder for pressure,
                            strokeID = strokes.size,
                            touchAngle = calculateStrokeAngle(strokes),
                            touchSpeed = 0.5f
                        )
                    }
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                for (i in 1 until strokes.size) {
                    drawLine(
                        color = androidx.compose.ui.graphics.Color.Black,
                        start = strokes[i - 1],
                        end = strokes[i],
                        strokeWidth = 5f
                    )
                }
            }
        }

        // Spacer for separation
        Spacer(modifier = Modifier.height(20.dp))

        // Button for navigation
        Button(
            onClick = { navController.navigate("home") },
            modifier = Modifier.padding(48.dp)  // Adding padding for better visibility
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

fun calculateStrokeAngle(strokes: List<Offset>): Float {
    if (strokes.size < 2) return 0f // Not enough points to calculate angle

    val startX = strokes[strokes.size - 2].x
    val startY = strokes[strokes.size - 2].y
    val endX = strokes.last().x
    val endY = strokes.last().y

    // Calculate angle in degrees
    val angle = atan2((endY - startY).toDouble(), (endX - startX).toDouble()) * (180 / Math.PI)
    return angle.toFloat()
}