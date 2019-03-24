package com.example.xletix.Workouts.InsaneInvertWorkoutSession;

import com.example.xletix.FRM.Units.IUnitProvider;
import com.example.xletix.FRM.WorkoutSessions.IWorkoutSessionIterator;
import com.example.xletix.FRM.Workouts.Workout;
import com.example.xletix.R;
import com.example.xletix.Workouts.TrainingUnitName;
import com.example.xletix.Workouts.WorkoutType;
import com.example.xletix.Workouts.WorkoutUnitProvider;

import java.util.Arrays;
import java.util.List;

public class InsaneInvertWorkoutSet extends Workout implements IWorkoutSessionIterator.IWorkoutSet {

    private IUnitProvider unitProvider;

    public InsaneInvertWorkoutSet(){
        super(3);
    }

    @Override
    public WorkoutType getId() {
        return WorkoutType.insaneInvertWorkoutSet;
    }

    @Override
    public int getName() {
        return R.string.insaneInvertWorkout;
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
                TrainingUnitName.KNEE_RAISES,
                TrainingUnitName.SQUAT_JUMPS,
                TrainingUnitName.SHOULDER_TAPS,
                TrainingUnitName.BICYCLE_CRUNCH,
                TrainingUnitName.X_SUPERMAN
        );
    }
}
