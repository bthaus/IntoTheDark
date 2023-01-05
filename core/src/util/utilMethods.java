package util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Character;

public class utilMethods {
    public static int conversionFactor=100;
    public static  float set(int pixels){
        float temp=(float) pixels;
        return temp/conversionFactor;
    }
    public static float set(float pixels){return pixels/conversionFactor;}
    public static Vector2 set(Vector2 vector2){
        Vector2 temp=new Vector2();
        temp.set(set((int) vector2.x),set((int) vector2.y));
        return temp;
    }
    public static float get(float value){
        return value*conversionFactor;
    }
    public static Character getCharacter(Body body){
        Character character=(Character) body.getUserData();
        return character;

    }


}
