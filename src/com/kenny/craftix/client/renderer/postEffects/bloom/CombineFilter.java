package com.kenny.craftix.client.renderer.postEffects.bloom;

import com.kenny.craftix.client.renderer.postEffects.ImageRenderer;
import com.kenny.craftix.client.renderer.textures.TextureManager;

public class CombineFilter 
{
	
	private ImageRenderer renderer;
	private CombineShader shader;
	
	public CombineFilter()
	{
		this.shader = new CombineShader();
		this.shader.start();
		this.shader.connectTextureUnits();
		this.shader.stop();
		this.renderer = new ImageRenderer();
	}
	
	public void render(int colourTexture, int highlightTexture)
	{
		this.shader.start();
		TextureManager.activeTexture0();
		TextureManager.bindTexture2d(colourTexture);
		TextureManager.activeTexture1();
		TextureManager.bindTexture2d(highlightTexture);
		this.renderer.renderQuad();
		this.shader.stop();
	}
	
	/**
	 * Clean up all when we close the game.
	 */
	public void cleanUp()
	{
		this.renderer.cleanUp();
		this.shader.cleanUp();
	}

}
