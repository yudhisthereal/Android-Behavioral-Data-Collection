package com.example.behavioraldatacollection.commonMain

data class HandwritingData (
    val points: List<Pair<Float, Float>>,
    val timestamps: List<Long>,
    val pressures: List<Float>,
    val strokeID: Int,
    val touchAngle: Float?,
    val touchSpeed: Float
)