package util;

import Types.HandlerType;
import Types.TerrainType;
import Types.UnitType;

public class TypeHolder {


    public TypeHolder(TerrainType terrainType, UnitType unitType) {
        this.terrainType = terrainType;
        this.unitType = unitType;
    }
    public HandlerType determineHandlerType(){
        switch (terrainType){
            case FLOOR:return handleFloor();
        }
        return HandlerType.DEFAULT;
    }
    public HandlerType handleFloor(){
        switch (unitType){
            case HERO:return HandlerType.TOUCHFLOOR;
        }
        return HandlerType.DEFAULT;
    }
    TerrainType terrainType;

    public TypeHolder(TerrainType terrainType, UnitType unitType, HandlerType handlerType) {
        this.terrainType = terrainType;
        this.unitType = unitType;
        this.handlerType = handlerType;
    }

    UnitType unitType;
    HandlerType handlerType;

}
