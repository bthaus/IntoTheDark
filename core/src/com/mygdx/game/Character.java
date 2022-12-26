package com.mygdx.game;

import Types.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import util.PhysicsTable;
import util.TypeHolder;
import util.Watch;

import java.util.LinkedList;

import static util.utilMethods.getCharacter;

//is saved in body.UserData
public class Character {
    Watch watch=new Watch();
    Stats stats;
    Equipment equipment;
    Friend friend=null;
    Texture texture;
    UnitType unitType=UnitType.DEFAULT;
    boolean isTerrain=false;
    CollisionHandler collisionHandler=new CollisionHandler(this);
    TerrainType terrainType=TerrainType.DEFAULT;
    BlockType blockType;
    LinkedList<Action>actions=new LinkedList<>();
    LinkedList<Body>touchedFloors=new LinkedList<>();
    Body body;
    Mode mode=Mode.ATTACKMODE;

    LinkedList<ActionType>actionFilter=new LinkedList<>();
    Action blockingAction;

    public Character(Body body) {
        this.body=body;
        this.equipment=new Equipment();
        this.equipment.arms=new Armament();
    }

    boolean onHold=false;
    public void doActions() {
        STATE state=STATE.NOTDONE;

        if(this.watch.active()&&watch.done())
        {
                watch=new Watch();
                actionFilter.clear();
                blockingAction.handler.after();
                blockingAction=null;


        }

        for (Action action:actions
             ) {
            if(actionFilter.contains(action.type)||actionFilter.contains(ActionType.ALL)) continue;

                action.handler.before();
                action.handler.onStart();
                state=action.handler.execute();

                if(state.equals(STATE.DONE)) action.handler.after();
                else    {
                    watch.start(action.duration);
                    actionFilter=action.actionFilter;
                    blockingAction=action;
                }


        }
         actions.clear();
    }
    public void addAction(Action action){
        actions.add(action);
    }


    public void collidedWith(Body bodyB) {
        Character body=getCharacter(bodyB);
        if(body.isTerrain&&isTerrain){
            collisionHandler.handleTerrainCollision(bodyB,TypeHolder.getHandlerType(body.getTerrainType(),getTerrainType()));
            return;
        }
        if(!body.isTerrain&&!isTerrain){
            collisionHandler.handleUnitCollision(bodyB,TypeHolder.getHandlerType(body.getUnitType(),getUnitType()));
            return;
        }
       if(body.isTerrain){
           collisionHandler.handleTerrainCollision(bodyB, TypeHolder.getHandlerType(body.getTerrainType(),getUnitType()));
           return;
       }else{
           collisionHandler.handleUnitCollision(bodyB,TypeHolder.getHandlerType(getTerrainType(), body.getUnitType()));
           return;
       }

    }





    public void uncollidedWith(Body bodyB) {
        Character body=getCharacter(bodyB);
        if(body.isTerrain&&isTerrain){
            collisionHandler.handleTerrainDetachment(bodyB,TypeHolder.getHandlerType(body.getTerrainType(),getTerrainType()));
        }
        if(!body.isTerrain&&!isTerrain){
            collisionHandler.handleUnitDetachment(bodyB,TypeHolder.getHandlerType(body.getUnitType(),getUnitType()));
        }
        if(body.isTerrain){
            collisionHandler.handleTerrainDetachment(bodyB,TypeHolder.getHandlerType(body.getTerrainType(),getUnitType()));
        }else{
           collisionHandler.handleUnitDetachment(bodyB,TypeHolder.getHandlerType(getTerrainType(), body.getUnitType()));
        }
    }

    //--------------------getter and setter-------------------------

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(TerrainType terrainType) {
        this.terrainType = terrainType;
        this.isTerrain=true;
    }

    public int getContacts(){
        return this.touchedFloors.size();
    }
    public int addContact(Body a){
        if (!touchedFloors.contains(a)&&getCharacter(a).getTerrainType().equals(TerrainType.FLOOR)) touchedFloors.add(a);

        return this.touchedFloors.size();
    }
    public int decrContacts(Body a){
       if(touchedFloors.contains(a)) touchedFloors.remove(a);

        return this.touchedFloors.size();
    }


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

    public boolean canJump() {

        if (this.getContacts() > 0) return true;
        else return false;
    }
    public void jump(){
        if(canJump()){
            body.applyLinearImpulse(PhysicsTable.jumpForce,body.getWorldCenter(),true);

        }

    }

    public void switchMode() {
        switch (mode){
            case ATTACKMODE:mode=Mode.LIGHTMODE;break;
            case LIGHTMODE:mode=Mode.ATTACKMODE;break;
            default: break;
        }
        System.out.println(mode);
    }
}
