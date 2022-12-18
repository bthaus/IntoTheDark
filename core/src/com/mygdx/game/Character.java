package com.mygdx.game;

import Types.BlockType;
import Types.UnitType;
import com.badlogic.gdx.graphics.Texture;

import java.util.LinkedList;

//is saved in body.UserData
public class Character {

    Stats stats;
    Equipment equipment;
    Friend friend=null;
    Texture texture;
    UnitType unitType;
    boolean airborn;

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public boolean isAirborn() {
        return airborn;
    }

    public void setAirborn(boolean airborn) {
        this.airborn = airborn;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    public LinkedList<Action> getActions() {
        return actions;
    }

    public void setActions(LinkedList<Action> actions) {
        this.actions = actions;
    }

    BlockType blockType;
    LinkedList<Action>actions=new LinkedList<>();


    public void doActions() {
        for (Action action:actions
             ) {
            action.execute();
        }
        actions.clear();
    }
    public void addAction(Action action){
        actions.add(action);
    }


}
