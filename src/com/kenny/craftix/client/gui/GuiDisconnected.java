package com.kenny.craftix.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.openal.AL;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiButton;
import com.kenny.craftix.server.gui.GuiServerLog;

public class GuiDisconnected extends GuiBackground
{
	/**List of the Multiplayer textures.*/
	public List<GuiQuad> textures = new ArrayList<GuiQuad>();
	protected static GuiButton b1, b2, b3;
	/**Event after click. Showing main menu.*/
	public static boolean f = false;
	public GuiServerLog guiServer = new GuiServerLog();
	
	@Override
	public void initGui(Craftix craftixIn) 
	{
		super.initGui(craftixIn);
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/gui_border", 0.05f, 0.43f, this.width / 4, this.height / 10));
		
		b1 = new GuiButton(90, BUTTON_RECT_TEXTURE, -0.20f, -0.40f, 2, "button.backToMenu");
		b2 = new GuiButton(91, BUTTON_RECT_TEXTURE, 0.20f, -0.40f, 2, "button.quit");
	}
	
	@Override
	protected void buttonClick(GuiButton button, Craftix craftixIn) throws IOException 
	{
		super.buttonClick(button, craftixIn);
		
		if(button.id == 90)
		{
			GuiDisconnected.removePage();
			Pages.isDisconnectedPage = false;
			f = true;
		}
		
		if(button.id == 91)
		{
			AL.destroy();
			craftixIn.exitController(0);
		}
	}
	
	public static void addPage()
	{
		buttonList.add(b1);
		buttonList.add(b2);
	}
	
	public static void removePage()
	{
		buttonList.remove(b1);
		buttonList.remove(b2);
	}
	
	public void eventAfterClick()
	{
		if(!Pages.isDisconnectedPage && GuiDisconnected.f)
		{
			Pages.isMainMenuPage = true;
			GuiMainMenu.addPage();
			GuiDisconnected.f = false;
		}
	}
	
	public void clearAllPrevieusData()
	{
		this.guiServer.clientUnknownIpAddress = false;
		this.guiServer.serverUnknownIpAdreess = false;
	}
}
