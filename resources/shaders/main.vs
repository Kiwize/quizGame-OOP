#version 330

layout (location = 0) in vec3 in_Position;
layout (location = 1) in vec2 in_TextureCoord;

out vec2 textureCoord;

uniform mat4 projectionMatrix;

void main() {
    textureCoord = in_TextureCoord;
    gl_Position = projectionMatrix * vec4(in_Position, 1.0);
}
