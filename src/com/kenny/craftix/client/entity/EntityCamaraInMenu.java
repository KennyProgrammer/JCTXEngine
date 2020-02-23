package com.kenny.craftix.client.entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class EntityCamaraInMenu
{
	private Vector3f position = new Vector3f(0,0,0);
	/**Its a pitch of the camera (Наклон)*/
	private float pitch = 5;
	private float yaw = 20;
	private float roll;
	
	public EntityCamaraInMenu()
	{
		
	}
	
	/**
	 * Это просто стандартная система передвежения камеры.
	 */
	public void moveCamera(float forward, float backward, float right, 
			float left, float fly, float down)
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			this.position.z -= forward;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			this.position.x += backward;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			this.position.x -= left;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			this.position.z += right;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
		{
			this.position.y += fly;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			this.position.y -= down;
		}
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public float getPitch() 
	{
		return pitch;
	}

	public float getYaw()
	{
		return yaw;
	}

	public float getRoll() 
	{
		return roll;
	}
	
	
}
