package com.example.xletix.FRM.WorkoutSessions;

import com.example.xletix.FRM.Units.IUnitProvider;
import com.example.xletix.FRM.Workouts.IWorkout;
import com.example.xletix.WorkoutSessions.WorkoutSessionUnitProvider;

import java.util.List;

public class WorkoutSession implements IWorkoutSession {

    private final List<IWorkout> workouts;
    private String name;

    public WorkoutSession(
            String name,
            List<IWorkout> workouts){
        this.name = name;
        this.workouts = workouts;
    }

    @Override
    public List<IWorkout> getWorkouts() {
        return workouts;
    }

    @Override
    public IUnitProvider getSessionUnitProvider() {
        return new WorkoutSessionUnitProvider(this);
    }

    @Override
    public String getName() {
        return name;
    }
}
