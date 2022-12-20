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

Universe universe;

	@Override
	public void create () {

	universe=new Universe();
	universe.init();

	}


	@Override
	public void render () {
		universe.getUserInput();
		universe.doStep();
		universe.adjustCamera();
		universe.sendMessages();
		universe.doLogic();
		universe.drawAll();
		universe.lightUp();
		universe.removeLights();

/*
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
		*/

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
			System.out.println("jump");
			body.applyForce(new Vector2(0,10000),body.getWorldCenter(),true);
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
		universe.holder.batch.dispose();
		img.dispose();
	}
}
