package com.example.xletix.Workouts.CoolDown;

import com.example.xletix.FRM.Units.Exercise;
import com.example.xletix.FRM.Units.ITrainingUnit;
import com.example.xletix.FRM.Units.UnitProvider;
import com.example.xletix.FRM.Units.Break;
import com.example.xletix.Workouts.ExerciseDetails;

import java.util.List;

public class CoolDownUnitProvider extends UnitProvider {

    public CoolDownUnitProvider(int reps, List<ExerciseDetails> names){
        super(reps, names);
    }

    @Override
    protected void initialize() {
        List<ExerciseDetails> names = getTrainingUnitNames();
        for(int rep = 1; rep <= getReps(); rep++){
            for(int i = 0; i < names.size(); i++) {
                ExerciseDetails exerciseDetails = names.get(i);
                ITrainingUnit trainingUnit = new Exercise(exerciseDetails.getName(),20, exerciseDetails.isOneSided());
                trainingUnit.setInfoImage(exerciseDetails.getImageResource());
                addUnit(trainingUnit, rep);
                if(!isLastExercise(rep, names.size(), i)) {
                    addUnitToStack(new Break(10));
                }
            }
        }
    }
}
