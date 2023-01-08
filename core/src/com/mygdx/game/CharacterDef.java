package com.mygdx.game;

import Types.UnitType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import util.Log;

public class CharacterDef {
    int ID,weaponID,HP,width,height;
    UnitType unitType;
    String texture;

    static int counter=0;

    public CharacterDef(int weaponID, int HP, String texture,UnitType type) {
        this.ID=counter++;
        this.weaponID = weaponID;
        this.HP = HP;
        Texture temp=new Texture(Gdx.files.internal("shuriken.png"));
        //todo: fix this weird bug once we actually see whats going on
        this.width = temp.getWidth()*2;
        this.height = temp.getHeight()*5;
        Log.g(this.width+" and height"+ this.height);
        this.texture = texture;
        this.unitType=type;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getWeaponID() {
        return weaponID;
    }

    public void setWeaponID(int weaponID) {
        this.weaponID = weaponID;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }
}
