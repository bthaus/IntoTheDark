package Types;

import java.util.EnumSet;

public enum Slot {
    RIGHTHAND, LEFTHAND(),HELMET(),CHESTPIECE(),PANTS(),BOOTS(),GLOVES(),RING(),AMULET(), BOTHHANDS(),
    ;

    public static Slot getSlot(int x) {
        EnumSet<Slot> set=EnumSet.allOf(Slot.class);
        for (Slot type:set) if(type.ordinal()==x) return type;
        return null;

    }
}
