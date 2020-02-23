package com.kenny.craftix.client.gui.button;

import java.io.IOException;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.GuiBackground;

public class GuiYesNo extends GuiBackground
{
	protected static GuiOptionButton b1, b2;
	
	@Override
	public void initGui(Craftix craftixIn) 
	{
		super.initGui(craftixIn);
		b1 = new GuiOptionButton(98, GUI_LOCATION + "menu/button_no", 0f, 0f, 3, "button_no");
		b1 = new GuiOptionButton(99, GUI_LOCATION + "menu/button_yes", 0.2f, 0f, 3, "button_yes");
		
		addPage();
	}
	
	@Override
	protected void buttonClick(GuiButton button) throws IOException 
	{
		super.buttonClick(button);
		
	}
	
	/**
	 * Add all button to the list.
	 */
	protected static void addPage()
	{
		
	}
	
	/**
	 * Remove all button from the list.
	 */
	protected static void removePage()
	{
		
	}
}
