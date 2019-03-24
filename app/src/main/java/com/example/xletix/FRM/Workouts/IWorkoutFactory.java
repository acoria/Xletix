package com.example.xletix.FRM.Workouts;

import com.example.xletix.FRM.Workouts.Workout;
import com.example.xletix.Workouts.WorkoutType;

public interface IWorkoutFactory {
    Workout createWorkoutByType(WorkoutType type);
}
