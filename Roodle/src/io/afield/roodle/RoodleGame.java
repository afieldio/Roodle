package io.afield.roodle;

import io.afield.roodle.Assets.Assets;
import io.afield.roodle.Screens.GameScreen;

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
		setScreen(new GameScreen(this));
		
	}
	
	@Override
	public void dispose() {
		Assets.instance.dispose();
		super.dispose();
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		Assets.instance.init(new AssetManager());
		super.resume();
	}
	

	
}
