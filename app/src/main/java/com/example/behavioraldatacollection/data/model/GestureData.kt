package com.example.behavioraldatacollection.data.model

data class GestureData(
    val startX: Float,
    val startY: Float,
    val endX: Float,
    val endY: Float,
    val duration: Long,
    val speed: Float,
    val direction: Float,
    val pressure: Float
) {
    override fun toString(): String {
        return "$startX, $startY, $endX, $endY, $duration, $speed, $direction, $pressure"
    }
    companion object {
        fun getColNames(): String {
            return "startX, startY, endX, endY, duration, speed, direction, pressure"
        }
    }
}
