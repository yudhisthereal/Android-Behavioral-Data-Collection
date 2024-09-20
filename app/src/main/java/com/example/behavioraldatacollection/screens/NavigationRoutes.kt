package com.example.behavioraldatacollection.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.behavioraldatacollection.screens.gesture.GestureScreen
import com.example.behavioraldatacollection.screens.handwriting.HandwritingScreen
import com.example.behavioraldatacollection.screens.home.HomeScreen
import com.example.behavioraldatacollection.screens.typing.TypingScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("typing") { TypingScreen(navController) }
        composable("gestures") { GestureScreen(navController) }
        composable("handwriting") { HandwritingScreen(navController) }
    }
}