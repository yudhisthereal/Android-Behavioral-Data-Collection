package com.example.behavioraldatacollection.data.model

data class KeystrokeData(
    val keyPressed: Char,
    val keyPressTime: Long,
    val keyReleaseTime: Long,
    val holdTime: Long,
    val flightTime: Long?,
    val typingPattern: TypingPattern
) {
    override fun toString(): String {
        return "'$keyPressed', $keyPressTime, $keyReleaseTime, $holdTime, $flightTime, $typingPattern"
    }

    companion object {
        fun getColNames(): String {
            return "keyPressed, keyPressTime, keyReleaseTime, holdTime, flightTime, typingSpeed, avgPause, avgTransition"
        }
    }
}