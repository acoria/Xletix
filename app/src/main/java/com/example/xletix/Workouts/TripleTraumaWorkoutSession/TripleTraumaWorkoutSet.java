package com.example.xletix.Workouts.TripleTraumaWorkoutSession;

import com.example.xletix.FRM.Units.IUnitProvider;
import com.example.xletix.FRM.WorkoutSessions.IWorkoutSessionIterator;
import com.example.xletix.FRM.Workouts.Workout;
import com.example.xletix.R;
import com.example.xletix.Workouts.TrainingUnitName;
import com.example.xletix.Workouts.WorkoutType;
import com.example.xletix.Workouts.WorkoutUnitProvider;

import java.util.Arrays;
import java.util.List;

public class TripleTraumaWorkoutSet extends Workout implements IWorkoutSessionIterator.IWorkoutSet {

    private IUnitProvider unitProvider;

    public TripleTraumaWorkoutSet(){
        super(3);
    }

    @Override
    public WorkoutType getId() {
        return WorkoutType.slipperySlopeWorkoutSet;
    }

    @Override
    public int getName() {
        return R.string.tripleTraumaWorkout;
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
    public List<TrainingUnitName> getTrainingUnits() {
        return Arrays.asList(
                TrainingUnitName.SPRINT_ON_SPOT,
                TrainingUnitName.SIDE_PLANK,
                TrainingUnitName.LUNGE_JUMPS,
                TrainingUnitName.SHOULDER_TAPS,
                TrainingUnitName.SPLIT_SQUAT
        );
    }
}
