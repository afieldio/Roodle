package io.afield.roodle;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

public class Main {
	
	private static boolean rebuildAtlas = true; 
	private static boolean drawDebugOutline = true;
	
	public static void main(String[] args) {
		 if (rebuildAtlas) {
	         Settings settings = new Settings();
	         settings.maxWidth = 2048;
	         settings.maxHeight = 2048;
	         settings.debug = drawDebugOutline;
	         TexturePacker2.process(settings, "assets-raw/images", "../Roodle-android/assets/images", "roodle.pack");
	         TexturePacker2.process(settings, "assets-raw/images-ui", "../Roodle-android/assets/images", "roodle-ui.pack");
	       }
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Roodle";
		
		cfg.width = 540;
		cfg.height = 960;
		
		new LwjglApplication(new RoodleGame(), cfg);
	}
}
