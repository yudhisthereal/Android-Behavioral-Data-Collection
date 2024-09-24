package com.example.behavioraldatacollection.domain

import android.view.accessibility.AccessibilityEvent

interface DataCollector {
    fun captureKeystroke(event: AccessibilityEvent)
    fun captureGestureOrHandwriting(event: AccessibilityEvent)
}