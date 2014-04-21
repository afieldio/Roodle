package io.afield.roodle;

import io.afield.roodle.Assets.Assets;
import io.afield.roodle.Screens.MenuScreen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

public class RoodleGame extends Game {

	private static final String TAG = RoodleGame.class.getName();

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Assets.instance.init(new AssetManager());
		setScreen(new MenuScreen(this));
		
	}
	

	
}
