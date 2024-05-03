package fr.thomas.engine.graphics;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.opengl.GL;

import fr.thomas.engine.GameEngine;

public class Window {

	private long window;
	private int width;
	private int height;
	private String title;

	private GLFWVidMode vidMode;

	private boolean isMousePressed;

	public Window(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;

		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
		GLFW.glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallbackI() {

			@Override
			public void invoke(long window, int width, int height) {
				GameEngine.instance.onWindowResize(width, height);
			}
		});

		GLFW.glfwSetCursorPosCallback(window, new GLFWCursorPosCallbackI() {

			@Override
			public void invoke(long window, double xpos, double ypos) {
				GameEngine.instance.onMouseMove(xpos, ypos);
			}
		});

		GLFW.glfwSetMouseButtonCallback(window, new GLFWMouseButtonCallback() {

			@Override
			public void invoke(long window, int button, int action, int mods) {
				if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
					if (action == 1)
						GameEngine.instance.onMousePress();
					else if (action == 0) {
						GameEngine.instance.onMouseRelease();
					}
				}
			}
		});

		GLFW.glfwSetCharCallback(window, new GLFWCharCallback() {

			@Override
			public void invoke(long window, int key) {
				GameEngine.instance.onCharPress((char) key);

			}
		});

		GLFW.glfwSetKeyCallback(window, new GLFWKeyCallback() {

			@Override
			public void invoke(long window, int keycode, int scancode, int action, int mods) {
				if (action == 1)
					GameEngine.instance.onKeyPress(keycode);
			}
		});

		if (window == 0)
			throw new IllegalStateException("Failed to create window !");

		vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

		glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
		glfwShowWindow(window);

		glfwMakeContextCurrent(window);
		GL.createCapabilities();
	}

	public long getWindow() {
		return window;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		GLFW.glfwSetWindowTitle(window, title);
	}

	public void setSizeOnly(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		GLFW.glfwSetWindowSize(window, width, height);
		glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
	}
}
