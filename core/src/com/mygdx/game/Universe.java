package com.mygdx.game;

import Handler.TerrainCollisionHandler;
import Handler.UnitCollisionHandler;
import Types.*;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import util.PhysicsTable;
import util.*;

import java.util.LinkedList;

import static util.utilMethods.*;

public class Universe {
    WorldHolder holder;
    Body hero;
    Character heroChar;
    LinkedList<Body>toRemove=new LinkedList<>();






    public void init(){
        holder = new WorldHolder();
        holder.init();
        global.universe=this;

        debuginit();

    }

    private void debuginit() {
        hero= addEntity(100,500,250,250,UnitType.HERO,"hero");
        heroChar=getCharacter(hero);

        CollisionHandler.setStandartTerrainHandler(new TerrainCollisionHandler() {


            @Override
            public void collideWith(Body hitter, Body hit) {
               if (!toRemove.contains(hit))toRemove.add(hit);
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
           TypeHolder.addTypeHolder(new TypeHolder(TerrainType.ALL,UnitType.BULLET,HandlerType.BULLETDISPOSAL));
            }
        });

        heroChar.collisionHandler.setCustomTerrainCollisionHandler(new TerrainCollisionHandler() {

            @Override
            public void collideWith(Body a,Body b) {
                Character temp=getCharacter(a);


                    heroChar.addContact(a);

            }

            @Override
            public void detachFrom(Body hitter,Body b) {

                Character temp=getCharacter(hitter);

                    heroChar.decrContacts(hitter);


            }

            @Override
            public HandlerType getName() {
                return HandlerType.TOUCHFLOOR;
            }

            @Override
            public void setTypeCombination() {
                TypeHolder.addTypeHolder(new TypeHolder(TerrainType.FLOOR,UnitType.HERO,HandlerType.TOUCHFLOOR));
            }
        });





        Body wolf=addEntity(700,500,250,250,UnitType.ENEMY,"herowolf");
        getCharacter(wolf).collisionHandler.setCustomUnitCollisionHandler(new UnitCollisionHandler() {
            @Override
            public void collideWith(Body hitter,Body b) {
                System.out.println("hit by bullet");
                if(!toRemove.contains(hitter))  toRemove.add(hitter);

            }

            @Override
            public void detachFrom(Body hitter,Body b) {

            }

            @Override
            public HandlerType getName() {
                return HandlerType.ENEMYHIT;
            }

            @Override
            public void setTypeCombination() {
                    TypeHolder.addTypeHolder(new TypeHolder(UnitType.BULLET,UnitType.ENEMY,HandlerType.ENEMYHIT,true));
            }
        });


       addObject(250,0,2000,5,0,TerrainType.FLOOR,"hero");
       addObject(600,0,1,500,0,TerrainType.WALL,"shuriken");

    }


    public void getUserInput(){
        if (Gdx.input.isTouched()){
            Action.createAction(ActionType.ATTACK,hero).link();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            Action.createAction(ActionType.JUMP,hero).link();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            Action.createAction(ActionType.MOVE,hero, Direction.RIGHT).link();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            Action.createAction(ActionType.MOVE,hero, Direction.LEFT).link();
        }

    }
    public void doStep(){
        Array<Body>bodies=new Array<>();
        holder.world.getBodies(bodies);
        for (Body selected:bodies
             ) {
            Character temp=(Character) selected.getUserData();
            temp.doActions();
        }

        holder.world.step(utilFields.getTimeStep(), utilFields.getVelocityIterations(), utilFields.getPositionIterations());
        for (Body a:toRemove
             ) {
            holder.world.destroyBody(a);
        }
        toRemove.clear();

    }

    public void adjustCamera(){
       holder.camera.update();
        ScreenUtils.clear(0, 0, 0.2f, 1);
    }
    public void sendMessages(){

    }
    public void doLogic(){

    }

    public void drawAll(){
        holder.batch.begin();
       Array<Body>bodies=new Array<>();
       holder.world.getBodies(bodies);
        for (Body body:bodies) {
            Character character=getCharacter(body);
            holder.batch.draw(character.getTexture(),get(body.getPosition().x),get(body.getPosition().y));

        }

        holder.batch.end();
    }
    public void lightUp(){
        PointLight light= new PointLight(holder.rayHandler,10,new Color(1,1,1,1),1000,500,500);
        holder.rayHandler.setCombinedMatrix(holder.camera.combined);
        holder.rayHandler.updateAndRender();


    }
    public void removeLights(){

    }


    public Body addEntity(int x, int y, int width, int height, UnitType type,String texture){
        Body body;
        BodyDef bodyDef=new BodyDef();
        bodyDef.type= BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(set(x), set(y));
        body= holder.world.createBody(bodyDef);
        PolygonShape dynamic=new PolygonShape();
        dynamic.setAsBox(set(width), set(height));

        FixtureDef fixtureDef=new FixtureDef();
        fixtureDef.shape = dynamic;
        fixtureDef.density = PhysicsTable.getDensity();
        fixtureDef.friction =PhysicsTable.getFriction();


        body.createFixture(fixtureDef);


        Texture text=new Texture(Gdx.files.internal(texture.concat(".png")));
        Character character=new Character(body);
        character.setTexture(text);
        character.setUnitType(type);
        body.setUserData(character);

        return body;
    }
    public Body addObject(int x, int y,int width, int height,int density, TerrainType type,String texture){
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(set(x),set(y));

        Body ground=holder.world.createBody(groundBodyDef);

        PolygonShape groundbox=new PolygonShape();
        groundbox.setAsBox(set(width),set(height));
        ground.createFixture(groundbox, density);
        Character chara=new Character(ground);
        Texture text;
        try {
             text=new Texture(Gdx.files.internal(texture));
        } catch (Exception e) {
             text=new Texture(Gdx.files.internal(texture.concat(".png")));
        }


        chara.setTexture(text);
        chara.setTerrainType(type);

        ground.setUserData(chara);
        return ground;
    }



}
