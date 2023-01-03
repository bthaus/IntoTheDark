package com.mygdx.game;

import Handler.ActionHandler;
import Types.*;
import com.badlogic.gdx.physics.box2d.Body;
import util.Log;
import util.PhysicsTable;

import java.io.Serializable;
import java.util.LinkedList;

import static Types.STATE.DONE;
import static util.utilMethods.*;

public class Action implements Serializable {
    public LinkedList<ActionType> actionFilter=new LinkedList<>();
    int actionID=0;
Body actor;

ActionType type;
boolean tosend=true;
long duration=0;
public  ActionHandler handler;
Direction direction;
//x and y pose as store for multiple use cases. in case of equip action the slot is stored in x, ind the case of move the direction is stored.
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

        switch (x){
            case -1: if(y==0){
                actor.applyLinearImpulse(PhysicsTable.walkingSpeedLeft,actor.getWorldCenter(),true);
                getActor().moveleft=this;
                Log.t("set moveleft to action");
            }else{
                Log.t("set moveleft to null");
                getActor().moveleft=null;
            }break;
            case 1: if(y==0){
                actor.applyLinearImpulse(PhysicsTable.walkingSpeedRight,actor.getWorldCenter(),true);
                getActor().moveright=this;
            }else{
                getActor().moveright=null;
            }break;
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


    static Action createAction(ActionType type, Body actor, int x, int y){
      Action action=createAction(type,actor);
        action.setX(x);
        action.setY(y);
        return action;
    }
    private boolean linked=false;
    public  void link(){

        Character temp= (Character) actor.getUserData();
        if(!linked) temp.addAction(this);
        linked=true;

    }


    public void setBlockingTypes(LinkedList<ActionType> types) {
        this.actionFilter=types;
    }

    public ActionType getType() {
        return this.type;
    }

    public Character getActor() {
        return getCharacter(actor);
    }
}
