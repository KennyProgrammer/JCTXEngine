package com.kenny.craftix.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiButton;
import com.kenny.craftix.client.settings.InGameSettings;

public class GuiLoadWorld extends GuiBackground
{
	public List<GuiQuad> textures = new ArrayList<GuiQuad>();
	protected static GuiButton b1, b2_slot0, b3_slot1, b4_slot2, b5_slot4, b6_slot5;
	public static boolean f;
	
	@Override
	public void initGui(Craftix craftixIn) 
	{
		super.initGui(craftixIn);
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/background", 0.35F, 0F, 0.75F, this.height));
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/gui_border", -0.625F, 0.7F, this.width / 3.4F, this.height / 10));
		
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/background", 0.3F, 0.65F, 0.55F, 0.125F));
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/background", 0.3F, 0.30F, 0.55F, 0.125F));
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/background", 0.3F, -0.05F, 0.55F, 0.125F));
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/background", 0.3F, -0.40F, 0.55F, 0.125F));
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/background", 0.3F, -0.75F, 0.55F, 0.125F));
		
		b1 = new GuiButton(100, BUTTON_RECT_TEXTURE, -0.7F, -0.7F, 2, "button.back");
		b2_slot0 = new GuiButton(101, BUTTON_QUAD_TEXTURE, 0.75F, 0.65F, 1, "button.loadSlot0");
	}
	
	@Override
	protected void buttonClick(GuiButton button, Craftix craftixIn) throws IOException 
	{
		super.buttonClick(button, craftixIn);
		
		if(button.id == 100)
		{
			GuiLoadWorld.removePage();
			Pages.isLoadWorldPage = false;
			Pages.isSingleplayerPage = true;
			f = true;
		}
		
		if(button.id == 101 && InGameSettings.slot0)
		{
			craftixIn.status.setGameMenu(false);
			craftixIn.status.setGameWorld(true);
			craftixIn.menuScene.isGoToNexScene = true;
			craftixIn.getWorldScene().isLoadWorld = true;
			GuiLoadWorld.removePage();
			Pages.isLoadWorldPage = false;
		}
	}
	
	/**
	 * Add button for this gui and render int on the screen.
	 */
	public static void addPage() 
	{
		buttonList.add(b1);
		if(InGameSettings.slot0)
			buttonList.add(b2_slot0);
	}
	
	/**
	 * Remove button from the list of buttons and stop render it.
	 */
	public static void removePage()
	{
		buttonList.remove(b1);
		if(InGameSettings.slot0)
			buttonList.remove(b2_slot0);
	}
	
	public void eventAfterClick()
	{
		if(Pages.isSingleplayerPage && f)
		{
			GuiSingleplayer.addPage();
			GuiLoadWorld.f = false;
		}
	}

}
