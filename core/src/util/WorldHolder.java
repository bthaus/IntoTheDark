package util;

import Handler.ActionHandler;
import Types.STATE;
import Types.TerrainType;
import Types.UnitType;
import Types.WeaponName;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Armament;
import com.mygdx.game.Character;

import java.util.HashMap;
import java.util.Map;

import static util.utilMethods.getCharacter;
import static util.utilMethods.set;

public class WorldHolder {
    public SpriteBatch batch;
    public  World world;
    public RayHandler rayHandler;
    public OrthographicCamera camera;
    public OrthographicCamera lightscam;
    private Map<WeaponName, Armament> armaments=new HashMap<>();

    public void init() {
        batch = new SpriteBatch();
        world=new World(PhysicsTable.getGravity(),true);
        rayHandler=new RayHandler(world);
        camera = new OrthographicCamera();



        camera.setToOrtho(false, utilFields.getCamerawidth(), utilFields.getCamerawidth());

        lightscam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        lightscam.setToOrtho(false);
        lightscam.update();



        world.setContinuousPhysics(true);

        rayHandler.setAmbientLight(new Color(.1f, .1f, .1f, .5f));
        rayHandler.useDiffuseLight(true);
        rayHandler.setShadows(true);
        rayHandler.setAmbientLight(1);
        rayHandler.setBlurNum(2);
        setDefaultListeners();
        initArmaments();



    }
    public Armament getArmament(WeaponName name){
        return armaments.get(name);
    }
    public void initArmaments(){
        Armament shuriken=new Armament();
        shuriken.setStandardThrowingWeaponHandler();
        shuriken.setName(WeaponName.SHURIKEN);
        shuriken.setAttackDuration(200);
        shuriken.setDamage(20);
        shuriken.setVelocity(1);
        shuriken.setTexture(new Texture(Gdx.files.internal("shuriken.png")));
        armaments.put(WeaponName.SHURIKEN,shuriken);

        Armament torch=new Armament();
        torch.setStandardTochHandler();
        torch.setName(WeaponName.TORCH);
        torch.setAttackDuration(0);
        torch.setDamage(0);
        torch.setAngle(15);
        torch.setRange(10);
        armaments.put(WeaponName.TORCH,torch);


    }

    public void setDefaultListeners(){
        world.setContactListener(new ContactListener() {


            @Override
            public void beginContact(Contact contact) {
                Body bodyA = contact.getFixtureA().getBody();
                Body bodyB = contact.getFixtureB().getBody();
                Character charA = getCharacter(bodyA);
                Character charB = getCharacter(bodyB);
                charA.collidedWith(bodyB);
                charB.collidedWith(bodyA);
            }

            @Override
            public void endContact(Contact contact) {
                Body bodyA = contact.getFixtureA().getBody();
                Body bodyB = contact.getFixtureB().getBody();
                Character charA = getCharacter(bodyA);
                Character charB = getCharacter(bodyB);
                charA.uncollidedWith(bodyB);
                charB.uncollidedWith(bodyA);
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

    }

    public void addCustomListener(ContactListener contactListener){
        world.setContactListener(contactListener);
    }
}
