package com.kenny.craftix.client.renderer.postEffects.blur;

import com.kenny.craftix.client.renderer.postEffects.ImageRenderer;
import com.kenny.craftix.client.renderer.textures.TextureManager;

public class HorizontalBlur 
{
	private ImageRenderer renderer;
	private HorizontalBlurShader shader;
	
	public HorizontalBlur(int targetFboWidth, int targetFboHeight)
	{
		this.shader = new HorizontalBlurShader();
		this.shader.start();
		this.shader.loadTargetWidth(targetFboWidth);
		this.shader.stop();
		this.renderer = new ImageRenderer(targetFboWidth, targetFboHeight);
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
	 * Clean all when we close the game.
	 */
	public void cleanUp()
	{
		this.renderer.cleanUp();
		this.shader.cleanUp();
	}

}
