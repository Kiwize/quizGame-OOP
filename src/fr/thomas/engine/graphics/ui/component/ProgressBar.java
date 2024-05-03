package fr.thomas.engine.graphics.ui.component;

import org.joml.Vector4f;

import fr.thomas.engine.GameEngine;
import fr.thomas.engine.graphics.Shader;
import fr.thomas.engine.graphics.ui.EComponentPosition;

public class ProgressBar extends GraphicalComponent {

	private GraphicalComponent fill;
	
	private int minValue;
	private int maxValue;
	private int currentValue;
	
	private Vector4f color;
	
	private final int baseWidth;
	
	public ProgressBar(float x, float y, int width, int height, int min, int max) {
		super(x, y, width, height);
		this.baseWidth = width;
		this.minValue = min;
		this.maxValue = max;
		this.currentValue = min;
		super.shaderUsed = new Shader("progressbar");
		this.color = new Vector4f(1.0f, 0.0f, 0.0f, 1.0f);
		
		this.fill = new GraphicalComponent(x, y, width, height).setRenderShader(shaderUsed);
		fill.setColorOffset(1f, 0.75f, 0.75f, 1);
	}
	
	@Override
	public void render() {
		fill.width = (int) (baseWidth * ((float) currentValue / maxValue));
		this.fill.pos.set(this.pos.x - (baseWidth / 2) + fill.width / 2, fill.pos.y);

		super.render();
	}
	
	@Override
	public void onWindowResize(int newWidth, int newHeight) {
		if (isAutoPlaced) {
			if (position == EComponentPosition.CENTER) {
				this.pos.set(newWidth / 2 , pos.y);
			}
		}

		super.onWindowResize(newWidth, newHeight);
	}

	public ProgressBar centerHorizontally() {
		this.isAutoPlaced = true;
		this.pos.set((GameEngine.instance.getWindow().getWidth() / 2) - width / 2, pos.y);
		super.position = EComponentPosition.CENTER;
		return this;
	}
	
	public int getMinValue() {
		return minValue;
	}
	
	public int getMaxValue() {
		return maxValue;
	}
	
	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}
	
	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}
	
	public int getCurrentValue() {
		return currentValue;
	}
	
	public void setCurrentValue(int currentValue) {
		this.currentValue = currentValue;
	}
	
	public GraphicalComponent getFill() {
		return fill;
	}
}
