package com.example.yolo.FRM.Workouts;

import com.example.yolo.FRM.Units.IExerciseDetails;
import com.example.yolo.FRM.Units.IUnitProvider;
import com.example.yolo.xletix.Workouts.ExerciseDetails;
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
