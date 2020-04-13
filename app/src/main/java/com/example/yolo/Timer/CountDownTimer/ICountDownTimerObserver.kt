package com.example.yolo.Timer.CountDownTimer

interface ICountDownTimerObserver{

    fun onNextImpulse(remainingTime: Int)
    fun onTimerStarted()
    fun onTimerResumed()
    fun onTimerPaused()
    fun onTimerStopped()

}