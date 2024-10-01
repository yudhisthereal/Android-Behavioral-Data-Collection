package com.example.behavioraldatacollection.data.model


data class Point(
    val x: Float,
    val y: Float,
    val timestamp: Long,
    val pressure: Float,
    val strokeID: Int,
    val touchAngle: Float,
    val touchSpeed: Float,
    val eventType: TouchEventType
)

enum class TouchEventType {
    DOWN, MOVE, UP
}