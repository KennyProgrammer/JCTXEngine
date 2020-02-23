package com.kenny.craftix.client.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kenny.craftix.client.settings.InGameSettings;

public class GuiYesNo 
{
	public static final Logger LOGGER = LogManager.getLogger("GuiYesNo");
	/**Something happpen if button eqauls "Yes"*/
	public static boolean isYesOptionFS;
	public static boolean isYesOptionFBO;
	public static boolean isYesOptionRS;
	public static boolean isYesOptionRW;
	public static boolean isYesOptionAudio;
	
	/**
	 * Check is button "Yes" or "No".
	 */
	public static void checkerYesNo()
	{
		checkIfYes();
	}
	
	public static void checkYesFullscreen()
	{
		if(InGameSettings.useFullscreenIn)
		{
			isYesOptionFS = true;
		}else{
			isYesOptionFS = false;
		}
	}
	
	public static void checkYesFramebuffer()
	{
		if(InGameSettings.useFboIn)
		{
			isYesOptionFBO = true;
		} else {
			isYesOptionFBO = false;
		}
	}
	
	public static void checkYesRenderSkybox()
	{
		if(InGameSettings.renderSkyBoxIn)
		{
			isYesOptionRS = true;
		} else {
			isYesOptionRS = false;
		}
	}
	
	public static void checkYesRenderWater()
	{
		if(InGameSettings.renderWaterIn)
		{
			isYesOptionRW = true;
		} else {
			isYesOptionRW = false;
		}
	}
	
	public static void checkYesAudio()
	{
		if(InGameSettings.useAudioIn)
		{
			isYesOptionAudio = true;
		} else {
			isYesOptionAudio = false;
		}
	}
	
	public static void checkIfYes()
	{
		if(isYesOptionFS)
		{	
			LOGGER.info("Button Yes is: " + isYesOptionFS + " (Yes)");
		}
		else
		{
			LOGGER.info("Button Yes is: " + isYesOptionFS + " (No)");
		}
	}

}
