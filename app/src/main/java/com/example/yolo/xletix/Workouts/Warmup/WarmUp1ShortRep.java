package com.example.yolo.xletix.Workouts.Warmup;

import com.example.yolo.FRM.Units.IUnitProvider;
import com.example.yolo.R;
import com.example.yolo.xletix.Workouts.WorkoutType;

public class WarmUp1ShortRep extends WarmUp {

    private IUnitProvider unitProvider;

    public WarmUp1ShortRep(){
        super(1);
    }

    public IUnitProvider getUnitProvider(){
        return unitProvider;
    }

    protected IUnitProvider buildUnitProvider(){ return unitProvider = new WarmUpUnitProvider1ShortRep(getReps(), getTrainingUnits()); }


    @Override
    public WorkoutType getId() {
        return WorkoutType.warmUp1ShortRep;
    }

    @Override
    public int getName() {
        return R.string.warmUp1ShortRep;
    }
}
