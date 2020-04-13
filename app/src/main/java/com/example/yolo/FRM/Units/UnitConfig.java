package com.example.yolo.FRM.Units;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vtewes on 07.01.2018.
 */

public abstract class UnitConfig implements IUnitConfig {

    private Map<UnitLengthType, Integer> lengthList = new HashMap<UnitLengthType, Integer>();

    @Override
    public int getLengthByType(UnitLengthType type){
        return lengthList.get(type);
    }

    protected void addUnitLength(UnitLengthType type, int length){
        //multiply with 1000 since timer accepts only milliseconds
        lengthList.put(type,length*1000);
    }


}
