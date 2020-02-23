package com.kenny.craftix.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.server.CraftixMP;
import com.kenny.craftix.client.gui.button.GuiButton;

public class GuiMultiplayer extends GuiBackground
{
	/**List of the Disconnected textures.*/
	public List<GuiQuad> textures = new ArrayList<GuiQuad>();
	protected static GuiButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b10;
	protected GuiConnecting guiConnecting = new GuiConnecting();
	protected GuiDisconnected guiDisconnected = new GuiDisconnected();
	
	@Override
	public void initGui(Craftix craftixIn) 
	{
		super.initGui(craftixIn);
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/gui_border", -0.54f, 0.53f, this.width / 4, this.height / 10));
		
		b1 = new GuiButton(83, BUTTON_RECT_TEXTURE, 0.68f, -0.72f, 2, "button.back");
		b2 = new GuiButton(84, BUTTON_RECT_TEXTURE, -0.60f, 0.07f, 0, "button.createServer");
		b3 = new GuiButton(85, BUTTON_RECT_TEXTURE, -0.60f, -0.15f, 0, "button.connectToServer");
	}
	
	@Override
	protected void buttonClick(GuiButton button, Craftix craftixIn) throws IOException 
	{
		super.buttonClick(button, craftixIn);
		
		if(button.id == 83)
		{
			GuiMultiplayer.removePage();
			Pages.isMultiplayerPage = false;
			Pages.isMainMenuPage = true;
			GuiMainMenu.addPage();
		}
		
		if(button.id == 84)
		{
			GuiMultiplayer.removePage();
			Pages.isMultiplayerPage = false;
			Pages.isConnectingPage = true;
			CraftixMP craftixMp = new CraftixMP(this.cx);
			craftixMp.runServer(true);
		}
		
		if(button.id == 85)
		{
			GuiMultiplayer.removePage();
			Pages.isMultiplayerPage = false;
			Pages.isConnectingPage = true;
			CraftixMP craftixMp = new CraftixMP(this.cx);
			craftixMp.runServer(false);
		}
		
		this.guiDisconnected.buttonClick(button, craftixIn);
	}
	
	public static void addPage()
	{
		buttonList.add(b1);
		buttonList.add(b2);
		buttonList.add(b3);
	}
	
	public static void removePage()
	{
		buttonList.remove(b1);
		buttonList.remove(b2);
		buttonList.remove(b3);
	}
}
