package io.afield.roodle;

import io.afield.roodle.Assets.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class WorldRenderer implements Disposable{

	private OrthographicCamera camera;
	private OrthographicCamera cameraGUI;
	private SpriteBatch batch;
	private WorldController wc;



	public WorldRenderer(WorldController worldController) {
		this.wc = worldController;
		init();
	}

	private void init(){
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(-Gdx.graphics.getWidth()/2, -Gdx.graphics.getHeight()/2, 0);
		camera.update();

		cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		cameraGUI.position.set(0, 0, 0);
		cameraGUI.setToOrtho(true); // flip y-axis
		cameraGUI.update();
	}

	public void render(){
		
		renderBackground(batch);
		
		
		renderHeart(batch);
		renderStars(batch);
		renderObstacles(batch);
		
		renderBullets(batch);
		
		renderRoodle(batch);
		renderGui(batch);
		

	}
	
	private void renderBullets(SpriteBatch batch){
		wc.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		for(int i=0; i<wc.bullets.size; i++){
			if(wc.bullets.get(i).position.y < Constants.VIEWPORT_HEIGHT){
				wc.bullets.get(i).render(batch);
			}else if(wc.bullets.get(i).position.y > Constants.VIEWPORT_HEIGHT){
				wc.bullets.removeIndex(i);
			}
		}
		
		batch.end();
	}
	
	private void renderRoodle(SpriteBatch batch){
		wc.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		wc.rP.render(batch);
		batch.end();
	}
	
	private void renderHeart(SpriteBatch batch){
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		for (int i = 0; i < wc.hearts.size; i++) {
			if(wc.hearts.get(i).position.y > - Constants.VIEWPORT_HEIGHT){
				wc.hearts.get(i).render(batch);
			}else if( wc.hearts.get(i).position.y < - Constants.VIEWPORT_HEIGHT){
				wc.hearts.removeIndex(i);
			}
		}
			
			
		batch.end();
	}
	
	private void renderStars(SpriteBatch batch){
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		for(int i = 0; i < wc.stars.size; i++){
			if(wc.stars.get(i).position.y > - Constants.VIEWPORT_HEIGHT){
				wc.stars.get(i).render(batch);
			}else if( wc.stars.get(i).position.y < - Constants.VIEWPORT_HEIGHT){
				wc.stars.removeIndex(i);
			}
		}
		
		batch.end();
	}
	
	private void renderObstacles(SpriteBatch batch){
		
			
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		for(int i = 0; i < wc.obsts.size; i++){
			if(wc.obsts.get(i).position.y > - Constants.VIEWPORT_HEIGHT){
				wc.obsts.get(i).render(batch);
			}else if( wc.obsts.get(i).position.y < - Constants.VIEWPORT_HEIGHT){
				wc.obsts.removeIndex(i);
			}
		}
		
		batch.end();
	}

	
	private void renderBackground(SpriteBatch batch){
		batch.setProjectionMatrix(camera.combined);		
		batch.begin();
		
		wc.bG1.render(batch);
		wc.bG2.render(batch);
		wc.bG3.render(batch);
		
//		System.out.println( "x: "+ wc.bG1.dimension.x + "Y: " + wc.bG1.dimension.y);

		
		batch.end();
	}
	
	
	
	
	private void renderGui(SpriteBatch batch){
		batch.setProjectionMatrix(cameraGUI.combined);
	    batch.begin();
	    renderGuiScore(batch);
		renderGuiExtraLive(batch);
		renderGuiFpsCounter(batch);
		
		batch.end();
	}
	
	
	private void renderGuiScore(SpriteBatch batch){
		Assets.instance.fonts.type.draw(batch, "Score: "+ wc.rP.getScore(), 20, 20);
	
	}

	private void renderGuiExtraLive (SpriteBatch batch) {
	
		float x = cameraGUI.viewportWidth -150;
		float y = cameraGUI.viewportHeight - 900 ;
		for (int i = 0; i < Constants.LIVES_START; i++) {
			if (wc.lives <= i)
				batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
			batch.draw(Assets.instance.roodle.roodle,
					x + i * 70, y, 0, 0, 50, 50, 1, 1, 180);
			batch.setColor(1, 1, 1, 1);
		} 
	}
	
	
	
	private void renderGuiFpsCounter (SpriteBatch batch) {
	       float x = cameraGUI.viewportWidth - 150;
	       float y = cameraGUI.viewportHeight - 50;
	       int fps = Gdx.graphics.getFramesPerSecond();
	       BitmapFont fpsFont = Assets.instance.fonts.type;
	       fpsFont.setScale(1, -1);
	       
	       if (fps >= 45) {
	         // 45 or more FPS show up in green
	         fpsFont.setColor(0, 1, 0, 1);
	       } else if (fps >= 30) {
	         // 30 or more FPS show up in yellow
	         fpsFont.setColor(1, 1, 0, 1);
	       } else {
	         // less than 30 FPS show up in red
	         fpsFont.setColor(1, 0, 0, 1);
	       }
	       fpsFont.draw(batch, "FPS: " + fps, x, y);
	       fpsFont.setColor(1, 1, 1, 1); // white
	     }
	

	public void resize(int width, int height){
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();

		cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
		cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT/ (float)height) * (float)width;
		cameraGUI.position.set(cameraGUI.viewportWidth / 2, cameraGUI.viewportHeight / 2, 0);
		cameraGUI.update();
	}


	@Override
	public void dispose() {
		batch.dispose();

	}

}
