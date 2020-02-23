#version 140

out vec4 out_colour;

in vec3 textureCoords1;
in vec3 textureCoords2;
in float blend;
in float visibility;

uniform sampler2D particleTexture;
uniform vec3 skyColour;

void main(void)
{
	vec4 colour1 = texture(particleTexture, textureCoords1);
	vec4 colour2 = texture(particleTexture, textureCoords2);

	out_colour = mix(colour1, colour2, blend);
	out_colour = mix(vec4(skyColour, 1.0), out_colour, visibility);
}
