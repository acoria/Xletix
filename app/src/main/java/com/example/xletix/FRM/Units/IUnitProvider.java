package com.example.xletix.FRM.Units;

import com.example.xletix.Workouts.ExerciseDetails;

import java.util.List;

/**
 * Created by vtewes on 07.01.2018.
 */

public interface IUnitProvider {
    ITrainingUnit getFirst();
    ITrainingUnit getLast();
    ITrainingUnit getUnitById(String id);
    void resetByTrainingUnit(ITrainingUnit trainingUnit);
    void reset();
    ITrainingUnit getPredecessor();
    ITrainingUnit getSuccessor();
    boolean hasPredecessor();
    boolean hasSuccessor();
    int getCurrentExercisePosition();
    int getNumberOfExercises();
    List<ITrainingUnit> getTrainingUnits();
    List<ExerciseDetails> getTrainingUnitNames();
    int getTotalLength();
    ITrainingUnit getSneakSuccessor();
    ITrainingUnit getSneakPredecessor();
}
