package com.kenny.craftix.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiButton;

public class GuiCreditsMenu extends GuiBackground
{
	public List<GuiQuad> textures = new ArrayList<GuiQuad>();
	protected static GuiButton b1, b2, b3;
	public static boolean f;
	
	@Override
	public void initGui(Craftix craftixIn) 
	{
		super.initGui(craftixIn);
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/credits", 0.0f, 0.75f, this.width / 3, this.height / 6));
		
		b1 = new GuiButton(18, BUTTON_RECT_TEXTURE, 0.68f, -0.72f, 2, "button.back");
	}
	
	@Override
	protected void buttonClick(GuiButton button) throws IOException 
	{
		super.buttonClick(button);
		
		if(button.id == 18)
		{
			GuiCreditsMenu.removePage();
			this.setLoaded(false);
			GuiMainMenu.loaded = true;
			f = true;
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
	
	@Override
	public void eventAfterClick() 
	{
		if(!this.isLoaded() && f)
		{
			GuiMainMenu.addPage();
			GuiCreditsMenu.f = false;
		}
	}
	
}
