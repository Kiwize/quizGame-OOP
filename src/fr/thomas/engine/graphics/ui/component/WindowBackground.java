package fr.thomas.engine.graphics.ui.component;

import fr.thomas.engine.GameEngine;
import fr.thomas.engine.graphics.texture.Texture;
import fr.thomas.engine.graphics.ui.EComponentPosition;

public class WindowBackground extends GraphicalComponent{

	public WindowBackground(float x, float y, int width, int height, Texture texture) {
		super(x, y, width, height);
		super.texture = texture;
	}

	public WindowBackground fullscreen() {
		this.width = GameEngine.instance.getWindow().getWidth();
		this.height = GameEngine.instance.getWindow().getHeight();
		return this;
	}
	
	public WindowBackground center() {
		this.pos.set(GameEngine.instance.getWindow().getWidth() / 2, GameEngine.instance.getWindow().getHeight() / 2);
		super.position = EComponentPosition.CENTER;
		return this;
	}
	
	public void setTexture(Texture texture) {
		super.texture = texture;
	}
	
	@Override
	public void onWindowResize(int newWidth, int newHeight) {
		if(super.position == EComponentPosition.CENTER) {
			this.height = (int) (newWidth / (float) (16f / 9f));
			this.width = newWidth;
			this.pos.set(newWidth / 2, newHeight / 2);
		}
		
		super.onWindowResize(newWidth, newHeight);
	}
	
}
