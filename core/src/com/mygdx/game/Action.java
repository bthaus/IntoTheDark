package com.mygdx.game;

import Handler.ActionHandler;
import Types.*;
import com.badlogic.gdx.physics.box2d.Body;
import util.PhysicsTable;
import util.Moment;

import java.util.LinkedList;

import static Types.STATE.DONE;
import static util.utilMethods.*;

public class Action {
    public LinkedList<ActionType> actionFilter=new LinkedList<>();
    int actionID=0;
Body actor;

ActionType type;
Moment startTime;
long duration=0;
public  ActionHandler handler;
Direction direction;
int x, y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public void setStatsByArmament(Armament armament){
        this.setActionHandler(armament.getActionHandler());
        this.setDuration(armament.attackDuration);
        this.actionFilter.addAll(armament.actionFilter);


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

    public void setDuration(long i){
        this.duration=i;

    }

    public static Action createAction(ActionType type, final Body actor){

        final Action action=new Action();
        action.type=type;
        action.actor=actor;

        switch (type){
            case JUMP:action.setActionHandler(new ActionHandler() {
                @Override
                public void before() {

                }

                @Override
                public void onStart() {

                }

                @Override
                public STATE execute(float destinationX, float destinationY) {
                    action.myjump();
                    return DONE;
                }

                @Override
                public void after() {

                }
            }); break;
           case MOVE:action.setActionHandler(new ActionHandler() {
                @Override
                public void before() {

                }

                @Override
                public void onStart() {

                }

                @Override
                public STATE execute(float destinationX, float destinationY) {
                    action.mymove();
                    return DONE;
                }

                @Override
                public void after() {

                }
            });

        }
        return action;
    }

    public void setActionHandler(ActionHandler handler){
        this.handler=handler;
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


    public void setBlockingTypes(LinkedList<ActionType> types) {
        this.actionFilter=types;
    }
}
