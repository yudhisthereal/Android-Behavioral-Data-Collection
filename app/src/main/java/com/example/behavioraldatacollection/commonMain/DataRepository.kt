package com.example.behavioraldatacollection.commonMain

import android.view.accessibility.AccessibilityEvent
import com.example.behavioraldatacollection.domain.DataCollector

class DataRepository : DataCollector {
    override fun captureKeystroke(event: AccessibilityEvent) {
        val keyText = event.text.toString()
        val timePressed = System.currentTimeMillis() // Timestamp
        // Save or process the keystroke data
    }

    override fun captureGestureOrHandwriting(event: AccessibilityEvent) {
        val eventType = event.eventType
        // Based on event type, save gesture or handwriting data
    }
}