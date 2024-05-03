package fr.thomas.engine.graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

public class Model {

	public static float ZOOM_LEVEL = 1.0f;
	public static float OBJECT_SCALE = 32 * ZOOM_LEVEL;

	public static final float[] WHOLE_TEXTURE = new float[] { 
			0f, 0f,
			1f, 0f,
			1f, 1f,
			0f, 1f };

	private FloatBuffer textureCoordsBuffer;
	private FloatBuffer vertexData;

	public static float[] vertices = new float[] {
			// X, Y, Z
			-0.5f, 0.5f, 0.0f, 0.5f, 0.5f, 0.0f, 0.5f, -0.5f, 0.0f, -0.5f, -0.5f, 0.0f };

	public static float[] tex_coords = new float[] { 0, 0, 1, 0, 1, 1, 0, 1 };

	public static Model DEFAULT_MODEL;
	private int verticesPosVBOID;
	private int texturePosVBOID;

	private int vertexCount;

	public Model(float[] vertices, float[] tex_coords) {
		vertexCount = vertices.length / 3;

		// Gen VBO
		IntBuffer verticesVBO = BufferUtils.createIntBuffer(1);
		GL15.glGenBuffers(verticesVBO);
		verticesPosVBOID = verticesVBO.get(0);

		IntBuffer textureVBO = BufferUtils.createIntBuffer(1);
		GL15.glGenBuffers(textureVBO);
		texturePosVBOID = textureVBO.get(0);

		// Create buffers used with VBOs
		vertexData = BufferUtils.createFloatBuffer(vertices.length);
		vertexData.put(vertices).flip();

		textureCoordsBuffer = BufferUtils.createFloatBuffer(tex_coords.length);
		textureCoordsBuffer.put(tex_coords).flip();

		// Inject data into theses buffers.
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, verticesPosVBOID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexData, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, texturePosVBOID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureCoordsBuffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	public void render() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, verticesPosVBOID);
		GL20.glEnableVertexAttribArray(0);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, texturePosVBOID);
		GL20.glEnableVertexAttribArray(1);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		GL15.glDrawArrays(GL15.GL_TRIANGLE_FAN, 0, vertexCount);

		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}

	public void updateTextureCoordinate(float[] textureCoords) {
		textureCoordsBuffer.put(textureCoords).flip();

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, texturePosVBOID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureCoordsBuffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	public void destroy() {
		GL15.glDeleteBuffers(verticesPosVBOID);
		GL15.glDeleteBuffers(texturePosVBOID);
	}
}
