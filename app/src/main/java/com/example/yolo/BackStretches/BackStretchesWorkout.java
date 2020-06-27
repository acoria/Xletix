package com.example.yolo.BackStretches;

import com.acoria.unittimer.unittimer_api.units.IExerciseDetails;
import com.acoria.unittimer.unittimer_api.units.IUnitProvider;
import com.example.yolo.FRM.WorkoutSessions.IWorkoutSessionIterator;
import com.example.yolo.FRM.Workouts.Workout;
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
