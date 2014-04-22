package io.afield.roodle.GameObjects;

import io.afield.roodle.Assets.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Roodle extends AbstractGameObject {

	private TextureRegion roodleRegion;
	private int score;	
	//private int lives;

	public Roodle(){
		init();
		super.velocity = velocity;
		super.position = position;
		super.friction = new Vector2(0, 0);
		score = 0;
	}

	private void init(){
		roodleRegion = Assets.instance.roodle.roodle;
		
		bounds.set(0, 0, dimension.x, dimension.y);
		origin.x = dimension.x/2;
		origin.y = dimension.y/2;
		
		
		// Set physics values
//		terminalVelocity.set(3.0f, 4.0f);
//		friction.set(12.0f, 0.0f);
//		acceleration.set(0.0f, -25.0f);
	}


	@Override
	public void update(float deltaTime) {
		checkBounds();
		super.update(deltaTime);
	}
	
	private void checkBounds() {
		if(bounds.x < -Gdx.graphics.getWidth() || bounds.x > Gdx.graphics.getWidth()){
			velocity.x = 0;
		}
		
		
	}

	public void setScore(int score){
		this.score = score;
	}
	
	public int getScore(){
		return score;
	}
	
	public void incrementScore(int update){
		score += update;
	}

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;

		reg = roodleRegion;

		//texture, position x, position y, origin x, origin y, width, height, scale x, scale y, rotation
		batch.draw(reg, position.x , position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation);



	}

}
