package com.kenny.craftix.client.renderer.postEffects.colour;

import com.kenny.craftix.client.renderer.postEffects.ImageRenderer;
import com.kenny.craftix.client.renderer.textures.TextureManager;
import com.kenny.craftix.init.PostEffectsInit;

public class ColourChanger 
{
	/**Its renderer for a post proccessing effects.*/
	private ImageRenderer renderer;
	private ColourShader shader;
	private PostEffectsInit effects = new PostEffectsInit();

	
	public ColourChanger()
	{
		this.shader = new ColourShader();
		this.renderer = new ImageRenderer();
	}
	
	public void render(int texture)
	{
		this.shader.start();
		TextureManager.activeTexture0();
		TextureManager.bindTexture2d(texture);
		this.effects.loadContrast();
		this.shader.loadBrightness(this.effects.BRIGHTNESS);
		this.shader.loadSaturation(this.effects.STATUATION);
		this.shader.loadContrast(this.effects.CONTRAST);
		this.renderer.renderQuad();
		this.shader.stop();
	}
	
	/**
	 * Set contrast effect value, with other colours change effect's.
	 */
	public ColourChanger setContrast(boolean u, float contrastIn,  float statuationIn)
	{
		this.effects.setContrast(u, contrastIn, statuationIn);
			return this;
	}
	
	/**
	 * Clean all stuff on close the game.
	 */
	public void cleanUp()
	{
		this.renderer.cleanUp();
		this.shader.cleanUp();
	}
}
