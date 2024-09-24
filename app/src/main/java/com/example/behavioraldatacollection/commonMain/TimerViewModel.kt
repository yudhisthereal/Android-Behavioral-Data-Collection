package com.example.behavioraldatacollection.commonMain

import android.annotation.SuppressLint
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.behavioraldatacollection.service.TimerService

class TimerViewModel(private val app: Application) : AndroidViewModel(app) {
    private var serviceConnection: ServiceConnection? = null
    @SuppressLint("StaticFieldLeak")
    private var timerService: TimerService? = null
    private val _currentTime = MutableLiveData(600000L)
    private val _isTimerRunning = MutableLiveData(false)

    init {
        _isTimerRunning.value = false
    }

    // Function to start the service
    fun startTimerService(totalTime: Long) {
        val serviceIntent = Intent(app, TimerService::class.java)
        app.startService(serviceIntent)
        bindService(totalTime)
    }

    // Function to stop the timer
    fun stopTimer() {
        timerService?.stopTimer()
        _isTimerRunning.value = false
        _currentTime.value = 600000
    }

    // Function to bind the service
    private fun bindService(totalTime: Long) {
        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
                val serviceBinder = binder as TimerService.TimerBinder
                timerService = serviceBinder.getService()
                // Observe timer data here
                timerService?.getCurrentTime()?.observeForever { time ->
                    _currentTime.value = time
                }

                // Once the service is connected, start the timer
                timerService?.startTimer(totalTime)
                _isTimerRunning.value = true
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                timerService = null
            }
        }

        val serviceIntent = Intent(app, TimerService::class.java)
        app.bindService(serviceIntent, serviceConnection!!, Context.BIND_AUTO_CREATE)
    }

    // Expose current time and isTimerRunning as LiveData
    fun getCurrentTime(): LiveData<Long> {
        return _currentTime
    }

    fun isTimerRunning(): LiveData<Boolean> {
        return _isTimerRunning
    }

    override fun onCleared() {
        super.onCleared()
        serviceConnection?.let {
            app.unbindService(it)
        }
    }
}
