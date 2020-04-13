package com.example.yolo.FRM.Units;

import com.example.yolo.xletix.Workouts.ExerciseDetails;

import java.util.List;

/**
 * Created by vtewes on 07.01.2018.
 */

public interface IUnitProvider {
    IUnit getFirst();
    IUnit getLast();
    IUnit getUnitById(String id);
    void resetByUnit(IUnit unit);
    void reset();
    IUnit getPredecessor();
    IUnit getSuccessor();
    boolean hasPredecessor();
    boolean hasSuccessor();
    int getCurrentExercisePosition();
    IUnit getCurrentUnit();
    int getNumberOfExercises();
    List<IUnit> getUnits();
    List<IExerciseDetails> getUnitNames();
    int getTotalLength();
    IUnit getSneakSuccessor();
    IUnit getSneakPredecessor();
}
