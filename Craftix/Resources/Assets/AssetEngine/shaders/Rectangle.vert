#version 400 core

layout (location = 0) in vec3 attrib_Position;

out vec3 position;

void main(void)
{
	position = attrib_Position;
	gl_Position = vec4(attrib_Position, 1.0f);
}
