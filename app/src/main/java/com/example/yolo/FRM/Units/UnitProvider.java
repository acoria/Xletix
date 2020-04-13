package com.example.yolo.FRM.Units;

import com.example.yolo.xletix.Workouts.ExerciseDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vtewes on 08.05.2016.
 */
public abstract class UnitProvider implements IUnitProvider {

    static final String TAG = UnitProvider.class.getSimpleName();
    private final List<IExerciseDetails> names;
    private List<IUnit> trainingUnits = new ArrayList();
    private int reps;
    private int currentPosition = -1;
    private int length;

    public UnitProvider(int reps, List<IExerciseDetails> names){
        this.reps = reps;
        this.names = names;
        initialize();
    }

    abstract protected void initialize();

    @Override
    public void resetByUnit(IUnit unit) {
        currentPosition = trainingUnits.indexOf(unit);
    }

    @Override
    public void reset() {
        currentPosition = -1;
    }

    @Override
    public List<IExerciseDetails> getUnitNames(){
        return names;
    }
    protected int getReps(){
        return reps;
    }

    protected void addUnitToStack(IUnit trUnit){
        this.trainingUnits.add(trUnit);
        length = length + trUnit.getLength();
    }
    @Override
    public IUnit getFirst(){
        return trainingUnits.get(0);
    }
    @Override
    public IUnit getLast() {
        return trainingUnits.get(trainingUnits.size()-1);
    }

    @Override
    public IUnit getUnitById(String id){
        for(IUnit unit : trainingUnits){
            if(unit.getId().equals(id)){
                return unit;
            }
        }
        return null;
    }
    @Override
    public IUnit getPredecessor() {
        if(hasPredecessor()){
            return trainingUnits.get(--currentPosition);
        }
        return null;
    }
    @Override
    public IUnit getSuccessor(){
        if(hasSuccessor()){
            return trainingUnits.get(++currentPosition);
        }
        return null;
    }

    @Override
    public IUnit getCurrentUnit() {
        return trainingUnits.get(currentPosition);
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
    public List<IUnit> getUnits() {
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

    private boolean isLastRep(int rep){
        return rep == getReps();
    }

    private boolean isNumberOfRepsEven(){
        return getReps()%2 == 0;
    }

    protected void addUnit(IUnit trainingUnit, int currentRep){
        if(needsSplit(trainingUnit,currentRep)){
            addLeftSidedExercise(trainingUnit);
            addSideSwap();
            addRightSidedExercise(trainingUnit);
        }else{
            addUnitToStack(trainingUnit);
        }
    }

    private void addSideSwap() {
        addUnitToStack(new Break("Swap side",2));
    }

    private boolean needsSplit(IUnit trainingUnit, int currentRep){
        //exercise needs splitting for each side in last rep
        return trainingUnit.isOneSided() && isLastRep(currentRep) && !isNumberOfRepsEven();
    }
    private void addLeftSidedExercise(IUnit sourceTrainingUnit){
        IUnit newTrainingUnit = new Exercise(sourceTrainingUnit.getTitle() + " - left", sourceTrainingUnit.getLength()/2, true);
        addExercise(sourceTrainingUnit, newTrainingUnit);
    }
    private void addRightSidedExercise(IUnit sourceTrainingUnit){
        IUnit newTrainingUnit = new Exercise(sourceTrainingUnit.getTitle() + " - right", sourceTrainingUnit.getLength()/2, true);
        addExercise(sourceTrainingUnit, newTrainingUnit);
    }

    private void addExercise(IUnit sourceTrainingUnit, IUnit newTrainingUnit) {
        newTrainingUnit.setInfoImage(sourceTrainingUnit.getInfoImage());
        addUnitToStack(newTrainingUnit);
    }

    @Override
    public int getTotalLength() {
        return length;
    }

    @Override
    public IUnit getSneakSuccessor() {
        if(hasSuccessor()) {
            return trainingUnits.get(currentPosition + 1);
        }
        return null;
    }

    @Override
    public IUnit getSneakPredecessor() {
        if(hasPredecessor()) {
            return trainingUnits.get(currentPosition - 1);
        }
        return null;
    }

    @Override
    public int getCurrentExercisePosition() {
        int currentExercisePosition = 0;
        for(IUnit trainingUnit : trainingUnits){
            try{
                Exercise exercise = (Exercise) trainingUnit;
                currentExercisePosition += 1;
                if(trainingUnit == trainingUnits.get(currentPosition)){
                    break;
                }
            }catch(ClassCastException e){
                //not important for counting
            }
        }
        return currentExercisePosition;
    }

    @Override
    public int getNumberOfExercises() {
        int numberOfExercises = 0;
        for(IUnit trainingUnit : trainingUnits){
            try{
                Exercise exercise = (Exercise) trainingUnit;
                numberOfExercises += 1;
            }catch(ClassCastException e){
                //not important for counting
            }
        }
        return numberOfExercises;
    }
}
