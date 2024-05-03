#version 330

layout (location = 0) in vec3 in_Position;
layout (location = 1) in vec2 in_TextureCoord;

out vec2 textureCoord;

uniform mat4 projectionMatrix;
uniform float time; // Temps écoulé depuis le début du rendu
uniform int enableGlitch; // Indicateur pour activer ou désactiver les glitchs

float random(vec2 co) {
    return fract(sin(dot(co.xy + time, vec2(12.9898, 78.233))) * 43758.5453);
}

void main() {
    if (enableGlitch == 1) { // Vérifiez si les glitchs sont activés
        float glitchX = random(in_TextureCoord + time * 0.1) * 0.05; // Perturbation aléatoire horizontale
        float glitchY = random(in_TextureCoord + time * 0.1) * 0.05; // Perturbation aléatoire verticale

        vec3 glitchedPos = in_Position + vec3(glitchX, glitchY, 0.0);

        textureCoord = in_TextureCoord;

        gl_Position = projectionMatrix * vec4(glitchedPos, 1.0);
    } else {
        textureCoord = in_TextureCoord;
        gl_Position = projectionMatrix * vec4(in_Position, 1.0);
    }
}
