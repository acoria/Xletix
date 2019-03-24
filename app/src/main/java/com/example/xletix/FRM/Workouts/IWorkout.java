package com.example.xletix.FRM.Workouts;

import com.example.xletix.FRM.Units.IUnitProvider;
import com.example.xletix.Workouts.TrainingUnitName;
import com.example.xletix.Workouts.WorkoutType;

import java.util.List;

/**
 * Created by vtewes on 07.01.2018.
 */

public interface IWorkout {

    IUnitProvider getUnitProvider();
    WorkoutType getId();
    int getName();
    int getTotalWorkoutLength();
    int getReps();
    List<TrainingUnitName> getTrainingUnits();
}
