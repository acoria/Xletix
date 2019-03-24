package com.example.xletix.Workouts.Warmup;

import com.example.xletix.FRM.Units.IUnitProvider;
import com.example.xletix.R;
import com.example.xletix.Workouts.WorkoutType;

public class WarmUp2Reps extends WarmUp {

    private IUnitProvider unitProvider;

    public WarmUp2Reps(){
        super(2);
    }

    public IUnitProvider getUnitProvider(){
        return unitProvider;
    }

    protected IUnitProvider buildUnitProvider(){ return unitProvider = new WarmUpUnitProvider2Reps(getReps(), getTrainingUnits()); }


    @Override
    public WorkoutType getId() {
        return WorkoutType.warmUp2Reps;
    }

    @Override
    public int getName() {
        return R.string.warmUp2Reps;
    }
}
