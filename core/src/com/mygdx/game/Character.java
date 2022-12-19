package com.mygdx.game;

import Types.BlockType;
import Types.TerrainType;
import Types.UnitType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;

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
       if(body.isTerrain){
           collisionHandler.handleTerrainCollision(bodyB);
       }else{
           collisionHandler.handleUnitCollision(bodyB);
       }

    }



    public void uncollidedWith(Body bodyB) {
        Character body=getCharacter(bodyB);
        if(body.isTerrain){
            collisionHandler.handleTerrainDetachment(bodyB);
        }else{
            collisionHandler.handleUnitDetachment(bodyB);
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

    public boolean isCanJump() {
        return canJump;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    boolean canJump=true;

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
}
