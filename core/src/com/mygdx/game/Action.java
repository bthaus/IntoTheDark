package com.mygdx.game;

import Handler.ActionHandler;
import Types.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import util.PhysicsTable;
import util.Moment;
import util.global;

import java.util.LinkedList;

import static util.utilMethods.*;

public class Action {
    public LinkedList<ActionType> actionFilter;
    int actionID=0;
Body actor;

ActionType type;
Moment startTime;
long duration=0;
public  ActionHandler handler;
Direction direction;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }



    private void myAttack() {

        int x,y;
        x= (int) get(actor.getPosition().x)+150;
        y= (int) get(actor.getPosition().y)+150;
        int xd,yd;
        xd=Gdx.input.getX();
        yd=Gdx.input.getY();

       Body bullet= global.universe.addEntity(x,y,1,1, UnitType.BULLET,"shuriken");
        //todo: collision like in  https://stackoverflow.com/questions/17162837/disable-collision-completely-of-a-body-in-andengine-box2d
        bullet.getFixtureList().get(0).setSensor(true);
       float impulse=getCharacter(actor).equipment.arms.getVelocity();

       Vector2 direction=new Vector2();
       direction.x=(xd-x)*impulse;
       direction.y=-(yd-y)*impulse;
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

    void setDuration(long i){
        this.duration=i;

    }

    static Action createAction(ActionType type, final Body actor){

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
                public boolean execute() {
                    action.myjump();
                    return true;
                }

                @Override
                public void after() {

                }
            }); break;
            case ATTACK:action.setActionHandler(new ActionHandler() {
                @Override
                public void before() {

                }

                @Override
                public void onStart() {

                }

                @Override
                public boolean execute() {
                    action.myAttack();
                    return true;
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
                public boolean execute() {
                    action.mymove();
                    return true;
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
