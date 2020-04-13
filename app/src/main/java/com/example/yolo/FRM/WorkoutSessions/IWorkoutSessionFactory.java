package com.example.yolo.FRM.WorkoutSessions;

public interface IWorkoutSessionFactory {
    IWorkoutSession createBySessionType(IWorkoutSessionType sessionType);
}
