package com.example.yolo.xletix.Workouts.InsaneInvertWorkoutSession;

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
    public List<IExerciseDetails> getTrainingUnits() {
        return Arrays.asList(
                ExerciseDetails.KNEE_RAISES,
                ExerciseDetails.SQUAT_JUMPS,
                ExerciseDetails.SHOULDER_TAPS,
                ExerciseDetails.BICYCLE_CRUNCH,
                ExerciseDetails.X_SUPERMAN
        );
    }
}
