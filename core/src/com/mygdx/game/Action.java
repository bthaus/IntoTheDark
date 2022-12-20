package com.mygdx.game;

import Types.ActionType;
import Types.BlockType;
import Types.Direction;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import util.PhysicsTable;
import util.Moment;

import static util.utilMethods.getCharacter;
import static util.utilMethods.set;

public class Action {
int actionID=0;
Body actor;
BlockType blockType=BlockType.FREE;
ActionType type;
Moment startTime;
float duration;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    Direction direction;



    public void execute() {
        try {
            switch (type){
                case MOVE:mymove();break;
                case JUMP:myjump();break;
            }
        } catch (Exception e) {
            return;
        }

        Character temp=(Character) actor.getUserData();
        temp.blockType=blockType;
    }

    private void myjump() {
      getCharacter(actor).jump();
    }

    private void mymove() {
        switch (direction){
            case LEFT: actor.applyLinearImpulse(PhysicsTable.walkingSpeedLeft,actor.getWorldCenter(),true);break;
            case RIGHT: actor.applyLinearImpulse(PhysicsTable.walkingSpeedRight,actor.getWorldCenter(),true);break;
        }
    }

    private void setDuration(){
        Character actorChar=(Character)actor.getUserData();
        switch (type){
            case ATTACK:duration=actorChar.stats.attackDuration;break;
        }
    }

    static Action createAction(ActionType type,Body actor){

        Action action=new Action();
        action.type=type;
        action.actor=actor;
        action.setBlockType();
        //action.isBlocked();
        action.setDuration();

        return action;
    }

    private void setBlockType() {
        switch (type){
            case ATTACK:blockType=BlockType.ATTACK;break;
            case OPEN:blockType=BlockType.OPEN;break;
            case REVIVE:blockType=BlockType.REVIVE;break;
            default:break;
        }
    }

    private boolean isBlocked() {
        Character temp=(Character) actor.getUserData();
        switch (temp.blockType){
            case ATTACK:
        }
        return true;
    }


    static Action createAction(ActionType type, Body actor, Direction direction){
      Action action=createAction(type,actor);
        action.setDirection(direction);
        return action;
    }
    public  void link(){

        Character temp= (Character) actor.getUserData();

        temp.addAction(this);
    }


}
