package com.example.xletix.WorkoutSessions;

import com.example.xletix.FRM.Units.ITrainingUnit;
import com.example.xletix.FRM.WorkoutSessions.IWorkoutSession;
import com.example.xletix.FRM.WorkoutSessions.IWorkoutSessionUnitProvider;
import com.example.xletix.FRM.Workouts.IWorkout;
import com.example.xletix.FRM.Workouts.WorkoutIterator;
import com.example.xletix.Workouts.TrainingUnitName;

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
    public void resetByTrainingUnit(ITrainingUnit trainingUnit) {
        initialize();
        IWorkout newWorkout = getWorkoutByTrainingUnit(trainingUnit);
        //reset workout iterator
        do{
            currentWorkout = workoutIterator.getNext();
        }while(currentWorkout != newWorkout);
        currentWorkout.getUnitProvider().resetByTrainingUnit(trainingUnit);
    }

    @Override
    public void reset() {
        initialize();
        //set first workout
        currentWorkout = workoutIterator.getNext();
    }

    @Override
    public ITrainingUnit getFirst() {
        return workoutSession.getWorkouts().get(0).getUnitProvider().getFirst();
    }

    @Override
    public ITrainingUnit getLast() {
        List<IWorkout> workouts = workoutSession.getWorkouts();
        return workouts.get(workouts.size()-1).getUnitProvider().getLast();
    }

    @Override
    public ITrainingUnit getUnitById(String id) {
        ITrainingUnit unit;
        for(IWorkout workout: workoutSession.getWorkouts()){
            unit = workout.getUnitProvider().getUnitById(id);
            if(unit != null){
                return unit;
            }
        }
        return null;
    }

    @Override
    public ITrainingUnit getPredecessor() {
        ITrainingUnit predecessorUnit = currentWorkout.getUnitProvider().getPredecessor();
        if(predecessorUnit != null){
            return predecessorUnit;
        }else if(workoutIterator.hasPrevious()){
            currentWorkout = workoutIterator.getPrevious();
            ITrainingUnit previousTrainingUnit = currentWorkout.getUnitProvider().getLast();
            currentWorkout.getUnitProvider().resetByTrainingUnit(previousTrainingUnit);
            return previousTrainingUnit;
        }
        return null;
    }

    private IWorkout getWorkoutByTrainingUnit(ITrainingUnit trainingUnit){
        for(IWorkout workout : workoutSession.getWorkouts()){
            if(workout.getUnitProvider().getUnitById(trainingUnit.getId()) != null){
                return workout;
            }
        }
        return null;
    }

    @Override
    public ITrainingUnit getSuccessor() {
        ITrainingUnit successorUnit = currentWorkout.getUnitProvider().getSuccessor();
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
        if (hasPredecessor == true){
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
        if (hasSuccessor == true){
            return true;
        }else if(workoutIterator.hasNext()) {
            hasSuccessor = workoutIterator.getNext().getUnitProvider().hasSuccessor();
            workoutIterator.getPrevious();
            return hasSuccessor;
        }
        return false;
    }

    @Override
    public List<ITrainingUnit> getTrainingUnits() {
        List<ITrainingUnit> trainingUnits = new ArrayList();
        for(IWorkout workout: workoutSession.getWorkouts()){
            trainingUnits.addAll(workout.getUnitProvider().getTrainingUnits());
        }
        return trainingUnits;
    }

    @Override
    public List<TrainingUnitName> getTrainingUnitNames() {
        List<TrainingUnitName> names = new ArrayList<>();
        for(IWorkout workout: workoutSession.getWorkouts()){
            names.addAll(workout.getUnitProvider().getTrainingUnitNames());
        }
        return names;
    }

    @Override
    public ITrainingUnit getSneakSuccessor() {
        ITrainingUnit sneakSuccessor = currentWorkout.getUnitProvider().getSneakSuccessor();
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
    public ITrainingUnit getSneakPredecessor() {
        ITrainingUnit sneakPredecessor = currentWorkout.getUnitProvider().getSneakPredecessor();
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
}


