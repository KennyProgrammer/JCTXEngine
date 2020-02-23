package com.kenny.craftix.client.collision;

import org.lwjgl.util.vector.Vector3f;

public class ObjCollision 
{
	public static float getDistance(Vector3f pointOne, Vector3f pointTwo) 
	{
	    float distance = 0;

	    float x1 = pointOne.x;
	    float y1 = pointOne.y;
	    float z1 = pointOne.z;

	    float x2 = pointTwo.x;
	    float y2 = pointTwo.y;
	    float z2 = pointTwo.z;

	    distance = (float) Math.pow((Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) + Math.pow(z1 - z2, 2)), .5f);

	    return distance;
	}
	

}
