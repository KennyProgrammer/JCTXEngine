#version 140

in vec2 position;

out vec2 textureCoords;

void main(void)
{
	//Init the open gl position and this position setup on textureCoords.
	gl_Position = vec4(position, 0.0, 1.0);
	textureCoords = position * 0.5 + 0.5;
}
