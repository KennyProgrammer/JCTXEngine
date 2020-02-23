package com.kenny.craftix.world.terrain;

import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.loader.TexturesLoader;
import com.kenny.craftix.client.renderer.textures.ModelTexture;

public class TerrainTexturePackLoader
{
	protected TexturesLoader textureLoader = new TexturesLoader();
	/**List with all terrain textures.*/
	public List<ModelTexture> textures = new ArrayList<ModelTexture>();
	
	/**
	 * Load all terrain texture pack to Vao, Vbo memory.
	 */
	public void loadTerrainTexturePack()
	{
		this.textures.add(new ModelTexture(this.textureLoader.loadTexture(TerrainTexturePack.TERRAIN_GRASS)));
		this.textures.add(new ModelTexture(this.textureLoader.loadTexture(TerrainTexturePack.TERRAIN_DIRT)).setShineDumper(0.7f));
		this.textures.add(new ModelTexture(this.textureLoader.loadTexture(TerrainTexturePack.TERRAIN_SAND)));
		this.textures.add(new ModelTexture(this.textureLoader.loadTexture(TerrainTexturePack.TERRAIN_FLOWERS)));
		this.textures.add(new ModelTexture(this.textureLoader.loadTexture(TerrainTexturePack.TERRAIN_PATH)));
	}
	
	/**
	 * Remove all terrian texture pack from Vao, Vbo memory.
	 */
	public void removeTerrainTexturePack()
	{
		while(!this.textures.isEmpty())
		{
			this.textureLoader.removeTexture(this.textures.get(0).getTextureId());
			this.textures.remove(0);
		}
	}
}

