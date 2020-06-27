package com.example.yolo.xletix.Workouts.CoolDown;

import com.acoria.unittimer.unittimer_api.units.IExerciseDetails;
import com.acoria.unittimer.unittimer_api.units.IUnitProvider;
import com.example.yolo.FRM.Workouts.ICoolDown;
import com.example.yolo.FRM.Workouts.Workout;
import com.example.yolo.R;
import com.example.yolo.xletix.Workouts.ExerciseDetails;
import com.example.yolo.xletix.Workouts.WorkoutType;

import java.util.Arrays;
import java.util.List;

public class CoolDown extends Workout implements ICoolDown {

    private IUnitProvider unitProvider;

    public CoolDown(){
        super(1);
    }

    @Override
    public List<IExerciseDetails> getTrainingUnits() {
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
