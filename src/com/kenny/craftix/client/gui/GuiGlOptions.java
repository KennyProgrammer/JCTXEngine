package com.kenny.craftix.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiButton;

public class GuiGlOptions extends GuiBackground
{
	public List<GuiQuad> textures = new ArrayList<GuiQuad>();
	protected static GuiButton b1, b2, b3;
	public static GuiQuad q1;
	
	@Override
	public void initGui(Craftix craftixIn) 
	{
		super.initGui(craftixIn);
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/gui_border", -0.65f, 0.63f, this.width / 3.5f, this.height / 10));
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/background", -0.2f, -0.25f, this.width / 1.4f, this.height / 1.9f));
		
		b1 = new GuiButton(36, BUTTON_RECT_TEXTURE, 0.68f, -0.72f, 2, "button.back");
		b2 = new GuiButton(37, GUI_LOCATION + "menu/button_back", 0.61f, -0.5f, 1, "button.previuos");
		b3 = new GuiButton(38, GUI_LOCATION + "menu/button_next", 0.75f, -0.5f, 1, "button.next");
		
	}
	
	@Override
	protected void buttonClick(GuiButton button) throws IOException
	{
		super.buttonClick(button);
		if(button.id == 36)
		{
			GuiGlOptions.removePage();
			Pages.isGlOptionsPage = false;
			Pages.isOptionsPage = true;
			GuiOptions.addPage();
		}
		
		if(button.id == 37)
		{
			
		}
		
		if(button.id == 38)
		{
			
		}
	}
	
	/**
	 * Add all button to the list.
	 */
	public static void addPage()
	{
		buttonList.add(b1);
		buttonList.add(b2);
		buttonList.add(b3);
	}
	
	/**
	 * Remove all button from the list.
	 */
	public static void removePage()
	{
		buttonList.remove(b1);
		buttonList.remove(b2);
		buttonList.remove(b3);
	}
}
