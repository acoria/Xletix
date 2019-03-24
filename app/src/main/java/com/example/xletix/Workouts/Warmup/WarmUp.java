package com.example.xletix.Workouts.Warmup;

import com.example.xletix.FRM.Workouts.IWarmUp;
import com.example.xletix.FRM.Workouts.IWorkout;
import com.example.xletix.FRM.Workouts.Workout;
import com.example.xletix.Workouts.TrainingUnitName;

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
    public List<TrainingUnitName> getTrainingUnits() {
        return Arrays.asList(
                TrainingUnitName.JUMPING_JACKS,
                TrainingUnitName.GRIP_STRENGTH,
                TrainingUnitName.SHOULDER_MOBILITY,
                TrainingUnitName.HORIZONTAL_LUNGE_STRETCH,
                TrainingUnitName.HIGH_KNEES
        );
    }
}
