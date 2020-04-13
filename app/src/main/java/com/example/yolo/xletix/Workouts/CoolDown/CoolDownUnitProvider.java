package com.example.yolo.xletix.Workouts.CoolDown;

import com.example.yolo.FRM.Units.Exercise;
import com.example.yolo.FRM.Units.IExerciseDetails;
import com.example.yolo.FRM.Units.IUnit;
import com.example.yolo.FRM.Units.UnitProvider;
import com.example.yolo.FRM.Units.Break;
import com.example.yolo.xletix.Workouts.ExerciseDetails;

import java.util.List;

public class CoolDownUnitProvider extends UnitProvider {

    public CoolDownUnitProvider(int reps, List<IExerciseDetails> names){
        super(reps, names);
    }

    @Override
    protected void initialize() {
        List<IExerciseDetails> names = getUnitNames();
        for(int rep = 1; rep <= getReps(); rep++){
            for(int i = 0; i < names.size(); i++) {
                IExerciseDetails exerciseDetails = names.get(i);
                IUnit trainingUnit = new Exercise(exerciseDetails.getName(),20, exerciseDetails.isOneSided());
                trainingUnit.setInfoImage(exerciseDetails.getImageResource());
                addUnit(trainingUnit, rep);
                if(!isLastExercise(rep, names.size(), i)) {
                    addUnitToStack(new Break(10));
                }
            }
        }
    }
}
