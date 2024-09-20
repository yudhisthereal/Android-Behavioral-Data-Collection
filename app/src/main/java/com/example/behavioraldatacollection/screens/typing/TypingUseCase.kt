package com.example.behavioraldatacollection.screens.typing

import com.example.behavioraldatacollection.commonMain.KeystrokeData

class TypingUseCase {
    private val keystrokeDataList = mutableListOf<KeystrokeData>()

    fun addKeystroke(key: Char, pressTimestamp: Long, releaseTimestamp: Long, holdTime: Long, flightTime: Long) {
        keystrokeDataList.add(KeystrokeData(key, pressTimestamp, releaseTimestamp, holdTime, flightTime, ""))
    }

    fun getKeystrokeData(): List<KeystrokeData> = keystrokeDataList
}