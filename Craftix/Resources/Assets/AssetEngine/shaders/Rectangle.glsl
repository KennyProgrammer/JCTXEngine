#type vertex
#version 400 core

layout (location = 0) in vec3 attrib_Position;

out vec3 position;

void main(void)
{
	position = attrib_Position;
	gl_Position = vec4(attrib_Position, 1.0f);
}

#endtype
#type fragment
#version 400 core

layout (location = 0) out vec4 out_colour;

in vec3 position;

//Output colour.
uniform vec4 u_Colour;

void main(void)
{
	out_colour = vec4(1.0f, 1.0f, 0.0f, 1.0f);
}
#endtype