package com.example.yolo.xletix.WorkoutSessions;

import com.example.yolo.Builder.IWorkoutSessionBuilder;
import com.example.yolo.Builder.WorkoutSessionDirector;
import com.example.yolo.FRM.WorkoutSessions.IWorkoutSession;
import com.example.yolo.FRM.WorkoutSessions.IWorkoutSessionFactory;
import com.example.yolo.FRM.WorkoutSessions.IWorkoutSessionType;
import com.example.yolo.xletix.Workouts.BarbwireBattleWorkoutSession.BarbwireBattleWorkoutSessionBuilder;
import com.example.yolo.xletix.Workouts.InsaneInvertWorkoutSession.InsaneInvertWorkoutSessionBuilder;
import com.example.yolo.xletix.Workouts.InstableIslandWorkoutSession.InstableIslandWorkoutSessionBuilder;
import com.example.yolo.xletix.Workouts.PowerPipeWorkoutSession.PowerPipeWorkoutSessionBuilder;
import com.example.yolo.xletix.Workouts.SlipperySlope.SlipperySlopeWorkoutSessionBuilder;
import com.example.yolo.xletix.Workouts.TripleTraumaWorkoutSession.TripleTraumaWorkoutSessionBuilder;

public class WorkoutSessionFactory implements IWorkoutSessionFactory {

    private final String TAG = WorkoutSessionFactory.class.getSimpleName();

    @Override
    public IWorkoutSession createBySessionType(IWorkoutSessionType sessionType) {
        IWorkoutSessionBuilder builder = null;
        switch((WorkoutSessionTypeXletix) sessionType){
            case powerPipe:
                builder = new PowerPipeWorkoutSessionBuilder();
                break;
            case insaneInvert:
                builder = new InsaneInvertWorkoutSessionBuilder();
                break;
            case slipperySlope:
                builder = new SlipperySlopeWorkoutSessionBuilder();
                break;
            case instableIsland:
                builder = new InstableIslandWorkoutSessionBuilder();
                break;
            case tripleTrauma:
                builder = new TripleTraumaWorkoutSessionBuilder();
                break;
            case barbwireBattle:
                builder = new BarbwireBattleWorkoutSessionBuilder();
                break;
        }
        return new WorkoutSessionDirector(builder).build();
    }
}
