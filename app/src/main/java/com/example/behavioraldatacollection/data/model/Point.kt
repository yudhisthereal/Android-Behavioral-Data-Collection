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
) {
    override fun toString(): String {
        return "$x, $y, $timestamp, $pressure, $strokeID, $touchAngle, $touchSpeed, $eventType"
    }

    companion object {
        fun getColNames(): String {
            return "x, y, timestamp, pressure, strokeID, touchAngle, touchSpeed, eventType"
        }
    }
}

enum class TouchEventType {
    DOWN, MOVE, UP
}