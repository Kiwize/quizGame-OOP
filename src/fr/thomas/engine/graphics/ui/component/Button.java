package fr.thomas.engine.graphics.ui.component;

import fr.thomas.engine.GameEngine;
import fr.thomas.engine.graphics.ui.EComponentPosition;
import fr.thomas.engine.graphics.ui.IOnClickCallback;
import fr.thomas.engine.graphics.ui.IOnHoverCallback;

public class Button extends GraphicalComponent {

	private Text text;

	protected boolean isEnabled;

	private IOnHoverCallback onHoverCallback;
	protected IOnClickCallback onClickCallback;

	public Button(float x, float y, int width, int height) {
		super(x, y, width, height);
		isEnabled = true;
	}

	public Button(float x, float y, int width, int height, String content) {
		super(x, y, width, height);
		this.text = new Text(x, y, (int) (height / 1.3f), (int) (height / 1.3f),
				GameEngine.instance.getFontManager().getLoadedFonts().get(0), content);

		this.text.pos.set(x - text.getTextWidth() / 2, y);
		super.haveText = true;
		isEnabled = true;
	}

	public void setHoverCallback(IOnHoverCallback callback) {
		this.onHoverCallback = callback;
	}

	public void setOnClickCallback(IOnClickCallback callback) {
		this.onClickCallback = callback;
	}

	@Override
	public void onWindowResize(int newWidth, int newHeight) {
		if (super.position == EComponentPosition.CENTER) {
			this.pos.set((newWidth / 2), pos.y);
			if (haveText)
				this.text.pos.set((newWidth / 2) - text.getTextWidth() / 2, pos.y);
		} else if (super.position == EComponentPosition.RIGHT) {
			this.pos.set(newWidth - this.width, pos.y);
			if (haveText)
				this.text.pos.set((newWidth - this.width) - text.getTextWidth() / 2, pos.y);
		}

		super.onWindowResize(newWidth, newHeight);
	}

	public Button centerHorizontally() {
		this.isAutoPlaced = true;
		this.pos.set((GameEngine.instance.getWindow().getWidth() / 2), pos.y);
		if (haveText)
			this.text.pos.set((GameEngine.instance.getWindow().getWidth() / 2) - text.getTextWidth() / 2, pos.y);
		super.position = EComponentPosition.CENTER;
		return this;
	}

	public Button right() {
		this.isAutoPlaced = true;
		this.pos.set(GameEngine.instance.getWindow().getWidth() - this.width, pos.y);
		if (haveText)
			this.text.pos.set((GameEngine.instance.getWindow().getWidth() - this.width) - text.getTextWidth() / 2,
					pos.y);
		super.position = EComponentPosition.RIGHT;
		return this;
	}

	public void click() {
		if (isHovered() && isEnabled) {
			if (onClickCallback != null) {
				onClickCallback.onClick(this);
			}
		}
	}

	@Override
	public boolean isCursorColliding(double mouseX, double mouseY) {
		if (super.isCursorColliding(mouseX, mouseY) && onHoverCallback != null) {
			onHoverCallback.onHover();
			return true;
		} else if (!super.isCursorColliding(mouseX, mouseY) && onHoverCallback != null) {
			onHoverCallback.onNotHovered();
			return false;
		}

		return false;
	}

	public Text getText() {
		return text;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	public boolean isEnabled() {
		return isEnabled;
	}

}
