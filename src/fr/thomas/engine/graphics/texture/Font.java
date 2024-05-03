package fr.thomas.engine.graphics.texture;

import java.util.HashMap;

import fr.thomas.engine.GameEngine;
import fr.thomas.engine.graphics.Model;
import fr.thomas.engine.graphics.sprite.SpriteSheet;

public class Font {
	
	private SpriteSheet sp;
	private Texture spriteSheetTexture;
	private int ceilSize;
	private int atlasWidth;
	private int atlasHeight;
	
	private HashMap<Character, Integer> charMap;
	
	public Font(Model model, int ceilSize, int atlasWidth, int atlasHeight, HashMap<Character, Integer> charMap, String fontName) {
		this.ceilSize = ceilSize;
		this.charMap = charMap;
		this.atlasWidth = atlasWidth;
		this.atlasHeight = atlasHeight;
		
		sp = new SpriteSheet(model, 256, 256, 16, 16);
		spriteSheetTexture = GameEngine.instance.getTextureLoader().getFont(fontName);
	}
	
	public void setCharMap(HashMap<Character, Integer> newMap) {
		this.charMap.clear();
		this.charMap.putAll(newMap);
	}
	
	
	
	public Texture getSpriteSheetTexture() {
		return spriteSheetTexture;
	}
	
	public SpriteSheet getSpriteSheet() {
		return sp;
	}
	
	public HashMap<Character, Integer> getCharMap() {
		return charMap;
	}
}
