package com.example.yolo.BackStretches;

import com.example.yolo.FRM.Units.IExerciseDetails;
import com.example.yolo.FRM.Units.IUnitProvider;
import com.example.yolo.FRM.Workouts.Workout;
import com.example.yolo.FRM.WorkoutSessions.IWorkoutSessionIterator;
import com.example.yolo.xletix.Workouts.ExerciseDetails;
import com.example.yolo.xletix.Workouts.WorkoutType;

import java.util.List;

public class BackStretchesWorkout extends Workout implements IWorkoutSessionIterator.IWorkoutSet {

    final String backStretchesWorkoutId = "backStretchesWorkoutId";
    public BackStretchesWorkout(){
        super(1);
    }

    @Override
    protected IUnitProvider buildUnitProvider() {
        return null;
    }

    @Override
    public WorkoutType getId() {
        return null;
//        return backStretchesWorkoutId;
    }

    @Override
    public int getName() {
        return 0;
    }

    @Override
    public List<IExerciseDetails> getTrainingUnits() {
        return null;
    }
}
