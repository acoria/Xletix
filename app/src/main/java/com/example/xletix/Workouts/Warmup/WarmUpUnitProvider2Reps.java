package com.example.xletix.Workouts.Warmup;

import com.example.xletix.FRM.Units.Exercise;
import com.example.xletix.FRM.Units.UnitProvider;
import com.example.xletix.FRM.Units.Break;
import com.example.xletix.FRM.Units.ITrainingUnit;
import com.example.xletix.Workouts.ExerciseDetails;

import java.util.List;

/**
 * Created by vtewes on 07.01.2018.
 */

public class WarmUpUnitProvider2Reps extends UnitProvider {

    public WarmUpUnitProvider2Reps(int reps, List<ExerciseDetails> names){
        super(reps, names);
    }

    @Override
    protected void initialize() {

        addUnitToStack(new Break("Get ready!", 3));
        List<ExerciseDetails> names = getTrainingUnitNames();

        for(int rep = 1; rep <= getReps(); rep++) {
            for (int i = 0; i < names.size(); i++) {
                ExerciseDetails exerciseDetails = names.get(i);
                ITrainingUnit trainingUnit = new Exercise(exerciseDetails.getName(), 30, exerciseDetails.isOneSided());
                trainingUnit.setInfoImage(exerciseDetails.getImageResource());
                addUnit(trainingUnit, rep);
                addUnitToStack(new Break(10));
            }
        }
    }
}
