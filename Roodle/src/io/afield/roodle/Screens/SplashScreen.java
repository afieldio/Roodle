package io.afield.roodle.Screens;

import io.afield.roodle.RoodleGame;
import io.afield.roodle.Assets.Assets;
import io.afield.roodle.Tweens.SpriteTween;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SplashScreen extends AbstractGameScreen{
	
	public TextureRegion splashTexture;
	public Sprite splashSprite;
	public SpriteBatch batch;
	public TweenManager manager;

	public SplashScreen(Game game) {
		super(game);
	
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		manager.update(deltaTime);
		
		batch.begin();
		splashSprite.draw(batch);
		batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		splashTexture = new TextureRegion(Assets.instance.background2.background2);
		
		// Need to look at how to load the regions....have chnage stuff in the RoodleGame class....More tomorrrow!!!!!
//		((GLTexture) splashTexture).setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		splashSprite = new Sprite(splashTexture);
		splashSprite.setColor(1, 1, 1, 0);
		splashSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		batch = new SpriteBatch();
		
		Tween.registerAccessor(Sprite.class, new SpriteTween());
		manager = new TweenManager();
		
		TweenCallback cb = new TweenCallback() {
			
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				
				tweenCompleted();
				
			}
		};
		
		Tween.to(splashSprite, SpriteTween.ALPHA, 2f).target(1).ease(TweenEquations.easeInQuad).repeatYoyo(1, 0.5f).setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE).start(manager);		
	}
	
	private void tweenCompleted(){
		game.setScreen(new MenuScreen(game));
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

}
