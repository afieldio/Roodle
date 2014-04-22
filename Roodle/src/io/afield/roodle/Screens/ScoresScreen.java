package io.afield.roodle.Screens;

import io.afield.roodle.Constants;
import io.afield.roodle.JsonClient;
import io.afield.roodle.JsonClient.ResponseCallback;
import io.afield.roodle.JsonClientException;
import io.afield.roodle.Score;
import io.afield.roodle.Assets.Assets;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.JsonIterator;

public class ScoresScreen extends AbstractGameScreen {
	
	private Skin skin;
	private TextureAtlas atlas;
	
	private Stage stage;
	private Label lable0, label1, label2, label3, label4, label5, label6, label7, label8, label9;
	private LabelStyle ls;
	private BitmapFont type, theFont;
	
	
	public HttpRequest httpRequest;
	String url, httpMethod, requestContent = null;
	
	//http stuff
	public JsonValue root = null;
	public String data;
	public String text;
	public JsonIterator jIter;
	
	private TextButton btnBack;
	private TextButtonStyle btnStyle;
	private TextureRegionDrawable drawableUp, drawableDown, drawableBackground;
	
	

	// debug
	private final float DEBUG_REBUILD_INTERVAL = 5.0f;
	private boolean debugEnabled = false;
	private float debugRebuildStage;


	public ScoresScreen(Game game) {
		super(game);
	}


	private void rebuildStage(){
		type = Assets.instance.fonts.type;
		
	
		
		
		btnStyle = new TextButtonStyle(drawableUp, drawableDown, null, theFont);
		btnStyle.font = Assets.instance.fonts.theFont;
		btnStyle.up = skin.getDrawable("btn_up");
		btnStyle.down = skin.getDrawable("btn_down");

		ls = new LabelStyle(type, Color.BLACK);
		
		Table layerScores = buildScoreLayer();
		Table layerBack = buildBackLayer();
		
		stage.clear();
		Stack stack = new Stack();
		stack.setWidth(Gdx.graphics.getWidth());
		stack.add(layerScores);
		stack.add(layerBack);
		stage.addActor(stack);
		
	}
	
	private Table buildBackLayer(){
		Table layer = new Table();
		layer.center();
		layer.pad( 0, 0, 1.0f, 0);
		
		btnBack = new TextButton("Back", btnStyle);
		btnBack.setBounds(0, 0, btnBack.getWidth(), btnBack.getHeight());
		btnBack.center().bottom();		
		layer.add(btnBack);
		
		btnBack.addListener(new ClickListener(){

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new MenuScreen(game));
				super.touchUp(event, x, y, pointer, button);
			}
			
		});
		
		return layer;
	}
	
	
	private Table buildScoreLayer(){
		
		getScore();
		
		Table layer = new Table();
		layer.top();
		
		while(root.iterator().hasNext()){
			System.out.println(root.next().name);
		}
		
		return layer;
		
	}
	

	
	/*
	 * 
	 * This section below controls the server aspects...there are two Functions to attempt to get the scores from the db
	 * 
	 * getScore and getScore1
	 * 
	 * These are called in the Show method below
	 * 
	 * It is currently set up so that it is requesting information from the server on the desktop version using the getScore.
	 * 
	 * The getScore1 I hoped would have been able to connect with the localhost with the android but I have failed to get it working.
	 * 
	 * 
	 */
	
	
	
	private void getScore1(){
		
		
		/*
		 *NOT BEING CALLED AT PRESENT
		 *
		 *I have a feeling that the error comes from the use of the Score class. Not sure if I have done this right. 
		 */
		
		
		
		Score score = new Score();
		
		ResponseCallback<Score> callback = new ResponseCallback<Score>() {
            public void onResponse(Score returnObject) {
            	System.out.println("Json ok");
            }
            public void onFail(JsonClientException exception) {
            	System.out.println("Json request failed: " + exception.getMessage());
              
            }
        };
        JsonClient.getInstance().sendPost(score, "?page=getScore", callback, Score.class);
	}
	
	

	private void getScore(){
		String url = "http://localhost:8888/?page=getScore";
		String httpMethod = Net.HttpMethods.GET;
		httpRequest = new HttpRequest(httpMethod);
		httpRequest.setHeader("Content-Type", "application/json");
		httpRequest.setUrl(url);
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(final HttpResponse httpResponse) {
				final int statusCode = httpResponse.getStatus().getStatusCode();
				// We are not in main thread right now so we need to post to main thread for ui updates
				String status = httpResponse.getResultAsString();
				JsonValue data = new JsonReader().parse(status);
				
				
			}
			
			@Override
			public void failed(Throwable t) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void cancelled() {
				// TODO Auto-generated method stub
				
			}
			
		
		
		});
		
	}
	
	
	/*
	 * 
	 * 		Generic Files for the Scores Screen
	 * 
	 * 		Render
	 * 		Resize
	 * 		Show
	 * 		Hide
	 * 		Pause
	 * 
	 */

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT );


		if (debugEnabled) {
			debugRebuildStage -= deltaTime;
			if (debugRebuildStage <= 0) {
				debugRebuildStage = DEBUG_REBUILD_INTERVAL;
				rebuildStage();
			}
		}
		stage.act(deltaTime);
		stage.draw();
		
		//Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);

	}

	@Override
	public void show() {
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		skin = new Skin();
		atlas = new TextureAtlas(Constants.TEXTURE_ATLAS_OBJECTS);
		skin.addRegions(atlas);
		
		rebuildStage();
		
		//getScore1();
		
	}

	@Override
	public void hide() {
		stage.dispose();

	}

	@Override
	public void pause() {

	}


	


}
