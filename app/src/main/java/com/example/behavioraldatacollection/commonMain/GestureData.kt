package com.example.behavioraldatacollection.commonMain

data class GestureData(
    val startX: Float,
    val startY: Float,
    val endX: Float,
    val endY: Float,
    val duration: Long,
    val speed: Float,
    val direction: Float,
    val pressure: Float
)
