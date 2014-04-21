package io.afield.roodle.GameObjects;

import io.afield.roodle.Assets.Assets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Heart extends AbstractGameObject {

	private TextureRegion regHearts;
	public boolean collected;


	private TextureRegion regHeart;

	public Heart(){
		init();
		super.velocity = velocity;
		super.position = position;

	}
	
	private void init(){
		regHeart = Assets.instance.heart.heart;

		bounds.set(0, 0, dimension.x, dimension.y);
		origin.x = dimension.x/2;
		origin.y = dimension.y/2;
	}


	@Override
	public void render(SpriteBatch batch) {
		batch.draw(regHeart, position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation);

	}


	public int getScore(){
		return 250;
	}
}
