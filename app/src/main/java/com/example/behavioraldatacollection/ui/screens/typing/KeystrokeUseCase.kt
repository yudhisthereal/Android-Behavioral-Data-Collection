package com.example.behavioraldatacollection.ui.screens.typing

import com.example.behavioraldatacollection.data.KeystrokeData
import com.example.behavioraldatacollection.data.repository.KeystrokeRepository.Companion.keystrokeDataList

class KeystrokeUseCase {
    fun addKeystroke(key: Char, pressTimestamp: Long, releaseTimestamp: Long, holdTime: Long, flightTime: Long, typingPattern: String) {
        keystrokeDataList.add(KeystrokeData(key, pressTimestamp, releaseTimestamp, holdTime, flightTime, typingPattern))
    }

    fun getKeystrokeData(): List<KeystrokeData> = keystrokeDataList
}