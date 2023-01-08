package com.mygdx.game;

import Handler.ActionHandler;
import Types.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import util.*;

import java.util.LinkedList;

import static util.utilMethods.getCharacter;
import static util.utilMethods.set;

//is saved in body.UserData
public class Character {
    //utility fields
    Watch watch=new Watch();


    //gamestats fields
    Stats stats;
    Equipment equipment;
    static int counter=0;
    int ID;

    //code fields
    Friend friend=null;
    Texture texture;
    UnitType unitType=UnitType.DEFAULT;
    boolean isTerrain=false;
    TerrainType terrainType=TerrainType.DEFAULT;
    BlockType blockType;
    Body body;
    Action blockingAction;
    Action moveright;
    Action moveleft;

    //handlers
    CollisionHandler collisionHandler=new CollisionHandler(this);



    //lists
    LinkedList<Action>actions=new LinkedList<>();
    LinkedList<Action>actionsToAdd=new LinkedList<>();
    LinkedList<Body>touchedFloors=new LinkedList<>();
    LinkedList<ActionType>actionFilter=new LinkedList<>();
    LinkedList<ActionHandler>onSpawn=new LinkedList<>();
    LinkedList<ActionHandler>onDeath=new LinkedList<>();

    public void spawn(int x, int y){
        for (ActionHandler onspawn:onSpawn
             ) {
            Action action=Action.createAction(ActionType.SPAWN,body);
            action.setActionHandler(onspawn);
            action.setX(x);
            action.setY(y);
            action.link();
        }
    }
    public void die(){
        for (ActionHandler ondeath:onDeath
        ) {
            Action action=Action.createAction(ActionType.DIE,body);
            action.tosend=false;
            action.setActionHandler(ondeath);
            action.link();
        }
    }

    public void equipArmament(Armament armament,Slot slot){
        armament.setWielder(body);
    }

    void unequip(Slot slot) {
        switch (slot){
            case LEFTHAND:if(equipment.leftHand!=null)equipment.leftHand.unequip();
        }
    }

    public void doActions() {
        STATE state;
        //check for blocking action
        if(this.watch.active()&&watch.done())
        {
                watch=new Watch();
                actionFilter.clear();
                blockingAction.handler.after();
                blockingAction=null;


        }
        //add actions to toAdd first due to multithreading in connectivity
        actions.addAll(actionsToAdd);
        actionsToAdd.clear();
        for (Action action:actions
             ) {
            //filter actions
            if(actionFilter.contains(action.type)||actionFilter.contains(ActionType.ALL)){
                if(action.getType().equals(ActionType.MOVE)){
                    //workaround for move optimization
                    global.universe.pressedA=false;
                    global.universe.pressedD=false;
                    moveleft=null;
                    moveright=null;
                }
                continue;
            }
                //send action
            if(global.host!=null&&action.tosend) global.host.addAction(action);


            action.handler.before();
                action.handler.onStart();
                state=action.handler.execute(action.x, action.y);

                if(state.equals(STATE.DONE)) action.handler.after();
                else    {
                    //starting watch and set blocking action
                    watch.start(action.duration);
                    actionFilter=action.actionFilter;
                    blockingAction=action;
                }


        }

         actions.clear();
        //workaroung for move optimization
       if(moveright!=null){
           moveright.tosend=false;
            addAction(moveright);
       }
       if(moveleft!=null){
           moveleft.tosend=false;
           addAction(moveleft);
       }
    }

    public void addAttackAction(int x, int y){

        if(equipment.rightHand!=null){
            equipment.rightHand.attack(x,y);

        }
        if(equipment.leftHand!=null){
          equipment.leftHand.attack(x,y);
        }
        for (AdditionalAction attack: equipment.getAllAdditionalActions(TriggerType.ONATTACK)
             ) {
            attack.execute(body);
        }

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
   public static Character createCharByEnemyDef(EnemyDef def,int x, int y){
        Body body= global.universe.addEntity(x,y,def.width,def.width,UnitType.ENEMY,def.texture);
        Character character=new Character(body);
        return character;
   }


    public Character(Body body) {
        this.ID=counter++;
        this.body=body;
        this.equipment=new Equipment();
        this.equipment.rightHand =new Armament();

    }
    public void addSpawnActionHandler(ActionHandler handler){
        onSpawn.add(handler);
    }

    public void addAction(Action action){
        actionsToAdd.add(action);
    }

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

    public int getID() {
        return ID;
    }

    public Body getBody() {
        return this.body;
    }
}
