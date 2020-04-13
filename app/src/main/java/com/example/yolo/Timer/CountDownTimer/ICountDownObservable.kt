package com.example.yolo.Timer.CountDownTimer

interface ICountDownTimerObservable{

    fun registerObserver(countDownObserver: ICountDownTimerObserver)
    fun removeObserver(countDownObserver: ICountDownTimerObserver)

    fun nextImpulse()
    fun timerStarted()
    fun timerResumed()
    fun timerPaused()
    fun timerStopped()

}