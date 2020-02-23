package com.kenny.craftix.world.terrain;

import com.kenny.craftix.client.renderer.textures.ModelTexture;

public class TerrainTexturePack 
{	
	private ModelTexture backgroundTexture;
	private ModelTexture rTexture;
	private ModelTexture bTexture;
	private ModelTexture gTexture;

	public static final String TERRAIN_GRASS = "terrain/grass";
	public static final String TERRAIN_DIRT = "terrain/dirt";
	public static final String TERRAIN_SAND = "terrain/sand";
	public static final String TERRAIN_FLOWERS = "terrain/pink_flowers";
	public static final String TERRAIN_PATH = "terrain/path";
	
	public TerrainTexturePack(ModelTexture backgroundTexture, ModelTexture rTexture, ModelTexture bTexture,
			ModelTexture gTexture) 
	{
		this.backgroundTexture = backgroundTexture;
		this.rTexture = rTexture;
		this.bTexture = bTexture;
		this.gTexture = gTexture;
	}

	public ModelTexture getBackgroundTexture() 
	{
		return backgroundTexture;
	}

	public ModelTexture getrTexture() 
	{
		return rTexture;
	}

	public ModelTexture getbTexture() 
	{
		return bTexture;
	}

	public ModelTexture getgTexture() 
	{
		return gTexture;
	}

}
