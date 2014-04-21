package io.afield.roodle.Assets;

import io.afield.roodle.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {
	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	private AssetManager assetManager;

	/**
	 * Assets
	 */

	public AssetRoodle roodle;
	public AssetBullet bullet;
	public AssetBackground background;
	public AssetFonts fonts;
	public AssetButtonUp buttonUp;
	public AssetButtonDown buttonDown;
	public AssetHeart heart;
	public AssetObstacle obstacle;
	public AssetStar star;
	


	private Assets () {}

	public void init(AssetManager assetManager){
		this.assetManager = assetManager;
		assetManager.setErrorListener(this);
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
		assetManager.finishLoading();

		Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
		for (String a : assetManager.getAssetNames())
			Gdx.app.debug(TAG, "asset: " + a);

		TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
		// enable texture filtering for pixel smoothing
		for (Texture t : atlas.getTextures())
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		roodle = new AssetRoodle(atlas);
		bullet = new AssetBullet(atlas);
		background = new AssetBackground(atlas);
		fonts = new AssetFonts();
		buttonUp = new AssetButtonUp(atlas);
		buttonDown = new AssetButtonDown(atlas);
		heart = new AssetHeart(atlas);
		obstacle = new AssetObstacle(atlas);
		star = new AssetStar(atlas);
	}

	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset " + asset + "'", (Exception)throwable);
	}

	@Override
	public void dispose() {
		assetManager.dispose();
		fonts.theFont.dispose();
		fonts.type.dispose();

	}

	/*
	 * Assets
	 * 
	 */

	public class AssetRoodle{
		public final AtlasRegion roodle;

		public AssetRoodle (TextureAtlas atlas){
			roodle = atlas.findRegion("roodle");
			roodle.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
	}

	public class AssetBullet{
		public final AtlasRegion bullet;

		public AssetBullet (TextureAtlas atlas){
			bullet = atlas.findRegion("bullet");
		}
	}

	public class AssetBackground{
		public final AtlasRegion background;

		public AssetBackground (TextureAtlas atlas){
			background = atlas.findRegion("background");
		}
	}
	
	public class AssetButtonUp{
		public final AtlasRegion buttonUp;

		public AssetButtonUp (TextureAtlas atlas){
			buttonUp = atlas.findRegion("buttonup");
		}
		
	}
	
	public class AssetButtonDown{
		public final AtlasRegion buttonDown;

		public AssetButtonDown (TextureAtlas atlas){
			buttonDown = atlas.findRegion("buttonDown");
		}
	}
	
	public class AssetHeart{
		public final AtlasRegion heart;
		
		public AssetHeart (TextureAtlas atlas){
			heart = atlas.findRegion("heart");
		}
	}
	
	public class AssetObstacle{
		public final AtlasRegion obstacle;
		
		public AssetObstacle(TextureAtlas atlas){
			obstacle = atlas.findRegion("obstacle");
		}
	}
	
	public class AssetStar{
		public final AtlasRegion star;
		
		public AssetStar (TextureAtlas atlas){
			star = atlas.findRegion("star");
		}
	}
	
	public class AssetFonts{
		
		public final BitmapFont theFont;
		public final BitmapFont type;
		
		public AssetFonts(){
			theFont = new BitmapFont(Gdx.files.internal("images/theFont.fnt"), true);
			type = new BitmapFont(Gdx.files.internal("images/type.fnt"), true);
			
			type.isFlipped();
			type.setScale(0.5f, 0.5f
					);
			
			theFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			type.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		
	}


}
