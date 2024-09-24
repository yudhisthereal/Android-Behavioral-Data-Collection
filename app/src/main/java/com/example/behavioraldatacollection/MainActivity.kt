package com.example.behavioraldatacollection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.behavioraldatacollection.commonMain.TimerViewModel
import com.example.behavioraldatacollection.screens.AppNavigation
import com.example.behavioraldatacollection.ui.theme.BehavioralDataCollectionTheme

class MainActivity : ComponentActivity() {

    private lateinit var timerViewModel: TimerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        timerViewModel = ViewModelProvider(this)[TimerViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            BehavioralDataCollectionTheme {
                val navController = rememberNavController()
                AppNavigation(navController, timerViewModel = timerViewModel)
            }
        }
    }
}
