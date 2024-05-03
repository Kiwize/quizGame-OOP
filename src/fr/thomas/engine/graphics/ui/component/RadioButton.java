package fr.thomas.engine.graphics.ui.component;

import fr.thomas.engine.GameEngine;
import fr.thomas.engine.graphics.ui.EComponentPosition;

public class RadioButton extends CheckBox {
	
	private RadioButtonGroup group;

	public RadioButton(float x, float y, int width, int height) {
		super(x, y, width, height);
		super.isChecked = false;
		group = null;
	}
	
	public RadioButton(float x, float y, int width, int height, String text) {
		super(x, y, width, height, text);
		super.isChecked = false;
		group = null;
	}
	
	public void defineGroup(RadioButtonGroup group) {
		this.group = group;
	}
	
	@Override
	public void click() {
		if(isHovered()) {
			super.isChecked = true;
			if(group != null) {
				group.updateGroupStatus(this);
			}
			
			if(super.onClickCallback != null) {
				onClickCallback.onClick(this);
			}
		}
	}
	
	@Override
	public void render() {
		super.render();
	}
	
	public void setContent(String text) {
		super.text.setContent(text);
	}
	
	@Override
	public RadioButton centerHorizontally() {
		this.isAutoPlaced = true;
		this.pos.set((GameEngine.instance.getWindow().getWidth() / 2), pos.y);
		if (haveText)
			this.text.pos.set((GameEngine.instance.getWindow().getWidth() / 2) - text.getTextWidth()- 40, pos.y);
		super.position = EComponentPosition.CENTER;
		return this;
	}
	
	@Override
	public void onWindowResize(int newWidth, int newHeight) {
		if (super.position == EComponentPosition.CENTER) {
			this.pos.set((newWidth / 2), pos.y);
			if (haveText)
				this.text.pos.set((newWidth / 2) - text.getTextWidth() - 40, pos.y);
		}
	}
}
