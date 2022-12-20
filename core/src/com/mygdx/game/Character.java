package com.mygdx.game;

import Types.BlockType;
import Types.HandlerType;
import Types.TerrainType;
import Types.UnitType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import util.PhysicsTable;
import util.TypeHolder;
import util.global;

import java.util.LinkedList;

import static util.utilMethods.getCharacter;

//is saved in body.UserData
public class Character {

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

    public Character(Body body) {
        this.body=body;
    }


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


    public void collidedWith(Body bodyB) {
        Character body=getCharacter(bodyB);
        if(body.isTerrain&&isTerrain){
            collisionHandler.handleTerrainCollision(bodyB,global.getHandlerType(body.getTerrainType(),getTerrainType()));
            return;
        }
        if(!body.isTerrain&&!isTerrain){
            collisionHandler.handleUnitCollision(bodyB,global.getHandlerType(body.getUnitType(),getUnitType()));
            return;
        }
       if(body.isTerrain){
           collisionHandler.handleTerrainCollision(bodyB, global.getHandlerType(body.getTerrainType(),getUnitType()));
           return;
       }else{
           collisionHandler.handleUnitCollision(bodyB,global.getHandlerType(getTerrainType(), body.getUnitType()));
           return;
       }

    }
    public HandlerType determineHandlerType(Body body, TerrainType terrainType){
        TypeHolder holder=new TypeHolder(terrainType,getCharacter(body).getUnitType());
        return holder.determineHandlerType();
    }




    public void uncollidedWith(Body bodyB) {
        Character body=getCharacter(bodyB);
        if(body.isTerrain&&isTerrain){
            collisionHandler.handleTerrainDetachment(bodyB,global.getHandlerType(body.getTerrainType(),getTerrainType()));
        }
        if(!body.isTerrain&&!isTerrain){
            collisionHandler.handleUnitDetachment(bodyB,global.getHandlerType(body.getUnitType(),getUnitType()));
        }
        if(body.isTerrain){
            collisionHandler.handleTerrainDetachment(bodyB,global.getHandlerType(body.getTerrainType(),getUnitType()));
        }else{
           collisionHandler.handleUnitDetachment(bodyB,global.getHandlerType(getTerrainType(), body.getUnitType()));
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
}
