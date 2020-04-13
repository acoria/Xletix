package com.example.yolo.xletix.Workouts.TripleTraumaWorkoutSession;

import com.example.yolo.Builder.IWorkoutSessionBuilder;
import com.example.yolo.FRM.WorkoutSessions.IWorkoutSessionIterator;
import com.example.yolo.FRM.Workouts.ICoolDown;
import com.example.yolo.FRM.Workouts.IWarmUp;
import com.example.yolo.FRM.Workouts.IWorkoutFactory;
import com.example.yolo.xletix.Workouts.WorkoutFactory;
import com.example.yolo.xletix.Workouts.WorkoutType;

public class TripleTraumaWorkoutSessionBuilder implements IWorkoutSessionBuilder {

    IWorkoutFactory workoutFactory;

    public TripleTraumaWorkoutSessionBuilder(){
        this.workoutFactory = new WorkoutFactory();
    }
    public TripleTraumaWorkoutSessionBuilder(IWorkoutFactory workoutFactory){
        this.workoutFactory = workoutFactory;
    }

    @Override
    public IWarmUp buildWarmUp() { return (IWarmUp) workoutFactory.createWorkoutByType(WorkoutType.warmUp1ShortRep);}

    @Override
    public IWorkoutSessionIterator.IWorkoutSet buildWorkoutSet() { return (IWorkoutSessionIterator.IWorkoutSet) workoutFactory.createWorkoutByType(WorkoutType.tripleTraumaWorkoutSet);}

    @Override
    public ICoolDown buildCoolDown() { return (ICoolDown) workoutFactory.createWorkoutByType(WorkoutType.coolDown);}

    @Override
    public String getWorkoutSessionName() {
        return "Triple Trauma (S)";
    }
}
