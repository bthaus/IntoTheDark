package Types;

import java.io.Serializable;
import java.util.Arrays;
import java.util.EnumSet;

public enum ActionType implements Serializable {
    MOVE,ATTACK, OPEN, HIDE, JUMP, ALL, SWITCHPRIMARY, LIGHT, EQUIP, PASSIVEATTACK, REVIVE,SPAWN, DIE, GLOBAL;

    static public ActionType getType(String s){
        EnumSet<ActionType> set=EnumSet.allOf(ActionType.class);
        for (ActionType type:set) if(type.toString().equals(s)) return type;
        return null;
    }



}
