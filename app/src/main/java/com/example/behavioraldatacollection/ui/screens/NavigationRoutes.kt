package com.example.behavioraldatacollection.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.behavioraldatacollection.ui.screens.gesture.GestureScreen
import com.example.behavioraldatacollection.ui.screens.handwriting.HandwritingScreen
import com.example.behavioraldatacollection.ui.screens.home.HomeScreen
import com.example.behavioraldatacollection.ui.screens.typing.TypingScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("typing") { TypingScreen(navController) }
        composable("gestures") { GestureScreen(navController) }
        composable("handwriting") { HandwritingScreen(navController) }
    }
}