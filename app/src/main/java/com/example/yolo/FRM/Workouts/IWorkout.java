package com.example.yolo.FRM.Workouts;

import com.acoria.unittimer.unittimer_api.units.IExerciseDetails;
import com.acoria.unittimer.unittimer_api.units.IUnitProvider;
import com.example.yolo.xletix.Workouts.WorkoutType;

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
    List<IExerciseDetails> getTrainingUnits();
}
