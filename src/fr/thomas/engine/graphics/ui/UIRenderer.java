package fr.thomas.engine.graphics.ui;

import java.util.ArrayList;

import org.joml.Matrix4f;

import fr.thomas.engine.GameEngine;
import fr.thomas.engine.graphics.Shader;
import fr.thomas.engine.graphics.ui.component.CheckBox;
import fr.thomas.engine.graphics.ui.component.GraphicalComponent;
import fr.thomas.engine.graphics.ui.component.TextField;

public class UIRenderer {

	private UserInterface renderedInterface;

	private ArrayList<GraphicalComponent> normalRender;
	private ArrayList<GraphicalComponent> primaryRender;

	private Matrix4f target = new Matrix4f();
	private Matrix4f result = new Matrix4f();

	private Matrix4f pos = new Matrix4f();
	private Matrix4f projection;

	private int screenX = 0;
	private int screenY = 0;

	public UIRenderer() {
		this.normalRender = new ArrayList<GraphicalComponent>();
		this.primaryRender = new ArrayList<GraphicalComponent>();

		this.target = new Matrix4f();
		projection = new Matrix4f().setOrtho2D(0, 1920, 0, 1080);
		screenX = 1920;
		screenY = 1080;
	}

	public void render() {
		if (renderedInterface == null)
			return;

		primaryRender.clear();
		normalRender.clear();

		if (renderedInterface.isVisible()) {
			// Get component to render with high priority and other one
			for (GraphicalComponent componentToRender : renderedInterface.getComponents()) {
				if (componentToRender.isRenderPrioritized())
					primaryRender.add(componentToRender);
				else
					normalRender.add(componentToRender);
			}

			for (GraphicalComponent componentToRender : normalRender) {
				renderGUIComponent(componentToRender);
			}

			for (GraphicalComponent componentToRender : primaryRender) {
				renderGUIComponent(componentToRender);
			}
		}
	}

	private void renderGUIComponent(GraphicalComponent componentToRender) {

		Shader shaderToUse = componentToRender.getShader();

		shaderToUse.start();
		shaderToUse.setUniform("textureSampler", 0);
		componentToRender.getTexture().bind(0);

		if (componentToRender instanceof CheckBox) {
			componentToRender.setColorOffset(1.0f, 1.0f, 1.0f, 1.0f);
			if (((CheckBox) componentToRender).isChecked()) {
				componentToRender.setColorOffset(0.0f, 1.0f, 0.0f, 1.0f);
			}
		}
		
		if (componentToRender instanceof TextField) {
			componentToRender.setColorOffset(1.0f, 1.0f, 1.0f, 1.0f);
			if (((TextField) componentToRender).isSelected()) {
				componentToRender.setColorOffset(1.0f, 1.0f, 0.0f, 1.0f);
			}
		}

		target.setTranslation(componentToRender.getPos().x, componentToRender.getPos().y, 1);

		shaderToUse.setUniform("projectionMatrix",
				getProjection().mul(target).scaleXY(componentToRender.getWidth(), componentToRender.getHeight()));

		componentToRender.render(); // Render the VBO
		shaderToUse.stop();
	}
	
	public void renderFrame(UserInterface frame) {
		renderedInterface = frame;
	}

	public void updateComponentLogic() {
		if (renderedInterface != null)
			for (GraphicalComponent uicomp : renderedInterface.getComponents()) {
				uicomp.isCursorColliding(GameEngine.instance.getMouseX(), GameEngine.instance.getMouseY());
			}
	}

	public void updateProjection(int width, int height) {
		screenX = width;
		screenY = height;

		if (renderedInterface != null)
			for (GraphicalComponent uicomp : renderedInterface.getComponents()) {
				uicomp.onWindowResize(width, height);
			}
	}

	public Matrix4f getProjection() {
		projection.mul(pos, result);
		return result;
	}

}
