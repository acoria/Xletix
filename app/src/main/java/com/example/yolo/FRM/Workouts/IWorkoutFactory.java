package com.example.yolo.FRM.Workouts;

import com.example.yolo.xletix.Workouts.WorkoutType;

public interface IWorkoutFactory {
    Workout createWorkoutByType(WorkoutType type);
}
