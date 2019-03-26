package com.example.xletix.Workouts.SlipperySlope;

import com.example.xletix.FRM.Units.IUnitProvider;
import com.example.xletix.FRM.WorkoutSessions.IWorkoutSessionIterator;
import com.example.xletix.FRM.Workouts.Workout;
import com.example.xletix.R;
import com.example.xletix.Workouts.ExerciseDetails;
import com.example.xletix.Workouts.WorkoutType;
import com.example.xletix.Workouts.WorkoutUnitProvider;

import java.util.Arrays;
import java.util.List;

public class SlipperySlopeWorkoutSet extends Workout implements IWorkoutSessionIterator.IWorkoutSet {

    private IUnitProvider unitProvider;

    public SlipperySlopeWorkoutSet(){
        super(3);
    }

    @Override
    public WorkoutType getId() {
        return WorkoutType.slipperySlopeWorkoutSet;
    }

    @Override
    public int getName() {
        return R.string.slipperySlopeWorkout;
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
                ExerciseDetails.WALL_SIT,
                ExerciseDetails.PUSH_UPS,
                ExerciseDetails.SQUAT_JUMPS,
                ExerciseDetails.X_SUPERMAN,
                ExerciseDetails.LUNGE_JUMPS
        );
    }
}
