package com.example.xletix.Builder;

import com.example.xletix.FRM.Workouts.IWorkout;
import com.example.xletix.FRM.WorkoutSessions.IWorkoutSession;
import com.example.xletix.FRM.WorkoutSessions.WorkoutSession;

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
