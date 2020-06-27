package com.example.yolo.xletix.Workouts.Warmup;

import com.acoria.unittimer.unittimer_api.units.IExerciseDetails;
import com.example.yolo.FRM.Workouts.IWarmUp;
import com.example.yolo.FRM.Workouts.IWorkout;
import com.example.yolo.FRM.Workouts.Workout;
import com.example.yolo.xletix.Workouts.ExerciseDetails;

import java.util.Arrays;
import java.util.List;

/**
 * Created by vtewes on 07.01.2018.
 * Test
 */

public abstract class WarmUp extends Workout implements IWorkout, IWarmUp {

    protected WarmUp(int reps){
        super(reps);
    }

    @Override
    public List<IExerciseDetails> getTrainingUnits() {
        return Arrays.asList(
                ExerciseDetails.JUMPING_JACKS,
                ExerciseDetails.GRIP_STRENGTH,
                ExerciseDetails.SHOULDER_MOBILITY,
                ExerciseDetails.HORIZONTAL_LUNGE_STRETCH,
                ExerciseDetails.HIGH_KNEES
        );
    }
}
