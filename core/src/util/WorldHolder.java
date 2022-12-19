package util;

import Types.TerrainType;
import Types.UnitType;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Character;

import static util.utilMethods.getCharacter;
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
        setDefauktListeners();



    }
    public void setDefauktListeners(){
        world.setContactListener(new ContactListener() {
            boolean relevant=false;
            boolean heroA=false;
            Character charA;
            Character charB;

            @Override
            public void beginContact(Contact contact) {
                System.out.println("checking fixtures");
                Body bodyA = contact.getFixtureA().getBody();
                Body bodyB = contact.getFixtureB().getBody();
                 charA=getCharacter(bodyA);
                 charB=getCharacter(bodyB);
                boolean AisFloor=false;
                boolean BisFloor=false;
                if(charA.getTerrainType().equals(TerrainType.FLOOR)){
                    AisFloor=true;
                }
                if(charB.getTerrainType().equals(TerrainType.FLOOR)){
                   BisFloor=true;
                }
                if(!(AisFloor||BisFloor)){
                    return;
                }
                if(AisFloor||charB.getUnitType().equals(UnitType.HERO)){
                    charB.setCanJump(true);
                    relevant=true;
                    System.out.println("B can jump");
                }
                if(BisFloor||charA.getUnitType().equals(UnitType.HERO)){
                    charA.setCanJump(true);
                    relevant=true;
                    heroA=true;
                    System.out.println("A can jump");
                }


            }

            @Override
            public void endContact(Contact contact) {
               if(relevant){
                   System.out.println("cant jump anymore");
                   if(heroA) charA.setCanJump(false);
                    else charB.setCanJump(false);
               }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }
}
