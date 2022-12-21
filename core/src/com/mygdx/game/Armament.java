package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Armament {
    public float getVelocity() {
        return 10;
    }

    enum Type{
        WEAPON,
        SHIELD,
        SORCERY
    }
    int dps;
    Texture texture;
    Vector2 offset;
    public Vector2 getRelativePosition(Vector2 a){
        return null;
    }


}
