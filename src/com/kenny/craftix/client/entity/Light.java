package com.kenny.craftix.client.entity;

import org.lwjgl.util.vector.Vector3f;

public class Light 
{
	/**Its a position of lighting source*/
	private Vector3f position;
	/**Its a color of light*/
	private Vector3f colour;
	private Vector3f attenuation = new Vector3f(1, 0, 0);
	
	public Light(Vector3f position, Vector3f colour) 
	{
		this.position = position;
		this.colour = colour;
	}
	
	public Light(Vector3f position, Vector3f colour, Vector3f attenuation) 
	{
		this.position = position;
		this.colour = colour;
		this.attenuation = attenuation;
	}

	
	public Vector3f getPosition() 
	{
		return position;
	}

	public void setPosition(Vector3f position) 
	{
		this.position = position;
	}

	public Vector3f getColour() 
	{
		return colour;
	}

	public void setColour(Vector3f colour) 
	{
		this.colour = colour;
	}

	public Vector3f getAttenuation() 
	{
		return attenuation;
	}
	
	public void setAttenuation(float x, float y, float z)
	{
		this.attenuation = new Vector3f(x, y, z);
	}

}
