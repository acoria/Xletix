package com.example.yolo;//package xletixUnittests;


import com.acoria.unittimer.unittimer_api.units.IExerciseDetails;
import com.acoria.unittimer.unittimer_api.units.IUnit;
import com.acoria.unittimer.unittimer_api.units.UnitProvider;
import com.example.yolo.FRM.Workouts.IWorkout;
import com.example.yolo.FRM.WorkoutSessions.IWorkoutSession;
import com.example.yolo.FRM.WorkoutSessions.WorkoutSessionUnitProvider;

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

    private WorkoutSessionUnitProvider testObject;
    private IWorkoutSession workoutSession;
    private List<IWorkout> workouts = new ArrayList<>();
    private IWorkout firstWorkout;
    private IWorkout secondWorkout;
    private IWorkout thirdWorkout;
    private MockUnitProvider firstUnitProvider;
    private MockUnitProvider secondUnitProvider;
    private MockUnitProvider thirdUnitProvider;

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
        testObject.resetByUnit(secondUnitProvider.firstUnit);
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
        testObject.resetByUnit(thirdUnitProvider.lastUnit);
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
        testObject.resetByUnit(thirdUnitProvider.lastUnit);
        assertNull(thirdUnitProvider.getSuccessor());
    }
    @Test
    public void hasPredecessor() {
        testObject.resetByUnit(thirdUnitProvider.lastUnit);
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
        testObject.resetByUnit(thirdUnitProvider.lastUnit);
        assertFalse(testObject.hasSuccessor());
    }
    @Test
    public void getTrainingUnits(){
        List<IUnit> trainingUnits = testObject.getUnits();
        assertEquals(6,trainingUnits.size());
    }
    @Test
    public void getUnitById() {
    }
}
class MockUnitProvider extends UnitProvider {
    static List<IExerciseDetails> names;

    public IUnit firstUnit;
    public IUnit lastUnit;
    private String firstId;
    private String lastId;

    public MockUnitProvider() {
        super(1,names);
}
    @Override
    public void initialize() {
        firstUnit = mock(IUnit.class);
        firstId = UUID.randomUUID().toString();
        when(firstUnit.getId()).thenReturn(firstId);
        addUnitToStack(firstUnit);
        lastUnit = mock(IUnit.class);
        lastId = UUID.randomUUID().toString();
        when(lastUnit.getId()).thenReturn(lastId);
        addUnitToStack(lastUnit);
    }
}