package fr.thomas.engine.graphics.ui.component;

import java.util.ArrayList;

import org.joml.Matrix4f;

import fr.thomas.engine.GameEngine;
import fr.thomas.engine.graphics.Model;
import fr.thomas.engine.graphics.sprite.SpriteSheet;
import fr.thomas.engine.graphics.ui.EComponentPosition;
import fr.thomas.engine.graphics.ui.UserInterface;

public class UIBackground extends GraphicalComponent {
	
	private final int BASE_SIZE = 70;

	private UserInterface userInterface;

	private SpriteSheet uiSpritesheet;

	private Matrix4f target = new Matrix4f();
	private Matrix4f result = new Matrix4f();
	private Matrix4f posMat = new Matrix4f();
	private Matrix4f projection;

	private ArrayList<GraphicalComponent> borderCornerComponents;

	private GraphicalComponent topLeftCorner;
	private GraphicalComponent topRightCorner;
	private GraphicalComponent bottomLeftCorner;
	private GraphicalComponent bottomRightCorner;

	private GraphicalComponent topBorder;
	private GraphicalComponent rightBorder;
	private GraphicalComponent bottomBorder;
	private GraphicalComponent leftBorder;

	public UIBackground(float x, float y, int width, int height, UserInterface userInterface) {
		super(x, y, width, height);
		this.userInterface = userInterface;
		this.borderCornerComponents = new ArrayList<GraphicalComponent>();
		projection = new Matrix4f().setOrtho2D(0, 1920, 0, 1080);
		super.texture = GameEngine.instance.getTextureLoader().getTexture("ui_background");

		this.uiSpritesheet = new SpriteSheet(Model.DEFAULT_MODEL, 48, 48, 16, 16);

		// CORNERS
		topLeftCorner = new GraphicalComponent(x - (width / 2) + BASE_SIZE - (BASE_SIZE / 2), y + (height / 2) - BASE_SIZE - (BASE_SIZE / 2), BASE_SIZE, BASE_SIZE).prioritize();
		topLeftCorner.setName("top_left_corner");
		bottomLeftCorner = new GraphicalComponent(x - (width / 2) + BASE_SIZE - (BASE_SIZE / 2), y - (height / 2) + BASE_SIZE - (BASE_SIZE / 2), BASE_SIZE, BASE_SIZE).prioritize();
		bottomLeftCorner.setName("bottom_left_corner");

		topRightCorner = new GraphicalComponent(x + (width / 2) - BASE_SIZE - (BASE_SIZE / 2), y + (height / 2) - BASE_SIZE - (BASE_SIZE / 2), BASE_SIZE, BASE_SIZE).prioritize();
		topRightCorner.setName("top_right_corner");
		bottomRightCorner = new GraphicalComponent(x + (width / 2) - BASE_SIZE - (BASE_SIZE / 2), y - (height / 2) + BASE_SIZE - (BASE_SIZE / 2), BASE_SIZE, BASE_SIZE).prioritize();
		bottomRightCorner.setName("bottom_right_corner");

		// BORDERS
		topBorder = new GraphicalComponent(x, y + (height / 2) - 10, width - 40, BASE_SIZE).prioritize();
		topBorder.setName("top_border");
		rightBorder = new GraphicalComponent(x - (width / 2) + 10, y, BASE_SIZE, height - 40).prioritize();
		rightBorder.setName("right_border");

		bottomBorder = new GraphicalComponent(x, y - (height / 2) + 10, width - 40, BASE_SIZE).prioritize();
		bottomBorder.setName("bottom_border");
		leftBorder = new GraphicalComponent(x + (width / 2) - 10, y, BASE_SIZE, height - 40).prioritize();
		leftBorder.setName("left_border");

		userInterface.add(this); // Add mainframe in first

		// CORNERS

		// BORDERS

		borderCornerComponents.add(topLeftCorner);
		borderCornerComponents.add(bottomLeftCorner);
		borderCornerComponents.add(topRightCorner);
		borderCornerComponents.add(bottomRightCorner);

		borderCornerComponents.add(topBorder);
		borderCornerComponents.add(rightBorder);
		borderCornerComponents.add(bottomBorder);
		borderCornerComponents.add(leftBorder);
	}

	@Override
	public void render() {
		target.setTranslation(this.getPos().x, this.getPos().y, 1);

		super.shaderUsed.setUniform("projectionMatrix",
				getProjection().mul(target).scaleXY(this.getWidth(), this.getHeight()));

		uiSpritesheet.renderSprite(4);
		super.render();

		for (GraphicalComponent comp : borderCornerComponents) {
			target.setTranslation(comp.getPos().x, comp.getPos().y, 1);

			super.shaderUsed.setUniform("projectionMatrix",
					getProjection().mul(target).scaleXY(comp.getWidth(), comp.getHeight()));

			switch (comp.name) {

			case "top_left_corner":
				uiSpritesheet.renderSprite(0);
				break;

			case "top_right_corner":
				uiSpritesheet.renderSprite(2);
				break;

			case "bottom_left_corner":
				uiSpritesheet.renderSprite(6);
				break;

			case "bottom_right_corner":
				uiSpritesheet.renderSprite(8);
				break;

			case "top_border":
				uiSpritesheet.renderSprite(1);
				break;

			case "bottom_border":
				uiSpritesheet.renderSprite(7);
				break;

			case "left_border":
				uiSpritesheet.renderSprite(5);
				break;

			case "right_border":
				uiSpritesheet.renderSprite(3);
				break;
			}

			super.render();
		}

		Model.DEFAULT_MODEL.updateTextureCoordinate(Model.WHOLE_TEXTURE);
	}

	public UserInterface getUserInterface() {
		return userInterface;
	}

	public UIBackground center() {
		this.pos.set(GameEngine.instance.getWindow().getWidth() / 2, this.pos.x = GameEngine.instance.getWindow().getHeight() / 2);
		topLeftCorner.pos.set(this.pos.x - (width / 2) + 10, this.pos.y + (height / 2) - 10);
		super.position = EComponentPosition.CENTER;
		return this;
	}

	@Override
	public void onWindowResize(int newWidth, int newHeight) {
		if (super.position == EComponentPosition.CENTER) {
			this.pos.set(newWidth / 2, newHeight / 2);
			topLeftCorner.pos.set(this.pos.x - (width / 2) + 10, this.pos.y + (height / 2) - 10);
			bottomLeftCorner.pos.set(this.pos.x - (width / 2) + 10, this.pos.y - (height / 2) + 10);
			topRightCorner.pos.set(pos.x + (width / 2) - 10, pos.y + (height / 2) - 10);
			bottomRightCorner.pos.set(pos.x + (width / 2) - 10, pos.y - (height / 2) + 10);

			topBorder.pos.set(pos.x, pos.y + (height / 2) - 10);
			rightBorder.pos.set(pos.x - (width / 2) + 10, pos.y);
			bottomBorder.pos.set(pos.x, pos.y - (height / 2) + 10);
			leftBorder.pos.set(pos.x + (width / 2) - 10, pos.y);
		}

		super.onWindowResize(newWidth, newHeight);
	}

	public ArrayList<GraphicalComponent> getBorderCornerComponents() {
		return borderCornerComponents;
	}

	public Matrix4f getProjection() {
		projection.mul(posMat, result);
		return result;
	}

}
