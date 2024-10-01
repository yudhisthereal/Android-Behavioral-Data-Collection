package com.example.behavioraldatacollection.ui.screens.handwriting

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlin.math.atan2

@Composable
fun HandwritingScreen(navController: NavController) {
    val handwritingUseCase = HandwritingUseCase()
    var strokes by remember { mutableStateOf(listOf<List<Offset>>()) }  // List of strokes (each stroke is a list of points)
    var currentStroke by remember { mutableStateOf(listOf<Offset>()) }  // Current stroke being drawn
    var selectedColor by remember { mutableStateOf(Color.Black) }  // Default color is black

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
                    detectDragGestures(
                        onDragStart = {
                            currentStroke = listOf(it)  // Start new stroke
                        },
                        onDrag = { change, _ ->
                            currentStroke = currentStroke + change.position  // Add points to the current stroke
                        },
                        onDragEnd = {
                            strokes = strokes + listOf(currentStroke)  // Save the current stroke
                            handwritingUseCase.addHandwritingData(
                                positions = currentStroke.map { it.x to it.y },
                                timestamps = currentStroke.map { System.currentTimeMillis() },
                                pressure = List(currentStroke.size) { 0.5f },  // Placeholder for pressure
                                strokeID = strokes.size + 1,
                                touchAngle = calculateStrokeAngle(currentStroke),
                                touchSpeed = 0.5f
                            )
                            currentStroke = emptyList()  // Reset current stroke
                        }
                    )
                }
        ) {
            // Draw previous strokes
            for (stroke in strokes) {
                for (i in 1 until stroke.size) {
                    drawLine(
                        color = selectedColor,
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
                            selectedColor = color  // Change the selected color
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