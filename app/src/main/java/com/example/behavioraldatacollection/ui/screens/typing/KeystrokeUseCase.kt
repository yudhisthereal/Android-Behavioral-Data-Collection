package com.example.behavioraldatacollection.ui.screens.typing

import com.example.behavioraldatacollection.data.model.KeystrokeData
import com.example.behavioraldatacollection.data.model.TypingPattern
import com.example.behavioraldatacollection.data.repository.KeystrokeRepository.Companion.keystrokeDataList

class KeystrokeUseCase {
    fun addKeystroke(key: Char, pressTimestamp: Long, releaseTimestamp: Long, holdTime: Long, flightTime: Long, typingPattern: TypingPattern) {
        keystrokeDataList.add(KeystrokeData(key, pressTimestamp, releaseTimestamp, holdTime, flightTime, typingPattern))
    }

//    fun getKeystrokeData(): List<KeystrokeData> = keystrokeDataList
}