package com.example.xletix.Builder;

import com.example.xletix.FRM.WorkoutSessions.IWorkoutSessionIterator;
import com.example.xletix.FRM.Workouts.IWarmUp;
import com.example.xletix.FRM.Workouts.ICoolDown;

public interface IWorkoutSessionBuilder {
    IWarmUp buildWarmUp();
    IWorkoutSessionIterator.IWorkoutSet buildWorkoutSet();
    ICoolDown buildCoolDown();
    String getWorkoutSessionName();
}
