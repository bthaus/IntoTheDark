package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.mygdx.game.Network.Client;
import com.mygdx.game.Network.Host;


import static com.badlogic.gdx.math.Intersector.overlaps;

public class Game extends ApplicationAdapter {


Universe universe;
Host host;

	@Override
	public void create () {

	universe=new Universe();
	universe.init();
	host =new Host(8000);

	if(host.running) host.start();
	else new Client("localhost",8000).start();


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


	}



	@Override
	public void dispose () {
		universe.holder.batch.dispose();

	}
}
