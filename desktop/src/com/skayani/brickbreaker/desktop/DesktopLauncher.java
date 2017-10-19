package com.skayani.brickbreaker.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.skayani.brickbreaker.BrickBreaker;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = BrickBreaker.WIDTH;
        config.height = BrickBreaker.HEIGHT;
		config.resizable = false;
		new LwjglApplication(new BrickBreaker(), config);
	}
}
