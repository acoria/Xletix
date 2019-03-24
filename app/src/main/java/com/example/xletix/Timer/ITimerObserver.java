package com.example.xletix.Timer;

/**
 * Created by vtewes on 08.05.2016.
 */
public interface ITimerObserver {

    void onNextImpulse();
    void onTimerStarted();
    void onTimerResumed();
    void onTimerPaused();
    void onTimerStopped();
}
