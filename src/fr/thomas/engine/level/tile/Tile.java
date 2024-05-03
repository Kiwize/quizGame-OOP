package fr.thomas.engine.level.tile;

import org.joml.Vector2f;
import org.joml.Vector4f;

import fr.thomas.engine.GameEngine;
import fr.thomas.engine.graphics.Model;
import fr.thomas.engine.graphics.Shader;
import fr.thomas.engine.graphics.texture.Texture;

public class Tile {

	private Texture texture;
	private Vector2f pos;

	protected Vector4f colors = new Vector4f(1, 1, 1, 1);
	protected TileType type;

	public Tile(String name, int x, int y, TileType type) {
		texture = GameEngine.instance.getTextureLoader().getTexture(name);
		this.type = type;

		pos = new Vector2f(x * Model.OBJECT_SCALE, y * Model.OBJECT_SCALE);
	}

	public void setPos(Vector2f pos) {
		this.pos = pos;
	}

	public Vector2f getPos() {
		return pos;
	}

	public Texture getTexture() {
		return texture;
	}

	public void render(Shader shader) {
		shader.setUniform("colors", colors);
		Model.DEFAULT_MODEL.render();
	}

	public void update() {

	}
}
