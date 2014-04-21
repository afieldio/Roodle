package io.afield.roodle.Screens;

import io.afield.roodle.Constants;
import io.afield.roodle.Assets.Assets;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class MenuScreen extends AbstractGameScreen {

	private Stage stage;
	private Skin skin;
	private TextureAtlas atlas;
	
	private Image imgBackground;

	private TextButton btnPlay;
	private TextButton btnScores;
	private TextButton btnInstructions;
	private TextButtonStyle textBtnStyle;
	private TextureRegionDrawable drawableUp, drawableDown, drawableBackground;
	
	private BitmapFont type;
	private BitmapFont theFont;

	// debug
	private final float DEBUG_REBUILD_INTERVAL = 5.0f;
	private boolean debugEnabled = false;
	private float debugRebuildStage;

	public MenuScreen(Game game) {
		super(game);

	}

	private void rebuildStage(){
		System.err.println( "AM I CALLING THIS?");
		Gdx.input.setInputProcessor(stage);
		
		textBtnStyle = new TextButtonStyle(drawableUp, drawableDown, null, theFont);
		textBtnStyle.font = Assets.instance.fonts.theFont;
		textBtnStyle.up = skin.getDrawable("btn_up");
		textBtnStyle.down = skin.getDrawable("btn_down");
		textBtnStyle.font.setScale(1, -1);
		
		
		
		//create layers
		//Table layerBackground = buildBackgroundLayer();
		Table layerControls = buildControlsLayer();

		//assemble stage
		stage.clear();
		Stack stack = new Stack();
		stack.setWidth(Gdx.graphics.getWidth());
		stack.setHeight(Gdx.graphics.getHeight());
		stack.setOrigin(0.0f, 0.0f);
		//stack.add(layerBackground);
		stack.add(layerControls);
		
		
		//stack.setPosition((Gdx.graphics.getWidth()/2 - stack.getWidth()/2), 0);
		stage.addActor(stack);
		
		
		

	}

	private Table buildBackgroundLayer () {
		Table layer = new Table();
		imgBackground = new Image(skin, "background");
		imgBackground.setFillParent(true);
		
		layer.add(imgBackground);
		return layer;
	}

	private Table buildControlsLayer () {
		Table layer = new Table();
		
		btnPlay = new TextButton("Play", textBtnStyle);
		btnPlay.setBounds(0, 0, btnPlay.getWidth(), btnPlay.getHeight());
		
		btnScores = new TextButton("Scores", textBtnStyle);
		btnScores.setBounds(0, 0, btnScores.getWidth(), btnScores.getHeight());
		
		btnInstructions = new TextButton("Instructions", textBtnStyle);
		btnInstructions.setBounds(0, 0, btnInstructions.getWidth(), btnInstructions.getHeight());
		
		VerticalGroup g = new VerticalGroup().space(10).reverse().pad(5).fill();
		
		
		
		g.addActor(btnInstructions);
		g.addActor(btnScores); 
		g.addActor(btnPlay);

		Table table = new Table();
		table.add(g);
		table.pack();
		layer.add(table);
		
		btnPlay.addListener(new ClickListener(){

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("Play");
				game.setScreen(new GameScreen(game));
				super.touchUp(event, x, y, pointer, button);
			}
			
		});
		
		btnScores.addListener(new ClickListener(){

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("Score");
				game.setScreen(new ScoresScreen(game));
				super.touchUp(event, x, y, pointer, button);
			}
			
		});
		
		btnInstructions.addListener(new ClickListener(){

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("Instructions");
				game.setScreen(new InstructionsScreen(game));
				super.touchUp(event, x, y, pointer, button);
			}
			
		});
			
		return layer;
	}
	
	private void onPlayClicked(){
		game.setScreen(new GameScreen(game));
	}
	
	private void onScoresClicked(){
		game.setScreen(new ScoresScreen(game));
	}

	private void onInstructionsClicked(){
		game.setScreen(new InstructionsScreen(game));
	}
	
	
	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT );

//		if(Gdx.input.isTouched()){
//			game.setScreen(new GameScreen(game));
//		}

		if (debugEnabled) {
			debugRebuildStage -= deltaTime;
			if (debugRebuildStage <= 0) {
				debugRebuildStage = DEBUG_REBUILD_INTERVAL;
				rebuildStage();
			}
		}
		stage.act(deltaTime);
		stage.draw();
		
		Table.drawDebug(stage);

	}

	@Override
	public void resize(int width, int height) {
		//stage.getViewport().update(width, height, true);
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
		stage.dispose();

	}

	@Override
	public void pause() {


	}

}
