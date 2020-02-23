package com.kenny.craftix.client.animation;

import com.kenny.craftix.client.Craftix;

public class Animation 
{
	public static float animationTime = 0.0f;
	
	public void updateAnimationTime() 
	{
		animationTime += Craftix.getCurrentTime() % 2000;
		System.out.println("Time is: " + animationTime);
	}
}
