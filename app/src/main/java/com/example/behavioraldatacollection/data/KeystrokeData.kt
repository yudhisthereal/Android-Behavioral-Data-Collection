package com.example.behavioraldatacollection.data

data class KeystrokeData(
    val keyPressed: Char,
    val keyPressTime: Long,
    val keyReleaseTime: Long,
    val holdTime: Long,
    val flightTime: Long?,
    val typingPattern: String
) {
    override fun toString(): String {
        return "KeystrokeData(keyPressed='$keyPressed', keyPressTime=$keyPressTime, keyReleaseTime=$keyReleaseTime, holdTime=$holdTime, flightTime=$flightTime, typingPattern='$typingPattern')"
    }

}