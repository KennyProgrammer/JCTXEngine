#version 400

in vec3 textureCoords;

layout (location = 0) out vec4 out_Color;
layout (location = 1) out vec4 out_BrightColor;

uniform samplerCube sunMoonCube;
uniform vec3 fogColour;

void main(void)
{
	vec4 texture1 = texture(sunMoonCube, textureCoords);
    vec4 finalColour = texture1;
    
    //float factor = (textureCoords.y - lowerLimit) / (upperLimit - lowerLimit);
    float factor = clamp(0.0, 0.0, 1.0);
    out_Color = mix(vec4(fogColour, 1.0), finalColour, factor);
    out_BrightColor = vec4(1.0);
}