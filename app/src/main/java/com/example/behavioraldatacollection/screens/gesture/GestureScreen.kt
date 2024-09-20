package com.example.behavioraldatacollection.screens.gesture

import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlin.math.atan2

@Composable
fun GestureScreen(navController: NavController) {
    val gestureUseCase = GestureUseCase()
    var gestureData by remember { mutableStateOf("") }

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
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        val startX = change.position.x
                        val startY = change.position.y
                        val angle = atan2(dragAmount.y, dragAmount.x) * (180 / Math.PI)
                        gestureData = "Swipe start: ($startX, $startY), Angle: $angle°"

                        gestureUseCase.addGesture(
                            startX,
                            startY,
                            dragAmount.x,
                            dragAmount.y,
                            500,
                            0.5f,
                            angle.toFloat(),
                            0.5f
                        )
                    }
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