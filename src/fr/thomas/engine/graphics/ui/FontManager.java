package fr.thomas.engine.graphics.ui;

import java.util.ArrayList;
import java.util.HashMap;

import fr.thomas.engine.graphics.Model;
import fr.thomas.engine.graphics.texture.Font;

public class FontManager {

	private ArrayList<Font> loadedFonts;

	private final HashMap<Character, Integer> DEFAULT_CHARMAP;

	public FontManager() {
		loadedFonts = new ArrayList<Font>();
		
		DEFAULT_CHARMAP = new HashMap<Character, Integer>();
		char c = 'a';
		for (int i = 0; i < 26; i++) {
		    DEFAULT_CHARMAP.put(c, i);
		    c++;
		}
		
		c = 'A';
		for (int i = 0; i < 26; i++) {
		    DEFAULT_CHARMAP.put(c, i + 26);
		    c++;
		}
		
		c = '1';
		for (int i = 52; i < 61; i++) {
		    DEFAULT_CHARMAP.put(c, i);
		    System.out.println(c);
		    c++;
		}
		
		DEFAULT_CHARMAP.put('0', 61);
		DEFAULT_CHARMAP.put('.', 62);
		DEFAULT_CHARMAP.put('?', 63);
		DEFAULT_CHARMAP.put('/', 64);
		DEFAULT_CHARMAP.put('!', 65);
		DEFAULT_CHARMAP.put('$', 66);
		DEFAULT_CHARMAP.put('\'', 67);
		
		DEFAULT_CHARMAP.put('*', 68);
		
		DEFAULT_CHARMAP.put('-', 69);
		DEFAULT_CHARMAP.put('_', 70);
		DEFAULT_CHARMAP.put('é', 71);
		DEFAULT_CHARMAP.put('è', 72);
		DEFAULT_CHARMAP.put(' ', 73);
		
		loadFonts();
	}

	private void loadFonts() {
		loadedFonts.add(new Font(Model.DEFAULT_MODEL, 16, 256, 256, DEFAULT_CHARMAP, "main_font"));
	}
	
	public ArrayList<Font> getLoadedFonts() {
		return loadedFonts;
	}
}
