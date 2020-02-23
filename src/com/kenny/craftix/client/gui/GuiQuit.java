package com.kenny.craftix.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.lwjgl.openal.AL;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiButton;

public class GuiQuit extends GuiBackground
{
	public List<GuiQuad> textures = new ArrayList<GuiQuad>();
	protected static GuiButton b1, b2, b3, b4;
	public static boolean f;

	@Override
	public void initGui(Craftix craftixIn)
	{
		super.initGui(craftixIn);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24 || calendar.get(2) + 1 == 1 && calendar.get(5) == 1)
	    {
			this.textures.add(new GuiQuad(GUI_LOCATION + "menu/title/craftix_logo_ny", 0f, 0.6f, this.width / 2, this.height / 5));
	    } else
	    {
	    	this.textures.add(new GuiQuad(GUI_LOCATION + "menu/title/craftix_logo", 0f, 0.6f, this.width / 2, this.height / 5));
	    }
		
		b1 = new GuiButton(16, BUTTON_RECT_TEXTURE, -0.20f, -0.35f, 2, "button.return");
		b2 = new GuiButton(17, BUTTON_RECT_TEXTURE, 0.20f, -0.35f, 2, "button.exitGame");
	}
	
	@Override
	protected void buttonClick(GuiButton button) throws IOException 
	{
		super.buttonClick(button);
		
		if(button.id == 16)
		{
			GuiQuit.removePage();
			Pages.isQuitPage = false;
			Pages.isMainMenuPage = true;
			f = true;
		}
		
		if(button.id == 17)
		{
			AL.destroy();
			System.exit(1);
		}
	}
	
	/**
	 * Add all button to the list.
	 */
	public static void addPage()
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
	
	public void eventAfterClick()
	{
		if(!Pages.isQuitPage && f)
		{
			GuiMainMenu.addPage();
			GuiQuit.f = false;
		}
	}
	

}
