package com.mygdx.game;

import Handler.TerrainCollisionHandler;
import Handler.UnitCollisionHandler;
import Types.HandlerType;
import com.badlogic.gdx.physics.box2d.Body;
import util.Log;

import java.util.LinkedList;

import static util.utilMethods.getCharacter;

public class CollisionHandler {
    //expl: in universe a defaultcollisionhandler is set, which calls "collidedwith" in each character. this then calls the correct handlecollision in this collisionhandler
    // the lists tch and uch contain custom collisionhandlers which can be set for each body.
    // this wraps the fact that the internal box2d collisionhandler only only returns two bodys without further information,
    //making the correct  assignment and the addition of custom handlers a little tricky and cumbersome
    Character character;
    LinkedList<TerrainCollisionHandler> tch=new LinkedList<>();
    static public LinkedList<TerrainCollisionHandler> standarttch=new LinkedList<>();
    LinkedList<UnitCollisionHandler> uch=new LinkedList<>();
    static public LinkedList<UnitCollisionHandler> standartuch=new LinkedList<>();

    public CollisionHandler(Character character) {
        tch.addAll(standarttch);
        uch.addAll(standartuch);
        this.character=character;
    }

    public void handleTerrainCollision(Body bodyB,LinkedList<HandlerType> handlerTypes ) {


        for (TerrainCollisionHandler t:tch
             ) {
            for (HandlerType h:handlerTypes
                 ) {
                if(t.getName().equals(h))
                    t.collideWith(bodyB);
            }
            }

    }

    public void handleUnitCollision(Body bodyB, LinkedList<HandlerType> handlerTypes) {

        for (UnitCollisionHandler u:uch) {
            for (HandlerType h:handlerTypes
                 ) {
                if(u.getName().equals(h)){
                    u.collideWith(bodyB);
                }
            }

        }
    }

    public void handleTerrainDetachment(Body bodyB,LinkedList<HandlerType> handlerTypes) {

        for (TerrainCollisionHandler t:tch) {
            for (HandlerType h:handlerTypes
                 ) {
                if(t.getName().equals(h)) {
                    t.detachFrom(bodyB);
                }
            }

        }
    }

    public void handleUnitDetachment(Body bodyB, LinkedList<HandlerType> handlerTypes) {

        for (UnitCollisionHandler u:uch
        ) {
            for (HandlerType h:handlerTypes
                 ) {
                if(u.getName().equals(h)){

                    u.detachFrom(bodyB);
                }
            }


        }
    }

    static public void setStandartTerrainHandler(TerrainCollisionHandler handler){
        handler.setTypeCombination();
        standarttch.add(handler);
    }
    static public void setStandartUnitHandler(UnitCollisionHandler handler){
        handler.setTypeCombination();
        standartuch.add(handler);
    }
    public void setCustomTerrainCollisionHandler(TerrainCollisionHandler handler){
        handler.setTypeCombination();
        tch.add(handler);
    }
    public void setCustomUnitCollisionHandler(UnitCollisionHandler handler){
        System.out.println("typeholder added");
        handler.setTypeCombination();
        uch.add(handler);
    }
}
