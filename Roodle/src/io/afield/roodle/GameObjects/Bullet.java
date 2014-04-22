package io.afield.roodle.GameObjects;

import io.afield.roodle.Assets.Assets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bullet extends AbstractGameObject{
	
	private float lifeTime;
	private float lifeTimer;
	
	private TextureRegion bulletRegion;
	
	private boolean remove;
	
	public Bullet(){
		init();
		super.velocity = velocity;
		super.position = position;
	}
	
	private void init(){
		bulletRegion = Assets.instance.bullet.bullet;
		
		bounds.set(0,0,dimension.x, dimension.y);
		origin.x = dimension.x/2;
		origin.y = dimension.y/2;
	}
	


	@Override
	public void update(float deltaTime) {
		position.y += 10*deltaTime;
		super.update(deltaTime);
	}

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		
		reg = bulletRegion;
		
		batch.draw(reg, position.x , position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation);
		
	}

}
