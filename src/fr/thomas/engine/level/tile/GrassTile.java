package fr.thomas.engine.level.tile;

import org.joml.Vector4f;

import fr.thomas.engine.graphics.Shader;

public class GrassTile extends Tile{
	
	public GrassTile(String name, int x, int y) {
		super(name, x, y, TileType.GROUND);
		super.colors = new Vector4f(0, 0.4f, 0, 1);
	}
	
	@Override
	public void render(Shader shader) {
		super.render(shader);
	}
}
