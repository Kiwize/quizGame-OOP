#version 330

layout (location = 0) in vec3 in_Position;
layout (location = 1) in vec2 in_TextureCoord;

out vec2 textureCoord;

uniform mat4 projectionMatrix;
uniform vec4 offset;

void main() {
    textureCoord = in_TextureCoord;
    gl_Position = projectionMatrix * vec4(in_Position + vec3(offset.x, offset.y, offset.z), 1.0);
}
