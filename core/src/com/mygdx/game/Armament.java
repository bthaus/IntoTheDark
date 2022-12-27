package com.mygdx.game;

import Handler.ActionHandler;
import Types.ActionType;
import Types.STATE;
import Types.UnitType;
import Types.WeaponName;
import box2dLight.ConeLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import util.Log;
import util.global;

import java.util.LinkedList;

import static java.lang.Math.atan2;
import static java.lang.Math.toDegrees;
import static util.utilMethods.get;

public class Armament  {
    ActionHandler attackHandler;



    ActionHandler onEquip;
    WeaponName name;
    Body wielder;

    public Body getWielder() {
        return wielder;
    }

    public void setWielder(Body wielder) {
        this.wielder = wielder;
        Action action=Action.createAction(ActionType.EQUIP,wielder);
        action.setActionHandler(onEquip);
        action.setDuration(100);
        LinkedList<ActionType>blockingtypes=new LinkedList<>();
        blockingtypes.add(ActionType.MOVE);
        blockingtypes.add(ActionType.ATTACK);
        blockingtypes.add(ActionType.JUMP);
        blockingtypes.add(ActionType.REVIVE);
        action.setBlockingTypes(blockingtypes);
        action.link();

    }

    int ID;
    private static int counter=0;

    public Armament() {

        onEquip=new ActionHandler() {
            @Override
            public void before() {

            }

            @Override
            public void onStart() {

            }

            @Override
            public STATE execute() {
                Log.a("putting on "+name);
                return STATE.NOTDONE;
            }

            @Override
            public void after() {

            }
        };
        ID=counter++;
        actionFilter.add(ActionType.ATTACK);
    }


    long attackDuration;
    int damage;
    int velocity;

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    int range;
    int angle;
    Texture texture;
    Vector2 offset;
    LinkedList<ActionType> actionFilter=new LinkedList<>();
    public ActionHandler getActionHandler(){
        return attackHandler;
    }
    public void addActionToFilter(ActionType type){
        actionFilter.add(type);
    }
    public void setCustomHandler(ActionHandler handler) {
        this.attackHandler = handler;
    }
    public void setStandardThrowingWeaponHandler(){
        this.attackHandler =new ActionHandler() {
            @Override
            public void before() {

            }

            @Override
            public void onStart() {

            }

            @Override
            public STATE execute() {
                int x,y;
                x= (int) get(wielder.getPosition().x)+150;
                y= (int) get(wielder.getPosition().y)+150;
                int xd,yd;
                xd= Gdx.input.getX();
                yd=Gdx.input.getY();

                Body bullet= global.universe.addEntity(x,y,1,1, UnitType.BULLET,"shuriken");
                //todo: collision like in  https://stackoverflow.com/questions/17162837/disable-collision-completely-of-a-body-in-andengine-box2d
                bullet.getFixtureList().get(0).setSensor(true);

                Vector2 direction=new Vector2();
                direction.x=(xd-x)*velocity;
                direction.y=-(yd-y)*velocity;
                bullet.applyLinearImpulse(direction,bullet.getWorldCenter(),true);
                return STATE.NOTDONE;
            }

            @Override
            public void after() {

            }
        };
    }
    public void setStandardShootingWeaponHandler(){

    }
    public void setStandardMeleeWeaponHandler(){

    }
    public void setStandardMagicSpellHandler(){

    }
    public void setStandardTochHandler(){
    attackHandler=new ActionHandler() {
        @Override
        public void before() {

        }

        @Override
        public void onStart() {

        }

        @Override
        public STATE execute() {
            float cx,cy, x,y;
            x=get(wielder.getPosition().x)+150;
            y=get(wielder.getPosition().y)+150;

            cx=Gdx.input.getX();
            cy=Gdx.input.getY();

            x=cx-x;
            y=-(cy-y);

            //determine correct factor
            float directionDegree=(float)toDegrees(atan2(y,x));

            ConeLight coneLight=new ConeLight(global.universe.holder.rayHandler,10,new Color(100,100,100,100),range,wielder.getPosition().x+2,wielder.getPosition().y+1,directionDegree,angle);
            ConeLight coneLight2=new ConeLight(global.universe.holder.rayHandler,10,new Color(100,100,100,100),range,wielder.getPosition().x+2,wielder.getPosition().y+1,directionDegree,angle);


            global.universe.coneLights.add(coneLight);
            global.universe.coneLights.add(coneLight2);
            return STATE.DONE;
        }

        @Override
        public void after() {

        }
    };
    }




    public void setOnEquip(ActionHandler onEquip) {
        this.onEquip = onEquip;
    }

    public float getVelocity() {
        return 10;
    }

    enum Type{
        WEAPON,
        SHIELD,
        SORCERY
    }

    public Vector2 getRelativePosition(Vector2 a){
        return null;
    }

    public WeaponName getName() {
        return name;
    }

    public void setName(WeaponName name) {
        this.name = name;
    }

    public long getAttackDuration() {
        return attackDuration;
    }

    public void setAttackDuration(long attackDuration) {
        this.attackDuration = attackDuration;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Vector2 getOffset() {
        return offset;
    }

    public void setOffset(Vector2 offset) {
        this.offset = offset;
    }

}
