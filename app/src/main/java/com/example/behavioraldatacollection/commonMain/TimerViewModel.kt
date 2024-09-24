package com.example.behavioraldatacollection.commonMain

import android.annotation.SuppressLint
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.behavioraldatacollection.service.TimerService

class TimerViewModel(private val app: Application) : AndroidViewModel(app) {
    private var serviceConnection: ServiceConnection? = null
    @SuppressLint("StaticFieldLeak")
    private var timerService: TimerService? = null
    private val _currentTime = MutableLiveData<Long>()
    private val _isTimerRunning = MutableLiveData<Boolean>()

    init {
        _isTimerRunning.value = false
    }

    // Function to start the service
    fun startTimerService(totalTime: Long) {
        val serviceIntent = Intent(app, TimerService::class.java)
        app.startService(serviceIntent)
        bindService()
        // Start the timer in the service
        timerService?.startTimer(totalTime)
        _isTimerRunning.value = true
    }

    // Function to stop the timer
    fun stopTimer() {
        timerService?.stopTimer()
        _isTimerRunning.value = false
    }

    // Function to bind the service
    private fun bindService() {
        val serviceIntent = Intent(app, TimerService::class.java)
        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
                val serviceBinder = binder as TimerService.TimerBinder
                timerService = serviceBinder.getService()
                // Observe timer data here
                timerService?.getCurrentTime()?.observeForever { time ->
                    _currentTime.value = time
                }
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                timerService = null
            }
        }
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
