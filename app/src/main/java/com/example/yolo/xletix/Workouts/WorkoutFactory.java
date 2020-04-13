package com.example.yolo.xletix.Workouts;

import com.example.yolo.FRM.Workouts.IWorkoutFactory;
import com.example.yolo.FRM.Workouts.Workout;
import com.example.yolo.xletix.Workouts.BarbwireBattleWorkoutSession.BarbwireBattleWorkoutSet;
import com.example.yolo.xletix.Workouts.CoolDown.CoolDown;
import com.example.yolo.xletix.Workouts.InsaneInvertWorkoutSession.InsaneInvertWorkoutSet;
import com.example.yolo.xletix.Workouts.InstableIslandWorkoutSession.InstableIslandWorkoutSet;
import com.example.yolo.xletix.Workouts.PowerPipeWorkoutSession.PowerPipeWorkoutSet;
import com.example.yolo.xletix.Workouts.SlipperySlope.SlipperySlopeWorkoutSet;
import com.example.yolo.xletix.Workouts.TripleTraumaWorkoutSession.TripleTraumaWorkoutSet;
import com.example.yolo.xletix.Workouts.Warmup.WarmUp1ShortRep;
import com.example.yolo.xletix.Workouts.Warmup.WarmUp2Reps;

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
