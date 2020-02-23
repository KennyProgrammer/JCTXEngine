package com.kenny.craftix.init;

import com.kenny.craftix.client.settings.InGameSettings;

public class PostEffectsInit 
{
	public float CONTRAST;
	public static float BRIGHTNESS;
	public float STATUATION;
	/**First level need be a bigger number than level two.*/
	public static int BLUR_LEVEL_1;
	public static int BLUR_LEVEL_2;
	public static int BRIGHT_SIZE;
	
	public static boolean isContrast = InGameSettings.useContrastIn;
	public static boolean isRenderBlur = false;
	public static boolean isRenderBrightFilter = false;
	public static boolean isRenderBloom = InGameSettings.useBloomIn;
	public static boolean isRenderPanoramaBlur = true;

	public void loadContrast()
	{
		this.setContrast(false, 0.1F, 1.65F);
	}
	
	public static void loadBlur()
	{
		setBlur(isRenderBloom, 8, 2);
	}
	
	public static void loadPanoramaBlur()
	{
		setBlurPanorama(isRenderPanoramaBlur, 8, 2);
	}
	
	public static void loadBrightFilter()
	{
		setBrightFilter(2);
	}
	
	public static void loadBloom()
	{
		setBloom(isRenderBlur, 10, 6);
	}
	
	/**
	 * Set and regulate a contrast effect.
	 * 
	 * @param contrastIn - change a colours.
	 * @param brightnessIn - change light of the colours.
	 * @param statuationIn - change the statuation of the rgb, rbg, or etc colours.
	 */
	public void setContrast(boolean contrast, float contrastIn,
			float statuationIn)
	{
		isContrast = contrast;
		
		if(isContrast)
		{
			CONTRAST = contrastIn;
			STATUATION = statuationIn;
		}
		if(!isContrast)
		{
			CONTRAST = 0.1F;
			STATUATION = 1.0F;
		}
	}
	
	/**
	 * Sets the blurring effect. It is better to use in small quantities.
	 */
	public static void setBlur(boolean renderBlur, int level1, int level2)
	{
		isRenderBlur = renderBlur;
		BLUR_LEVEL_1 = level1;
		BLUR_LEVEL_2 = level2;
		
	}
	
	/**
	 * Sets the blurring effect. It is better to use in small quantities.
	 */
	public static void setBlurPanorama(boolean renderBlurPanorama, int level1, int level2)
	{
		isRenderPanoramaBlur = renderBlurPanorama;
		BLUR_LEVEL_1 = level1;
		BLUR_LEVEL_2 = level2;
		
	}
	
	/**
	 * Set the bright filter effect.
	 * @param size - the resolution of effect on the screen.
	 */
	public static void setBrightFilter(int size)
	{
		BRIGHT_SIZE = size;
	}
	
	public static void setBloom(boolean renderBlur, int level1, int size)
	{
		isRenderBlur = renderBlur;
		BLUR_LEVEL_1 = level1;
		BRIGHT_SIZE = size;
	}
	
	public static void controllBrightness(int brightnessIn)
	{
		if(brightnessIn == 1)
			BRIGHTNESS = 0.4f;
		else if(brightnessIn == 2)
			BRIGHTNESS = 0.6f;
		else if(brightnessIn == 3)
			BRIGHTNESS = 0.8f;
		else if(brightnessIn == 4)
			BRIGHTNESS = 1.0f;
		else if(brightnessIn == 5)
			BRIGHTNESS = 1.2f;
		else if(brightnessIn == 6)
			BRIGHTNESS = 1.4f;
	}

}
