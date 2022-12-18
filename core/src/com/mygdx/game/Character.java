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
    BlockType blockType;
    LinkedList<Action>actions=new LinkedList<>();


    public void doActions() {
        for (Action action:actions
             ) {
            action.execute();
        }
    }
    public void addAction(Action action){
        actions.add(action);
    }
}
