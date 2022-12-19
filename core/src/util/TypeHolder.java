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


    public TypeHolder(TerrainType terrainType, UnitType unitType, HandlerType handlerType) {
        this.terrainType = terrainType;
        this.unitType = unitType;
        this.handlerType = handlerType;
    }
    public TypeHolder(UnitType unitType, UnitType unitType2, HandlerType handlerType) {
        this.unitType2 = unitType2;
        this.unitType = unitType;
        this.handlerType = handlerType;
    }
    public TypeHolder(TerrainType terrainType, TerrainType terrainType2, HandlerType handlerType) {
        this.terrainType2 = terrainType2;
        this.terrainType = terrainType;
        this.handlerType = handlerType;
    }

    UnitType unitType=UnitType.DEFAULT;
    UnitType unitType2=UnitType.DEFAULT;

    public UnitType getUnitType2() {
        return unitType2;
    }

    public void setUnitType2(UnitType unitType2) {
        this.unitType2 = unitType2;
    }

    public TerrainType getTerrainType2() {
        return terrainType2;
    }

    public void setTerrainType2(TerrainType terrainType2) {
        this.terrainType2 = terrainType2;
    }

    HandlerType handlerType=HandlerType.DEFAULT;
    TerrainType terrainType=TerrainType.DEFAULT;
    TerrainType terrainType2=TerrainType.DEFAULT;

}
