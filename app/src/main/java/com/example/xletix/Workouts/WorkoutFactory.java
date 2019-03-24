package com.example.xletix.Workouts;

import com.example.xletix.FRM.Workouts.IWorkoutFactory;
import com.example.xletix.FRM.Workouts.Workout;
import com.example.xletix.Workouts.BarbwireBattleWorkoutSession.BarbwireBattleWorkoutSet;
import com.example.xletix.Workouts.CoolDown.CoolDown;
import com.example.xletix.Workouts.InsaneInvertWorkoutSession.InsaneInvertWorkoutSet;
import com.example.xletix.Workouts.InstableIslandWorkoutSession.InstableIslandWorkoutSet;
import com.example.xletix.Workouts.PowerPipeWorkoutSession.PowerPipeWorkoutSet;
import com.example.xletix.Workouts.SlipperySlope.SlipperySlopeWorkoutSet;
import com.example.xletix.Workouts.TripleTraumaWorkoutSession.TripleTraumaWorkoutSet;
import com.example.xletix.Workouts.Warmup.WarmUp1ShortRep;
import com.example.xletix.Workouts.Warmup.WarmUp2Reps;

/**
 * Created by vtewes on 07.01.2018.
 */

public class WorkoutFactory implements IWorkoutFactory {

    @Override
    public Workout createWorkoutByType(WorkoutType type){
        Workout workout = null;

        switch(type){
            case warmUp1ShortRep:
                return new WarmUp1ShortRep();
            case warmUp2Reps:
                return new WarmUp2Reps();
            case coolDown:
                return new CoolDown();
            case powerPipeWorkoutSet:
                return new PowerPipeWorkoutSet();
            case insaneInvertWorkoutSet:
                return new InsaneInvertWorkoutSet();
            case slipperySlopeWorkoutSet:
                return new SlipperySlopeWorkoutSet();
            case instableIslandWorkoutSet:
                return new InstableIslandWorkoutSet();
            case tripleTraumaWorkoutSet:
                return new TripleTraumaWorkoutSet();
            case barbwireBattleWorkoutSet:
                return new BarbwireBattleWorkoutSet();
        }
        return workout;
    }
}
