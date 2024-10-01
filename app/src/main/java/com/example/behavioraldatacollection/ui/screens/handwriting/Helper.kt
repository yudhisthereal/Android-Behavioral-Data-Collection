package com.example.behavioraldatacollection.ui.screens.handwriting

import androidx.compose.ui.geometry.Offset
import kotlin.math.atan2

fun calculateAngleForPoint(offset: Offset): Float {
    return atan2(offset.y, offset.x)
}

fun calculateSpeedForPoint(offset: Offset, timestamp: Long): Float {
    val duration = System.currentTimeMillis() - timestamp
    return offset.getDistance() / duration
}