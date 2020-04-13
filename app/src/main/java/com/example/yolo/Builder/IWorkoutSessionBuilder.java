package com.example.yolo.Builder;

import com.example.yolo.FRM.WorkoutSessions.IWorkoutSessionIterator;
import com.example.yolo.FRM.Workouts.IWarmUp;
import com.example.yolo.FRM.Workouts.ICoolDown;

public interface IWorkoutSessionBuilder {
    IWarmUp buildWarmUp();
    IWorkoutSessionIterator.IWorkoutSet buildWorkoutSet();
    ICoolDown buildCoolDown();
    String getWorkoutSessionName();
}
