package com.example.xletix.Workouts.CoolDown;

import com.example.xletix.FRM.Units.UnitProvider;
import com.example.xletix.FRM.Units.Break;
import com.example.xletix.FRM.Units.Exercise;
import com.example.xletix.FRM.Units.ITrainingUnit;
import com.example.xletix.Workouts.TrainingUnitName;

import java.util.List;

public class CoolDownUnitProvider extends UnitProvider {

    public CoolDownUnitProvider(int reps, List<TrainingUnitName> names){
        super(reps, names);
    }

    @Override
    protected void initialize() {
        List<TrainingUnitName> names = getTrainingUnitNames();
        for(int rep = 0; rep < getReps(); rep++){
            for(int i = 0; i < names.size(); i++) {
                ITrainingUnit trainingUnit = new Exercise(names.get(i).getName(),20);
                trainingUnit.setInfoImage(names.get(i).getImageResource());
                addUnitToStack(trainingUnit);
                if(!isLastExercise(rep, names.size(), i)) {
                    addUnitToStack(new Break(10));
                }
            }
        }
    }
}
