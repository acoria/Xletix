package com.example.yolo;

import android.os.Handler;

import com.example.yolo.FRM.Units.IUnit;
import com.example.yolo.FRM.Units.IUnitProvider;
import com.example.yolo.Timer.UnitTimer.IUnitTimer;
import com.example.yolo.Timer.UnitTimer.IUnitTimerObserver;
import com.example.yolo.Timer.UnitTimer.UnitTimer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UnitTimerTest {

    private IUnitTimer unitTimer;

    private IUnitProvider unitProvider;
    private IUnitTimerObserver observer;
    private IUnit unit;
    private Handler handler;

    @Before
    public void setup() {
        unitProvider = mock(IUnitProvider.class);
        observer = mock(IUnitTimerObserver.class);
        unit = mock(IUnit.class);
        handler = mock(Handler.class);
        unitTimer = new UnitTimer(1, unitProvider, handler);
        unitTimer.registerObserver(observer);

        when(unitProvider.getSuccessor()).thenReturn(unit);
        when(unit.getLength()).thenReturn(10);
        when(handler.postDelayed(any(), anyLong())).thenReturn(true);
        Mockito.doNothing().when(handler).removeCallbacks((Runnable) unitTimer);
    }

    @Test
    public void initializationObserve() {
        unitTimer.initialize();

        verify(observer, times(1)).onTimerInitialized(unitProvider.getSuccessor(), 10);
        assertEquals(unitTimer.getState(), UnitTimer.TimerState.INITIAL);
    }

    @Test
    public void timerStartedObserve() {
        unitTimer.startTimer();

        verify(observer, times(1)).onTimerStarted();
        assertEquals(unitTimer.getState(), UnitTimer.TimerState.RUNNING);
        verify(handler, times(1)).postDelayed((Runnable) unitTimer, 0);
    }

    @Test
    public void timerResumedObserve() {
        unitTimer.resumeTimer();

        verify(observer, times(1)).onTimerResumed();
        assertEquals(unitTimer.getState(), UnitTimer.TimerState.RUNNING);
        verify(handler, times(1)).postDelayed((Runnable) unitTimer, 0);
    }

    @Test
    public void timerPausedObserve() {
        unitTimer.pauseTimer();

        verify(observer, times(1)).onTimerPaused();
        assertEquals(unitTimer.getState(), UnitTimer.TimerState.PAUSED);
        verify(handler, times(1)).removeCallbacks((Runnable) unitTimer);
    }

    @Test
    public void timerStoppedObserve() {
        unitTimer.stopTimer();

        verify(observer, times(1)).onTimerStopped();
        assertEquals(unitTimer.getState(), UnitTimer.TimerState.STOPPED);
        verify(handler, times(1)).removeCallbacks((Runnable) unitTimer);
    }

    @Test
    public void timerFinishedObserve() {
        unitTimer.finishTimer();

        verify(observer, times(1)).onTimerFinished();
        assertEquals(unitTimer.getState(), UnitTimer.TimerState.FINISHED);
        verify(handler, times(1)).removeCallbacks((Runnable) unitTimer);
    }

    @Test
    public void skipUnitSuccessor() {
        IUnit successorUnit = mock(IUnit.class);
        when(unitProvider.hasSuccessor()).thenReturn(true);
        when(unitProvider.getSuccessor()).thenReturn(successorUnit);
        when(successorUnit.getLength()).thenReturn(5);

        unitTimer.skipUnit();

        verify(observer, times(1)).onTimerInitialized(successorUnit, 5);
        verify(observer, times(1)).onTimerStopped();
    }

    @Test public void skipUnitNoSuccessor(){
        when(unitProvider.hasSuccessor()).thenReturn(false);

        unitTimer.skipUnit();

        verify(observer, times(0)).onTimerInitialized(any(), anyInt());
        verify(observer, times(1)).onTimerFinished();
    }

    @Test
    public void backUnitPredecessor() {
        IUnit predeccessorUnit = mock(IUnit.class);
        when(unitProvider.hasPredecessor()).thenReturn(true);
        when(unitProvider.getPredecessor()).thenReturn(predeccessorUnit);
        when(predeccessorUnit.getLength()).thenReturn(5);

        unitTimer.backOneUnit();

        verify(observer, times(1)).onTimerInitialized(predeccessorUnit, 5);
        verify(observer, times(1)).onTimerStopped();
    }

    @Test
    public void backUnitNoPredecessor() {
        when(unitProvider.hasPredecessor()).thenReturn(false);

        unitTimer.backOneUnit();

        verify(observer, times(0)).onTimerInitialized(any(), anyInt());
    }
}