package util;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import static util.utilMethods.set;

public class WorldHolder {
    public SpriteBatch batch;
    public  World world;
    public RayHandler rayHandler;
    public OrthographicCamera camera;

    public void init() {
        batch = new SpriteBatch();
        world=new World(PhysicsTable.getGravity(),true);
        rayHandler=new RayHandler(world);
        camera = new OrthographicCamera();


        camera.setToOrtho(false, utilFields.getCamerawidth(), utilFields.getCamerawidth());



        world.setContinuousPhysics(true);

        rayHandler.setAmbientLight(new Color(.1f, .1f, .1f, .5f));
        rayHandler.useDiffuseLight(true);
        rayHandler.setShadows(true);



    }
}
