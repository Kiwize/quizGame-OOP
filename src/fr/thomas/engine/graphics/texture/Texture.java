package fr.thomas.engine.graphics.texture;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.stb.STBImage;

public class Texture {
	private final int id;

	public Texture(String fileName) {
		ByteBuffer image = null;
		int width = 0, height = 0;
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		IntBuffer comp = BufferUtils.createIntBuffer(1);

		try {
			image = STBImage.stbi_load(fileName, w, h, comp, STBImage.STBI_rgb_alpha);
			if (image == null) {
				throw new Exception("Failed to load image file: " + fileName);
			}

			System.out.println("Entire image");
			width = w.get();
			height = h.get();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		id = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE,
				image);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public int getId() {
		return id;
	}

	public boolean bind(int sampler) {
		if (sampler >= 0 && sampler <= 31) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0 + sampler);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
			return true;
		} else
			return false;
	}

	public void cleanup() {
		GL11.glDeleteTextures(id);
	}
}
