package fr.thomas.engine;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;

import org.lwjgl.opengl.GL11;

import fr.thomas.engine.graphics.Camera;
import fr.thomas.engine.graphics.Model;
import fr.thomas.engine.graphics.Window;
import fr.thomas.engine.graphics.texture.TextureLoader;
import fr.thomas.engine.graphics.ui.FontManager;
import fr.thomas.engine.graphics.ui.FrameManager;
import fr.thomas.proto0.controller.GameController;
import fr.thomas.proto0.view.HomeFrame;
import fr.thomas.proto0.view.LoginFrame;
import fr.thomas.proto0.view.PlayFrame;

public class GameEngine {

	public static final int FPS_LIMIT = 60;
	public static final double TIME_BETWEEN_UPDATES = 1000000000 / FPS_LIMIT;
	
	private GameController controller;

	public static int CURRENT_FPS = 0;
	public static int FPS = FPS_LIMIT;

	public static final String VERSION = "0.2a";
	public static final String NAME = "Test Game Engine";

	private long lastTime = System.currentTimeMillis();

	public static GameEngine instance;

	private GameTimer timer;
	private TextureLoader tl;

	// private TestWorld testWorld;
	
	private FrameManager frameManager;
	
	private HomeFrame homeView;
	private LoginFrame loginView;
	private PlayFrame playView;
	
	private Window window;
	private Camera camera;

	private double mouseX;
	private double mouseY;

	private FontManager fontManager;

	public GameEngine(GameController controller) {
		this.controller = controller;
		instance = this;

		if (!glfwInit()) {
			throw new IllegalStateException("Failed to initialize LWJGL !");
		}
		window = new Window(1920, 1080, NAME + " " + VERSION);
		glEnable(GL_TEXTURE_2D); // Enable texture rendering

		tl = new TextureLoader();
		camera = new Camera(window.getWidth(), window.getHeight());
		Model.DEFAULT_MODEL = new Model(Model.vertices, Model.tex_coords);

		// testWorld = new TestWorld("test_world");
		fontManager = new FontManager();
		frameManager = new FrameManager();

		homeView = new HomeFrame(controller);
		loginView = new LoginFrame(controller);
		playView = new PlayFrame(controller);
		
		frameManager.loadFrame(homeView);
		frameManager.loadFrame(loginView);
		frameManager.loadFrame(playView);
		
		frameManager.showFrame("login_frame"); //Show first frame at program launch
		
		timer = new GameTimer(this);
	}
	
	public void openWindow() {
		long lastUpdateTime = System.nanoTime();
		long lastRenderTime = System.nanoTime();

		while (!glfwWindowShouldClose(window.getWindow())) {
			long now = System.nanoTime();
			lastUpdateTime = now;

			lastRenderTime = System.nanoTime();
			int timeToRender = (int) (lastRenderTime - lastUpdateTime);
			if (timeToRender < TIME_BETWEEN_UPDATES) {
				try {
					Thread.sleep((long) (TIME_BETWEEN_UPDATES - timeToRender) / 1000000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			frameExecTime();
		}

		timer.stopTimer();
		glfwTerminate();
	}

	private void frameExecTime() {
		camera.setMoving(false);

		glfwPollEvents();
		glClear(GL_COLOR_BUFFER_BIT); // Clear buffer to black pixels.

		// testWorld.render();

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		frameManager.render();

		GL11.glDisable(GL11.GL_BLEND);

		glfwSwapBuffers(window.getWindow());

		// FPS
		CURRENT_FPS++;
		if (System.currentTimeMillis() - lastTime >= 1000) {
			System.out.println(CURRENT_FPS);
			FPS = CURRENT_FPS;
			CURRENT_FPS = 0;
			lastTime = System.currentTimeMillis();
		}
	}

	// Exec in Timer thread.
	public void tickExecTime() {
		// testWorld.update();
	}

	public void onWindowResize(int width, int height) {
		camera.updateProjection(width, height);
		frameManager.updateProjection(width, height);
		frameManager.updateComponentsHitbox();
		window.setSizeOnly(width, height);
	}

	public void onMouseMove(double xpos, double ypos) {
		this.mouseX = xpos;
		this.mouseY = (window.getHeight() - ypos);
		frameManager.updateComponentLogic();
	}

	public void onMousePress() {
		frameManager.onMousePress();
	}
	
	public void onCharPress(char key) {
		frameManager.onCharPress(key);
	}
	
	public void onKeyPress(int keycode) {
		frameManager.onKeyPress(keycode);
	}

	public void onMouseRelease() {

	}

	public Camera getCamera() {
		return camera;
	}

	public TextureLoader getTextureLoader() {
		return tl;
	}

	public Window getWindow() {
		return window;
	}

	public double getMouseX() {
		return mouseX;
	}

	public double getMouseY() {
		return mouseY;
	}

	public FontManager getFontManager() {
		return fontManager;
	}

	public FrameManager getFrameManager() {
		return frameManager;
	}
	
}