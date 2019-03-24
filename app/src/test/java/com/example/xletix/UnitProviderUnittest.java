package com.example.xletix;//package xletixUnittests;

import com.example.xletix.FRM.Units.ITrainingUnit;
import com.example.xletix.FRM.Units.UnitProvider;
import com.example.xletix.Workouts.TrainingUnitName;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class UnitProviderUnittest {

    TestUnitProvider unitProvider;
    String firstId = "1";
    String secondId = "2";
    String thirdId = "3";

    @Before
    public void setup(){
        unitProvider = new TestUnitProvider();
        when(unitProvider.firstTrainingUnit.getId()).thenReturn(firstId);
        when(unitProvider.secondTrainingUnit.getId()).thenReturn(secondId);
        when(unitProvider.thirdTrainingUnit.getId()).thenReturn(thirdId);
    }

    @Test
    public void getFirst(){
        assertEquals(unitProvider.firstTrainingUnit,unitProvider.getFirst());
    }
    @Test
    public void getLast(){
        assertEquals(unitProvider.thirdTrainingUnit,unitProvider.getLast());
    }
    @Test
    public void getUnitById(){
        assertEquals(unitProvider.firstTrainingUnit,unitProvider.getUnitById(unitProvider.firstTrainingUnit.getId()));
        assertEquals(unitProvider.secondTrainingUnit,unitProvider.getUnitById(unitProvider.secondTrainingUnit.getId()));
        assertEquals(unitProvider.thirdTrainingUnit,unitProvider.getUnitById(unitProvider.thirdTrainingUnit.getId()));
    }
    @Test
    public void getPredecessor(){
        unitProvider.getSuccessor();
        unitProvider.getSuccessor();
        unitProvider.getSuccessor();
        assertEquals(unitProvider.secondTrainingUnit,unitProvider.getPredecessor());
        assertEquals(unitProvider.firstTrainingUnit,unitProvider.getPredecessor());
    }
    @Test
    public void getSuccessor(){
        assertEquals(unitProvider.firstTrainingUnit,unitProvider.getSuccessor());
        assertEquals(unitProvider.secondTrainingUnit,unitProvider.getSuccessor());
        assertEquals(unitProvider.thirdTrainingUnit,unitProvider.getSuccessor());

    }
    @Test
    public void hasPredecessor(){
        unitProvider.getSuccessor();
        unitProvider.getSuccessor();
        assertTrue(unitProvider.hasPredecessor());
        unitProvider.getSuccessor();
        assertTrue(unitProvider.hasPredecessor());
    }
    @Test
    public void hasNoPredecessor(){
        assertFalse(unitProvider.hasPredecessor());
        unitProvider.getSuccessor();
        assertFalse(unitProvider.hasPredecessor());
    }
    @Test
    public void hasSuccessor(){
        assertTrue(unitProvider.hasSuccessor());
        unitProvider.getSuccessor();
        assertTrue(unitProvider.hasSuccessor());
        unitProvider.getSuccessor();
        assertTrue(unitProvider.hasSuccessor());
    }
    @Test
    public void hasNoSuccessor(){
        unitProvider.getSuccessor();
        unitProvider.getSuccessor();
        unitProvider.getSuccessor();
        assertFalse(unitProvider.hasSuccessor());
    }
    @Test
    public void getTrainingUnits(){
        List<ITrainingUnit> trainingUnits = unitProvider.getTrainingUnits();
        assertEquals(3, trainingUnits.size());
    }
    @Test
    public void initializeByTrainingUnit(){
        unitProvider.resetByTrainingUnit(unitProvider.secondTrainingUnit);
        assertEquals(unitProvider.thirdTrainingUnit, unitProvider.getSuccessor());
    }
}

class TestUnitProvider extends UnitProvider {

    public static List<TrainingUnitName> names;
    public ITrainingUnit firstTrainingUnit;
    public ITrainingUnit secondTrainingUnit;
    public ITrainingUnit thirdTrainingUnit;

    public TestUnitProvider(){
        super(1, names);
    }
    @Override
    protected void initialize() {
        firstTrainingUnit = mock(ITrainingUnit.class);
        addUnitToStack(firstTrainingUnit);
        secondTrainingUnit = mock(ITrainingUnit.class);
        addUnitToStack(secondTrainingUnit);
        thirdTrainingUnit = mock(ITrainingUnit.class);
        addUnitToStack(thirdTrainingUnit);

    }
}
