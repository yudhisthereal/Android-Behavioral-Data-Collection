package com.example.behavioraldatacollection.data

data class HandwritingData ( // should I rename it to StrokeData?
    val points: List<Pair<Float, Float>>,
    val timestamps: List<Long>,
    val pressures: List<Float>,
    val strokeID: Int,
    val touchAngle: Float?,
    val touchSpeed: Float
) {
  override fun toString(): String {
      return "Handwriting Data(points=$points, timestamps=$timestamps, pressures=$pressures, strokeID=$strokeID, touchAngle=$touchAngle, touchSpeed=$touchSpeed)"
  }
}