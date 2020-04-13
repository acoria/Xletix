package com.example.yolo.FRM.WorkoutSessions;

import com.example.yolo.FRM.Units.IUnitProvider;
import com.example.yolo.FRM.Workouts.IWorkout;

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
