package com.example.xletix.FRM.Units;

import android.util.Log;

import com.example.xletix.Workouts.TrainingUnitName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vtewes on 08.05.2016.
 */
public abstract class UnitProvider implements IUnitProvider {

    static final String TAG = UnitProvider.class.getSimpleName();
    private final List<TrainingUnitName> names;
    private List<ITrainingUnit> trainingUnits = new ArrayList();
    private int reps;
    private int currentPosition = -1;
    private int length;

    public UnitProvider(int reps, List<TrainingUnitName> names){
        this.reps = reps;
        this.names = names;
        initialize();
    }

    abstract protected void initialize();

    @Override
    public void resetByTrainingUnit(ITrainingUnit trainingUnit) {
        currentPosition = trainingUnits.indexOf(trainingUnit);
    }

    @Override
    public void reset() {
        currentPosition = -1;
    }

    @Override
    public List<TrainingUnitName> getTrainingUnitNames(){
        return names;
    }
    protected int getReps(){
        return reps;
    }

    protected void addUnitToStack(ITrainingUnit trUnit){
        this.trainingUnits.add(trUnit);
        length = length + trUnit.getLength();
    }
    @Override
    public ITrainingUnit getFirst(){
        return trainingUnits.get(0);
    }
    @Override
    public ITrainingUnit getLast() {
        return trainingUnits.get(trainingUnits.size()-1);
    }

    @Override
    public ITrainingUnit getUnitById(String id){
        for(ITrainingUnit unit : trainingUnits){
            if(unit.getId().equals(id)){
                return unit;
            }
        }
        return null;
    }
    @Override
    public ITrainingUnit getPredecessor() {
        if(hasPredecessor()){
            return trainingUnits.get(--currentPosition);
        }
        return null;
    }
    @Override
    public ITrainingUnit getSuccessor(){
        if(hasSuccessor()){
            return trainingUnits.get(++currentPosition);
        }
        return null;
    }
    @Override
    public boolean hasSuccessor() {
        return currentPosition + 1 < trainingUnits.size();
    }

    @Override
    public boolean hasPredecessor() {
      return currentPosition > 0;
    }

    @Override
    public List<ITrainingUnit> getTrainingUnits() {
        return trainingUnits;
    }

    protected boolean isLastExercise(int currentRep, int numberOfExercises, int currentExercisePos) {
        if (currentRep < getReps() - 1) {
            return false;
        }
        if (currentExercisePos < numberOfExercises-1){
            return false;
        }
        return true;
    }
    @Override
    public int getTotalLength() {
        return length;
    }

    @Override
    public ITrainingUnit getSneakSuccessor() {
        if(hasSuccessor()) {
            return trainingUnits.get(currentPosition + 1);
        }
        return null;
    }

    @Override
    public ITrainingUnit getSneakPredecessor() {
        if(hasPredecessor()) {
            return trainingUnits.get(currentPosition - 1);
        }
        return null;
    }
}
