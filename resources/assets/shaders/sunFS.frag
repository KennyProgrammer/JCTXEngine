#version 330

in vec2 textureCoords;

out vec4 out_colour;

uniform sampler2D sunTexture;

void main(void){

    out_colour = texture(sunTexture, textureCoords);
}
