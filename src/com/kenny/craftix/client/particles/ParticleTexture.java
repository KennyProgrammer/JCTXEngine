package com.kenny.craftix.client.particles;

public class ParticleTexture 
{
	/**Its id of texture atlas particle*/
	private int textureID;
	/**This is a number of row on the current atlas*/
	private int numberOfRows;
	private boolean additive;
	
	public ParticleTexture(int textureID, int numberOfRows, boolean additive) 
	{
		this.textureID = textureID;
		this.numberOfRows = numberOfRows;
		this.additive = additive;
	}
	
	public boolean usesAdditives()
	{
		return additive;
	}

	public int getTextureID() 
	{
		return textureID;
	}

	public int getNumberOfRows() 
	{
		return numberOfRows;
	}
	
	
}
