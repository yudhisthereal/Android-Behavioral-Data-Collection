package com.example.behavioraldatacollection.data.model

data class TypingPattern(
    var typingSpeed: Int, // Typing speed in characters per minute
    var pauseDuration: Float, // Pause duration in milliseconds
    var transitionDuration: Float // Transition duration in milliseconds
) {
    override fun toString(): String {
        return "$typingSpeed, $pauseDuration, $transitionDuration"
    }
}
