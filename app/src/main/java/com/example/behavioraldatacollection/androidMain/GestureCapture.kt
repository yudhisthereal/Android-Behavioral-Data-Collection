package com.example.behavioraldatacollection.androidMain

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent

class GestureCapture(context: Context) {
    private val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            // Capture gesture data and store it
            return true
        }
    })
}