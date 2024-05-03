package fr.thomas.engine.graphics.ui.component;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector4f;

import fr.thomas.engine.GameEngine;
import fr.thomas.engine.graphics.Model;
import fr.thomas.engine.graphics.Shader;
import fr.thomas.engine.graphics.texture.Texture;
import fr.thomas.engine.graphics.ui.EComponentPosition;

public class GraphicalComponent {
	
	protected Texture texture;
	protected Vector2f pos;
	protected int width;
	protected int height;

	protected Vector2d topLeftPoint;
	protected Vector2d bottomRightPoint;

	protected boolean isHovered;

	protected Vector4f colorOffset;

	protected Shader shaderUsed;
	protected boolean isVisible;

	protected boolean isAutoPlaced;
	protected EComponentPosition position;

	protected String name;

	protected boolean haveText;
	protected boolean prioritizeRender;
	
	protected int enableGlitch;
	
	public GraphicalComponent(float x, float y, int width, int height) {
		pos = new Vector2f(x, y);
		this.width = width;
		this.height = height;
		texture = GameEngine.instance.getTextureLoader().getTexture("grass");

		this.topLeftPoint = new Vector2d(x - (width / 2), y + (height / 2));
		this.bottomRightPoint = new Vector2d(x + (width / 2), y - (height / 2));
		this.colorOffset = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.isVisible = true;
		this.shaderUsed = new Shader("uishader");
		this.haveText = false;
		this.prioritizeRender = false;
		this.name = "default_name";
		this.enableGlitch = 0;
	}

	public void updateHitbox(float x, float y, int width, int height) {
		topLeftPoint.set(x - (width / 2), y + (height / 2));
		bottomRightPoint.set(x + (width / 2), y - (height / 2));
	}

	public void render() {
		if (isVisible) {
			shaderUsed.setUniform("color", colorOffset);
			Model.DEFAULT_MODEL.render();
		}
	}

	public boolean isCursorColliding(double mouseX, double mouseY) {
		if (mouseX > topLeftPoint.x && mouseX < bottomRightPoint.x) {
			if (mouseY < topLeftPoint.y && mouseY > bottomRightPoint.y) {
				isHovered = true;
				return true;
			}
		}
		isHovered = false;
		return false;
	}

	public void onWindowResize(int width, int height) {

	}

	public boolean isRenderPrioritized() {
		return prioritizeRender;
	}

	public GraphicalComponent prioritize() {
		this.prioritizeRender = true;
		return this;
	}

	public void setVisibility(boolean newState) {
		this.isVisible = newState;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public Vector2f getPos() {
		return pos;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public Vector4f getColorOffset() {
		return colorOffset;
	}

	public void setColorOffset(float red, float green, float blue, float alpha) {
		this.colorOffset.set(red, green, blue, alpha);
	}

	public GraphicalComponent setRenderShader(Shader shader) {
		this.shaderUsed = shader;
		return this;
	}

	public boolean isHovered() {
		return isHovered;
	}

	public Shader getShader() {
		return shaderUsed;
	}

	public boolean haveText() {
		return haveText;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setEnableGlitch(int enableGlitch) {
		this.enableGlitch = enableGlitch;
	}
}
