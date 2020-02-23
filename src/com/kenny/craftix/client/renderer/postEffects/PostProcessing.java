package com.kenny.craftix.client.renderer.postEffects;

import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.client.renderer.models.ModelGui;
import com.kenny.craftix.client.renderer.postEffects.blur.HorizontalBlur;
import com.kenny.craftix.client.renderer.postEffects.blur.VerticalBlur;
import com.kenny.craftix.client.renderer.postEffects.colour.ColourChanger;
import com.kenny.craftix.client.settings.InGameSettings;

public class PostProcessing 
{
	private static Model quad;
	protected static ModelGui modelGui;
	protected static Loader loader = new Loader();
	/**Colour change effect init.*/
	private static ColourChanger contrastChanger;
	/**Gaussian Blur effect init.*/
	private static HorizontalBlur hBlur;
	private static VerticalBlur vBlur;
	private static HorizontalBlur hBlur2;
	private static VerticalBlur vBlur2;
	protected static final int BLUR_CONTROLLER = 8;

	/**
	 * Init the post-proccessing effect setup.
	 */
	public static void init(int displayWidthIn, int displayHeightIn)
	{
		modelGui = new ModelGui();
		quad = loader.loadToVao(modelGui.getModelQuad(), 2);
		contrastChanger = new ColourChanger();
		hBlur = new HorizontalBlur(displayWidthIn / BLUR_CONTROLLER, displayHeightIn / BLUR_CONTROLLER);
		vBlur = new VerticalBlur(displayWidthIn / BLUR_CONTROLLER, displayHeightIn / BLUR_CONTROLLER);
		hBlur2 = new HorizontalBlur(displayWidthIn / BLUR_CONTROLLER / 2, displayHeightIn / BLUR_CONTROLLER / 2);
		vBlur2 = new VerticalBlur(displayWidthIn / BLUR_CONTROLLER / 2, displayHeightIn / BLUR_CONTROLLER / 2);
	}
	
	/**
	 * Performs all post-effect display processes.
	 */
	public static void doPostProcessing(int colourTexture)
	{
		startRendering();
		
		if(InGameSettings.useContrastIn)
			renderContrast(colourTexture);
		else if(InGameSettings.useGaussianBlurIn)
			renderGaussianBlur(colourTexture);
		else
			renderContrast(colourTexture);
		
		endRendering();
	}
	
	/**
	 * Renders the contrast of the rendered image using the 
	 * framebuffer object (Fbo). This method squared only the contrast.
	 * 
	 * @param colourTexture - output contrast colour.
	 */
	private static void renderContrast(int colourTexture)
	{
		contrastChanger.render(colourTexture);
	}
	
	/**
	 * Renders the horizontal and vertical blur at 2 stage and create the Gaussian Blur effect,
	 * using the framebuffer object (Fbo).
	 * 
	 * @param colourTexture - output gaussian blur effect colour.
	 */
	private static void renderGaussianBlur(int colourTexture)
	{
		hBlur2.render(colourTexture);
		vBlur2.render(hBlur2.getOutputTexture());
		hBlur.render(vBlur2.getOutputTexture());
		vBlur.render(hBlur.getOutputTexture());
		contrastChanger.render(vBlur.getOutputTexture());
	}

	/**
	 * Displays the usual buffer of frames without special effects.
	 */
	protected static void outputNullEffect(int colourTexture)
	{
		contrastChanger.setContrast(false, 0f, 0f);
		contrastChanger.render(colourTexture);
	}
	
	private static void startRendering()
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
	 * Clean all post-processing effect's when we close the game.
	 */
	public static void cleanUp()
	{
		contrastChanger.cleanUp();
		hBlur.cleanUp();
		vBlur.cleanUp();
		hBlur2.cleanUp();
		vBlur2.cleanUp();
	}
	
	/**
	 * Return the contrast changer effect.
	 */
	public static ColourChanger getContrast()
	{
		return contrastChanger;
	}


}
