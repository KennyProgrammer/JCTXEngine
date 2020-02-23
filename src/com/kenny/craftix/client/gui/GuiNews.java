package com.kenny.craftix.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiButton;

public class GuiNews extends GuiBackground
{
	public List<GuiQuad> textures = new ArrayList<GuiQuad>();
	/**The Main Craftix Instance.*/
	public Craftix cx;
	protected static GuiButton b1, b2, b3;
	
	@Override
	public void initGui(Craftix craftixIn) 
	{
		super.initGui(craftixIn);
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/gui_border", -0.68f, 0.63f, this.width / 4f, this.height / 10));
		
		b1 = new GuiButton(10, BUTTON_RECT_TEXTURE, 0.68f, -0.72f, 2, "button.back");
		b2 = new GuiButton(11, GUI_LOCATION + "menu/button_back", 0.61f, -0.5f, 1, "button.previuos");
		b3 = new GuiButton(12, GUI_LOCATION + "menu/button_next", 0.75f, -0.5f, 1, "button.next");
		
	}
	
	@Override
	protected void buttonClick(GuiButton button) throws IOException 
	{
		if(button.id == 10)
		{
			GuiNews.removePage();
			Pages.isNewsPage = false;
			Pages.isMainMenuPage = true;
			GuiMainMenu.addPage();
		}
		
		if(button.id == 11)
		{
			if(!Pages.isNews1)
			{
				Pages.isNews1 = true;
				Pages.isNews2 = false;
			}
		}
		
		if(button.id == 12)
		{
			if(!Pages.isNews2)
			{
				Pages.isNews2 = true;
				Pages.isNews1 = false;
			}
		}
	}
	
	
	/**
	 * Add all button to the list.
	 */
	protected static void addPage()
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
