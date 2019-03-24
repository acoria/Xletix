package com.example.xletix.FRM.Workouts;

import java.util.List;

public class WorkoutIterator implements IWorkoutIterator{

    private final List<IWorkout> workouts;
    private int currentPosition = -1;

    public WorkoutIterator(List<IWorkout> workouts){
        this.workouts = workouts;
    }

    @Override
    public IWorkout getNext(){
        if(hasNext()){
            return workouts.get(++currentPosition);
        }
        return null;
    }
    @Override
    public IWorkout getPrevious(){
        if(hasPrevious()) {
            return workouts.get(--currentPosition);
        }
        return null;
    }

    @Override
    public boolean hasPrevious(){
        return currentPosition > 0;
    }

    @Override
    public boolean hasNext(){
        int nextPosition = currentPosition+1;
        return nextPosition < workouts.size();
    }

    @Override
    public void reset() {
        currentPosition = -1;
    }
}