package com.mygdx.game;

import Types.ActionType;
import Types.TerrainType;
import Types.UnitType;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import util.ForceTable;

import java.util.ArrayList;

public class Universe {
    World world;
    RayHandler rayHandler;
    OrthographicCamera camera;
    Body hero;
    Character heroChar;



    public void addEntity(Texture text,int x, int y, UnitType type){

    }
    public void addObject(Texture text,int x, int y, TerrainType type){

    }

    public void init(){


        heroChar=(Character) hero.getUserData();
    }


    public void getUserInput(){
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            Action.createAction(ActionType.JUMP,hero).link();
        }

    }
    public void doStep(){
        Array<Body>bodies=new Array<>();
        world.getBodies(bodies);
        for (Body selected:bodies
             ) {
            Character temp=(Character) selected.getUserData();
            temp.doActions();

        }

    }
    public void adjustCamera(){

    }
    public void sendMessages(){

    }
    public void doLogic(){

    }

    public void drawAll(SpriteBatch batch){

    }
    public void lightUp(){

    }
    public void removeLights(){

    }





}
