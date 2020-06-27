package com.example.yolo.FRM.WorkoutSessions;

import com.acoria.unittimer.unittimer_api.units.IUnitProvider;
import com.example.yolo.FRM.Workouts.IWorkout;

import java.util.List;

public interface IWorkoutSession {

    List<IWorkout> getWorkouts();
    String getName();
    IUnitProvider getSessionUnitProvider();
}
