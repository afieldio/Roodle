	package io.afield.roodle.GameObjects;

import io.afield.roodle.Assets.Assets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Background extends AbstractGameObject {

	private TextureRegion backgroundRegion1, backgroundRegion2;

	public Background(){
		init();
		super.position = position;
		super.velocity = velocity;
		super.dimension = dimension;
		super.friction = new Vector2(0, 0);


	}

	private void init(){

		backgroundRegion1 = 
				Assets.instance.background.background;

	}




	@Override
	public void render(SpriteBatch batch) {


		TextureRegion reg1 = null;

		reg1 = backgroundRegion1;

		batch.draw(reg1, position.x, position.y, 
				origin.x, origin.y, 
				dimension.x, dimension.y, 
				scale.x, scale.y, 
				rotation);

	}

	@Override
	public void update(float deltaTime) {

		super.update(deltaTime);
	}

}
