package com.example.yolo.FRM.Units;

/**
 * Created by vtewes on 12.02.2016.
 */
public class Break extends MainUnit {

    public Break(int length){
        super("Break", length);
    }
    public Break(String breakName, int length){
        super(breakName, length);
    }

    @Override
    public boolean isOneSided() {
        return false;
    }
}
