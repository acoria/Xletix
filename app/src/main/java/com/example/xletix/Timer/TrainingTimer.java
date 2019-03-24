package com.example.xletix.Timer;

import android.os.Handler;

import java.util.ArrayList;

/**
 * Created by vtewes on 08.05.2016.
 */
public class TrainingTimer implements ITimerObserverHandler, Runnable{

    public static final CharSequence STATE_RUNNING = "RUNNING";
    public static final CharSequence STATE_STOPPED = "STOPPED";
    public static final CharSequence STATE_PAUSED = "PAUSED";
    //public static final CharSequence STATE_RESUMED = "RESUMED";
    public static final CharSequence STATE_INITIAL = "INITIAL";

    private ITimerObserver timerObserver;
    private ArrayList<ITimerObserver> timerObservers = new ArrayList<ITimerObserver>();

    private Handler impulseHandler;
    private int interval;
    private CharSequence state = STATE_INITIAL;

    public TrainingTimer(int intervalInSeconds){
        this.interval = intervalInSeconds*1000;
    }


    @Override
    public void run() {
        nextImpulse();
        //avoid timer getting restarted during the impulse it was stopped
        if (state != STATE_STOPPED && state != STATE_PAUSED){
            impulseHandler.postDelayed(this, interval);
        }
    }

    public void startTimer(){
        impulseHandler = new Handler();
        timerStarted();
        impulseHandler.postDelayed(this, 0);
    }
    public void resumeTimer(){
        if(impulseHandler != null) {
            impulseHandler.postDelayed(this, 0);
            timerResumed();
        }
    }
    public void pauseTimer(){
        if(impulseHandler != null) {
            impulseHandler.removeCallbacks(this);
            timerPaused();
        }
    }
    public void stopTimer() {
        if(impulseHandler != null) {
            impulseHandler.removeCallbacks(this);
            timerStopped();
        }
    }
    public CharSequence getState(){
        return state;
    }

    @Override
    public void registerObserver(ITimerObserver observer) {
        timerObservers.add(observer);
    }
    @Override
    public void removeObserver(ITimerObserver observer) {
        timerObservers.remove(observer);
    }
    @Override
    public void nextImpulse(){
        for(int i=0;i<timerObservers.size();i++){
            timerObserver = timerObservers.get(i);
            timerObserver.onNextImpulse();
        }
    }
    @Override
    public void timerStarted(){
        state = STATE_RUNNING;
        for(int i=0;i<timerObservers.size();i++){
            timerObserver = timerObservers.get(i);
            timerObserver.onTimerStarted();
        }
    }
    @Override
    public void timerResumed(){
        state = STATE_RUNNING;
        for(int i=0;i<timerObservers.size();i++){
            timerObserver = timerObservers.get(i);
            timerObserver.onTimerResumed();
        }
    }
    @Override
    public void timerPaused(){
        state = STATE_PAUSED;
        for(int i=0;i<timerObservers.size();i++){
            timerObserver = timerObservers.get(i);
            timerObserver.onTimerPaused();
        }
    }
    @Override
    public void timerStopped(){
        state = STATE_STOPPED;
        for(int i=0;i<timerObservers.size();i++){
            timerObserver = timerObservers.get(i);
            timerObserver.onTimerStopped();
        }
    }
}

