package com.example.yolo.xletix.Workouts.PowerPipeWorkoutSession;

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
    public List<IExerciseDetails> getTrainingUnits() {
        return Arrays.asList(
                ExerciseDetails.SPRINT_ON_SPOT,
                ExerciseDetails.SQUAT_JUMPS,
                ExerciseDetails.SIDE_PLANK,
                ExerciseDetails.X_BURPEES,
                ExerciseDetails.LUNGE_JUMPS
        );
    }
}
