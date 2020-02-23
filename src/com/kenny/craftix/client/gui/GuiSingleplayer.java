package com.kenny.craftix.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.TestClass;
import com.kenny.craftix.client.gui.button.GuiButton;

import com.kenny.craftix.client.scenes.MainMenuScene;

public class GuiSingleplayer extends GuiBackground
{
	public Craftix cx;
	public MainMenuScene scene;
	public List<GuiQuad> textures = new ArrayList<GuiQuad>();
	public TestClass clazz = new TestClass();
	protected static GuiButton b1, b2, b3, b4, b5, b6;
	public GuiCreateWorld guiCreateWorld = new GuiCreateWorld();
	public GuiLoadWorld guiLoadWorld = new GuiLoadWorld();
	public static boolean f;
	
	@Override
	public void initGui(Craftix craftixIn) 
	{
		super.initGui(craftixIn);
		this.scene = craftixIn.menuScene;
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/in_background_game", 0.75f, -0.35f, this.width * 1.2f, this.height * 1.4f));
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/singleplayer", -0.55f, 0.75f, this.width / 3, this.height / 6));
		
		b1 = new GuiButton(19, BUTTON_RECT_TEXTURE, 0.68f, -0.72f, 2, "button.back");
		b2 = new GuiButton(20, BUTTON_RECT_TEXTURE, -0.60f, 0.29f, 0, "button.createWorld");
		b3 = new GuiButton(21, BUTTON_RECT_TEXTURE, -0.60f, 0.07f, 0, "button.generateLowpolyWorld");
		b4 = new GuiButton(22, BUTTON_RECT_TEXTURE, -0.60f, -0.15f, 0, "button.generateFlatWorld");
		b5 = new GuiButton(23, BUTTON_RECT_TEXTURE, -0.60f, -0.37f, 0, "button.generateEmptyWorld");
		b6 = new GuiButton(24, BUTTON_RECT_TEXTURE, -0.60f, -0.71f, 0, "button.loadWorld");
	}
	
	@Override
	protected void buttonClick(GuiButton button, Craftix craftixIn) throws IOException 
	{
		super.buttonClick(button, craftixIn);
		
		if(button.id == 19)
		{
			GuiSingleplayer.removePage();
			Pages.isSingleplayerPage = false;
			Pages.isMainMenuPage = true;
			GuiMainMenu.addPage();
		}
		
		if(button.id == 20)
		{
			GuiSingleplayer.removePage();
			Pages.isSingleplayerPage = false;
			Pages.isCreateWorldPage = true;
			GuiCreateWorld.addPage();
		}
		
		if(button.id == 21)
		{
			
		}
		
		if(button.id == 22)
		{
			
		}
		
		if(button.id == 23)
		{
			
		}
		
		if(button.id == 24)
		{
			//craftixIn.getWorldScene().isLoadWorld = true;
			//craftixIn.status.setGameMenu(false);
			//craftixIn.status.setGameWorld(true);
			//craftixIn.menuScene.isGoToNexScene = true;
			GuiSingleplayer.removePage();
			Pages.isSingleplayerPage = false;
			Pages.isLoadWorldPage = true;
			f = true;
		}
		
		this.guiCreateWorld.buttonClick(button, craftixIn);
		this.guiLoadWorld.buttonClick(button, craftixIn);
		
	}
	
	/**
	 * Add all button to the list.
	 */
	public static void addPage()
	{
		buttonList.add(b1);
		buttonList.add(b2);
		buttonList.add(b3);
		buttonList.add(b4);
		buttonList.add(b5);
		buttonList.add(b6);
	}
	
	/**
	 * Remove all button from the list.
	 */
	public static void removePage()
	{
		buttonList.remove(b1);
		buttonList.remove(b2);
		buttonList.remove(b3);
		buttonList.remove(b4);
		buttonList.remove(b5);
		buttonList.remove(b6);
	}
	
	public void eventAfterClick()
	{
		if(Pages.isLoadWorldPage && f)
		{
			GuiLoadWorld.addPage();
			GuiSingleplayer.f = false;
		}
	}

}
