package util;

import Types.HandlerType;
import Types.TerrainType;
import Types.UnitType;

import java.util.LinkedList;

public class TypeHolder {




    public TypeHolder(TerrainType terrainType, UnitType unitType, HandlerType handlerType) {
        terrainTypes.add(terrainType);
        this.unitType = unitType;
        this.handlerType = handlerType;
    }
    public TypeHolder(LinkedList<TerrainType>terrainTypes,UnitType unitType,HandlerType handlerType){
        this.terrainTypes=terrainTypes;
        this.unitType=unitType;
        this.handlerType=handlerType;
    }
    public TypeHolder(LinkedList<UnitType> unitTypes, UnitType unitType2, HandlerType handlerType,boolean garbage) {
       this.unitTypes=unitTypes;
        this.unitType2 = unitType2;
        this.handlerType = handlerType;
    }
    public TypeHolder(UnitType enemy, UnitType hero, HandlerType enemycollision, boolean garbage) {
        this.unitTypes.add(enemy);
        this.unitType2=hero;
        this.handlerType=enemycollision;
    }

    public TypeHolder(TerrainType terrainType, TerrainType terrainType2, HandlerType handlerType) {
        this.terrainTypes.add(terrainType);
        this.terrainType = terrainType;
        this.handlerType = handlerType;
    }
    public TypeHolder(LinkedList<TerrainType>terrainTypes,TerrainType terrainType,HandlerType handlerType){
        this.terrainTypes=terrainTypes;
        this.terrainType2=terrainType;
        this.handlerType=handlerType;
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
    LinkedList<TerrainType>terrainTypes=new LinkedList<>();
    LinkedList<UnitType>unitTypes=new LinkedList<>();
    HandlerType handlerType=HandlerType.DEFAULT;
    TerrainType terrainType=TerrainType.DEFAULT;
    TerrainType terrainType2=TerrainType.DEFAULT;
    static public LinkedList<TypeHolder> typeHolders=new LinkedList<>();



    public static void addTypeHolder(TypeHolder typeHolder) {
        typeHolders.add(typeHolder);
    }
    public static LinkedList<HandlerType> getHandlerType(TerrainType terrainType,UnitType unitType){
        LinkedList<HandlerType>handlerTypes=new LinkedList<>();
        for (TypeHolder a:typeHolders
        ) {

            if(a.terrainTypes.contains(terrainType)&&a.unitType.equals(unitType)){

                handlerTypes.add(a.handlerType);
            }
        }
       return handlerTypes;
    }
    public static LinkedList<HandlerType> getHandlerType(TerrainType terrainType,TerrainType terrainType2){
        LinkedList<HandlerType>handlerTypes=new LinkedList<>();
        for (TypeHolder a:typeHolders
        ) {

            if(a.terrainTypes.contains(terrainType)&&a.terrainType2.equals(terrainType2)){

                handlerTypes.add(a.handlerType);
            }
        }
        return handlerTypes;
    }
    public static LinkedList<HandlerType> getHandlerType(UnitType unitType,UnitType unitType2){
        LinkedList<HandlerType>handlerTypes=new LinkedList<>();
        for (TypeHolder a:typeHolders
        ) {

            if(a.unitTypes.contains(unitType)&&a.unitType2.equals(unitType2)){

                handlerTypes.add(a.handlerType);
            }
        }
        return handlerTypes;
    }

}
