package fr.thomas.engine.graphics.texture;

import java.util.HashMap;
import java.util.Map;

public class TextureLoader {
	private Map<String, Texture> loadedTextures;
	private Map<String, Texture> loadedFonts;
	private final String[] TEXTURES;
	private final String[] FONTS;
	
	public TextureLoader() {
		loadedTextures = new HashMap<>();
		loadedFonts = new HashMap<>();
		
		TEXTURES = new String[] {"air", "grass", "sprites_test", "background", "ui_background"};
		FONTS = new String[] {"main_font"};
		
		System.out.println("Loading textures...");
		
		loadTextures();
		loadFonts();
	}
	
	private void loadTextures() {
		for(String textureName : TEXTURES) {
			loadedTextures.put(textureName, new Texture("./resources/texture/level/" + textureName + ".png"));
		}
	}
	
	private void loadFonts() {
		for(String fontName: FONTS) {
			loadedFonts.put(fontName, new Texture("./resources/texture/font/" + fontName + ".png"));
		}
	}
	
	
	public Texture getTexture(String textureName) {
		return loadedTextures.get(textureName);
	}
	
	public Texture getFont(String fontName) {
		return loadedFonts.get(fontName);
	}
	
	public String[] getTexturesArray() {
		return TEXTURES;
	}
}
