package com.example.behavioraldatacollection.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = { navController.navigate("monitoring") }) {
            Text("Data Collection")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { navController.navigate("typing") }) {
            Text("Typing Activity")
        }

        Button(onClick = { navController.navigate("gestures") }) {
            Text("Gesture Activity")
        }

        Button(onClick = { navController.navigate("handwriting") }) {
            Text("Handwriting Activity")
        }
    }
}