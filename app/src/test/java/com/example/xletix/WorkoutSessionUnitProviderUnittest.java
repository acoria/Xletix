package com.example.xletix;//package xletixUnittests;


import com.example.xletix.FRM.Units.ITrainingUnit;
import com.example.xletix.FRM.Units.UnitProvider;
import com.example.xletix.FRM.Workouts.IWorkout;
import com.example.xletix.FRM.WorkoutSessions.IWorkoutSession;
import com.example.xletix.WorkoutSessions.WorkoutSessionUnitProvider;
import com.example.xletix.Workouts.TrainingUnitName;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class WorkoutSessionUnitProviderUnittest {

    WorkoutSessionUnitProvider testObject;
    IWorkoutSession workoutSession;
    List<IWorkout> workouts = new ArrayList<>();
    IWorkout firstWorkout;
    IWorkout secondWorkout;
    IWorkout thirdWorkout;
    MockUnitProvider firstUnitProvider;
    MockUnitProvider secondUnitProvider;
    MockUnitProvider thirdUnitProvider;

    @Before
    public void setup(){
        workoutSession = mock(IWorkoutSession.class);
        when(workoutSession.getWorkouts()).thenReturn(workouts);


        firstWorkout = mock(IWorkout.class);
        workouts.add(firstWorkout);
        firstUnitProvider = new MockUnitProvider();
        when(firstWorkout.getUnitProvider()).thenReturn(firstUnitProvider);

        secondWorkout = mock(IWorkout.class);
        workouts.add(secondWorkout);
        secondUnitProvider = new MockUnitProvider();
        when(secondWorkout.getUnitProvider()).thenReturn(secondUnitProvider);

        thirdWorkout = mock(IWorkout.class);
        workouts.add(thirdWorkout);
        thirdUnitProvider = new MockUnitProvider();
        when(thirdWorkout.getUnitProvider()).thenReturn(thirdUnitProvider);

        testObject = new WorkoutSessionUnitProvider(workoutSession);
    }

    @Test
    public void initializeByTrainingUnit(){
        testObject.resetByTrainingUnit(secondUnitProvider.firstUnit);
        assertEquals(secondUnitProvider.lastUnit,testObject.getSuccessor());
        assertEquals(thirdUnitProvider.firstUnit,testObject.getSuccessor());
        assertEquals(thirdUnitProvider.lastUnit,testObject.getSuccessor());
    }

    @Test
    public void getFirst(){
        assertEquals(firstUnitProvider.firstUnit,testObject.getFirst());
    }
    @Test
    public void getLast(){
        assertEquals(thirdUnitProvider.lastUnit,testObject.getLast());
    }
    @Test
    public void getPredecessor() {
        testObject.resetByTrainingUnit(thirdUnitProvider.lastUnit);
        assertEquals(thirdUnitProvider.firstUnit,testObject.getPredecessor());
        assertEquals(secondUnitProvider.lastUnit,testObject.getPredecessor());
        assertEquals(secondUnitProvider.firstUnit,testObject.getPredecessor());
        assertEquals(firstUnitProvider.lastUnit,testObject.getPredecessor());
        assertEquals(firstUnitProvider.firstUnit,testObject.getPredecessor());
    }
    @Test
    public void getNoPredecessor() {
        assertNull(firstUnitProvider.getPredecessor());
    }
    @Test
    public void getSuccessor() {
        assertEquals(firstUnitProvider.firstUnit,testObject.getSuccessor());
        assertEquals(firstUnitProvider.lastUnit,testObject.getSuccessor());
        assertEquals(secondUnitProvider.firstUnit,testObject.getSuccessor());
        assertEquals(secondUnitProvider.lastUnit,testObject.getSuccessor());
        assertEquals(thirdUnitProvider.firstUnit,testObject.getSuccessor());
        assertEquals(thirdUnitProvider.lastUnit,testObject.getSuccessor());
    }
    @Test
    public void getNoSuccessor() {
        testObject.resetByTrainingUnit(thirdUnitProvider.lastUnit);
        assertNull(thirdUnitProvider.getSuccessor());
    }
    @Test
    public void hasPredecessor() {
        testObject.resetByTrainingUnit(thirdUnitProvider.lastUnit);
        for(int i=0; i < 4;i++) {
            assertTrue(testObject.hasPredecessor());
        }
    }
    @Test
    public void hasNoPredecessor() {
        testObject.getSuccessor();
        assertFalse(testObject.hasPredecessor());
    }
    @Test
    public void hasSuccessor() {
        for(int i=0; i < 4;i++) {
            testObject.getSuccessor();
            assertTrue(testObject.hasSuccessor());
        }
    }
    @Test
    public void hasNoSuccessor() {
        testObject.resetByTrainingUnit(thirdUnitProvider.lastUnit);
        assertFalse(testObject.hasSuccessor());
    }
    @Test
    public void getTrainingUnits(){
        List<ITrainingUnit> trainingUnits = testObject.getTrainingUnits();
        assertEquals(6,trainingUnits.size());
    }
    @Test
    public void getUnitById() {
    }
}
class MockUnitProvider extends UnitProvider {
    static List<TrainingUnitName> names;

    public ITrainingUnit firstUnit;
    public ITrainingUnit lastUnit;
    String firstId;
    String lastId;

    public MockUnitProvider() {
        super(1,names);
}
    @Override
    protected void initialize() {
        firstUnit = mock(ITrainingUnit.class);
        firstId = UUID.randomUUID().toString();
        when(firstUnit.getId()).thenReturn(firstId);
        addUnitToStack(firstUnit);
        lastUnit = mock(ITrainingUnit.class);
        lastId = UUID.randomUUID().toString();
        when(lastUnit.getId()).thenReturn(lastId);
        addUnitToStack(lastUnit);
    }
}