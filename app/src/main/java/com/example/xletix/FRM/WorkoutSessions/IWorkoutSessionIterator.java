package com.example.xletix.FRM.WorkoutSessions;

import com.example.xletix.FRM.Workouts.IWorkout;

public interface IWorkoutSessionIterator {
    IWorkoutSession getNext();
    boolean hasNext();

    interface IWorkoutSet extends IWorkout {
    }
}
