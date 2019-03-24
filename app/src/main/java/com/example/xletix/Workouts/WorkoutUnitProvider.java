package com.example.xletix.Workouts;

import com.example.xletix.FRM.Units.Break;
import com.example.xletix.FRM.Units.Exercise;
import com.example.xletix.FRM.Units.ITrainingUnit;
import com.example.xletix.FRM.Units.UnitProvider;

import java.util.List;

public class WorkoutUnitProvider extends UnitProvider {

    public WorkoutUnitProvider(int reps, List<TrainingUnitName> names){
        super(reps,names);
    }

    @Override
    protected void initialize() {
        List<TrainingUnitName> names = getTrainingUnitNames();
        for(int rep = 0; rep < getReps(); rep++){
            for(int i = 0; i < names.size(); i++) {
                ITrainingUnit trainingUnit = new Exercise(names.get(i).getName(),30);
                trainingUnit.setInfoImage(names.get(i).getImageResource());
                addUnitToStack(trainingUnit);
                addUnitToStack(new Break(15));
            }
        }
    }
}
