package com.kenny.craftix.client.gui;

import com.kenny.craftix.client.settings.InGameSettings;

public class GuiScale 
{
	/**Current scale of the all component in the game engine. (Like a textures, buttons, texts).*/
	private int currentScale;
	/**Scale on x (Width of the screen.)*/
	private float scaleX;
	/**Scale on y (Height of the screen.)*/
	private float scaleY;
	/**Scale on position X*/
	private float scalePosX;
	/**Scale on position Y*/
	private float scalePosY;
	private float textScale;
	private float textScalePos;
	
	public GuiScale() 
	{
		this.currentScale = InGameSettings.guiScaleIn;
		this.loadScale();
	}
	
	public void loadScale()
	{
		if(this.currentScale == 0)
		{
			this.scaleX = 0f;
			this.scaleY = 0f;
			this.textScalePos = 0f;
			this.textScale = 0f;
		}
		else if(this.currentScale == 1)
		{
			this.scaleX = 0.01f;
			this.scaleY = 0.02f;
			this.textScalePos = 0.003f;
			this.textScale = 0.2f;
		}
		else if(this.currentScale == 2)
		{
			this.scaleX = 0.04f;
			this.scaleY = 0.035f;
			this.textScalePos = 0.006f;
			this.textScale = 0.4f;
		}
	}
	
	/**
	 * Return the current scale value.
	 */
	public int getScaleValue()
	{
		return this.currentScale;
	}
	
	public float getScaleX()
	{
		return this.scaleX;
	}
	
	public float getScaleY()
	{
		return this.scaleY;
	}
	
	public float getScalePosX()
	{
		return this.scalePosX;
	}
	
	public float getScalePosY()
	{
		return this.scalePosY;
	}
	
	public float getTextScale() 
	{
		return textScale;
	}

	public float getTextScalePos()
	{
		return this.textScalePos;
	}

}
