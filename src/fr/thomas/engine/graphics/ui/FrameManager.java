package fr.thomas.engine.graphics.ui;

import java.util.ArrayList;

import fr.thomas.engine.GameEngine;

public class FrameManager {

	private UserInterface shownFrame;
	private ArrayList<UserInterface> loadedFrames;

	private UIRenderer uiRenderer;

	public FrameManager() {
		this.loadedFrames = new ArrayList<UserInterface>();
		this.uiRenderer = new UIRenderer();
	}

	public void loadFrame(UserInterface frame) {
		this.loadedFrames.add(frame);
	}

	public void unloadFrame(UserInterface frame) {
		if (shownFrame.equals(frame)) {
			System.err.println("Cannot unload frame in use !");
			return;
		} else {
			this.loadedFrames.remove(frame);
		}
	}

	public void showFrame(String frameName) {
		for (UserInterface frame : loadedFrames) {
			if (frame.getName().equals(frameName)) {
				frame.setVisible(true);
				shownFrame = frame;
				uiRenderer.renderFrame(frame);
				this.updateProjection(GameEngine.instance.getWindow().getWidth(),
						GameEngine.instance.getWindow().getHeight());
				this.updateComponentsHitbox();
			} else {
				frame.setVisible(false);
			}
		}
	}

	public void render() {
		uiRenderer.render();
	}

	public void updateProjection(int width, int height) {
		uiRenderer.updateProjection(width, height);
	}

	public void updateComponentLogic() {
		uiRenderer.updateComponentLogic();
	}

	public ArrayList<UserInterface> getLoadedFrames() {
		return loadedFrames;
	}

	public UserInterface getShownFrame() {
		return shownFrame;
	}

	public UserInterface getShownFrame(String name) {
		for (UserInterface frame : loadedFrames) {
			if (frame.getName().equals(name))
				return frame;
		}

		return null;
	}

	public void updateComponentsHitbox() {
		if (shownFrame != null)
			shownFrame.updateComponentsHitbox();
	}

	public void onMousePress() {
		if (shownFrame != null)
			shownFrame.onMousePress();
	}

	public void onCharPress(char key) {
		if (shownFrame != null)
			shownFrame.onCharPress(key);
	}

	public void onKeyPress(int keycode) {
		if (shownFrame != null)
			shownFrame.onKeyPress(keycode);
	}

}
