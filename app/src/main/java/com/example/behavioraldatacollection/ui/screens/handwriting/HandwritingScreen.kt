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
import com.example.behavioraldatacollection.data.model.TouchEventType

@Composable
fun HandwritingScreen(navController: NavController) {
    val handwritingUseCase = HandwritingUseCase()
    var strokes by remember { mutableStateOf(listOf<Pair<List<Offset>, Color>>()) }  // List of strokes (each stroke has points and a color)
    var currentStroke by remember { mutableStateOf(listOf<Offset>()) }  // Current stroke being drawn
    var selectedColor by remember { mutableStateOf(Color.Black) }  // Default color is black
    var strokeID by remember { mutableIntStateOf(0) }  // To track stroke IDs
    var lastTimeStamp by remember { mutableLongStateOf(-1L)} // To track point speed

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
                        onDragStart = { offset ->
                            strokeID++  // Increment stroke ID for each new stroke
                            currentStroke = listOf(offset)  // Start new stroke
                            addPointData(offset, TouchEventType.DOWN, strokeID, -1L, handwritingUseCase)
                            lastTimeStamp = 0 // if set to current time, it will cause infinite speed for the first MOVE point
                        },
                        onDrag = { change, _ ->
                            currentStroke = currentStroke + change.position  // Add points to the current stroke
                            addPointData(change.position, TouchEventType.MOVE, strokeID, lastTimeStamp, handwritingUseCase)
                            lastTimeStamp = System.currentTimeMillis()
                        },
                        onDragEnd = {
                            strokes = strokes + Pair(currentStroke, selectedColor)  // Save the current stroke with its color
                            addPointData(currentStroke.last(), TouchEventType.UP, strokeID, -2L, handwritingUseCase)
                            currentStroke = emptyList()  // Reset current stroke
                        }
                    )
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