package com.mygdx.game;

import Handler.TerrainCollisionHandler;
import Handler.UnitCollisionHandler;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.LinkedList;

public class CollisionHandler {
    Character character;
    LinkedList<TerrainCollisionHandler> tch=new LinkedList<>();
    LinkedList<UnitCollisionHandler> uch=new LinkedList<>();
    public CollisionHandler(Character character) {
        this.character=character;
    }

    public void handleTerrainCollision(Body bodyB) {


        for (TerrainCollisionHandler t:tch
             ) {
            t.collideWith(bodyB);
        }
    }

    public void handleUnitCollision(Body bodyB) {

        for (UnitCollisionHandler u:uch
        ) {
            u.collideWith(bodyB);
        }
    }

    public void handleTerrainDetachment(Body bodyB) {

        for (TerrainCollisionHandler t:tch
        ) {
            t.detachFrom(bodyB);
        }
    }

    public void handleUnitDetachment(Body bodyB) {
        for (UnitCollisionHandler u:uch
        ) {
            u.detachFrom(bodyB);
        }
    }

    public void setCustomTerrainCollisionHandler(TerrainCollisionHandler handler){
        tch.add(handler);
    }
    public void setCustomUnitCollisionHandler(UnitCollisionHandler handler){
        uch.add(handler);
    }
}
