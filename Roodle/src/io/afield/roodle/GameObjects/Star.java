package io.afield.roodle.GameObjects;

import io.afield.roodle.Assets.Assets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Star extends AbstractGameObject {
	
	private TextureRegion regStar;
	public boolean collected;
	
	public Star (){
		init();
		
	}
	
	private void init(){
		dimension.set(0.5f, 0.5f);
		regStar = Assets.instance.star.star;
		bounds.set(0, 0, dimension.x, dimension.y);
		collected = false;
	}

	@Override
	public void render(SpriteBatch batch) {
		if(collected) return;
		
		TextureRegion reg = null;
		reg = regStar;
		batch.draw(regStar, position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation);

	}
	
	public int getScore(){
		return 100;
	}

}
