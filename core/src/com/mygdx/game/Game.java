package com.mygdx.game;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;


import java.util.ArrayList;
import java.util.LinkedList;

import static com.badlogic.gdx.math.Intersector.overlaps;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	Rectangle enemy;
	Texture img;
	Texture enemyImage;
	Rectangle hero;
	Texture heroImage;
	public BitmapFont font;
	World world;
	Friend friend;
	Body body;
	RayHandler rayHandler;
	LinkedList<Bullet>bullets=new LinkedList<>();
	LinkedList<Bullet>hitBullets=new LinkedList<>();
	int enemyHP=100;
	Box2DDebugRenderer debugRenderer;

	private OrthographicCamera camera;
	@Override
	public void create () {
		batch = new SpriteBatch();
		heroImage= new Texture(Gdx.files.internal("hero.png"));
		enemyImage=new Texture(Gdx.files.internal("enemy1.png"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		font = new BitmapFont();
		Box2D.init();

		world=new World(new Vector2(0, -100f),true);
		world.setContinuousPhysics(true);

		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.position.set(0.0f, -10.0f);

		Body ground=world.createBody(groundBodyDef);

		PolygonShape groundbox=new PolygonShape();
		groundbox.setAsBox(50.0f, 10.0f);
		ground.createFixture(groundbox, 0.0f);


		BodyDef bodyDef=new BodyDef();
		bodyDef.type= BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(100f, 500.0f);
		body= world.createBody(bodyDef);
		PolygonShape dynamic=new PolygonShape();
		dynamic.setAsBox(1.0f, 1.0f);

		FixtureDef fixtureDef=new FixtureDef();
		fixtureDef.shape = dynamic;
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 0.3f;

		body.createFixture(fixtureDef);


		
		 rayHandler=new RayHandler(world);
		 rayHandler.setAmbientLight(new Color(.1f, .1f, .1f, .5f));
		rayHandler.useDiffuseLight(true);
		rayHandler.setShadows(true);



		

		hero=new Rectangle();
		hero.width=270;
		hero.height=270;
		hero.x=100;
		hero.y=100;

		enemy=new Rectangle();
		enemy.height=450;
		enemy.width=370;
		enemy.x=1000;
		enemy.y=100;

		img = new Texture("badlogic.jpg");
	}




	PointLight light;

boolean alive=true;
	@Override
	public void render () {


		getHeroInput();
		world.step(1f/60f, 6, 2);
		Array<Body>arr=new Array<>();
		world.getBodies(arr);


		getShots();
		ScreenUtils.clear(0, 0, 0.2f, 1);
		camera.update();
		batch.begin();


		 light=new PointLight(rayHandler,10,new Color(10,10,10,10),800.0f,body.getPosition().x,body.getPosition().y);

		// batch.draw(friend.hero2imag	e,friend.hero2.x,friend.hero2.y);
		//batch.draw(heroImage, hero.x, hero.y);
		batch.draw(heroImage,body.getPosition().x,body.getPosition().y);


		if(alive)	batch.draw(enemyImage,enemy.x,enemy.y);



		for (Bullet bullet:bullets) {
			bullet.move();
			if(!overlaps(enemy,bullet.rect))	batch.draw(bullet.text,bullet.rect.x, bullet.rect.y);
			else								hitBullets.add(bullet);


		}

		for (Bullet bullet:hitBullets
			 ) {
			enemyHP--;
			if(enemyHP<0){
				alive=false;
			}
			bullets.remove(bullet);
		}


		font.draw(batch, "HP: "+enemyHP, enemy.x, enemy.y);
		hitBullets.clear();
		batch.end();
		rayHandler.setCombinedMatrix(camera.combined);
		rayHandler.updateAndRender();
		light.remove();
	}



	private void getShots() {
		int x, y;
		if (Gdx.input.isTouched()) {
			x = Gdx.input.getX();
			y = Gdx.graphics.getHeight() - Gdx.input.getY();
			bullets.add(new Bullet(hero,x,y));
		}

	}

	private void getHeroInput() {

		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			body.localVector.y+=50;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			System.out.println("impulse entered");
		body.applyLinearImpulse(-100.80f, 0, body.localVector.x, body.localVector.y, true);
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.D)){
			body.applyLinearImpulse(100.80f, 0, body.localVector.x, body.localVector.y, true);
		}
		//addGravity(body);
	}

	private void addGravity(Body  hero) {
		if(hero.localVector.y>100){
			hero.localVector.y-=20;
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
