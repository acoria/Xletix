package com.example.yolo.FRM.WorkoutSessions;

import com.example.yolo.FRM.Workouts.IWorkout;

public interface IWorkoutSessionIterator {
    IWorkoutSession getNext();
    boolean hasNext();

    interface IWorkoutSet extends IWorkout {
    }
}
