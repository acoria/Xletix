package com.example.xletix.Activities;

/**
 * Created by vtewes on 06.01.2018.
 */

public class ActivityScreenFactory {

    private final static char activityConfigureExercise = 'c';
    private final static char activityTimerDisplay = 't';


    public static IActivityScreen createActivityScreen(char activityName){
        IActivityScreen activityScreen = null;

        switch (activityName){
            case activityConfigureExercise:
                activityScreen = new Act_ConfigureExercises();
                break;
            case activityTimerDisplay:
                activityScreen = new Act_TimerDisplay();
                break;
        }

        return activityScreen;
    }

}
