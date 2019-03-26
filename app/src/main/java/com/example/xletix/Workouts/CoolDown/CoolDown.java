package com.example.xletix.Workouts.CoolDown;

import com.example.xletix.FRM.Workouts.ICoolDown;
import com.example.xletix.FRM.Units.IUnitProvider;
import com.example.xletix.FRM.Workouts.Workout;
import com.example.xletix.R;
import com.example.xletix.Workouts.ExerciseDetails;
import com.example.xletix.Workouts.WorkoutType;

import java.util.Arrays;
import java.util.List;

public class CoolDown extends Workout implements ICoolDown {

    private IUnitProvider unitProvider;

    public CoolDown(){
        super(1);
    }

    @Override
    public List<ExerciseDetails> getTrainingUnits() {
        return Arrays.asList(
                ExerciseDetails.SIDE_TOUCHES,
                ExerciseDetails.GRIP_STRENGTH,
                ExerciseDetails.SHOULDER_MOBILITY,
                ExerciseDetails.HIGH_KNEE_STRETCHES,
                ExerciseDetails.VERTICAL_LUNGE_STRETCH
        );
    }

    @Override
    public WorkoutType getId() {
        return WorkoutType.coolDown;
    }

    @Override
    public int getName() {
        return R.string.coolDown;
    }

    @Override
    protected IUnitProvider buildUnitProvider() {
        return unitProvider = new CoolDownUnitProvider(getReps(), getTrainingUnits());
    }

    @Override
    public IUnitProvider getUnitProvider() {
        return unitProvider;
    }
}
