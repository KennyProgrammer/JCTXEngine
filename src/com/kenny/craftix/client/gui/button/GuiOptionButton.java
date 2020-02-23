package com.kenny.craftix.client.gui.button;

import com.kenny.craftix.client.settings.InGameSettings;

public class GuiOptionButton extends GuiButton
{
	private InGameSettings.Options enumOptions;
	
	public GuiOptionButton(int buttonIdIn, String fileIn, float x, float y, int typeIn, String buttonName) 
	{
		this(buttonIdIn, fileIn, x, y, typeIn, (InGameSettings.Options)null, buttonName);
	}
	
	public GuiOptionButton(int buttonIdIn, String fileIn, float x, float y, int typeIn, InGameSettings.Options options, String buttonName) 
	{
		super(buttonIdIn, fileIn, x, y, typeIn, buttonName);
		this.enumOptions = options;
	}
	
	
	public InGameSettings.Options returnEnumOption()
	{
		return this.enumOptions;
	}

}
