package com.example.xletix.FRM.WorkoutSessions;

import com.example.xletix.FRM.WorkoutSessions.IWorkoutSession;
import com.example.xletix.WorkoutSessions.WorkoutSessionType;

public interface IWorkoutSessionFactory {
    IWorkoutSession createBySessionType(WorkoutSessionType sessionType);
}
