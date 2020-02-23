#version 400

in vec3 textureCoords;

layout (location = 0) out vec4 out_Color;
layout (location = 1) out vec4 out_BrightColor;

uniform samplerCube cubeMapPanorama;
uniform vec3 fogColour;
uniform float lowerLimit;
uniform float upperLimit;

void main(void)
{
	vec4 texture1 = texture(cubeMapPanorama, textureCoords);
    
    float factor = (textureCoords.y - lowerLimit) / (upperLimit - lowerLimit);
    factor = clamp(factor, 0.0, 1.0);
    out_Color = mix(vec4(fogColour, 1.0), texture1, factor);
    out_BrightColor = vec4(1.0);
}