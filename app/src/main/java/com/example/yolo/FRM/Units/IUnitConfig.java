package com.example.yolo.FRM.Units;

/**
 * Created by vtewes on 07.01.2018.
 */

public interface IUnitConfig {

    enum UnitLengthType {
        UNIT_LENGTH_SHORT,
        UNIT_LENGTH_MIDDLE,
        UNIT_LENGTH_LONG,
        UNIT_LENGTH_PREP,
        UNIT_LENGTH_SWAP_SIDE,
        UNIT_LENGTH_BREAK
    }

    int getLengthByType(UnitLengthType type);

}
