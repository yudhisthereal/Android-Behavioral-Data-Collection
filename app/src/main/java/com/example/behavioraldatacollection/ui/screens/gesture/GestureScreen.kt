package com.example.behavioraldatacollection.ui.screens.gesture

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import kotlin.math.pow

@Composable
fun GestureScreen(navController: NavController) {
    val gestureUseCase = GestureUseCase()
    var gestureData by remember { mutableStateOf("Angle = 0\n Speed = 0\n, Duration = \n") }
    var startTime by remember { mutableLongStateOf(0L) }
    var startX by remember { mutableFloatStateOf(0f) }
    var startY by remember { mutableFloatStateOf(0f) }
    var endX by remember { mutableFloatStateOf(0f) }
    var endY by remember { mutableFloatStateOf(0f) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = gestureData,
            modifier = Modifier.padding(32.dp)
        )

        var gesturePoints by remember { mutableStateOf(listOf<Offset>()) }  // To store gesture points

        Box(
            modifier = Modifier
                .weight(1f)
                .pointerInput(Unit) {
                    awaitEachGesture {
                        val down = awaitFirstDown()
                        // Clear the gesture points when a new gesture starts
                        gesturePoints = emptyList()

                        startX = down.position.x
                        startY = down.position.y
                        startTime = System.currentTimeMillis()

                        // Add the first point (start of the gesture)
                        gesturePoints = gesturePoints + Offset(startX, startY)

                        // Track the gesture
                        do {
                            val event = awaitPointerEvent()
                            event.changes.forEach { change ->
                                // Add each intermediate point of the gesture
                                gesturePoints = gesturePoints + change.position
                            }
                        } while (event.changes.any { it.pressed })

                        endX = gesturePoints.lastOrNull()?.x ?: startX
                        endY = gesturePoints.lastOrNull()?.y ?: startY

                        val angle = atan2(endY - startY, endX - startX) * 180 / Math.PI
                        val duration = System.currentTimeMillis() - startTime
                        val distance = kotlin.math.sqrt((endX - startX).toDouble().pow(2) + (endY - startY).toDouble().pow(2))
                        val speed = distance / duration

                        gestureData = "Angle = $angle\nSpeed = $speed\nDuration = $duration\n"

                        gestureUseCase.addGesture(
                            startX,
                            startY,
                            endX,
                            endY,
                            duration,
                            speed.toFloat(),
                            angle.toFloat(),
                            0f // TODO: Touch pressure capture
                        )
                    }
                }
        ) {
            // Use Canvas to visualize the gesture
            Canvas(modifier = Modifier.fillMaxSize()) {
                for (i in 1 until gesturePoints.size) {
                    drawLine(
                        color = Color.Blue,
                        start = gesturePoints[i - 1],
                        end = gesturePoints[i],
                        strokeWidth = 5f
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier.padding(48.dp),
            onClick = { navController.navigate("home") }) {
            Text("Back to Home")
        }
    }
}

@Preview
@Composable
fun GestureScreenPreview() {
    GestureScreen(navController = rememberNavController())
}