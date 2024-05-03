package fr.thomas.engine.graphics;

import org.joml.Matrix4f;

import fr.thomas.engine.GameEngine;
import fr.thomas.engine.level.tile.Tile;
import fr.thomas.engine.level.world.World;

public class TileRenderer {

	private World world;
	private int renderDistance = (int) Math.floor(GameEngine.instance.getWindow().getWidth() / Model.OBJECT_SCALE) + 5;

	private Camera camera = GameEngine.instance.getCamera();

	private Matrix4f target = new Matrix4f();
	private Tile lastRenderedTile;

	private Shader tileShader;

	public TileRenderer(World world) {
		this.world = world;
		tileShader = new Shader("world");
	}

	public void render() {
		tileShader.start();
		tileShader.setUniform("textureSampler", 1);

		int posX = (int) ((camera.getPosition().x + (GameEngine.instance.getWindow().getWidth() / 2) / Math.pow(Model.OBJECT_SCALE, 2)) / Model.OBJECT_SCALE);
		int posY = (int) ((camera.getPosition().y - (GameEngine.instance.getWindow().getHeight() / 2) / Math.pow(Model.OBJECT_SCALE, 2)) / Model.OBJECT_SCALE);

		for (int x = -renderDistance / 2 + 1; x < renderDistance / 2; x++) {
			for (int y = -renderDistance / 2 + 1; y < renderDistance / 2; y++) {
				Tile tile = world.getTile(x - posX, y - posY);

				if (tile != null) {
					if (lastRenderedTile == null) {
						lastRenderedTile = tile;
						lastRenderedTile.getTexture().bind(1);
					}

					if (tile.getTexture().getId() != lastRenderedTile.getTexture().getId()) {
						tile.getTexture().bind(1);
					}

					target.setTranslation(tile.getPos().x, tile.getPos().y, 0);
					tileShader.setUniform("projectionMatrix", camera.getProjection().mul(world.getProjection().mul(target)).scale(Model.OBJECT_SCALE));
					tile.render(tileShader); // Render the VBO

					lastRenderedTile = tile;
				}
			}
		}

		tileShader.stop();
	}

	public Shader getTileShader() {
		return tileShader;
	}
}
