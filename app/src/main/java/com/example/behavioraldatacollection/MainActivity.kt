package com.example.behavioraldatacollection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.behavioraldatacollection.screens.AppNavigation
import com.example.behavioraldatacollection.ui.theme.BehavioralDataCollectionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BehavioralDataCollectionTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}
