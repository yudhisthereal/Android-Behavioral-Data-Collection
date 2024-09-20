package com.example.behavioraldatacollection.commonMain

data class KeystrokeData(
    val keyPressed: Char,
    val keyPressTime: Long,
    val keyReleaseTime: Long,
    val holdTime: Long,
    val flightTime: Long?,
    val typingPattern: String
)