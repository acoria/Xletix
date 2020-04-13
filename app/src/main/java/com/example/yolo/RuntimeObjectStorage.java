package com.example.yolo;

import com.example.yolo.FRM.WorkoutSessions.IWorkoutSession;

/**
 * Created by vtewes on 07.01.2018.
 */

public class RuntimeObjectStorage {
    private static IWorkoutSession workoutSession;

    public static IWorkoutSession getWorkoutSession() {
        return workoutSession;
    }
    public static void setWorkoutSession(IWorkoutSession workoutSession) {
        RuntimeObjectStorage.workoutSession = workoutSession;
    }
}
