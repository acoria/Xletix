package com.example.xletix.FRM.Units;

/**
 * Created by vtewes on 12.02.2016.
 */
public interface ITrainingUnit {

    String getId();
    String getTitle();
    int getLength();
    int getInfoImage();
    boolean isOneSided();

    void setTitle(String title);
    void setInfoImage(int infoImage);
}
