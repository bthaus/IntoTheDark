package com.mygdx.game;

import Handler.ActionHandler;
import Handler.UnitCollisionHandler;
import Types.*;
import box2dLight.ConeLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import util.AdditionalAction;
import util.Log;
import util.TypeHolder;
import util.global;

import java.util.LinkedList;

import static java.lang.Math.atan2;
import static java.lang.Math.toDegrees;
import static util.utilMethods.get;
import static util.utilMethods.getCharacter;

public class Armament {

    //util fields
    int ID;
    private static int counter = 0;
    Body wielder;
    Texture texture;
    Vector2 offset;

    //game fields
    Armament armament;
    Slot slot;
    long attackDuration;
    int damage;
    int velocity;
    WeaponName name;
    int range;
    int angle;
    long equipDuration = 350;

    //handlers
    ActionHandler attackHandler;
    ActionHandler onEquip = new ActionHandler() {
        @Override
        public void before() {

        }

        @Override
        public void onStart() {

        }

        @Override
        public STATE execute(float destinationX, float destinationY) {
            Log.a("putting on " + name);
            return STATE.NOTDONE;
        }

        @Override
        public void after() {
            switch (slot) {
                case RIGHTHAND:
                    getCharacter(wielder).equipment.rightHand = armament;
                    break;
                case LEFTHAND:
                    getCharacter(wielder).equipment.leftHand = armament;
                    break;

                case BOTHHANDS:
                    getCharacter(wielder).equipment.rightHand = armament;
                    getCharacter(wielder).unequip(Slot.LEFTHAND);
                    break;

            }
        }
    };
    ActionHandler onUnequip = new ActionHandler() {
        @Override
        public void before() {

        }

        @Override
        public void onStart() {

        }

        @Override
        public STATE execute(float destinationX, float destinationY) {
            Log.a("unequipping " + name);
            return STATE.DONE;
        }

        @Override
        public void after() {

        }
    };

    //lists
    LinkedList<AdditionalAction> additionalActions = new LinkedList<>();
    LinkedList<ActionType> actionFilter = new LinkedList<>();

    public void addAdditionalAction(ActionHandler handler, long cooldown, TriggerType triggerType) {
        additionalActions.add(new AdditionalAction(handler, cooldown, triggerType));
    }

    //generating an equip-action,setting all fields. X=slotnumber, Y=ID
    public void setWielder(Body wielder) {
        this.wielder = wielder;
        Action action = Action.createAction(ActionType.EQUIP, wielder);
        action.setActionHandler(onEquip);
        action.setDuration(equipDuration);
        action.setX(slot.ordinal());
        action.setY(this.ID);
        LinkedList<ActionType> blockingtypes = new LinkedList<>();
        blockingtypes.add(ActionType.ALL);
        action.setBlockingTypes(blockingtypes);
        action.link();

    }


    //to be called after initialization, otherwise its set to throwing weapon as standart
    public void setStandardThrowingWeaponHandler() {
        this.attackHandler = new ActionHandler() {
            @Override
            public void before() {

            }

            @Override
            public void onStart() {

            }

            @Override
            public STATE execute(float destinationX, float destinationY) {
                int x, y;
                x = (int) get(wielder.getPosition().x) + 150;
                y = (int) get(wielder.getPosition().y) + 150;
                Body bullet = global.universe.addEntity(x, y, 25, 25, UnitType.BULLET, "shuriken");

                //  bullet.getFixtureList().get(0).setSensor(true);

                Vector2 direction = new Vector2();
                direction.x = (destinationX - x) * velocity;
                direction.y = -(destinationY - y) * velocity;
                bullet.applyLinearImpulse(direction, bullet.getWorldCenter(), true);
                return STATE.NOTDONE;
            }

            @Override
            public void after() {

            }
        };
    }

    public void setStandardShootingWeaponHandler() {

    }

    public void setStandardMeleeWeaponHandler() {

    }

    public void setStandardMagicSpellHandler() {

    }

    public void setStandardTochHandler() {
        attackHandler = new ActionHandler() {
            @Override
            public void before() {

            }

            @Override
            public void onStart() {

            }

            @Override
            public STATE execute(float destinationX, float destinationY) {
                float cx, cy, x, y;
                x = get(wielder.getPosition().x) + 150;

                y = get(wielder.getPosition().y) + 150;


                cx = Gdx.input.getX();
                cy = Gdx.input.getY();

                x = destinationX - x;
                y = -(destinationY - y);

                //determine correct factor
                float directionDegree = (float) toDegrees(atan2(y, x));

                ConeLight coneLight = new ConeLight(global.universe.holder.rayHandler, 10, new Color(100, 100, 100, 100), range, wielder.getPosition().x + 2, wielder.getPosition().y + 1, directionDegree, angle);
                ConeLight coneLight2 = new ConeLight(global.universe.holder.rayHandler, 10, new Color(100, 100, 100, 100), range, wielder.getPosition().x + 2, wielder.getPosition().y + 1, directionDegree, angle);


                global.universe.coneLights.add(coneLight);
                global.universe.coneLights.add(coneLight2);
                return STATE.DONE;
            }

            @Override
            public void after() {

            }
        };
    }

    //creates attackAction
    public void attack(int x, int y) {

        Action actionrightHand = Action.createAction(ActionType.ATTACK, wielder);
        actionrightHand.setX(x);
        actionrightHand.setY(y);
        actionrightHand.setStatsByArmament(this);
        actionrightHand.link();

    }

    public void unequip() {
        if (onUnequip != null) {
            onUnequip.before();
            onUnequip.before();
            onUnequip.execute(0, 0);
            onUnequip.after();
        }

    }


    //--------------------------getters and setters

    public Armament() {
        ID = counter++;
        actionFilter.add(ActionType.ATTACK);
        this.armament = this;
        this.setStandardThrowingWeaponHandler();

    }

    public ActionHandler getActionHandler() {
        return attackHandler;
    }

    public void addActionToFilter(ActionType type) {
        actionFilter.add(type);
    }

    public void setCustomHandler(ActionHandler handler) {
        this.attackHandler = handler;
    }

    public void setOnEquip(ActionHandler onEquip) {
        this.onEquip = onEquip;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setSlot(Slot righthand) {
        this.slot = righthand;
    }

    enum Type {
        WEAPON,
        SHIELD,
        SORCERY
    }

    public Vector2 getRelativePosition(Vector2 a) {
        return null;
    }

    public WeaponName getName() {
        return name;
    }

    public void setName(WeaponName name) {
        global.universe.holder.index.put(this.ID, name);
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

    public ActionHandler getOnUnequip() {
        return onUnequip;
    }

    public void setOnUnequip(ActionHandler onUnequip) {
        this.onUnequip = onUnequip;
    }

    public long getEquipDuration() {
        return equipDuration;
    }

    public void setEquipDuration(long equipDuration) {
        this.equipDuration = equipDuration;
    }

    public Body getWielder() {
        return wielder;
    }

}
