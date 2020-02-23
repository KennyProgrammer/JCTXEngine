#version 140

in vec3 position; //vec2

in mat4 modelViewMatrix;
in vec4 texOffests;
in float blendFactor;

out float visibility;
out vec3 textureCoords1; //vec2
out vec3 textureCoords2; //vec2
out float blend;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform float numberOfRows;
uniform float density;
uniform float gradient;

void main(void)
{
	vec4 worldPosition = transformationMatrix * vec4(position, 1.0);

	vec3 textureCoords = position + vec3(0.5, 0.5, 0.0); //vec2
	textureCoords.y = 1.0 - textureCoords.y;
	textureCoords /= numberOfRows;
	textureCoords1 = textureCoords + texOffests.xy;
	textureCoords2 = textureCoords + texOffests.zw;
	blend = blendFactor;
	
	vec4 positionRelativeToCam = modelViewMatrix * worldPosition;
	gl_Position = projectionMatrix * positionRelativeToCam;

	float distance = length(positionRelativeToCam.xyz);
	visibility = exp(-pow((distance * density), gradient));
	visibility = clamp(visibility, 0.0, 1.0);
}
