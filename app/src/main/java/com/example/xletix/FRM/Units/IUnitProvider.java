package com.example.xletix.FRM.Units;

import com.example.xletix.Workouts.TrainingUnitName;

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
    List<ITrainingUnit> getTrainingUnits();
    List<TrainingUnitName> getTrainingUnitNames();
    int getTotalLength();
    ITrainingUnit getSneakSuccessor();
    ITrainingUnit getSneakPredecessor();
}
