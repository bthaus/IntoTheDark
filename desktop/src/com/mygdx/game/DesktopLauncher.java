package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.Game;

import java.awt.*;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		Dimension size
				= Toolkit.getDefaultToolkit().getScreenSize();

		// width will store the width of the screen
		int width = (int)size.getWidth();

		// height will store the height of the screen
		int height = (int)size.getHeight();
		config.setWindowedMode(width,height);
		config.setForegroundFPS(60);
		config.setTitle("setup1");
		new Lwjgl3Application(new Game(), config);
	}
}
