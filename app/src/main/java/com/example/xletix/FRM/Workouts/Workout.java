package com.example.xletix.FRM.Workouts;

import com.example.xletix.FRM.Units.IUnitProvider;
import com.example.xletix.FRM.Units.ITrainingUnit;

import java.util.List;

/**
 * Created by vtewes on 07.01.2018.
 */

public abstract class Workout implements IWorkout {

    IUnitProvider unitProvider;
    int reps;

    protected Workout(int reps){
        this.reps = reps;
        unitProvider = buildUnitProvider();
    }

    @Override
    public int getReps() {
        return reps;
    }

    @Override
    public int getTotalWorkoutLength(){
        int totalLength = 0;
        IUnitProvider unitProvider = getUnitProvider();
        List<ITrainingUnit> trainingUnits = unitProvider.getTrainingUnits();
        for (ITrainingUnit trainingUnit:trainingUnits) {
            totalLength += trainingUnit.getLength();
        }
        return totalLength;
    }

    protected abstract IUnitProvider buildUnitProvider();

    public IUnitProvider getUnitProvider(){
        return unitProvider;
    }

}
