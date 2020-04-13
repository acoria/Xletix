package com.example.yolo.xletix.Workouts.InstableIslandWorkoutSession;

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

public class InstableIslandWorkoutSet extends Workout implements IWorkoutSessionIterator.IWorkoutSet {

    private IUnitProvider unitProvider;

    public InstableIslandWorkoutSet(){
        super(3);
    }

    @Override
    public WorkoutType getId() {
        return WorkoutType.instableIslandWorkoutSet;
    }

    @Override
    public int getName() {
        return R.string.instableIslandsWorkout;
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
                ExerciseDetails.SPLIT_SQUAT,
                ExerciseDetails.HIGH_PLANK,
                ExerciseDetails.SQUAT_JUMPS,
                ExerciseDetails.SIDE_PLANK,
                ExerciseDetails.LUNGE_JUMPS
        );
    }
}
