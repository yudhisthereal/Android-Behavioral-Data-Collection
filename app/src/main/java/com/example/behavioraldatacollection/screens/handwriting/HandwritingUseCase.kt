package com.example.behavioraldatacollection.screens.handwriting

import com.example.behavioraldatacollection.commonMain.HandwritingData

class HandwritingUseCase {
    private val handwritingDataList = mutableListOf<HandwritingData>()

    fun addHandwritingData(positions: List<Pair<Float, Float>>, timestamps: List<Long>, pressure: List<Float>, strokeID: Int, touchAngle: Float?, touchSpeed: Float) {
        handwritingDataList.add(HandwritingData(positions, timestamps, pressure, strokeID, touchAngle, touchSpeed))
    }

    fun getHandwritingData(): List<HandwritingData> = handwritingDataList
}