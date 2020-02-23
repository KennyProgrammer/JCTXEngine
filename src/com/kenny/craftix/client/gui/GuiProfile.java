package com.kenny.craftix.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiButton;

public class GuiProfile extends GuiBackground 
{
	public List<GuiQuad> textures = new ArrayList<GuiQuad>();
	protected static GuiButton b1, b2, b3;
	
	@Override
	public void initGui(Craftix craftixIn) 
	{
		super.initGui(craftixIn);
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/gui_border", -0.68f, 0.63f, this.width / 4f, this.height / 10));
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/gui_border",  0.67f, 0.63f, this.width / 3.5f, this.height / 10));
		
		b1 = new GuiButton(13, BUTTON_RECT_TEXTURE, 0.68f, -0.72f, 2, "button.back");
	}
	
	@Override
	protected void buttonClick(GuiButton button) throws IOException 
	{
		super.buttonClick(button);
		
		if(button.id == 13)
		{
			GuiProfile.removePage();
			Pages.isProfilePage = false;
			Pages.isMainMenuPage = true;
			GuiMainMenu.addPage();
		}
	}
	
	/**
	 * Add all button to the list.
	 */
	protected static void addPage()
	{
		buttonList.add(b1);
	}
	
	/**
	 * Remove all button from the list.
	 */
	public static void removePage()
	{
		buttonList.remove(b1);
	}
}
