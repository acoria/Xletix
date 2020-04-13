package com.example.yolo.xletix.WorkoutSessions;

import com.example.yolo.FRM.WorkoutSessions.IWorkoutSession;
import com.example.yolo.FRM.WorkoutSessions.IWorkoutSessionFactory;
import com.example.yolo.FRM.WorkoutSessions.IWorkoutSessionProvider;

import java.util.ArrayList;
import java.util.List;

public class WorkoutSessionProvider implements IWorkoutSessionProvider {

    List<IWorkoutSession> workoutSessions;

    public WorkoutSessionProvider(IWorkoutSessionFactory sessionFactory){
        workoutSessions = new ArrayList<>();
        workoutSessions.add(sessionFactory.createBySessionType(WorkoutSessionTypeXletix.barbwireBattle));
        workoutSessions.add(sessionFactory.createBySessionType(WorkoutSessionTypeXletix.slipperySlope));
        workoutSessions.add(sessionFactory.createBySessionType(WorkoutSessionTypeXletix.tripleTrauma));
        workoutSessions.add(sessionFactory.createBySessionType(WorkoutSessionTypeXletix.powerPipe));
        workoutSessions.add(sessionFactory.createBySessionType(WorkoutSessionTypeXletix.insaneInvert));
        workoutSessions.add(sessionFactory.createBySessionType(WorkoutSessionTypeXletix.instableIsland));
    }

    @Override
    public List<IWorkoutSession> getWorkoutSessions() {
        return workoutSessions;
    }
}
