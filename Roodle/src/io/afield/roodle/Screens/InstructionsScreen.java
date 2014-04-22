package io.afield.roodle.Screens;

import io.afield.roodle.Constants;
import io.afield.roodle.Assets.Assets;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class InstructionsScreen extends AbstractGameScreen {
	private Stage stage;
	private Skin skin;
	private TextureAtlas atlas;
	private Label label;
	private LabelStyle ls;
	
	private TextureRegion bgRegion;
	private Image imgBackground;
	private Sprite instSprite;
	
	private TextButton btnBack;
	private TextButtonStyle btnStyle;
	private TextureRegionDrawable drawableUp, drawableDown, drawableBackground;

	
	
	private BitmapFont type;
	private BitmapFont theFont;

	// debug
	private final float DEBUG_REBUILD_INTERVAL = 5.0f;
	private boolean debugEnabled = false;
	private float debugRebuildStage;
	
	
	public InstructionsScreen(Game game) {
		super(game);
	}
	
	private void rebuildStage(){
		Gdx.input.setInputProcessor(stage);
		type = Assets.instance.fonts.type;
		bgRegion = Assets.instance.instructions.instructions;

		
		

		
		
		btnStyle = new TextButtonStyle(drawableUp, drawableDown, null, theFont);
		btnStyle.font = Assets.instance.fonts.theFont;
		btnStyle.up = skin.getDrawable("btn_up");
		btnStyle.down = skin.getDrawable("btn_down");
//		btnStyle.font.setScale(1, -1);
		
		ls = new LabelStyle(type, Color.BLACK);
		ls.font.setScale(1, -1);
		
		//Table layerLabel = buildLabelLayer();
		Table layerBackground = buildBackgroundLayer();
		Table layerBack = buildBackLayer();
		
		stage.clear();
		Stack stack = new Stack();
		stack.setWidth(Gdx.graphics.getWidth());
		stack.setHeight(Gdx.graphics.getHeight());
		stack.add(layerBackground);
		stack.add(layerBack);
		
		stage.addActor(stack);
		
		
		
		
	}
	
	private Table buildBackgroundLayer(){
		
		Table layer = new Table();
		
		
		imgBackground = new Image(skin, "instructions");
		
		layer.add(imgBackground);
		
		return layer;
	}
	
	
	private Table buildLabelLayer(){
		
		label = new Label("Instructions", ls);
		
		Table layer = new Table();

		
		layer.add(label);
		return layer;
	}
	

	
	private Table buildBackLayer(){
		Table layer = new Table();
		layer.pad( 0, 0, 10.0f, 0);
		layer.bottom();
		
		btnBack = new TextButton("Back", btnStyle);
		btnBack.setBounds(0, 0, btnBack.getWidth(), btnBack.getHeight());
			
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
		
		Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

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
		skin.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

}
