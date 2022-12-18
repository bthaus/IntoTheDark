package util;

import com.badlogic.gdx.math.Vector2;

public class utilMethods {
    public static  float set(int pixels){
        float temp=(float) pixels;
        return temp/100;
    }
    public static Vector2 set(Vector2 vector2){
        Vector2 temp=new Vector2();
        temp.set(set((int) vector2.x),set((int) vector2.y));
        return temp;
    }
    public static int get(float value){
        return (int)value*100;
    }

}
