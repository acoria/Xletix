package com.example.xletix.WorkoutSessions;

import com.example.xletix.FRM.WorkoutSessions.IWorkoutSession;
import com.example.xletix.FRM.WorkoutSessions.IWorkoutSessionFactory;
import com.example.xletix.FRM.WorkoutSessions.IWorkoutSessionProvider;

import java.util.ArrayList;
import java.util.List;

public class WorkoutSessionProvider implements IWorkoutSessionProvider {

    List<IWorkoutSession> workoutSessions;

    public WorkoutSessionProvider(IWorkoutSessionFactory sessionFactory){
        workoutSessions = new ArrayList<>();
        workoutSessions.add(sessionFactory.createBySessionType(WorkoutSessionType.slipperySlope));
        workoutSessions.add(sessionFactory.createBySessionType(WorkoutSessionType.tripleTrauma));
        workoutSessions.add(sessionFactory.createBySessionType(WorkoutSessionType.powerPipe));
        workoutSessions.add(sessionFactory.createBySessionType(WorkoutSessionType.insaneInvert));
        workoutSessions.add(sessionFactory.createBySessionType(WorkoutSessionType.instableIsland));
    }

    @Override
    public List<IWorkoutSession> getWorkoutSessions() {
        return workoutSessions;
    }
}
