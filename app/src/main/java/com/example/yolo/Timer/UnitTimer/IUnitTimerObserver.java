package com.example.yolo.Timer.UnitTimer;

import com.example.yolo.FRM.Units.IUnit;

/**
 * Created by vtewes on 08.05.2016.
 */
public interface IUnitTimerObserver {

    void onNextImpulse(IUnit currentUnit, int remainingTime);
    void onTimerInitialized(IUnit currentUnit, int unitLength);
    void onTimerStarted();
    void onTimerResumed();
    void onTimerPaused();
    void onTimerStopped();
    void onTimerFinished();
}
