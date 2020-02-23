package com.kenny.craftix.world.terrain;

public class TerrainOptions 
{
	private float amplitude;
	private float octaves;
	private float roughness;
	private float smooth;
	private float smoothNext;
	/**Size terrain on X coordinate.*/
	private int xSize;
	/**Size terrain on Z coordinate.*/
	private int zSize;
	/**Max height of current terrain*/
	private int maxHeight;

	public TerrainOptions(int xSizeIn, int zSizeIn, int maxHeightIn, float amplitudeIn, float octavesIn, float roughnessIn, 
			float smoothIn) 
	{
		this.xSize = xSizeIn;
		this.zSize = zSizeIn;
		this.maxHeight = maxHeightIn;
		this.amplitude = amplitudeIn;
		this.octaves = octavesIn;
		this.roughness = roughnessIn;
		this.smooth = smoothIn;
		this.smoothNext = this.smooth / 2;
	}
	
	public int getXSize() 
	{
		return xSize;
	}

	public int getZSize() 
	{
		return zSize;
	}

	/**
	 * Return the amplidute vertices of the terrain.
	 */
	public float getAmplitude()
	{
		return amplitude;
	}

	/**
	 * Return the octaves of the terrain height.
	 */
	public float getOctaves() 
	{
		return octaves;
	}

	/**
	 * Returns the number of terrain roughness.
	 */
	public float getRoughness() 
	{
		return roughness;
	}

	/**
	 * Return the first smooth effect on terrain.
	 */
	public float getSmooth() 
	{
		return smooth;
	}

	/**
	 * Return the double smooth effect on terrain.
	 */
	public float getSmoothNext() 
	{
		return smoothNext;
	}
	
	/**
	 * Return max height or max Y on current terrain.
	 */
	public float getMaxHeight()
	{
		return this.maxHeight;
	}

	public void setAmplitude(float amplitude) 
	{
		this.amplitude = amplitude;
	}

	public void setOctaves(float octaves)
	{
		this.octaves = octaves;
	}

	public void setRoughness(float roughness) 
	{
		this.roughness = roughness;
	}

	public void setSmooth(float smooth) 
	{
		this.smooth = smooth;
	}

	public void setSmoothNext(float smoothNext)
	{
		this.smoothNext = smoothNext;
	}

	public void setXSize(int xSize) 
	{
		this.xSize = xSize;
	}

	public void setZSize(int zSize) 
	{
		this.zSize = zSize;
	}

	public void setMaxHeight(int maxHeight) 
	{
		this.maxHeight = maxHeight;
	}
}
