package com.example.behavioraldatacollection.ui.screens.handwriting

import androidx.compose.ui.geometry.Offset
import com.example.behavioraldatacollection.data.model.Point
import com.example.behavioraldatacollection.data.model.TouchEventType
import com.example.behavioraldatacollection.data.repository.HandwritingRepository.Companion.handwritingDataList

fun addPointData(
    offset: Offset,
    eventType: TouchEventType,
    strokeID: Int,
    lastTimeStamp: Long,
    handwritingUseCase: HandwritingUseCase
) {
    val pressure = 0.5f  // TODO: Touch pressure capture
    val angle = if (lastTimeStamp > 0L) calculateAngleForPoint(offset) else 0f
    val speed = if (lastTimeStamp > 0L) calculateSpeedForPoint(offset, lastTimeStamp) else 0f

    val timestamp = System.currentTimeMillis()
    handwritingUseCase.addPointData(
        Point(
            x = offset.x,
            y = offset.y,
            timestamp = timestamp,
            pressure = pressure,
            strokeID = strokeID,
            touchAngle = angle,
            touchSpeed = speed,
            eventType = eventType
        )
    )
}

class HandwritingUseCase {

    fun addPointData(point: Point) {
        handwritingDataList.add(point)
    }
}