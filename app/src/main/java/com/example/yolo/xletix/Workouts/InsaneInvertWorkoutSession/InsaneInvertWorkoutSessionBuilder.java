package com.example.yolo.xletix.Workouts.InsaneInvertWorkoutSession;

import com.example.yolo.Builder.IWorkoutSessionBuilder;
import com.example.yolo.FRM.WorkoutSessions.IWorkoutSessionIterator;
import com.example.yolo.FRM.Workouts.ICoolDown;
import com.example.yolo.FRM.Workouts.IWarmUp;
import com.example.yolo.FRM.Workouts.IWorkoutFactory;
import com.example.yolo.xletix.Workouts.WorkoutFactory;
import com.example.yolo.xletix.Workouts.WorkoutType;

public class InsaneInvertWorkoutSessionBuilder implements IWorkoutSessionBuilder {

    IWorkoutFactory workoutFactory;

    public InsaneInvertWorkoutSessionBuilder(){
        this.workoutFactory = new WorkoutFactory();
    }
    public InsaneInvertWorkoutSessionBuilder(IWorkoutFactory workoutFactory){
        this.workoutFactory = workoutFactory;
    }

    @Override
    public IWarmUp buildWarmUp() { return (IWarmUp) workoutFactory.createWorkoutByType(WorkoutType.warmUp1ShortRep);}

    @Override
    public IWorkoutSessionIterator.IWorkoutSet buildWorkoutSet() { return (IWorkoutSessionIterator.IWorkoutSet) workoutFactory.createWorkoutByType(WorkoutType.insaneInvertWorkoutSet);}

    @Override
    public ICoolDown buildCoolDown() { return (ICoolDown) workoutFactory.createWorkoutByType(WorkoutType.coolDown);}

    @Override
    public String getWorkoutSessionName() {
        return "Insane Invert (L)";
    }
}
