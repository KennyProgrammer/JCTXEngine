package com.kenny.craftix.world.terrain;

import com.kenny.craftix.client.renderer.textures.ModelTexture;

public class ModelTextureTerrain extends ModelTexture
{
	/**This id for each texture.*/
	private int textureId;
	
	public ModelTextureTerrain(int textureId) 
	{
		super(textureId);
		this.textureId = textureId;
	}

	/**
	 * Return the terrain texture id.
	 */
	public int getTextureTerrainId() 
	{
		return textureId;
	}
	
	/**
	 * All additional effects in this method for this texture are collected.
	 */
	public ModelTextureTerrain setEffects(boolean hasTransparancyIn, boolean useFakeLightIn, int numberOfRowsIn,
			int reflectivityIn, int shineDumperIn)
	{
		this.setHasTransparency(hasTransparancyIn);
		this.setNumberOfRows(numberOfRowsIn);
		this.setReflectivity(reflectivityIn);
		this.setShineDumper(shineDumperIn);
		this.setUseFakeLighting(useFakeLightIn);
		return this;
	}

}
