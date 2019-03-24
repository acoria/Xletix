package com.example.xletix.WorkoutSessions;

import com.example.xletix.Builder.IWorkoutSessionBuilder;
import com.example.xletix.Builder.WorkoutSessionDirector;
import com.example.xletix.FRM.WorkoutSessions.IWorkoutSession;
import com.example.xletix.FRM.WorkoutSessions.IWorkoutSessionFactory;
import com.example.xletix.Workouts.BarbwireBattleWorkoutSession.BarbwireBattleWorkoutSessionBuilder;
import com.example.xletix.Workouts.InsaneInvertWorkoutSession.InsaneInvertWorkoutSessionBuilder;
import com.example.xletix.Workouts.InstableIslandWorkoutSession.InstableIslandWorkoutSessionBuilder;
import com.example.xletix.Workouts.PowerPipeWorkoutSession.PowerPipeWorkoutSessionBuilder;
import com.example.xletix.Workouts.SlipperySlope.SlipperySlopeWorkoutSessionBuilder;
import com.example.xletix.Workouts.TripleTraumaWorkoutSession.TripleTraumaWorkoutSessionBuilder;

public class WorkoutSessionFactory implements IWorkoutSessionFactory {

    private final String TAG = WorkoutSessionFactory.class.getSimpleName();

    @Override
    public IWorkoutSession createBySessionType(WorkoutSessionType sessionType) {
        IWorkoutSessionBuilder builder = null;
        switch(sessionType){
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
