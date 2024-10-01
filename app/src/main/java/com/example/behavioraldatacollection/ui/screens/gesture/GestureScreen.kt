package com.example.behavioraldatacollection.ui.screens.gesture

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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlin.math.atan2
import kotlin.math.pow

@OptIn(ExperimentalComposeUiApi::class)
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

        Box(
            modifier = Modifier
                .weight(1f)
                .pointerInteropFilter {
                    when (it.action) {
                        android.view.MotionEvent.ACTION_DOWN -> {
                            startX = it.x
                            startY = it.y
                            startTime = System.currentTimeMillis()
                        }
                        android.view.MotionEvent.ACTION_MOVE -> {

                        }
                        android.view.MotionEvent.ACTION_UP -> {
                            endX = it.x
                            endY = it.y

                            val angle = atan2(endY - startY, endX - startX) * 180 / Math.PI
                            val duration = System.currentTimeMillis() - startTime
                            val distance = kotlin.math.sqrt((endX - startX).toDouble().pow(2) + (endY - startY).toDouble().pow(2))
                            val speed = distance / duration

                            gestureData = "Angle = $angle\n Speed = $speed\n, Duration = $duration\n"

                            gestureUseCase.addGesture(
                                startX,
                                startY,
                                endX,
                                endY,
                                duration,
                                speed.toFloat(),
                                angle.toFloat(),
                                0f
                            )
                        }

                        else -> {}
                    }
                    true
                }
        )

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