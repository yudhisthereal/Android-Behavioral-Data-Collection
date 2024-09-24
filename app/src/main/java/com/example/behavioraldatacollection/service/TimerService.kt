package com.example.behavioraldatacollection.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerService : Service() {
    private val binder = TimerBinder()
    private var timerJob: Job? = null
    private var currentTime = MutableLiveData<Long>()
    private val timerInterval = 100L

    inner class TimerBinder : Binder() {
        fun getService(): TimerService = this@TimerService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    fun startTimer(totalTime: Long) {
        stopTimer() // Reset any previous timers
        currentTime.value = totalTime
        timerJob = CoroutineScope(Dispatchers.Default).launch {
            while (currentTime.value!! > 0) {
                delay(timerInterval)
                currentTime.postValue(currentTime.value!! - timerInterval)
            }
            stopSelf()
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
    }

    fun getCurrentTime(): LiveData<Long> = currentTime
}
