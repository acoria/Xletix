package com.example.yolo;//package xletixUnittests;

import com.example.yolo.FRM.Workouts.IWorkout;
import com.example.yolo.FRM.Workouts.IWorkoutIterator;
import com.example.yolo.FRM.Workouts.WorkoutIterator;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class WorkoutIteratorUnittest {

    IWorkoutIterator iterator;
    IWorkout firstWorkout;
    IWorkout secondWorkout;
    IWorkout thirdWorkout;

    @Before
    public void setup(){
        firstWorkout = mock(IWorkout.class);
        secondWorkout = mock(IWorkout.class);
        thirdWorkout = mock(IWorkout.class);
        List<IWorkout> workouts = new ArrayList<>();
        workouts.add(firstWorkout);
        workouts.add(secondWorkout);
        workouts.add(thirdWorkout);
        iterator = new WorkoutIterator(workouts);
    }

    @Test
    public void getNext(){
        assertEquals(firstWorkout,iterator.getNext());
    }
    @Test
    public void getNoPrevious(){
        iterator.getNext();
        assertNull(iterator.getPrevious());
    }
    @Test
    public void getPreviousFirst(){
        iterator.getNext();
        iterator.getNext();
        assertEquals(firstWorkout,iterator.getPrevious());
    }
    @Test
    public void getPreviousSecond(){
        iterator.getNext();
        iterator.getNext();
        iterator.getNext();
        assertEquals(secondWorkout,iterator.getPrevious());
    }
    @Test
    public void hasNoPrevious(){
        assertFalse(iterator.hasPrevious());
    }
    @Test
    public void hasPrevious(){
        iterator.getNext();
        iterator.getNext();
        assertTrue(iterator.hasPrevious());
    }
    @Test
    public void hasNext(){
        assertTrue(iterator.hasNext());
    }
    @Test
    public void hasNoNext(){
        iterator.getNext();
        iterator.getNext();
        iterator.getNext();
        assertFalse(iterator.hasNext());
    }

}
