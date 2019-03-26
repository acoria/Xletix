package com.example.xletix.Workouts.Warmup;

import com.example.xletix.FRM.Workouts.IWarmUp;
import com.example.xletix.FRM.Workouts.IWorkout;
import com.example.xletix.FRM.Workouts.Workout;
import com.example.xletix.Workouts.ExerciseDetails;

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
    public List<ExerciseDetails> getTrainingUnits() {
        return Arrays.asList(
                ExerciseDetails.JUMPING_JACKS,
                ExerciseDetails.GRIP_STRENGTH,
                ExerciseDetails.SHOULDER_MOBILITY,
                ExerciseDetails.HORIZONTAL_LUNGE_STRETCH,
                ExerciseDetails.HIGH_KNEES
        );
    }
}
