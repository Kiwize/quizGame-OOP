package fr.thomas.engine.graphics.sprite;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import fr.thomas.engine.graphics.Model;

public class SpriteSheet {
	
	private Model model;
	
	private int totalWidth;
	private int totalHeight;
	
	private int spriteSizeX;
	private int spriteSizeY;
	
	private float sheetRows;
	private float sheetCols;
	
	private int spriteCount;
	
	private float[] textureCoordsBuffer = new float[8];
	private Map<Integer, float[]> textureCoords = new HashMap<>();
	
	public SpriteSheet(Model model, int ttx, int tty, int spx, int spy) {
		this.model = model;
		this.totalWidth = ttx;
		this.totalHeight = tty;
		
		this.spriteSizeX = spx;
		this.spriteSizeY = spy;
		
		sheetRows = (totalHeight / spriteSizeY);
		sheetCols = (totalWidth / spriteSizeX);
		
		spriteCount = (int) (sheetRows * sheetCols);
		
		
		//TODO Générer les coordonées et les stocker dans le tableau.
		genTextureCoords();
	}
	
	private void genTextureCoords() {
		for(int x = 1; x < spriteCount +1; x++) {
			genTexCoordsFromID(x);
			textureCoords.put(x - 1, Arrays.copyOf(textureCoordsBuffer, textureCoordsBuffer.length));
		}
	}
	
	private boolean genTexCoordsFromID(int sid) { //sid min 1
		if(sid >= 1 && sid <= spriteCount) {
			float spritesPointer = spriteSizeX * sid;
			float currentRow = 0;
			
			while(spritesPointer > totalWidth) {
				currentRow++;
				spritesPointer -= totalWidth;
			}
			
			float spriteXStart = (float) ((spritesPointer / spriteSizeX) / sheetRows  - (float) (1.0f / sheetRows));
			float spriteYStart = (float) (currentRow / sheetRows);

			textureCoordsBuffer[0] = spriteXStart;
			textureCoordsBuffer[1] = spriteYStart;
			
			textureCoordsBuffer[2] = spriteXStart + ((float) (1.0f / sheetRows));
			textureCoordsBuffer[3] = spriteYStart;
			
			textureCoordsBuffer[4] = spriteXStart + ((float) (1.0f / sheetRows));
			textureCoordsBuffer[5] = spriteYStart + ((float) (1.0f / sheetCols));
			
			textureCoordsBuffer[6] = spriteXStart;
			textureCoordsBuffer[7] = spriteYStart + ((float) (1.0f / sheetCols));
			
			return true;
		}
		
		return false;
	}
	
	public boolean renderSprite(int sid) {
		if(sid >= 0 && sid <= spriteCount - 1) {
			model.updateTextureCoordinate(textureCoords.get(sid));
			return true;
		}
		
		return false;
	}
	
	public Map<Integer, float[]> getSprites() {
		return textureCoords;
	}
	
	public void yFlip() {
		float[] data = Arrays.copyOf(textureCoordsBuffer, textureCoordsBuffer.length);
		
		for(int x = 0; x < 4; x++)
			textureCoordsBuffer[x] = data[x + 4];
		
		for(int y = 4; y < 8; y++)
			textureCoordsBuffer[y] = data[y - 4];
		
		model.updateTextureCoordinate(textureCoordsBuffer);
	}
	
	public void xFlip() {
		//2, 3, 0, 1, 6, 7, 4, 5
		float[] data = Arrays.copyOf(textureCoordsBuffer, textureCoordsBuffer.length);
		
		textureCoordsBuffer[0] = data[2];
		textureCoordsBuffer[1] = data[3];
		textureCoordsBuffer[2] = data[0];
		textureCoordsBuffer[3] = data[1];
		textureCoordsBuffer[4] = data[6];
		textureCoordsBuffer[5] = data[7];
		textureCoordsBuffer[6] = data[4];
		textureCoordsBuffer[7] = data[5];
		
		model.updateTextureCoordinate(textureCoordsBuffer);
	}
	
	public void renderPart(float[] textureCoords) {
		for(int x = 0; x < textureCoords.length; x++)
			textureCoordsBuffer[x] = textureCoords[x];
		model.updateTextureCoordinate(textureCoords);
	}
}
