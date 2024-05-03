package fr.thomas.engine.level.world;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import fr.thomas.engine.graphics.TileRenderer;
import fr.thomas.engine.level.tile.GrassTile;
import fr.thomas.engine.level.tile.Tile;

public class World {

	private Tile[] worldTiles;
	private int width;
	private int height;

	private Matrix4f target = new Matrix4f();
	private Vector3f position;
	private TileRenderer tileRenderer;

	public World(int width, int height) {
		this.width = width;
		this.height = height;

		worldTiles = new Tile[width * height];
		position = new Vector3f(0);

		tileRenderer = new TileRenderer(this);

		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++) {
				worldTiles[x + height * y] = new GrassTile("grass", x, y);
			}
	}
	
	public Tile getTile(int x, int y) {
		if(x < 0 || y < 0)
			return null;
		if(y >= height || x >= width)
			return null;
		
		return worldTiles[x + y * height];
	}

	public void render() {
		tileRenderer.render();
	}

	public void tickUpdate() {
		for (Tile tile : worldTiles)
			tile.update();
	}

	public Matrix4f getProjection() {
		target.setTranslation(position);
		return target;
	}

	public Tile[] getStructure() {
		return worldTiles;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
}