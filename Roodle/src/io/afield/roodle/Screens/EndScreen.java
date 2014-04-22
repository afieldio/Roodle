package io.afield.roodle.Screens;

import io.afield.roodle.Constants;
import io.afield.roodle.Assets.Assets;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class EndScreen extends AbstractGameScreen implements HttpResponseListener{

	private static final String TAG = EndScreen.class.getName();


	private Stage stage;

	private Skin skin;
	private TextureAtlas atlas;

	private LabelStyle ls;
	private Label label, label2;
	private String s;

	private Image imgBackground;

	private TextButton btnSubmit, btnPlay;
	private TextButtonStyle btnStyle;
	private TextureRegionDrawable drawableUp, drawableDown;

	private TextField tf;
	private TextFieldStyle tas;


	private BitmapFont type;
	private BitmapFont theFont;

	// debug
	private final float DEBUG_REBUILD_INTERVAL = 5.0f;
	private boolean debugEnabled = false;
	private float debugRebuildStage;
	private int score;
	private String user;

	private HttpRequest httpRequest;

	public EndScreen(Game game, int score) {
		super(game);
		this.score = score;

		// TODO Auto-generated constructor stub
	}

	private void rebuildStage(){

		//System.out.println("score is:" + score);
		type = Assets.instance.fonts.type;


		btnStyle = new TextButtonStyle(drawableUp, drawableDown, null, theFont);
		btnStyle.font = Assets.instance.fonts.theFont;
		btnStyle.up = skin.getDrawable("btn_up");
		btnStyle.down = skin.getDrawable("btn_down");
		btnStyle.font.setScale(1, -1);

		tas = new TextFieldStyle();
		tas.font = Assets.instance.fonts.type;
		tas.fontColor = Color.RED; 


		ls = new LabelStyle(type, Color.RED);

		Table layerScore = buildScoreLayer();
		Table layerBackground = buildBackgroundLayer();
		Table layerControl = buildControlLayer();

		layerScore.center();

		stage.clear();
		Stack stack = new Stack();
		stack.setWidth(Gdx.graphics.getWidth());
		stack.setHeight(Gdx.graphics.getHeight());

		stack.add(layerBackground);
		stack.add(layerScore);
		
		stack.add(layerControl);
		stage.addActor(stack);

	}

	private Table buildBackgroundLayer(){

		Table layer = new Table();

		imgBackground = new Image(skin, "background");

		layer.add(imgBackground);

		return layer;
	}



	private Table buildControlLayer() {
		Table layer = new Table();
		layer.bottom();

		layer.pad(0, 0, 20, 0);

		btnPlay = new TextButton("Play", btnStyle);
		btnSubmit = new TextButton("Submit", btnStyle);

		btnPlay.setBounds(0, 0, btnPlay.getWidth(), btnPlay.getHeight());
		btnSubmit.setBounds(0, 0, btnSubmit.getWidth(), btnSubmit.getHeight());


		VerticalGroup g = new VerticalGroup().space(20).reverse().pad(5).fill();

		g.addActor(btnPlay);
		g.addActor(btnSubmit);

		Table table = new Table().debug();
		table.add(g);
		table.pack();
		//table.setPosition(0, 0);
		layer.add(table);

		btnPlay.addListener(new ClickListener(){

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				System.err.println("Yea play again!");
				game.setScreen(new GameScreen(game));
				super.touchUp(event, x, y, pointer, button);
			}

		});

		btnSubmit.addListener(new ClickListener(){


			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				//System.err.println("This is going to be tough, " + "i dont know what I am doing here");
				int playerScore = score;
				String userName = user; 
				String url = "http://localhost:8888/?page=insertScore&score=" + playerScore + "&user=" + userName;
				String httpMethod = Net.HttpMethods.GET;
				httpRequest = new HttpRequest(httpMethod);
				httpRequest.setUrl(url);

				Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {

					@Override
					public void handleHttpResponse(HttpResponse httpResponse) {
						String data = httpResponse.getResultAsString();
						System.out.println("Data is " + data);
						
					}

					@Override
					public void failed(Throwable t) {
						System.err.println("something not good: " + t);

					}

					@Override
					public void cancelled() {
						// TODO Auto-generated method stub

					}
				});

				game.setScreen(new MenuScreen(game));
				super.touchUp(event, x, y, pointer, button);
			}
		});

		return layer;


	}

	private Table buildScoreLayer() {
		Table layer = new Table();
		String playerScore = Integer.toString(score);
		layer.padBottom(10.0f);
		label = new Label("Your Score is:", ls);
		label2 = new Label(playerScore, ls);


		VerticalGroup g = new VerticalGroup().space(20).reverse().pad(5).fill();

		g.addActor(label);
		g.addActor(label2);

		Table table = new Table().debug();
		table.add(g);
		table.pack();
		//table.setPosition(0, 0);
		layer.add(table);
		layer.center();


		return layer;
	}


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

	}

	@Override
	public void hide() {
		//stage.dispose();

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleHttpResponse(HttpResponse httpResponse) {
		final int statusCode = httpResponse.getStatus().getStatusCode();

		System.out.println("http respons code: " + statusCode);
	}

	@Override
	public void failed(Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelled() {
		// TODO Auto-generated method stub

	}



}
