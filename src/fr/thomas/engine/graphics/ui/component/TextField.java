package fr.thomas.engine.graphics.ui.component;

import fr.thomas.engine.GameEngine;
import fr.thomas.engine.graphics.texture.Font;
import fr.thomas.engine.graphics.ui.EComponentPosition;

public class TextField extends GraphicalComponent {

	private Text fieldContent;
	private Text fieldText;

	private Font font;

	private boolean isSelected;

	public TextField(float x, float y, int width, int height) {
		super(x, y, width, height);
		this.fieldContent = new Text(x - (width / 2.5f), y, (int) (height / 1.9f), (int) (height / 1.9f), font, "");
		isSelected = false;
	}

	public TextField(float x, float y, int width, int height, String text) {
		super(x, y, width, height);
		this.font = GameEngine.instance.getFontManager().getLoadedFonts().get(0);
		this.fieldText = new Text(x - this.width, y, height, height, font, text);
		this.fieldText.pos.set(x - (this.width / 2 + fieldText.getTextWidth() * 2.5f), y);

		this.fieldContent = new Text(x - (width / 2.5f), y, (int) (height / 1.9f), (int) (height / 1.9f), font, "");
		super.haveText = true;
		isSelected = false;
	}
	
	public void hideContent(boolean isHidden) {
		this.fieldContent.hideContent(isHidden);
	}

	@Override
	public void render() {
		super.render();
	}

	public TextField centerHorizontally() {
		this.isAutoPlaced = true;
		this.pos.set((GameEngine.instance.getWindow().getWidth() / 2), pos.y);
		this.fieldContent.pos.set(pos.x - (width / 2.5f), pos.y);
		if (haveText)
			this.fieldText.pos.set(pos.x - (this.width / 2 + fieldText.getTextWidth() + 5), pos.y);
		super.position = EComponentPosition.CENTER;
		return this;
	}

	@Override
	public void onWindowResize(int newWidth, int newHeight) {
		if (super.position == EComponentPosition.CENTER) {
			this.pos.set((newWidth / 2), pos.y);
			this.fieldContent.pos.set(pos.x - (width / 2.5f), pos.y);
			if (haveText)
				this.fieldText.pos.set(pos.x - (this.width / 2 + fieldText.getTextWidth() + 5), pos.y);
		}

		super.onWindowResize(newWidth, newHeight);
	}

	public void click() {
		if (super.isHovered) {
			isSelected = true;
		} else {
			isSelected = false;
		}
	}

	public Text getFieldContent() {
		return fieldContent;
	}

	public Text getFieldText() {
		return fieldText;
	}

	public String getValue() {
		return fieldContent.getContent();
	}

	public void updateKeyPress(int keycode) {
		if (isSelected) {
			switch (keycode) {
			case 259:
				this.fieldContent.removeLastChar();
				break;
			}
		}

	}

	public void updateCharPress(char key) {
		if (isSelected) {
			if (fieldContent.getFont().getCharMap().containsKey(key)) {
				this.fieldContent.append(String.valueOf(key));
			}
		}
	}

	public boolean isSelected() {
		return isSelected;
	}
}
