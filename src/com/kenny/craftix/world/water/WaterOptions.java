package com.kenny.craftix.world.water;

public class WaterOptions 
{
	/**Speed of water wave.*/
	private float waveSpeed;
	/**Is coating the surface of the water.*/
	private float tiling;
	/**This is the strength of wave movement.*/
	private float waveStrength;
	/**This effect damper sparkle to water surface.*/
	private float shineDamper;
	/**This parameter adjusts the reflectivity of light from the water.*/
	private float reflectivity;
	
	public WaterOptions(float waveSpeedIn, float tilingIn, float waveStrengthIn, float shineDamperIn, 
			float reflectivityIn) 
	{
		this.waveSpeed = waveSpeedIn;
		this.tiling = tilingIn;
		this.waveStrength = waveStrengthIn;
		this.shineDamper = shineDamperIn;
		this.reflectivity = reflectivityIn;
	}

	public float getWaveSpeed() 
	{
		return waveSpeed;
	}

	/**
	 * Set the speed of the wave water.
	 */
	public void setWaveSpeed(float waveSpeed) 
	{
		this.waveSpeed = waveSpeed;
	}

	public float getTiling() 
	{
		return tiling;
	}

	/**
	 * Set the tiling of the water surface.
	 */
	public void setTiling(float tiling) 
	{
		this.tiling = tiling;
	}

	public float getWaveStrength() 
	{
		return waveStrength;
	}

	/**
	 * Set the wave streingth of the wave water.
	 */
	public void setWaveStrength(float waveStrength) 
	{
		this.waveStrength = waveStrength;
	}

	public float getShineDamper() 
	{
		return shineDamper;
	}
	
	/**
	 * Set the shine damper of the water surface.
	 */
	public void setShineDamper(float shineDamper) 
	{
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() 
	{
		return reflectivity;
	}

	/**
	 * Set the reflectivity of the water surface.
	 */
	public void setReflectivity(float reflectivity) 
	{
		this.reflectivity = reflectivity;
	}
}
