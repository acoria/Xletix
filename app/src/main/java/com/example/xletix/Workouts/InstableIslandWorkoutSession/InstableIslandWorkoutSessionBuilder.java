package com.example.xletix.Workouts.InstableIslandWorkoutSession;

import com.example.xletix.Builder.IWorkoutSessionBuilder;
import com.example.xletix.FRM.WorkoutSessions.IWorkoutSessionIterator;
import com.example.xletix.FRM.Workouts.ICoolDown;
import com.example.xletix.FRM.Workouts.IWarmUp;
import com.example.xletix.FRM.Workouts.IWorkoutFactory;
import com.example.xletix.Workouts.WorkoutFactory;
import com.example.xletix.Workouts.WorkoutType;

public class InstableIslandWorkoutSessionBuilder implements IWorkoutSessionBuilder {

    IWorkoutFactory workoutFactory;

    public InstableIslandWorkoutSessionBuilder(){
        this.workoutFactory = new WorkoutFactory();
    }
    public InstableIslandWorkoutSessionBuilder(IWorkoutFactory workoutFactory){
        this.workoutFactory = workoutFactory;
    }

    @Override
    public IWarmUp buildWarmUp() { return (IWarmUp) workoutFactory.createWorkoutByType(WorkoutType.warmUp2Reps);}

    @Override
    public IWorkoutSessionIterator.IWorkoutSet buildWorkoutSet() { return (IWorkoutSessionIterator.IWorkoutSet) workoutFactory.createWorkoutByType(WorkoutType.instableIslandWorkoutSet);}

    @Override
    public ICoolDown buildCoolDown() { return (ICoolDown) workoutFactory.createWorkoutByType(WorkoutType.coolDown);}

    @Override
    public String getWorkoutSessionName() {
        return "Instable Islands (L)";
    }
}
