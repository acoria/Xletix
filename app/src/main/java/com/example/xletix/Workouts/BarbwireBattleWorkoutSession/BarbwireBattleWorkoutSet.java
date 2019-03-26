package com.example.xletix.Workouts.BarbwireBattleWorkoutSession;

import com.example.xletix.FRM.Units.IUnitProvider;
import com.example.xletix.FRM.WorkoutSessions.IWorkoutSessionIterator;
import com.example.xletix.FRM.Workouts.Workout;
import com.example.xletix.R;
import com.example.xletix.Workouts.ExerciseDetails;
import com.example.xletix.Workouts.WorkoutType;
import com.example.xletix.Workouts.WorkoutUnitProvider;

import java.util.Arrays;
import java.util.List;

public class BarbwireBattleWorkoutSet extends Workout implements IWorkoutSessionIterator.IWorkoutSet {

    private IUnitProvider unitProvider;

    public BarbwireBattleWorkoutSet(){
        super(3);
    }

    @Override
    public WorkoutType getId() {
        return WorkoutType.barbwireBattleWorkoutSet;
    }

    @Override
    public int getName() {
        return R.string.barbwireBattleWorkout;
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
                ExerciseDetails.LEGUAN_WALK,
                ExerciseDetails.HIGH_PLANK,
                ExerciseDetails.X_SUPERMAN,
                ExerciseDetails.SIDE_PLANK,
                ExerciseDetails.X_BURPEES
        );
    }
}
