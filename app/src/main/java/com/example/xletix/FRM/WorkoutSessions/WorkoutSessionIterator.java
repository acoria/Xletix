package com.example.xletix.FRM.WorkoutSessions;

import java.util.List;

public class WorkoutSessionIterator implements IWorkoutSessionIterator {

    List<IWorkoutSession> workoutSessions;


    public WorkoutSessionIterator(IWorkoutSessionProvider workoutProvider){
        workoutSessions = workoutProvider.getWorkoutSessions();
    }

    @Override
    public IWorkoutSession getNext() {
        IWorkoutSession workoutSession = workoutSessions.get(0);
        workoutSessions.remove(0);
        return workoutSession;
    }

    @Override
    public boolean hasNext() {
        return workoutSessions.size() > 0;
    }
}
