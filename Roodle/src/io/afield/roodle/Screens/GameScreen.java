package io.afield.roodle.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import io.afield.roodle.WorldController;
import io.afield.roodle.WorldRenderer;

public class GameScreen extends AbstractGameScreen {
	

	private static final String TAG = GameScreen.class.getName();
	
	private WorldController wc;
	private WorldRenderer wr;
	
	
	private boolean paused;

	
	public GameScreen(Game game) {
		super(game);
		
	}
	
	
	@Override
	public void render(float deltaTime) {
		if(!paused){
			wc.update(deltaTime);
		}
		
		Gdx.gl.glClearColor(40.0f, 40.0f, 40.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		wr.render();

	}

	@Override
	public void resize(int width, int height) {
		wr.resize(width, height);

	}

	@Override
	public void show() {
		wc = new WorldController(game);
		wr = new WorldRenderer(wc);
		Gdx.input.setCatchBackKey(false);

	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
		paused = true;
	}

	@Override
	public void resume() {
		
		super.resume();
		paused = false;
	}
	
	
	
}
