package util;

import com.badlogic.gdx.math.Vector2;

public class PhysicsTable {
    public static Vector2 jumpForce=new Vector2(0,50);
    public static Vector2 walkingSpeedLeft=new Vector2(-10,0);
    public static Vector2 walkingSpeedRight=new Vector2(10,0);
    private static float density=1;
    private static float friction=1f;
    private static Vector2 gravity=new Vector2(0,-30f);
    public static float getDensity() {
        return density;
    }

    public static float getFriction() {
        return friction;
    }

    public static Vector2 getGravity() {
        return gravity;
    }
}
