package com.example.yolo.Timer.UnitTimer;

import com.example.yolo.FRM.Units.IUnit;

public interface IUnitTimer extends IUnitTimerObserverHandler {

    void startTimer();
    void resumeTimer();
    void pauseTimer();
    void stopTimer();
    void finishTimer();

    UnitTimer.TimerState getState();
    void toggleState();

    void initialize();
    void initializeFromUnit(IUnit unit, int startingTime);

    void skipUnit();
    void backOneUnit();

    String getFormattedTime(int time);
}
