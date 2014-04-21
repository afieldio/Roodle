package io.afield.roodle.Screens;

import io.afield.roodle.WorldController;
import io.afield.roodle.WorldRenderer;
import io.afield.roodle.Assets.Assets;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

public abstract class AbstractGameScreen implements Screen {
	
	protected Game game;
	private WorldController wc;
	private WorldRenderer wr;
	protected int width;
	protected int height;
	
	public AbstractGameScreen (Game game) {
		this.game = game;
		
		
		
		
	}
	
	
	public abstract void render (float deltaTime);
	public abstract void resize (int width, int height);
	public abstract void show ();
	public abstract void hide ();
	public abstract void pause ();
	public void resume () {
		Assets.instance.init(new AssetManager());
	}
	
	public void dispose () {
		Assets.instance.dispose(); 
	}
}