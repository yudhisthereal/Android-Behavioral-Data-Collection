package com.example.behavioraldatacollection.ui.screens.handwriting

import com.example.behavioraldatacollection.data.HandwritingData
import com.example.behavioraldatacollection.data.repository.HandwritingRepository.Companion.handwritingDataList

class HandwritingUseCase {
    fun addHandwritingData(positions: List<Pair<Float, Float>>, timestamps: List<Long>, pressure: List<Float>, strokeID: Int, touchAngle: Float?, touchSpeed: Float) {
        handwritingDataList.add(HandwritingData(positions, timestamps, pressure, strokeID, touchAngle, touchSpeed))
    }

    fun getHandwritingData(): List<HandwritingData> = handwritingDataList
}