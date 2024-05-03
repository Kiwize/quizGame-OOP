package fr.thomas.engine.level.world;

import fr.thomas.engine.level.tile.Tile;

public class WorldGenerator {
	
	private Tile[][] generatedTiles;
	
	private int worldSizeX;
	private int worldSizeY;
	
	public WorldGenerator(int wx, int wy) {
		this.worldSizeX = wx;
		this.worldSizeY = wy;
	}
	
	
	public World genWorld() {
		return new World(worldSizeX, worldSizeY);
	}
	
}
