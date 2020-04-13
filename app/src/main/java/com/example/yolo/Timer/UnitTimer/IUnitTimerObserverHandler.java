package com.example.yolo.Timer.UnitTimer;

/**
 * Created by vtewes on 08.05.2016.
 */
interface IUnitTimerObserverHandler {

    void registerObserver(IUnitTimerObserver observer);
    void removeObserver(IUnitTimerObserver observer);

    void nextImpulse();
    void timerStarted();
    void timerResumed();
    void timerPaused();
    void timerStopped();
    void timerFinished();
    void timerInitialized();
}
