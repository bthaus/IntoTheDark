package com.mygdx.game;

import Handler.TerrainCollisionHandler;
import Handler.UnitCollisionHandler;
import Types.*;
import box2dLight.ConeLight;
import box2dLight.PointLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import util.PhysicsTable;
import util.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static util.utilMethods.*;

public class Universe {
    public WorldHolder holder;
    Body hero;
    Character heroChar;
    LinkedList<Body> toRemove = new LinkedList<>();
    LinkedList<ConeLight> coneLights = new LinkedList<>();
    Map<Integer, Character> activeBodies = new HashMap<>();

    public Body getBodyByID(int characterID) {
        return hero;
        // return activeBodies.get(characterID).body;
    }

    public void putActiveBody(Character unit) {
        activeBodies.put(unit.getID(), unit);
    }

    public void removeActiveBody(Character unit) {
        activeBodies.remove(unit.ID);
    }


    public void init() {
        holder = new WorldHolder();
        global.universe = this;
        holder.init();


        debuginit();

    }

    private void debuginit() {
        hero = addEntity(100, 500, 250, 250, UnitType.HERO, "hero");
        heroChar = getCharacter(hero);
        heroChar.equipArmament(holder.getArmament(WeaponName.SHURIKEN), Slot.RIGHTHAND);
        PointLight pointLight = new PointLight(holder.rayHandler, 10, new Color(1, 1, 1, 1), 1000, hero.getPosition().x, hero.getPosition().y);
        CollisionHandler.setStandartTerrainHandler(new TerrainCollisionHandler() {


            @Override
            public void collideWith(Body hitter, Body hit) {
                if (!toRemove.contains(hit)) toRemove.add(hit);
            }

            @Override
            public void detachFrom(Body hitter, Body hit) {

            }

            @Override
            public HandlerType getName() {
                return HandlerType.BULLETDISPOSAL;
            }

            @Override
            public void setTypeCombination() {
                TypeHolder.addTypeHolder(new TypeHolder(TerrainType.ALL, UnitType.BULLET, HandlerType.BULLETDISPOSAL));
            }
        });

        heroChar.collisionHandler.setCustomTerrainCollisionHandler(new TerrainCollisionHandler() {

            @Override
            public void collideWith(Body a, Body b) {
                Character temp = getCharacter(a);


                heroChar.addContact(a);

            }

            @Override
            public void detachFrom(Body hitter, Body b) {

                Character temp = getCharacter(hitter);

                heroChar.decrContacts(hitter);


            }

            @Override
            public HandlerType getName() {
                return HandlerType.TOUCHFLOOR;
            }

            @Override
            public void setTypeCombination() {
                LinkedList<TerrainType> types = new LinkedList<>();
                types.add(TerrainType.FLOOR);
                types.add(TerrainType.ICE);
                TypeHolder.addTypeHolder(new TypeHolder(types, UnitType.HERO, HandlerType.TOUCHFLOOR));
            }
        });

        Body wolf = addEntity(700, 500, 250, 250, UnitType.ENEMY, "herowolf");
        getCharacter(wolf).collisionHandler.setCustomUnitCollisionHandler(new UnitCollisionHandler() {
            @Override
            public void collideWith(Body hitter, Body b) {
                Log.t("hit by bullet");
                if (!toRemove.contains(hitter)) toRemove.add(hitter);

            }

            @Override
            public void detachFrom(Body hitter, Body b) {

            }

            @Override
            public HandlerType getName() {
                return HandlerType.ENEMYHIT;
            }

            @Override
            public void setTypeCombination() {
                TypeHolder.addTypeHolder(new TypeHolder(UnitType.BULLET, UnitType.ENEMY, HandlerType.ENEMYHIT, true));
            }
        });


        addObject(250, 0, 2000, 5, 0, TerrainType.FLOOR, "hero");
        addObject(600, 0, 1, 500, 0, TerrainType.WALL, "shuriken");

    }

    public boolean pressedA = false;
    public boolean pressedD = false;

    public void getUserInput() {

        if (Gdx.input.isTouched()) {
            //hero set at 400x amd 650y
            int x = Gdx.input.getX() - 400 - 75;
            int y = Gdx.input.getY() - (650 / 2) + 78;
            int hx = (int) get(hero.getPosition().x) + x;
            int hy = (int) get(hero.getPosition().y) + y;


            heroChar.addAttackAction(hx, hy);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            //todo: implement actual weapon switching
            if (!heroChar.equipment.rightHand.name.equals(WeaponName.TORCH))
                heroChar.equipArmament(holder.getArmament(WeaponName.TORCH), Slot.RIGHTHAND);
            else heroChar.equipArmament(holder.getArmament(WeaponName.SHURIKEN), Slot.RIGHTHAND);

        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (heroChar.canJump()) Action.createAction(ActionType.JUMP, hero).link();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (!pressedD) {
                Action.createAction(ActionType.MOVE, hero, 1, 0).link();
                pressedD = true;
            }

        } else if (pressedD) {
            Action.createAction(ActionType.MOVE, hero, 1, 1).link();
            pressedD = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (!pressedA) {
                Action.createAction(ActionType.MOVE, hero, -1, 0).link();
                pressedA = true;
            }

        } else if (pressedA) {
            Action.createAction(ActionType.MOVE, hero, -1, 1).link();
            pressedA = false;
        }

    }

    public void doStep() {
        Array<Body> bodies = new Array<>();
        holder.world.getBodies(bodies);
        for (Body selected : bodies
        ) {
            Character temp = (Character) selected.getUserData();
            temp.doActions();
        }

        holder.world.step(utilFields.getTimeStep(), utilFields.getVelocityIterations(), utilFields.getPositionIterations());
        for (Body a : toRemove
        ) {
            holder.world.destroyBody(a);
        }
        toRemove.clear();

    }

    private float previousX = 100;
    private float previousY = 500;

    public void adjustCamera() {
        Vector2 vector2 = new Vector2(get(hero.getPosition().x) - previousX, get(hero.getPosition().y) - previousY);

        holder.batch.setProjectionMatrix(holder.lightscam.combined);

        holder.lightscam.position.set((hero.getPosition().x + 400) * 1, (hero.getPosition().y - set(holder.lightscam.viewportHeight) + 650) * 1, 0);
        holder.lightscam.translate(vector2.x, vector2.y);
        holder.lightscam.update();
        ScreenUtils.clear(0, 0, 0.2f, 1);
        // holder.camera.update();


    }

    public void sendMessages() {

    }

    public void doLogic() {

    }

    public void drawAll() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        holder.batch.setProjectionMatrix(holder.lightscam.combined);
        holder.batch.begin();
        Array<Body> bodies = new Array<>();
        holder.world.getBodies(bodies);
        for (Body body : bodies) {
            Character character = getCharacter(body);
            holder.batch.draw(character.getTexture(), get(body.getPosition().x), get(body.getPosition().y));

        }

        holder.batch.end();


        // holder.lightscam.update();

    }

    public void lightUp() {

        holder.rayHandler.setCombinedMatrix(holder.lightscam.combined.scale(conversionFactor, conversionFactor, conversionFactor), set(holder.lightscam.position.x), set(holder.lightscam.position.y), holder.lightscam.viewportWidth * holder.lightscam.zoom, holder.lightscam.viewportHeight * holder.lightscam.zoom);

        for (ConeLight cone : coneLights
        ) {
            cone.update();
        }
        holder.rayHandler.updateAndRender();

    }

    public void removeLights() {

        for (ConeLight c : coneLights) {
            c.remove();
        }

        coneLights.clear();

    }


    public Body addEntity(int x, int y, int width, int height, UnitType type, String texture) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(set(x), set(y));
        body = holder.world.createBody(bodyDef);
        PolygonShape dynamic = new PolygonShape();
        dynamic.setAsBox(set(width), set(height));

        FixtureDef fixtureDef = new FixtureDef();

        switch (type) {
            case HERO:
            case BULLET:
                fixtureDef.filter.groupIndex = -1;
                break;
        }

        fixtureDef.shape = dynamic;
        fixtureDef.density = PhysicsTable.getDensity();
        fixtureDef.friction = PhysicsTable.getFriction();
        body.createFixture(fixtureDef);

        Texture text = new Texture(Gdx.files.internal(texture.concat(".png")));
        Character character = new Character(body);
        character.setTexture(text);
        character.setUnitType(type);
        body.setUserData(character);

        return body;
    }

    public Body addObject(int x, int y, int width, int height, int density, TerrainType type, String texture) {
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(set(x), set(y));

        Body ground = holder.world.createBody(groundBodyDef);

        PolygonShape groundbox = new PolygonShape();
        groundbox.setAsBox(set(width), set(height));
        ground.createFixture(groundbox, density);
        Character chara = new Character(ground);
        Texture text;
        try {
            text = new Texture(Gdx.files.internal(texture));
        } catch (Exception e) {
            text = new Texture(Gdx.files.internal(texture.concat(".png")));
        }


        chara.setTexture(text);
        chara.setTerrainType(type);

        ground.setUserData(chara);
        return ground;
    }


}
