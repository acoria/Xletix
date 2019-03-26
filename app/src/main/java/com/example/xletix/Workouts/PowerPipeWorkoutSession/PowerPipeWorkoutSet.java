package com.example.xletix.Workouts.PowerPipeWorkoutSession;

import com.example.xletix.FRM.WorkoutSessions.IWorkoutSessionIterator;
import com.example.xletix.FRM.Units.IUnitProvider;
import com.example.xletix.FRM.Workouts.Workout;
import com.example.xletix.R;
import com.example.xletix.Workouts.ExerciseDetails;
import com.example.xletix.Workouts.WorkoutType;
import com.example.xletix.Workouts.WorkoutUnitProvider;

import java.util.Arrays;
import java.util.List;

public class PowerPipeWorkoutSet extends Workout implements IWorkoutSessionIterator.IWorkoutSet {

    private IUnitProvider unitProvider;

    public PowerPipeWorkoutSet(){
        super(3);
    }

    @Override
    public WorkoutType getId() {
        return WorkoutType.powerPipeWorkoutSet;
    }

    @Override
    public int getName() {
        return R.string.powerPipeWorkout;
    }

    @Override
    protected IUnitProvider buildUnitProvider() {
        return unitProvider = new WorkoutUnitProvider(getReps(), getTrainingUnits());
    }

    @Override
    public IUnitProvider getUnitProvider() {
        return unitProvider;
    }

    @Override
    public List<ExerciseDetails> getTrainingUnits() {
        return Arrays.asList(
                ExerciseDetails.SPRINT_ON_SPOT,
                ExerciseDetails.SQUAT_JUMPS,
                ExerciseDetails.SIDE_PLANK,
                ExerciseDetails.X_BURPEES,
                ExerciseDetails.LUNGE_JUMPS
        );
    }
}
