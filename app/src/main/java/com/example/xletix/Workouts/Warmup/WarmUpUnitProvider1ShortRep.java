package com.example.xletix.Workouts.Warmup;

import com.example.xletix.FRM.Units.UnitProvider;
import com.example.xletix.FRM.Units.Break;
import com.example.xletix.FRM.Units.Exercise;
import com.example.xletix.FRM.Units.ITrainingUnit;
import com.example.xletix.Workouts.TrainingUnitName;

import java.util.List;

/**
 * Created by vtewes on 07.01.2018.
 */

public class WarmUpUnitProvider1ShortRep extends UnitProvider {

    public WarmUpUnitProvider1ShortRep(int reps, List<TrainingUnitName> names){
        super(reps, names);
    }

    @Override
    protected void initialize() {
        addUnitToStack(new Exercise("Get ready!", 3));
        List<TrainingUnitName> names = getTrainingUnitNames();

        for(int rep = 0; rep < getReps(); rep++){
            for(int i = 0; i < names.size(); i++) {
                ITrainingUnit trainingUnit = new Exercise(names.get(i).getName(),20);
                trainingUnit.setInfoImage(names.get(i).getImageResource());
                addUnitToStack(trainingUnit);
                addUnitToStack(new Break(10));
            }
        }
    }
}
