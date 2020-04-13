package com.example.yolo.xletix.Workouts.TripleTraumaWorkoutSession;

import com.example.yolo.FRM.Units.IExerciseDetails;
import com.example.yolo.FRM.Units.IUnitProvider;
import com.example.yolo.FRM.WorkoutSessions.IWorkoutSessionIterator;
import com.example.yolo.FRM.Workouts.Workout;
import com.example.yolo.R;
import com.example.yolo.xletix.Workouts.ExerciseDetails;
import com.example.yolo.xletix.Workouts.WorkoutType;
import com.example.yolo.xletix.Workouts.WorkoutUnitProvider;

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
    public List<IExerciseDetails> getTrainingUnits() {
        return Arrays.asList(
                ExerciseDetails.SPRINT_ON_SPOT,
                ExerciseDetails.SIDE_PLANK,
                ExerciseDetails.LUNGE_JUMPS,
                ExerciseDetails.SHOULDER_TAPS,
                ExerciseDetails.SPLIT_SQUAT
        );
    }
}
