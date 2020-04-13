package com.example.yolo.Timer.UnitTimer;

import android.os.Handler;

import com.example.yolo.FRM.Units.IUnit;
import com.example.yolo.FRM.Units.IUnitProvider;

import java.util.ArrayList;

/**
 * Created by vtewes on 15.03.2020.
 */
public class UnitTimer implements IUnitTimer, Runnable{
    private static final String TAG = UnitTimer.class.getSimpleName();

    public enum TimerState {
        RUNNING("RUNNING"), STOPPED("STOPPED"), PAUSED("PAUSED"), INITIAL("INITIAL"), FINISHED("FINISHED");
        String stateName;
        TimerState(String stateName) {
            this.stateName = stateName;
        }
        public String getStateName(){
            return stateName;
        }
        public static TimerState findStateByName(String stateName) {
            for (TimerState stateValue : values()) {
                if (stateValue.stateName == stateName) {
                    return stateValue;
                }
            }
            throw new IllegalArgumentException(stateName + "is not a valid TimerState");
        }
    }

    private ArrayList<IUnitTimerObserver> unitTimerObservers = new ArrayList();
    private Handler impulseHandler;
    private int interval;
    private TimerState state = TimerState.INITIAL;
    private int remainingTime;
    private IUnitProvider unitProvider;
    private IUnit currentUnit;

    public UnitTimer(int intervalToRunAtInSeconds, IUnitProvider unitProvider){
        this.interval = intervalToRunAtInSeconds*1000;
        this.unitProvider = unitProvider;
        impulseHandler = new Handler();
    }

    public UnitTimer(int intervalToRunAtInSeconds, IUnitProvider unitProvider, Handler handler){
        this.interval = intervalToRunAtInSeconds*1000;
        this.unitProvider = unitProvider;
        impulseHandler = handler;
    }

    @Override
    public void initialize() {
        initializeFromNewUnit(unitProvider.getSuccessor());
        timerInitialized();
    }

    @Override
    public void initializeFromUnit(IUnit unit, int startingTime) {
        unitProvider.resetByUnit(unit);
        initializeFromNewUnit(unitProvider.getCurrentUnit());
        remainingTime = startingTime;
        timerInitialized();
    }

    @Override
    public void run() {
        nextImpulse();
        //avoid timer getting restarted during the impulse it was stopped
        if (state != TimerState.STOPPED && state != TimerState.PAUSED && state != TimerState.FINISHED){
           impulseHandler.postDelayed(this, interval);
        }
    }

    @Override
    public void startTimer(){
        timerStarted();
        impulseHandler.postDelayed(this, 0);
    }
    @Override
    public void resumeTimer(){
        if(impulseHandler != null) {
            impulseHandler.postDelayed(this, 0);
            timerResumed();
        }
    }
    @Override
    public void pauseTimer(){
        if(impulseHandler != null) {
            impulseHandler.removeCallbacks(this);
            timerPaused();
        }
    }
    @Override
    public void stopTimer(){
        if(impulseHandler != null) {
            impulseHandler.removeCallbacks(this);
            timerStopped();
        }
    }

    @Override
    public void finishTimer() {
        if(impulseHandler != null) {
            impulseHandler.removeCallbacks(this);
            timerFinished();
        }
    }

    @Override
    public void skipUnit() {
        stopTimer();
        if(unitProvider.hasSuccessor()){
            //while there is another exercise
            initializeFromNewUnit(unitProvider.getSuccessor());
            timerInitialized();
        }else{
            timerFinished();
        }
    }

    @Override
    public void backOneUnit() {
        stopTimer();
        if(unitProvider.hasPredecessor()){
            initializeFromNewUnit(unitProvider.getPredecessor());
            timerInitialized();
        }
    }

    @Override
    public TimerState getState(){
        return state;
    }

    @Override
    public void registerObserver(IUnitTimerObserver observer) {
        unitTimerObservers.add(observer);
    }
    @Override
    public void removeObserver(IUnitTimerObserver observer) {
        unitTimerObservers.remove(observer);
    }
    @Override
    public void nextImpulse(){
        if(remainingTime > 0) {
            for(IUnitTimerObserver observer : unitTimerObservers) {
                observer.onNextImpulse(currentUnit, remainingTime);
            }
            remainingTime--;
        }else{
            //while there is another unit
            if(unitProvider.hasSuccessor()){
                //get next exercise
                initializeFromNewUnit(unitProvider.getSuccessor());
                for(IUnitTimerObserver observer : unitTimerObservers) {
                    observer.onNextImpulse(currentUnit, remainingTime);
                }
                remainingTime--;
            }else {
                //no more exercises
                finishTimer();
            }
        }
    }

    private void initializeFromNewUnit(IUnit newUnit) {
        currentUnit = newUnit;
        if(currentUnit == null){
            throw new IllegalArgumentException("UnitProvider has no units");
        }
        remainingTime = currentUnit.getLength();
    }

    @Override
    public void timerStarted(){
        state = TimerState.RUNNING;
        for(IUnitTimerObserver observer : unitTimerObservers){
            observer.onTimerStarted();
        }
    }
    @Override
    public void timerResumed(){
        state = TimerState.RUNNING;
        for(IUnitTimerObserver observer : unitTimerObservers){
            observer.onTimerResumed();
        }
    }
    @Override
    public void timerPaused(){
        state = TimerState.PAUSED;
        for(IUnitTimerObserver observer : unitTimerObservers){
            observer.onTimerPaused();
        }
    }
    @Override
    public void timerStopped(){
        state = TimerState.STOPPED;
        for(IUnitTimerObserver observer : unitTimerObservers){
            observer.onTimerStopped();
        }
    }

    @Override
    public void timerFinished() {
        state = TimerState.FINISHED;
        for(IUnitTimerObserver observer : unitTimerObservers){
            observer.onTimerFinished();
        }
    }

    @Override
    public void timerInitialized() {
        for(IUnitTimerObserver observer : unitTimerObservers){
            observer.onTimerInitialized(currentUnit, currentUnit.getLength());
        }
    }

    @Override
    public void toggleState() {
        switch (state){
            case INITIAL:
            case STOPPED:
                startTimer();
                break;
            case PAUSED :
                resumeTimer();
                break;
            case RUNNING :
                pauseTimer();
                break;
            default :
                break;
        }
    }

    @Override
    public String getFormattedTime(int time) {
        long minutes = time / 60;
        long seconds = time % 60;
        if(seconds == 0){
            return minutes + ":00";
        }else if(seconds > 0 && seconds < 10){
            return minutes + ":0" + seconds;
        }
        else{
            return minutes + ":" + seconds;
        }
    }
}

