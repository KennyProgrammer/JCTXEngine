package com.kenny.craftix.client.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.util.vector.Vector2f;

import com.kenny.craftix.client.CraftixOld;
import com.kenny.craftix.client.gui.button.GuiAbstractButton;
import com.kenny.craftix.client.settings.InGameSettings;

public class GuiScaled implements IGuiScaleManager
{
	private static final Logger LOGGER = LogManager.getLogger(CraftixOld.TITLE);
	public static boolean isButtonYesNo = false;
	public float currentTextSize;
	public Vector2f currentButtonSize;
	public Vector2f curretnButtonMediumSize;
	public float buttonX;
	public float buttonY;
	
	public void loadGuiScale() 
	{
		if(InGameSettings.guiScaleLargeIn)
		{
			this.initGuiScaleLarge();
		}
		if(InGameSettings.guiScaleMediumIn)
		{
			this.initGuiScaleMedium();
		}
		if(InGameSettings.guiScaleSmallIn)
		{
			this.initGuiScaleSmall();
		}
		
		this.scaleNotExist();
	}

	@Override
	public void initGuiScaleAuto() 
	{
		this.currentTextSize = 1.6f;
		GuiAbstractButton.x1 = this.buttonX = 0.26f;
		GuiAbstractButton.y1 = this.buttonY = 0.08f;
	}

	@Override
	public void initGuiScaleLarge() 
	{
		this.currentTextSize = 1.6f;
		GuiAbstractButton.x1 = this.buttonX = 0.26f;
		GuiAbstractButton.y1 = this.buttonY = 0.08f;
		GuiAbstractButton.xYesNo = this.buttonX = 0f;
		GuiAbstractButton.yYesNo = this.buttonY = 0f;
	}

	@Override
	public void initGuiScaleMedium() 
	{
		this.currentTextSize = 1.5f;
		GuiAbstractButton.x1 = this.buttonX = 0.23f;
		GuiAbstractButton.y1 = this.buttonY = 0.06f;
	}

	@Override
	public void initGuiScaleSmall() 
	{
		this.currentTextSize = 1.3f;
		GuiAbstractButton.x1 = this.buttonX = 0.20f;
		GuiAbstractButton.y1 = this.buttonY = 0.05f;
	}
	
	/**
	 * If gui scale size not exits then engine load auto scale size.
	 */
	public void scaleNotExist()
	{
		if(!InGameSettings.guiScaleLargeIn && !InGameSettings.guiScaleMediumIn &&
				!InGameSettings.guiScaleSmallIn)
		{
			LOGGER.info("Scale format not exits! Auto scale has been initialize.");
			this.initGuiScaleAuto();
		}
	}

	public float getCurrentTextSize() 
	{
		return currentTextSize;
	}
	
	public float getButtonX()
	{
		return buttonX;
	}
	
	public float getButtonY()
	{
		return buttonY;
	}

	public void setCurrentTextSize(float currentTextSize) 
	{
		this.currentTextSize = currentTextSize;
	}
	
}
