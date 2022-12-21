package com.mygdx.game;

import Handler.UnitCollisionHandler;
import Types.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import util.PhysicsTable;
import util.Moment;
import util.TypeHolder;
import util.global;

import static util.utilMethods.*;

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
                case ATTACK:myAttack();break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Character temp=(Character) actor.getUserData();
        temp.blockType=blockType;
    }

    private void myAttack() {

        int x,y;
        x= (int) get(actor.getPosition().x);
        y= (int) get(actor.getPosition().y);
        int xd,yd;
        xd=Gdx.input.getX();
        yd=Gdx.input.getY();

       Body bullet= global.universe.addEntity(x+261,y,1,1, UnitType.BULLET,"shuriken");

       float impulse=getCharacter(actor).equipment.arms.getVelocity();
       Vector2 direction=new Vector2();
       direction.x=10000;
       direction.y=100;
        global.numbullets++;
        System.out.println(global.numbullets);
       bullet.applyForce(direction,bullet.getWorldCenter(),true);
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
           // case ATTACK:duration=actorChar.stats.attackDuration;break;
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
