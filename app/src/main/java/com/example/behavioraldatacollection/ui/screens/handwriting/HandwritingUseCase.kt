package com.example.behavioraldatacollection.ui.screens.handwriting

import com.example.behavioraldatacollection.data.model.Point
import com.example.behavioraldatacollection.data.repository.HandwritingRepository.Companion.handwritingDataList

class HandwritingUseCase {

    fun addPointData(point: Point) {
        handwritingDataList.add(point)
    }
}