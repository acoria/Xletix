package com.example.xletix.FRM.Units;

import com.example.xletix.Workouts.ExerciseDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vtewes on 08.05.2016.
 */
public abstract class UnitProvider implements IUnitProvider {

    static final String TAG = UnitProvider.class.getSimpleName();
    private final List<ExerciseDetails> names;
    private List<ITrainingUnit> trainingUnits = new ArrayList();
    private int reps;
    private int currentPosition = -1;
    private int length;

    public UnitProvider(int reps, List<ExerciseDetails> names){
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
    public List<ExerciseDetails> getTrainingUnitNames(){
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

    private boolean isLastRep(int rep){
        return rep == getReps();
    }

    private boolean isNumberOfRepsEven(){
        return getReps()%2 == 0;
    }

    protected void addUnit(ITrainingUnit trainingUnit, int currentRep){
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

    private boolean needsSplit(ITrainingUnit trainingUnit, int currentRep){
        //exercise needs splitting for each side in last rep
        return trainingUnit.isOneSided() && isLastRep(currentRep) && !isNumberOfRepsEven();
    }
    private void addLeftSidedExercise(ITrainingUnit sourceTrainingUnit){
        ITrainingUnit newTrainingUnit = new Exercise(sourceTrainingUnit.getTitle() + " - left", sourceTrainingUnit.getLength()/2, true);
        addExercise(sourceTrainingUnit, newTrainingUnit);
    }
    private void addRightSidedExercise(ITrainingUnit sourceTrainingUnit){
        ITrainingUnit newTrainingUnit = new Exercise(sourceTrainingUnit.getTitle() + " - right", sourceTrainingUnit.getLength()/2, true);
        addExercise(sourceTrainingUnit, newTrainingUnit);
    }

    private void addExercise(ITrainingUnit sourceTrainingUnit, ITrainingUnit newTrainingUnit) {
        newTrainingUnit.setInfoImage(sourceTrainingUnit.getInfoImage());
        addUnitToStack(newTrainingUnit);
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

    @Override
    public int getCurrentExercisePosition() {
        int currentExercisePosition = 0;
        for(ITrainingUnit trainingUnit : trainingUnits){
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
        for(ITrainingUnit trainingUnit : trainingUnits){
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
