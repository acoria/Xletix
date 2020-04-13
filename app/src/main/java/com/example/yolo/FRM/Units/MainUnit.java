package com.example.yolo.FRM.Units;

import java.util.UUID;

/**
 * Created by vtewes on 07.05.2016.
 */
public abstract class MainUnit implements IUnit {

    private static boolean isInTestMode;
    private String id;
    private String title;
    private int length;
    private String predecessorId;
    private String successorId;
    private int infoImage;

    public MainUnit(String unitName, int lengthInMilliseconds){
        this.id = UUID.randomUUID().toString();
        this.title = unitName;
        this.length = lengthInMilliseconds;
    }

    @Override
    public String getId() { return id; }
    @Override
    public String getTitle() { return title; }
    @Override
    public int getLength() {
        if(isInTestMode && length > 3){
            return length/10+1;
        }else{
           return length;
        }
    }

    @Override
    public int getInfoImage() {
        return infoImage;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public static void setTestMode (boolean testModeOn){
        isInTestMode = testModeOn;
    }

    @Override
    public void setInfoImage(int infoImage) {
        this.infoImage = infoImage;
    }
}
