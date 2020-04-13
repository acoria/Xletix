package com.example.yolo.FRM.WorkoutSessions;

import com.example.yolo.FRM.Units.IExerciseDetails;
import com.example.yolo.FRM.Units.IUnit;
import com.example.yolo.FRM.Workouts.IWorkout;
import com.example.yolo.FRM.Workouts.WorkoutIterator;
import com.example.yolo.xletix.Workouts.ExerciseDetails;

import java.util.ArrayList;
import java.util.List;

public class WorkoutSessionUnitProvider implements IWorkoutSessionUnitProvider {

    private final static String TAG = WorkoutSessionUnitProvider.class.getSimpleName();
    private final IWorkoutSession workoutSession;
    private WorkoutIterator workoutIterator;
    private IWorkout currentWorkout;

    public WorkoutSessionUnitProvider(IWorkoutSession workoutSession){
        this.workoutSession = workoutSession;
        workoutIterator = new WorkoutIterator(workoutSession.getWorkouts());
        currentWorkout = workoutIterator.getNext();
    }

    private void initialize(){
        workoutIterator.reset();
        for(IWorkout workout : workoutSession.getWorkouts()){
            workout.getUnitProvider().reset();
        }
    }

    @Override
    public void resetByUnit(IUnit unit) {
        initialize();
        IWorkout newWorkout = getWorkoutByTrainingUnit(unit);
        //reset workout iterator
        do{
            currentWorkout = workoutIterator.getNext();
        }while(currentWorkout != newWorkout);
        currentWorkout.getUnitProvider().resetByUnit(unit);
    }

    @Override
    public void reset() {
        initialize();
        //set first workout
        currentWorkout = workoutIterator.getNext();
    }

    @Override
    public IUnit getFirst() {
        return workoutSession.getWorkouts().get(0).getUnitProvider().getFirst();
    }

    @Override
    public IUnit getLast() {
        List<IWorkout> workouts = workoutSession.getWorkouts();
        return workouts.get(workouts.size()-1).getUnitProvider().getLast();
    }

    @Override
    public IUnit getCurrentUnit() {
        return currentWorkout.getUnitProvider().getCurrentUnit();
    }

    @Override
    public IUnit getUnitById(String id) {
        IUnit unit;
        for(IWorkout workout: workoutSession.getWorkouts()){
            unit = workout.getUnitProvider().getUnitById(id);
            if(unit != null){
                return unit;
            }
        }
        return null;
    }

    @Override
    public IUnit getPredecessor() {
        IUnit predecessorUnit = currentWorkout.getUnitProvider().getPredecessor();
        if(predecessorUnit != null){
            return predecessorUnit;
        }else if(workoutIterator.hasPrevious()){
            currentWorkout = workoutIterator.getPrevious();
            IUnit previousTrainingUnit = currentWorkout.getUnitProvider().getLast();
            currentWorkout.getUnitProvider().resetByUnit(previousTrainingUnit);
            return previousTrainingUnit;
        }
        return null;
    }

    private IWorkout getWorkoutByTrainingUnit(IUnit trainingUnit){
        for(IWorkout workout : workoutSession.getWorkouts()){
            if(workout.getUnitProvider().getUnitById(trainingUnit.getId()) != null){
                return workout;
            }
        }
        return null;
    }

    @Override
    public IUnit getSuccessor() {
        IUnit successorUnit = currentWorkout.getUnitProvider().getSuccessor();
        if (successorUnit != null){
            return successorUnit;
        }else if(workoutIterator.hasNext()) {
            currentWorkout = workoutIterator.getNext();
            return currentWorkout.getUnitProvider().getSuccessor();
        }
        return null;
    }

    @Override
    public boolean hasPredecessor() {
        boolean hasPredecessor = currentWorkout.getUnitProvider().hasPredecessor();
        if (hasPredecessor){
            return true;
        }else if(workoutIterator.hasPrevious()) {
            hasPredecessor = workoutIterator.getPrevious().getUnitProvider().hasPredecessor();
            workoutIterator.getNext();
            return hasPredecessor;
        }
        return false;
    }

    @Override
    public boolean hasSuccessor() {
        boolean hasSuccessor = currentWorkout.getUnitProvider().hasSuccessor();
        if (hasSuccessor){
            return true;
        }else if(workoutIterator.hasNext()) {
            hasSuccessor = workoutIterator.getNext().getUnitProvider().hasSuccessor();
            workoutIterator.getPrevious();
            return hasSuccessor;
        }
        return false;
    }

    @Override
    public List<IUnit> getUnits() {
        List<IUnit> trainingUnits = new ArrayList();
        for(IWorkout workout: workoutSession.getWorkouts()){
            trainingUnits.addAll(workout.getUnitProvider().getUnits());
        }
        return trainingUnits;
    }

    @Override
    public List<IExerciseDetails> getUnitNames() {
        List<IExerciseDetails> names = new ArrayList<>();
        for(IWorkout workout: workoutSession.getWorkouts()){
            names.addAll(workout.getUnitProvider().getUnitNames());
        }
        return names;
    }

    @Override
    public IUnit getSneakSuccessor() {
        IUnit sneakSuccessor = currentWorkout.getUnitProvider().getSneakSuccessor();
        if (sneakSuccessor != null){
            return sneakSuccessor;
        }else if(workoutIterator.hasNext()) {
            int currentPos = workoutSession.getWorkouts().indexOf(currentWorkout);
            IWorkout nextWorkout = workoutSession.getWorkouts().get(++currentPos);
            return nextWorkout.getUnitProvider().getSneakSuccessor();
        }
        return null;
    }

    @Override
    public IUnit getSneakPredecessor() {
        IUnit sneakPredecessor = currentWorkout.getUnitProvider().getSneakPredecessor();
        if(sneakPredecessor != null){
            return sneakPredecessor;
        }else if(workoutIterator.hasPrevious()){
            int currentPos = workoutSession.getWorkouts().indexOf(currentWorkout);
            IWorkout nextWorkout = workoutSession.getWorkouts().get(--currentPos);
            return nextWorkout.getUnitProvider().getLast();
        }
        return null;
    }

    @Override
    public int getTotalLength() {
        int length = 0;
        for(IWorkout workout: workoutSession.getWorkouts()){
            length = length + workout.getUnitProvider().getTotalLength();
        }
        return length;
    }

    @Override
    public int getCurrentExercisePosition() {
        int currentPosition = 0;
        for(IWorkout workout: workoutSession.getWorkouts()) {
            if(workout != currentWorkout){
                currentPosition += currentWorkout.getUnitProvider().getNumberOfExercises();
            }else{
                currentPosition += currentWorkout.getUnitProvider().getCurrentExercisePosition();
                break;
            }
        }
        return currentPosition;
    }

    @Override
    public int getNumberOfExercises() {
        int numberOfTrainingUnits = 0;
        for(IWorkout workout: workoutSession.getWorkouts()) {
            numberOfTrainingUnits += workout.getUnitProvider().getNumberOfExercises();
        }
        return numberOfTrainingUnits;
    }
}


