package fr.thomas.engine.graphics.ui.component;

import org.joml.Vector4f;

import fr.thomas.engine.GameEngine;
import fr.thomas.engine.graphics.Model;
import fr.thomas.engine.graphics.Shader;
import fr.thomas.engine.graphics.texture.Font;
import fr.thomas.engine.graphics.ui.EComponentPosition;

public class Text extends GraphicalComponent {

	private String content;

	private Font font;

	private Vector4f letterOffset;

	private boolean hideContent;

	private int textWidth;

	public Text(float x, float y, int width, int height, Font font, String content) {
		super(x, y, width, height);
		this.font = font;
		this.content = content;
		this.letterOffset = new Vector4f();
		super.texture = font.getSpriteSheetTexture();
		super.shaderUsed = new Shader("text");
		this.textWidth = (int) (content.toCharArray().length * width / 1.5f);
	}

	@Override
	public void render() {
		int cursor = 0;
		for (char letter : content.toCharArray()) {
			letterOffset.set(0.7f * cursor, 0, 0);
			shaderUsed.setUniform("offset", letterOffset);
			renderChar(letter);
			super.render();
			cursor++;
		}

		// Reset to default texture coordinates to prevent overflow on other renders.
		Model.DEFAULT_MODEL.updateTextureCoordinate(Model.WHOLE_TEXTURE);
	}

	public void renderChar(char charToRender) {
		if (!hideContent) {
			if (font.getCharMap().containsKey(charToRender)) {
				font.getSpriteSheet().renderSprite(font.getCharMap().get(charToRender));
			}
		} else {
			if (font.getCharMap().containsKey('*')) {
				font.getSpriteSheet().renderSprite(font.getCharMap().get('*'));
			}
		}
	}

	@Override
	public void onWindowResize(int newWidth, int newHeight) {
		if (isAutoPlaced) {
			if (position == EComponentPosition.CENTER) {
				this.pos.set(newWidth / 2 - textWidth / 2, pos.y);
			} else if (position == EComponentPosition.RIGHT) {
				this.pos.set(newWidth - textWidth, pos.y);
			}
		}

		super.onWindowResize(newWidth, newHeight);
	}

	@Override
	public void updateHitbox(float x, float y, int width, int height) {
		topLeftPoint.set(x - (width / 2), y + (height / 2));
		bottomRightPoint.set(x + (width / 2) + textWidth, y - (height / 2));
	}

	public Text centerHorizontally() {
		this.isAutoPlaced = true;
		this.pos.set((GameEngine.instance.getWindow().getWidth() / 2) - textWidth / 2, pos.y);
		super.position = EComponentPosition.CENTER;
		return this;
	}

	public Text left() {
		this.isAutoPlaced = true;
		this.pos.set(width, pos.y);
		super.position = EComponentPosition.LEFT;
		return this;
	}

	public Text right() {
		this.isAutoPlaced = true;
		this.pos.set(GameEngine.instance.getWindow().getWidth() - textWidth, pos.y);
		super.position = EComponentPosition.RIGHT;
		return this;
	}

	public void append(String text) {
		this.content += text;
	}

	public void clear() {
		this.content = "";
	}

	public void removeLastChar() {
		if (content.length() > 0)
			this.content = this.content.substring(0, this.content.length() - 1);
	}
	
	public void setContent(String content) {
		this.content = content;
		this.textWidth = (int) (content.toCharArray().length * width / 1.5f);
	}

	public void hideContent(boolean state) {
		this.hideContent = state;
	}

	@Override
	public Shader getShader() {
		return super.getShader();
	}

	public int getTextWidth() {
		return textWidth;
	}

	public String getContent() {
		return content;
	}

	public Font getFont() {
		return font;
	}
}
