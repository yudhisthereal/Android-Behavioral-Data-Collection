package com.example.behavioraldatacollection.screens.monitoring

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.behavioraldatacollection.commonMain.TimerViewModel

@Composable
fun MonitoringScreen(
    navController: NavController,
    viewModel: TimerViewModel,
) {


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Timer(
            viewModel = viewModel,
            handleColor = Color.Blue,
            inactiveBarColor = Color.DarkGray,
            activeBarColor = Color.Blue,
            modifier = Modifier
                .size(200.dp)
                .fillMaxSize()
        )



        Button(
            modifier = Modifier.padding(32.dp),
            onClick = {
                navController.navigate("home")
            },
        ) {
            Text("Back to Home")
        }
    }
}

