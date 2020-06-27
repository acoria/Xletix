package com.example.yolo.xletix.Workouts.BarbwireBattleWorkoutSession;

import com.acoria.unittimer.unittimer_api.units.IExerciseDetails;
import com.acoria.unittimer.unittimer_api.units.IUnitProvider;
import com.example.yolo.FRM.WorkoutSessions.IWorkoutSessionIterator;
import com.example.yolo.FRM.Workouts.Workout;
import com.example.yolo.R;
import com.example.yolo.xletix.Workouts.ExerciseDetails;
import com.example.yolo.xletix.Workouts.WorkoutType;
import com.example.yolo.xletix.Workouts.WorkoutUnitProvider;

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
    public List<IExerciseDetails> getTrainingUnits() {
        return Arrays.asList(
                ExerciseDetails.LEGUAN_WALK,
                ExerciseDetails.HIGH_PLANK,
                ExerciseDetails.X_SUPERMAN,
                ExerciseDetails.SIDE_PLANK,
                ExerciseDetails.X_BURPEES
        );
    }
}
