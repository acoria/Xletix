package com.example.yolo.FRM.Units;

/**
 * Created by vtewes on 11.02.2016.
 */
public class Exercise extends MainUnit {

    private boolean isOneSided;

    public Exercise(String exerciseName, int length) {
        super(exerciseName, length);
    }

    public Exercise(String exerciseName, int length, boolean isOneSided){
        super(exerciseName, length);
        this.isOneSided = isOneSided;
    }
    @Override
    public boolean isOneSided() {
        return isOneSided;
    }
}
