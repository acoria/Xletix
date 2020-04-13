package com.example.yolo.FRM.Workouts;

public interface IWorkoutIterator {

    IWorkout getNext();
    IWorkout getPrevious();
    boolean hasPrevious();
    boolean hasNext();
    void reset();
}
