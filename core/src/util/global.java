package util;

import Types.HandlerType;
import Types.TerrainType;
import Types.UnitType;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class global {
   static public LinkedList<TypeHolder>typeHolders=new LinkedList<>();

    public static void addTypeHolder(TypeHolder typeHolder) {
        typeHolders.add(typeHolder);
    }
    public static HandlerType getHandlerType(TerrainType terrainType,UnitType unitType){

        for (TypeHolder a:typeHolders
             ) {

            if(a.unitType.equals(unitType)&&a.terrainType.equals(terrainType)){

                return a.handlerType;
            }
        }
        return HandlerType.DEFAULT;
    }
    public static HandlerType getHandlerType(TerrainType terrainType,TerrainType terrainType2){

        for (TypeHolder a:typeHolders
        ) {

            if(a.terrainType.equals(terrainType)&&a.terrainType2.equals(terrainType2)){

                return a.handlerType;
            }
        }
        return HandlerType.DEFAULT;
    }
    public static HandlerType getHandlerType(UnitType unitType,UnitType unitType2){

        for (TypeHolder a:typeHolders
        ) {

            if(a.unitType.equals(unitType)&&a.unitType2.equals(unitType2)){

                return a.handlerType;
            }
        }
        return HandlerType.DEFAULT;
    }

}
