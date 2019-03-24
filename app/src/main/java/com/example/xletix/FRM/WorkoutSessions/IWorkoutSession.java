package com.example.xletix.FRM.WorkoutSessions;

import com.example.xletix.FRM.Units.IUnitProvider;
import com.example.xletix.FRM.Workouts.IWorkout;

import java.util.List;

public interface IWorkoutSession {

    List<IWorkout> getWorkouts();
    String getName();
    IUnitProvider getSessionUnitProvider();
}
