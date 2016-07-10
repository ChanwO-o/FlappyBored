package com.chanwoo.flappybored.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.chanwoo.flappybored.FlappyBored;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = FlappyBored.WIDTH;
		config.height = FlappyBored.HEIGHT;
		config.title = FlappyBored.TITLE;
		new LwjglApplication(new FlappyBored(), config);
	}
}
