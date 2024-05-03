#version 330

in vec2 textureCoord;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec4 color;
uniform float time;
uniform int enableGlitch;

// Fonction pour générer un nombre pseudo-aléatoire entre 0 et 1
float random(vec2 co) {
    return fract(sin(dot(co.xy + time, vec2(12.9898, 78.233))) * 43758.5453);
}

void main() {
    if(enableGlitch == 1) {
	 	vec4 originalColor = texture(textureSampler, textureCoord);

	    vec4 glitchedColor = originalColor;
	
	    float amplitude = 0.5;
	
	    float randomOffset = random(textureCoord) * amplitude;
	    
	    float randomR = random(textureCoord + time * 0.1);
        float randomG = random(textureCoord + time * 0.2);
        float randomB = random(textureCoord + time * 0.3);
	
	    glitchedColor.r += sin(textureCoord.y * 20.0 + time * 10.0) * amplitude + randomOffset + randomR;
	    glitchedColor.g += cos(textureCoord.x * 15.0 + time * 15.0) * amplitude + randomOffset + randomG;
	    glitchedColor.b += sin(textureCoord.x * textureCoord.y * 30.0 + time * 20.0) * amplitude + randomOffset + randomB;
	
	    vec4 finalColor = mix(originalColor, glitchedColor, 0.5); // 0.5 contrôle le mélange
	
	    out_Color = finalColor * color;
	} else {
		out_Color = texture(textureSampler, textureCoord) * color;
	}
}
