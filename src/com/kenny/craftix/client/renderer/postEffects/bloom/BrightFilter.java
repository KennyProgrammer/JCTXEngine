package com.kenny.craftix.client.renderer.postEffects.bloom;

import com.kenny.craftix.client.renderer.postEffects.ImageRenderer;
import com.kenny.craftix.client.renderer.textures.TextureManager;

public class BrightFilter 
{
	private ImageRenderer renderer;
	/**Get the bright filter shader for this class*/
	private BrightFilterShader shader;
	
	public BrightFilter(int width, int height)
	{
		this.shader = new BrightFilterShader();
		this.renderer = new ImageRenderer(width, height);
	}
	
	public void render(int texture)
	{
		this.shader.start();
		TextureManager.activeTexture0();
		TextureManager.bindTexture2d(texture);
		this.renderer.renderQuad();
		this.shader.stop();
	}
	
	public int getOutputTexture()
	{
		return renderer.getOutputTexture();
	}
	
	/**
	 * Clean all render stuff when we close the game.
	 */
	public void cleanUp()
	{
		this.renderer.cleanUp();
		this.shader.cleanUp();
	}
	
}
