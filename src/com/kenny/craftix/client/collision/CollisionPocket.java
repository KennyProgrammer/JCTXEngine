package com.kenny.craftix.client.collision;

import org.lwjgl.util.vector.Vector3f;

public class CollisionPocket 
{
	/**Its a collision detect position*/
	private Vector3f collisionPosition;
	/**This is a distance collision to player*/
	private float collisionDistance;
	/**0 for null, 1 for surface, 2 for vertex, 3 for edge*/
	private int type;
	
	public CollisionPocket(Vector3f collisionPosition, float collisionDistance, int type) 
	{
		this.collisionPosition = collisionPosition;
		this.collisionDistance = collisionDistance;
		this.type = type;
	}

	public Vector3f getCollisionPosition() 
	{
		return collisionPosition;
	}

	public void setCollisionPosition(Vector3f collisionPosition)
	{
		this.collisionPosition = collisionPosition;
	}

	public float getCollisionDistance() 
	{
		return collisionDistance;
	}

	public void setCollisionDistance(float collisionDistance) 
	{
		this.collisionDistance = collisionDistance;
	}

	public int getType() 
	{
		return type;
	}

	public void setType(int type) 
	{
		this.type = type;
	}

}
