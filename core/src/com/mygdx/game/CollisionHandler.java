package com.mygdx.game;

import Handler.TerrainCollisionHandler;
import Handler.UnitCollisionHandler;
import Types.HandlerType;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.LinkedList;

import static util.utilMethods.getCharacter;

public class CollisionHandler {
    //expl: in universe a defaultcollisionhandler is set, which calls "collidedwith" in each character. this then calls the correct handlecollision in this collisionhandler
    // the lists tch and uch contain custom collisionhandlers which can be set for each body.
    // this wraps the fact that the internal box2d collisionhandler only only returns two bodys without further information,
    //making the correct  assignment and the addition of custom handlers a little tricky and cumbersome
    Character character;
    LinkedList<TerrainCollisionHandler> tch=new LinkedList<>();
    LinkedList<UnitCollisionHandler> uch=new LinkedList<>();
    public CollisionHandler(Character character) {
        this.character=character;
    }

    public void handleTerrainCollision(Body bodyB,HandlerType type ) {


        for (TerrainCollisionHandler t:tch
             ) {

            if(t.getName().equals(type))
               t.collideWith(bodyB);
        }
    }

    public void handleUnitCollision(Body bodyB, HandlerType handlerType) {

        for (UnitCollisionHandler u:uch) {

            if(u.getName().equals(handlerType)){

                u.collideWith(bodyB);
            }
        }
    }

    public void handleTerrainDetachment(Body bodyB,HandlerType type) {

        for (TerrainCollisionHandler t:tch) {
            if(t.getName().equals(type)) {
                 t.detachFrom(bodyB);
            }
        }
    }

    public void handleUnitDetachment(Body bodyB, HandlerType handlerType) {

        for (UnitCollisionHandler u:uch
        ) {
            if(u.getName().equals(handlerType)){

                u.detachFrom(bodyB);
            }

        }
    }

    public void setCustomTerrainCollisionHandler(TerrainCollisionHandler handler){
        handler.setTypeCombination();
        tch.add(handler);
    }
    public void setCustomUnitCollisionHandler(UnitCollisionHandler handler){
        uch.add(handler);
    }
}