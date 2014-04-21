package io.afield.roodle.GameObjects;

import io.afield.roodle.Assets.Assets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Obstacle extends AbstractGameObject{

	private TextureRegion regObst;
	public boolean collected;
	
	public Obstacle(){
		init();
	}
	
	private void init(){
		dimension.set(1.8f, 1.8f);
		regObst = Assets.instance.obstacle.obstacle;
		bounds.set(0, 0, dimension.x, dimension.y);
		collected = false;
	}

	@Override
	public void render(SpriteBatch batch) {
		if(collected) return;
		
		TextureRegion reg = null;
		reg = regObst;
		batch.draw(regObst, position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation);
		
		
	}
	
	public int lifeLoss(){
		return -1;
	}
}
