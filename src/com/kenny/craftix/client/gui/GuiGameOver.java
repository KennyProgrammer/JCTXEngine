package com.kenny.craftix.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiButton;
import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.gameplay.event.EventList;

public class GuiGameOver extends GuiBackground
{
	public List<GuiQuad> textures = new ArrayList<GuiQuad>();
	public WorldScene world;
	protected static GuiButton b1, b2, b3;
	
	@Override
	public void initGui(Craftix craftixIn) 
	{
		super.initGui(craftixIn);
		this.textures.add(new GuiQuad(GUI_LOCATION + "game/background", 0.3f, -0.3f, this.width * 1.3f, this.height * 1.3f));
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/gui_border", 0.053f, 0.33f, this.width / 4, this.height / 10));
		
		b1 = new GuiButton(80, BUTTON_RECT_TEXTURE, -0.252f, -0.4f, 2, "button.back");
		b2 = new GuiButton(81, BUTTON_RECT_TEXTURE, 0.252f, -0.4f, 2, "button.respawn");
	}
	
	@Override
	protected void buttonClick(GuiButton button, Craftix craftixIn) throws IOException 
	{
		super.buttonClick(button, craftixIn);
		
		if(button.id == 80)
		{
			craftixIn.getWorldScene().getPlayer().respawn();
			GuiGameOver.removePage();
			Pages.isGameOverPage = false;
			craftixIn.backToMenu();
			EventList.reloadEventDataList();
		}
		
		if(button.id == 81)
		{
			craftixIn.getWorldScene().getPlayer().respawn();
			GuiGameOver.removePage();
			Pages.isGameOverPage = false;
			EventList.reloadEventDataList();
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
	
	
}
