package com.example.yolo.xletix.Workouts.Warmup;

import com.example.yolo.FRM.Units.Exercise;
import com.example.yolo.FRM.Units.IExerciseDetails;
import com.example.yolo.FRM.Units.UnitProvider;
import com.example.yolo.FRM.Units.Break;
import com.example.yolo.FRM.Units.IUnit;
import com.example.yolo.xletix.Workouts.ExerciseDetails;

import java.util.List;

/**
 * Created by vtewes on 07.01.2018.
 */

public class WarmUpUnitProvider1ShortRep extends UnitProvider {

    public WarmUpUnitProvider1ShortRep(int reps, List<IExerciseDetails> names){
        super(reps, names);
    }

    @Override
    protected void initialize() {
        addUnitToStack(new Break("Get ready!", 3));
        List<IExerciseDetails> names = getUnitNames();

        for(int rep = 1; rep <= getReps(); rep++){
            for(int i = 0; i < names.size(); i++) {
                IExerciseDetails exerciseDetails = names.get(i);
                IUnit trainingUnit = new Exercise(exerciseDetails.getName(),20, exerciseDetails.isOneSided());
                trainingUnit.setInfoImage(exerciseDetails.getImageResource());
                addUnit(trainingUnit,rep);
                addUnitToStack(new Break(10));
            }
        }
    }
}
