package com.kenny.craftix.client.renderer.postEffects;

import org.lwjgl.opengl.Display;

import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.client.renderer.postEffects.blur.HorizontalBlur;
import com.kenny.craftix.client.renderer.postEffects.blur.VerticalBlur;
import com.kenny.craftix.init.PostEffectsInit;

public class PanoramaBlur 
{
	private static final float[] POSITIONS = { -1, 1, -1, -1, 1, 1, 1, -1 };
	private static Model quad;
	private static HorizontalBlur hBlur;
	private static VerticalBlur vBlur;
	private static HorizontalBlur hBlur2;
	private static VerticalBlur vBlur2;
	
	/**
	 * Here be init a panorama blur effect in main menu.
	 */
	public static void init(Loader loader)
	{
		quad = loader.loadToVao(POSITIONS, 2);
		PostEffectsInit.loadPanoramaBlur();
		hBlur = new HorizontalBlur(Display.getWidth() / 8, Display.getHeight() / 8);
		vBlur = new VerticalBlur(Display.getWidth() / 8, Display.getHeight() / 8);
		hBlur2 = new HorizontalBlur(Display.getWidth() / 2, Display.getHeight() / 2);
		vBlur2 = new VerticalBlur(Display.getWidth() / 2, Display.getHeight() / 2);
	}
	
	public static void doPanoramaBlur(int colourTextures)
	{
		start();
		if(PostEffectsInit.isRenderPanoramaBlur)
		{
			hBlur2.render(colourTextures);
			vBlur2.render(hBlur2.getOutputTexture());
			hBlur.render(vBlur2.getOutputTexture());
			vBlur.render(hBlur.getOutputTexture());
		}
		endRendering();
	}
	
	private static void start()
	{
		GlHelper.glBindVertexArray(quad.getVaoID());
		GlHelper.glEnableVertexAttribArray(0);
		GlHelper.disableDepthTest();
	}
	
	private static void endRendering()
	{
		GlHelper.enableDepthTest();
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glBindVertexArray(0);
	}
	
	/**
	 * Clean all stuff when we close the game.
	 */
	public static void cleanUp()
	{
		hBlur.cleanUp();
		vBlur.cleanUp();
		hBlur2.cleanUp();
		vBlur2.cleanUp();
	}
}
