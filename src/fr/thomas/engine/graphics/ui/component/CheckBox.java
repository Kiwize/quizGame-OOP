package fr.thomas.engine.graphics.ui.component;

import fr.thomas.engine.GameEngine;
import fr.thomas.engine.graphics.texture.Font;

public class CheckBox extends Button {

	protected boolean isChecked;

	protected Text text;
	private Font font;

	public CheckBox(float x, float y, int width, int height) {
		super(x, y, width, height);
	}

	public CheckBox(float x, float y, int width, int height, String text) {
		super(x, y, width, height, text);
		this.font = GameEngine.instance.getFontManager().getLoadedFonts().get(0);
		this.text = new Text(x + this.width * 1.05f, y, height, height, font, text);
		super.haveText = true;
	}

	@Override
	public void click() {
		if (isHovered()) {
			isChecked = !isChecked;
		}
	}

	@Override
	public void render() {
		super.render();
	}
	
	public Text getText() {
		return text;
	}

	public boolean isChecked() {
		return isChecked;
	}
	
	public void setChecked(boolean status) {
		this.isChecked = status;
	}

}
