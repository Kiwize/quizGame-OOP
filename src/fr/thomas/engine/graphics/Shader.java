package fr.thomas.engine.graphics;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

public class Shader {
    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;
    
    private String name;
    
    private FloatBuffer uniformData = BufferUtils.createFloatBuffer(16);

    public Shader(String name) {
    	this.name = name;
        vertexShaderID = loadShader("./resources/shaders/" + name + ".vs", GL_VERTEX_SHADER);
        fragmentShaderID = loadShader("./resources/shaders/" + name + ".fs", GL_FRAGMENT_SHADER);
        programID = glCreateProgram();
        glAttachShader(programID, vertexShaderID);
        glAttachShader(programID, fragmentShaderID);
        
        glBindAttribLocation(programID, 0, "in_Position"); //vertices position
        glBindAttribLocation(programID, 1, "in_TextureCoord"); //texture coords
        
        glLinkProgram(programID);
        glValidateProgram(programID);
    }

    public void start() {
        glUseProgram(programID);
    }
    
    public void setUniform(String name, int value) {
    	int location = GL20.glGetUniformLocation(programID, name);
    	if(location != -1)
    		GL20.glUniform1i(location, value);
    	else
    		System.err.println("OPENGL ERROR > Invalid uniform location !");
    }
    
    public void setUniform(String name, float value) {
    	int location = GL20.glGetUniformLocation(programID, name);
    	if(location != -1)
    		GL20.glUniform1f(location, value);
    	else
    		System.err.println("OPENGL ERROR > Invalid uniform location !");
    }
    
    public void setUniform(String name, Matrix4f value) {
    	int location = GL20.glGetUniformLocation(programID, name);
    	value.get(uniformData);
    	if(location != -1)
    		GL20.glUniformMatrix4fv(location, false, uniformData);
    	else
    		System.err.println("OPENGL ERROR > Invalid uniform location !");
    }
    
    public void setUniform(String name, Vector4f value) {
    	int location = GL20.glGetUniformLocation(programID, name);
    	if(location != -1)
    		GL20.glUniform4f(location, value.x, value.y, value.z, value.w);
    	else
    		System.err.println("OPENGL ERROR > Invalid uniform location !");
    }

    public void stop() {
        glUseProgram(0);
    }

    public void cleanUp() {
        stop();
        glDetachShader(programID, vertexShaderID);
        glDetachShader(programID, fragmentShaderID);
        glDeleteShader(vertexShaderID);
        glDeleteShader(fragmentShaderID);
        glDeleteProgram(programID);
    }

    private int loadShader(String file, int type) {
        StringBuilder shaderSource = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Could not read file.");
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = glCreateShader(type);
        glShaderSource(shaderID, shaderSource);
        glCompileShader(shaderID);
        if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.out.println(glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader.");
            System.exit(-1);
        }
        return shaderID;
    }
    
    public String getName() {
		return name;
	}
}

