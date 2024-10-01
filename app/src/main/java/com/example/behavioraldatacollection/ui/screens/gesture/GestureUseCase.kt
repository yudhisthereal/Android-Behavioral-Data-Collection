package com.example.behavioraldatacollection.ui.screens.gesture

import com.example.behavioraldatacollection.data.model.GestureData
import com.example.behavioraldatacollection.data.repository.GestureRepository.Companion.gestureDataList

class GestureUseCase {
    fun addGesture(
        startX: Float, startY: Float, endX: Float, endY: Float, duration: Long, speed: Float, direction: Float, pressure: Float
    ) {
        gestureDataList.add(GestureData(startX, startY, endX, endY, duration, speed, direction, pressure))
    }

    fun getGestureData(): List<GestureData> = gestureDataList
}
