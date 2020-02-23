package com.kenny.craftix.client.renderer;

public class RenderDistance 
{
	/**
	 * Render distance between camera.
	 */
	private int renderDistance;
	public WorldRenderer renderer;
	public float renderDistanceFloat;
	public float fogDistance;
	public float lowerLimit;
	public float upperLimit;
	
	public RenderDistance(int renderDistanceIn) 
	{
		this.renderDistance = renderDistanceIn;
		this.convertFloatToPartsInt();
	}
	
	public void convertFloatToPartsInt()
	{
		if(this.renderDistance == 1)
		{
			renderDistanceFloat = 50F;
			fogDistance = 0.0535F;
			lowerLimit = -10.0F;
			upperLimit = 20000.0F;
		}
		else if(this.renderDistance == 2)
		{
			renderDistanceFloat = 60f;
			fogDistance = 0.0435F;
			lowerLimit = -10.0F;
			upperLimit = 20000.0F;
		}
		else if(this.renderDistance == 3)
		{
			renderDistanceFloat = 70f;
			fogDistance = 0.0335F;
			lowerLimit = -10.0F;
			upperLimit = 20000.0F;
		}
		else if(this.renderDistance == 4)
		{
			renderDistanceFloat = 450f;
			fogDistance = 0.0135F;
			lowerLimit = -10.0F;
			upperLimit = 1100.0F;
		}
		else if(this.renderDistance == 5)
		{
			renderDistanceFloat = 500f;
			fogDistance = 0.0100F;
			lowerLimit = -10.0F;
			upperLimit = 50.0F;
		}
		else if(this.renderDistance == 6)
		{
			renderDistanceFloat = 550f;
			fogDistance = 0.070F;
			lowerLimit = -10.0F;
			upperLimit = 40.0F;
		}
		else if(this.renderDistance == 7)
		{
			renderDistanceFloat = 750f;
			fogDistance = 0.020F;
			lowerLimit = -10.0F;
			upperLimit = 40.0F;
		}
		else if(this.renderDistance == 8)
		{
			renderDistanceFloat = 950f;
			fogDistance = 0.010F;
			lowerLimit = -10.0F;
			upperLimit = 40.0F;
		}
		else if(this.renderDistance == 9)
		{
			renderDistanceFloat = 1150f;
			fogDistance = 0.005F;
			lowerLimit = -10.0F;
			upperLimit = 30.0F;
		}
		else if(this.renderDistance == 10)
		{
			renderDistanceFloat = 1450f;
			fogDistance = 0.003F;
			lowerLimit = -10.0F;
			upperLimit = 30.0F;
		}
		else if(this.renderDistance == 11)
		{
			renderDistanceFloat = 1750f;
			fogDistance = 0.002F;
			lowerLimit = -10.0F;
			upperLimit = 25.0F;
		}
		else if(this.renderDistance == 12)
		{
			renderDistanceFloat = 2000f;
			fogDistance = 0.00150F;
			lowerLimit = -10.0F;
			upperLimit = 20.0F;
		}
	}
	
	public int getRenderDistance()
	{
		return this.renderDistance;
	}
}
