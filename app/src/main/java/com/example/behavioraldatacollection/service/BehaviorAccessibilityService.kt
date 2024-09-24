package com.example.behavioraldatacollection.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.annotation.SuppressLint
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.example.behavioraldatacollection.commonMain.DataRepository

class BehaviorAccessibilityService : AccessibilityService() {
    private val dataRepository = DataRepository()

    @SuppressLint("SwitchIntDef")
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.let {
            when (event.eventType) {
                AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> {
                    // Capture keystroke data
                    dataRepository.captureKeystroke(event)
                }
                AccessibilityEvent.TYPE_TOUCH_INTERACTION_START, AccessibilityEvent.TYPE_TOUCH_INTERACTION_END -> {
                    // Capture gesture or handwriting data
                    dataRepository.captureGestureOrHandwriting(event)
                }
            }
        }
    }

    override fun onInterrupt() {
        Log.d("BehaviorService", "Service Interrupted")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED or
                    AccessibilityEvent.TYPE_TOUCH_INTERACTION_START or
                    AccessibilityEvent.TYPE_TOUCH_INTERACTION_END
            feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK
            notificationTimeout = 100
        }
        this.serviceInfo = info
    }
}
