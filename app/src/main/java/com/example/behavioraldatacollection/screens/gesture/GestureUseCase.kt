package com.example.behavioraldatacollection.screens.gesture

import com.example.behavioraldatacollection.commonMain.GestureData

class GestureUseCase {
    private val gestureDataList = mutableListOf<GestureData>()

    fun addGesture(
        startX: Float, startY: Float, endX: Float, endY: Float, duration: Long, speed: Float, direction: Float, pressure: Float
    ) {
        gestureDataList.add(GestureData(startX, startY, endX, endY, duration, speed, direction, pressure))
    }

    fun getGestureData(): List<GestureData> = gestureDataList
}