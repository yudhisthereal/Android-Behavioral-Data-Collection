package com.example.behavioraldatacollection.data.repository

import com.example.behavioraldatacollection.data.model.GestureData

class GestureRepository {
    companion object {
        val gestureDataList = mutableListOf<GestureData>()
    }
}