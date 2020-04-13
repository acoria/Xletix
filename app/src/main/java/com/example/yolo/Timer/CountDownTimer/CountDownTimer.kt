package com.example.yolo.Timer.CountDownTimer

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit


public class CountDownTimer(
        private val intervalInSeconds: Int,
        private val scheduledExecutorService: ScheduledExecutorService = Executors.newScheduledThreadPool(1)) : ICountDownTimer {

    private var scheduledTask: ScheduledFuture<*>? = null
    private val timerObservers = mutableListOf<ICountDownTimerObserver>()
    private var timerState = ICountDownTimer.TimerState.INITIAL
    private var remainingTime: Int = 0
    private val SYNC_MONITOR = Object()

    override fun startTimer() {
        scheduleTask()
        timerStarted()
    }

    override fun resumeTimer() {
        scheduleTask()
        timerResumed()
    }

    override fun pauseTimer() {
        cancelScheduledTask()
        timerPaused()
    }

    override fun stopTimer() {
        cancelScheduledTask()
        timerStopped()
    }

    override fun getState(): ICountDownTimer.TimerState {
        return timerState
    }

    override fun toggleState() {
        when (timerState) {
            ICountDownTimer.TimerState.INITIAL, ICountDownTimer.TimerState.STOPPED -> startTimer()
            ICountDownTimer.TimerState.PAUSED -> resumeTimer()
            ICountDownTimer.TimerState.RUNNING -> pauseTimer()
        }
    }

    override fun initializeWithNewLength(length: Int) {
        remainingTime = length
    }

    override fun getFormattedTime(time: Int): String? {
        val minutes = time / 60.toLong()
        return when (val seconds = time % 60.toLong()) {
            0L -> "$minutes:00"
            in 1..9 -> "$minutes:0$seconds"
            else -> "$minutes:$seconds"
        }
    }

    override fun run() {
        synchronized(SYNC_MONITOR) {
            //Synchronization of access to non-thread-safe parts of code,
            // see: https://www.techyourchance.com/thread-safe-observer-design-pattern-in-java/
        }
        try {
            nextImpulse()
        } catch (e: Exception) {
            println(e.message)
        }
    }

    override fun nextImpulse() {
        if (remainingTime > 0) {
            remainingTime--
        } else {
            stopTimer()
        }
        for(observer in timerObservers){
            observer.onNextImpulse(remainingTime)
        }
    }

    override fun timerStarted() {
        timerState = ICountDownTimer.TimerState.RUNNING
        for(observer in timerObservers){
            observer.onTimerStarted()
        }
    }

    override fun timerStopped() {
        timerState = ICountDownTimer.TimerState.STOPPED
        for(observer in timerObservers){
            observer.onTimerStopped()
        }
    }

    override fun timerResumed() {
        timerState = ICountDownTimer.TimerState.RUNNING
        for(observer in timerObservers){
            observer.onTimerResumed()
        }
    }

    override fun timerPaused() {
        timerState = ICountDownTimer.TimerState.PAUSED
        for(observer in timerObservers){
            observer.onTimerPaused()
        }
    }

    override fun registerObserver(observer: ICountDownTimerObserver) {
        timerObservers.add(observer)
    }

    override fun removeObserver(observer: ICountDownTimerObserver) {
        timerObservers.remove(observer)
    }

    private fun scheduleTask() {
        scheduledTask = scheduledExecutorService.scheduleAtFixedRate(this, intervalInSeconds.toLong(), intervalInSeconds.toLong(), TimeUnit.SECONDS)
    }

    private fun cancelScheduledTask() {
        if (scheduledTask != null) {
            scheduledTask?.cancel(true)
        }
    }
}