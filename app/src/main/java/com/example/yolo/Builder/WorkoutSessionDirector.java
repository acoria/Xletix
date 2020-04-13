package com.example.yolo.Builder;

import com.example.yolo.FRM.Workouts.IWorkout;
import com.example.yolo.FRM.WorkoutSessions.IWorkoutSession;
import com.example.yolo.FRM.WorkoutSessions.WorkoutSession;

import java.util.ArrayList;
import java.util.List;

public class WorkoutSessionDirector implements IWorkoutSessionDirector {

    private final IWorkoutSessionBuilder builder;

    public WorkoutSessionDirector(IWorkoutSessionBuilder builder){
        this.builder = builder;
    }

    @Override
    public IWorkoutSession build() {
        List<IWorkout> workouts = new ArrayList();
        workouts.add(builder.buildWarmUp());
        workouts.add(builder.buildWorkoutSet());
        workouts.add(builder.buildCoolDown());
        return new WorkoutSession(builder.getWorkoutSessionName(),workouts);
    }
}
