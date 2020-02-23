package com.kenny.craftix.client.renderer.postEffects.blur;

import com.kenny.craftix.client.renderer.postEffects.ImageRenderer;
import com.kenny.craftix.client.renderer.textures.TextureManager;

public class VerticalBlur
{
	private ImageRenderer renderer;
	private VerticalBlurShader shader;
	
	public VerticalBlur(int targetFboWidth, int targetFboHeight)
	{
		this.shader = new VerticalBlurShader();
		this.renderer = new ImageRenderer(targetFboWidth, targetFboHeight);
		this.shader.start();
		this.shader.loadTargetHeight(targetFboHeight);
		this.shader.stop();
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
