package com.kenny.craftix.world.water;

import com.kenny.craftix.client.entity.EntityLivingBase;

public class Water 
{
	/**This is a tile size of our quad model water.*/
	public float tileSize = 100 + 500;
	/**This is height of water (Simple waves)*/
	public float height;
	/**Its a position X and Z*/;
	public float x,z;
	/**Load all options of water quad.*/
	private WaterOptions waterOptions;
	
	public Water(float centerX, float centerZ, float height)
	{
	
		this.waterOptions = new WaterOptions(0.04F, 41.0F, 0.05F, 20.0F, 0.5F);	
		
		this.x = centerX;
		this.z = centerZ;
		this.height = height;
		EntityLivingBase.waterHeight = this.getHeight();
	}

	public float getHeight() 
	{
		return height;
	}

	public float getX() 
	{
		return x;
	}

	public float getZ() 
	{
		return z;
	}
	
	public WaterOptions getWaterOptions()
	{
		return this.waterOptions;
	}
}
