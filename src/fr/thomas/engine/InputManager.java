package fr.thomas.engine;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

import fr.thomas.engine.entity.Direction;
import fr.thomas.engine.entity.Player;

public class InputManager {

	// Current window
	private long window;
	
	private Player player;

	// Movement keys
	private Map<String, Integer> keyMap = new HashMap<>();

	public InputManager(long window, Player player) {
		this.window = window;
		this.player = player;
		populateKeyMap();
	}

	public void updateInput() {
		for (Map.Entry<String, Integer> entry : keyMap.entrySet()) {
			if (GLFW.glfwGetKey(window, entry.getValue()) == GLFW.GLFW_TRUE) {
				switch (entry.getKey()) {

				case "up":
					player.move(Direction.NORTH);
					break;

				case "down":
					player.move(Direction.SOUTH);
					break;

				case "left":
					player.move(Direction.WEST);
					break;

				case "right":
					player.move(Direction.EAST);
					break;

				default:
					System.err.println("Unknown input !");
				}
			}
		}
	}

	private void populateKeyMap() {
		this.keyMap.put("up", GLFW.GLFW_KEY_W);
		this.keyMap.put("down", GLFW.GLFW_KEY_S);
		this.keyMap.put("left", GLFW.GLFW_KEY_A);
		this.keyMap.put("right", GLFW.GLFW_KEY_D);

		System.out.println(keyMap.size() + " keys registered.");
	}

	public Map<String, Integer> getKeyMap() {
		return keyMap;
	}

}