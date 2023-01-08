package util;

import Handler.ActionHandler;
import Handler.TerrainCollisionHandler;
import Types.*;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Action;
import com.mygdx.game.Armament;
import com.mygdx.game.Character;
import com.mygdx.game.CharacterDef;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static util.utilMethods.*;
import static util.utilMethods.get;

public class WorldHolder {
    public SpriteBatch batch;
    public  World world;
    public RayHandler rayHandler;
    public OrthographicCamera camera;
    public OrthographicCamera lightscam;
    private Map<WeaponName, Armament> armaments=new HashMap<>();
    public Map<Integer, WeaponName> index=new HashMap<>();
    private Map<Integer, CharacterDef>enemyDefMap=new HashMap<>();

    public void init() {
        batch = new SpriteBatch();
        world=new World(PhysicsTable.getGravity(),true);

       // camera = new OrthographicCamera();



      //  camera.setToOrtho(false, utilFields.getCamerawidth(), utilFields.getCamerawidth());

        lightscam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        lightscam.setToOrtho(false);
        lightscam.update();
        lightscam.zoom=1.5f;

        rayHandler=new RayHandler(world);

        world.setContinuousPhysics(true);

        rayHandler.setAmbientLight(new Color(.1f, .1f, .1f, .5f));
        rayHandler.useDiffuseLight(true);
        rayHandler.setShadows(true);
        rayHandler.setAmbientLight(1);
        rayHandler.setBlurNum(2);

        setDefaultListeners();
        initArmaments();
        initEnemyDefs();



    }
    public Armament getArmament(WeaponName name){
        return armaments.get(name);
    }
    public void initArmaments(){
        //todo: create external datasheet and implement parsing
        final Armament shuriken=new Armament();
        shuriken.setStandardThrowingWeaponHandler();
        shuriken.setName(WeaponName.SHURIKEN);
        shuriken.setAttackDuration(350);
        shuriken.setSlot(Slot.RIGHTHAND);
        shuriken.setDamage(20);
        shuriken.setVelocity(1);
        shuriken.addAdditionalAction(new ActionHandler() {
            @Override
            public void before() {

            }

            @Override
            public void onStart() {

            }

            @Override
            public STATE execute(float destinationX, float destinationY) {
                int x,y;
                x= (int) get(shuriken.getWielder().getPosition().x)+150;
                y= (int) get(shuriken.getWielder().getPosition().y)+150;
                Body bullet= global.universe.addEntity(x,y,1,1, UnitType.BULLET,"shuriken");
                //todo: collision like in  https://stackoverflow.com/questions/17162837/disable-collision-completely-of-a-body-in-andengine-box2d
                bullet.getFixtureList().get(0).setSensor(true);
                Log.a("additional bullet shot");
                Vector2 direction=new Vector2();
                direction.x=0;
                direction.y=10;
                bullet.applyLinearImpulse(direction,bullet.getWorldCenter(),true);
                return STATE.DONE;
            }

            @Override
            public void after() {

            }

            @Override
            public void perFrame(float x, float y) {

            }
        },500, TriggerType.ONATTACK);
        shuriken.setTexture(new Texture(Gdx.files.internal("shuriken.png")));
        armaments.put(WeaponName.SHURIKEN,shuriken);

        Armament torch=new Armament();
        torch.setStandardTochHandler();
        torch.setName(WeaponName.TORCH);
        torch.setAttackDuration(0);
        torch.setDamage(0);
        torch.setSlot(Slot.BOTHHANDS);
        torch.setAngle(15);
        torch.setRange(10);
        torch.setEquipDuration(1000);
        armaments.put(WeaponName.TORCH,torch);


    }
    public void initEnemyDefs(){
        CharacterDef debugEnemy=new CharacterDef(1,100,"enemy1",UnitType.ENEMY);
        enemyDefMap.put(debugEnemy.getID(),debugEnemy);

    }

    public ActionHandler getDefaultSpawnHandler(final Action action){
        return new ActionHandler() {
            @Override
            public void before() {
                Log.g("starting to spawn something");
            }

            @Override
            public void onStart() {

            }

            @Override
            public STATE execute(float destinationX, float destinationY) {
                Body body= global.universe.addEntity(action.getX(),action.getY(),action.getCharacterDef().getWidth(),action.getCharacterDef().getHeight(),action.getCharacterDef().getUnitType(),action.getCharacterDef().getTexture());
                //if the hosts spawns a character the id is -1, if it is received from network it is set
                if(action.getSpawnedCharID()==-1) action.setSpawnedCharID(getCharacter(body).getID());
                else getCharacter(body).setID(action.getSpawnedCharID());

                return STATE.NOTDONE;
            }
            //todo: set to execute again
            @Override
            public void after() {

            }

            @Override
            public void perFrame(float x, float y) {

            }
        };
    }

    public ActionHandler getDefaultHeroSpawnHandler(final Action action){
        return new ActionHandler() {
            @Override
            public void before() {
                Log.g("starting to spawn something");
            }

            @Override
            public void onStart() {

            }

            @Override
            public STATE execute(float destinationX, float destinationY) {
                final Body body= global.universe.addEntity(action.getX(),action.getY(),action.getCharacterDef().getWidth(),action.getCharacterDef().getHeight(),action.getCharacterDef().getUnitType(),action.getCharacterDef().getTexture());
                //if the hosts spawns a character the id is -1, if it is received from network it is set
                if(action.getSpawnedCharID()==-1) action.setSpawnedCharID(getCharacter(body).getID());
                else getCharacter(body).setID(action.getSpawnedCharID());
                //todo: create list of standard collisionhandlers for heroes and enemies, either here or as standardcollisoinhandlers
                getCharacter(body).collisionHandler.setCustomTerrainCollisionHandler(new TerrainCollisionHandler() {

                    @Override
                    public void collideWith(Body a, Body b) {
                        Character temp = getCharacter(a);


                        getCharacter(body).addContact(a);

                    }

                    @Override
                    public void detachFrom(Body hitter, Body b) {

                        Character temp = getCharacter(hitter);

                        getCharacter(body).decrContacts(hitter);


                    }

                    @Override
                    public HandlerType getName() {
                        return HandlerType.TOUCHFLOOR;
                    }

                    @Override
                    public void setTypeCombination() {
                        LinkedList<TerrainType> types = new LinkedList<>();
                        types.add(TerrainType.FLOOR);
                        types.add(TerrainType.ICE);
                        TypeHolder.addTypeHolder(new TypeHolder(types, UnitType.HERO, HandlerType.TOUCHFLOOR));
                    }
                });
                return STATE.NOTDONE;
            }
            //todo: set to execute again
            @Override
            public void after() {

            }

            @Override
            public void perFrame(float x, float y) {

            }
        };
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

    public Armament getArmamentByID(int y) {
        return armaments.get(index.get(y));
    }

    public CharacterDef getCharacterDefByID(int ID){
        return enemyDefMap.get(ID);
    }
}
