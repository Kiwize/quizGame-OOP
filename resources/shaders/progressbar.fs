#version 330

in vec2 textureCoord;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec4 color;

void main() {
    out_Color = texture(textureSampler, textureCoord) * color;
}
