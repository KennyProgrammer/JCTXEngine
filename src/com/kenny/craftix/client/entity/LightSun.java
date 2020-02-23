package com.kenny.craftix.client.entity;

import org.lwjgl.util.vector.Vector3f;

public class LightSun extends Light
{
	/**Position of the sun light.*/
	private static final Vector3f SUN_POSITION = new Vector3f(10000f, 15000f, -10000f);
	/**Colour of all sun light points.*/
	private static final Vector3f SUN_COLOR = new Vector3f(1.3f, 1.3f, 1.3f);
	/**Light sun attenuation.*/
	private static final Vector3f SUN_ATTENU = new Vector3f(1f, 0f, 0f);
	protected float prevSunPosX, prevSunPosY, prevSunPosZ;
	/**
	 * Simple sun light source initialization.
	 */
	public LightSun() 
	{
		super(SUN_POSITION, SUN_COLOR, SUN_ATTENU);
		this.prevSunPosX = SUN_POSITION.x;
		this.prevSunPosY = SUN_POSITION.y;
		this.prevSunPosZ = SUN_POSITION.z;
	}
	
	/**
	 * Enable sun light source.
	 */
	public LightSun enableSun()
	{
		SUN_POSITION.x = this.prevSunPosX;
		SUN_POSITION.y = this.prevSunPosY;
		SUN_POSITION.z = this.prevSunPosZ;
			return this;
	}
	
	/**
	 * Disable sun light source.
	 */
	public LightSun disableSun()
	{
		SUN_POSITION.x = 0.1F;
		SUN_POSITION.y = 0.1F;
		SUN_POSITION.z = 0.1F;
			return this;
	}
}
