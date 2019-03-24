package com.example.xletix.Timer;

/**
 * Created by vtewes on 08.05.2016.
 */
interface ITimerObserverHandler {

    void registerObserver(ITimerObserver observer);
    void removeObserver(ITimerObserver observer);

    void nextImpulse();
    void timerStarted();
    void timerResumed();
    void timerPaused();
    void timerStopped();
}
