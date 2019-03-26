package com.example.xletix.Workouts;

import com.example.xletix.FRM.Units.Break;
import com.example.xletix.FRM.Units.Exercise;
import com.example.xletix.FRM.Units.ITrainingUnit;
import com.example.xletix.FRM.Units.UnitProvider;

import java.util.List;

public class WorkoutUnitProvider extends UnitProvider {

    public WorkoutUnitProvider(int reps, List<ExerciseDetails> names){
        super(reps,names);
    }

    @Override
    protected void initialize() {
        List<ExerciseDetails> names = getTrainingUnitNames();
        for(int rep = 1; rep <= getReps(); rep++){
            for(int i = 0; i < names.size(); i++) {
                ExerciseDetails exerciseDetails = names.get(i);
                ITrainingUnit trainingUnit = new Exercise(exerciseDetails.getName(),30, exerciseDetails.isOneSided());
                trainingUnit.setInfoImage(exerciseDetails.getImageResource());
                addUnit(trainingUnit,rep);
                addUnitToStack(new Break(15));
            }
        }
    }

}
