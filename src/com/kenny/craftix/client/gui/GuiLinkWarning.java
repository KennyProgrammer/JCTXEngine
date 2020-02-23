package com.kenny.craftix.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiButton;

public class GuiLinkWarning extends GuiBackground
{
	public List<GuiQuad> textures = new ArrayList<GuiQuad>();
	protected static GuiButton b1, b2, b3, b4;
	
	@Override
	public void initGui(Craftix craftixIn) 
	{
		super.initGui(craftixIn);
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/gui_border", 0.75f, 0.33f, this.width / 3.7f, this.height / 10f));
		
		b1 = new GuiButton(14, BUTTON_RECT_TEXTURE, 0.5f, 0f, 2, "button.back");
		b2 = new GuiButton(15, BUTTON_RECT_TEXTURE, 0.83f, 0f, 2, "button.continue");
	}

	@Override
	protected void buttonClick(GuiButton button) throws IOException 
	{
		super.buttonClick(button);
		
		if(button.id == 14)
		{
			GuiLinkWarning.removePage();
			Pages.isLinkWarning = false;
		}
		
		if(button.id == 15)
		{
			this.isClickedOnURI(true, 21022003);
		}
	}

	/**
	 * Add all button to the list.
	 */
	protected static void addPage()
	{
		buttonList.add(b1);
		buttonList.add(b2);
	}
	
	/**
	 * Remove all button from the list.
	 */
	public static void removePage()
	{
		buttonList.remove(b1);
		buttonList.remove(b2);
	}

}
