package com.example.yolo.FRM.Units;

/**
 * Created by vtewes on 12.02.2016.
 */
public interface IUnit {

    String getId();
    String getTitle();
    int getLength();
    int getInfoImage();
    boolean isOneSided();

    void setTitle(String title);
    void setInfoImage(int infoImage);
}
