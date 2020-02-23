#version 140

in vec2 textureCoords;

out vec4 out_Colour;

uniform sampler2D colourTexture;

uniform float contrast;
uniform float brightness;
uniform float saturation;

void main(void)
{
	//Out put the finally creating contrast effect.
	out_Colour = texture(colourTexture, textureCoords);

	//Calculate saturation effect
	vec3 luminanceWeights = vec3(0.299, 0.587, 0.114);
	float luminance = dot(out_Colour.rgb, luminanceWeights);
	out_Colour = mix(vec4(luminance), out_Colour, saturation);

	//Calculate contrast effect
	out_Colour.rgb = (out_Colour.rgb - 0.5) * (1.0 + contrast) + 0.5;
	
	//Calculate brightness effect
	out_Colour.rgb *= brightness;

}
