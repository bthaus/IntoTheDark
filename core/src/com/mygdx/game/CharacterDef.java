package com.mygdx.game;

import Types.UnitType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

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
        this.width = temp.getWidth();
        this.height = temp.getHeight();
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
