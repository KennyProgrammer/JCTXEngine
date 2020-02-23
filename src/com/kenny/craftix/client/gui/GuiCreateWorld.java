package com.kenny.craftix.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiButton;
import com.kenny.craftix.client.text.TextLanguage;
import com.kenny.craftix.client.text.TextLoader;
import com.kenny.craftix.world.CreatingWorld;

import static com.kenny.craftix.client.language.Language.CREATE_WORLD_WORLD_SEED;
import static com.kenny.craftix.client.language.Language.CREATE_WORLD_WORLD_TYPE;
import static com.kenny.craftix.client.language.Language.CREATE_WORLD_GAMEMODE;

public class GuiCreateWorld extends GuiBackground
{
	protected static GuiButton b1, b2, b3, b4, b5, b6;
	protected boolean inMainCreatePage, inGenWorldSettingsPage;
	public List<GuiQuad> textures = new ArrayList<GuiQuad>();
	public GuiScale scaleManager = new GuiScale();
	
	@Override
	public void initGui(Craftix craftixIn)
	{
		int l = 0;
		float i = this.scaleManager.getScaleX();
		float j =  this.scaleManager.getScaleY();
		
		this.inMainCreatePage = true;
		if(this.inMainCreatePage)
		{	
			super.initGui(craftixIn);
			this.textures.add(new GuiQuad(GUI_LOCATION + "menu/gui_border", 0.07f, 0.83f, this.width / 3, this.height / 10));
			this.textures.add(new GuiQuad(GUI_LOCATION + "menu/background", 0F, -0.1F, this.width / 1.2F, this.height / 1.2F));
			this.textures.add(new GuiQuad(BUTTON_SCROLL_RECT_TEXTURE, 0f, 0.3f, 0.26f - i, 0.08f - j));
			
			b1 = new GuiButton(87, BUTTON_RECT_TEXTURE, -0.2F, -0.7F, 2, "button.back");
			b2 = new GuiButton(88, BUTTON_RECT_TEXTURE, 0.2F, -0.7F, 2, "button.createWorld");
			b3 = new GuiButton(95, BUTTON_RECT_TEXTURE, -0.3F, -0.1F, 0, "button.gamemode");
			b4 = new GuiButton(96, BUTTON_RECT_TEXTURE, 0.3F, -0.1F, 0, "button.worldType");
			b5 = new GuiButton(97, BUTTON_RECT_TEXTURE, -0.3F, -0.3F, 0, "button.worldSeed");
			b6 = new GuiButton(98, BUTTON_RECT_TEXTURE, 0.3F, -0.3F, 0, "button.otherWorldSettings");
		}
	}
	
	@Override
	protected void buttonClick(GuiButton button, Craftix craftixIn) throws IOException 
	{
		super.buttonClick(button, craftixIn);
		
		if(button.id == 87)
		{
			GuiCreateWorld.removePage();
			Pages.isCreateWorldPage = false;
			Pages.isSingleplayerPage = true;
			GuiSingleplayer.addPage();
			craftixIn.menuScene.getCreatingWorld().resetWorldSettings();
			TextLoader.updateText(TextLanguage.t123, CREATE_WORLD_WORLD_SEED + ": " + craftixIn.menuScene.getCreatingWorld().getTempSeed());
		}
		
		if(button.id == 88)
		{
			craftixIn.status.setGameMenu(false);
			craftixIn.status.setGameWorld(true);
			craftixIn.menuScene.isGoToNexScene = true;
			GuiCreateWorld.removePage();
			Pages.isCreateWorldPage = false;
		}
		
		if(button.id == 95)
		{
			this.changeGamemode(craftixIn);
		}
		
		if(button.id == 96)
		{
			this.changeWorldType(craftixIn);
		}
	}
	
	/**
	 * Add button to the screen.
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
	 * Remove buttons from this page gui.
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
	
	public void changeWorldType(Craftix craftixIn)
	{
		CreatingWorld creatingWorldIn = craftixIn.menuScene.getCreatingWorld();
		if(creatingWorldIn.getTempWorldType() == 0)
		{
			creatingWorldIn.setTempWorldType(0 + 1);
			creatingWorldIn.parseIntToStringTags();
			TextLoader.updateText(TextLanguage.t122, CREATE_WORLD_WORLD_TYPE + ": " + creatingWorldIn.stringTypes);
		}
		else if(creatingWorldIn.getTempWorldType() == 1)
		{
			creatingWorldIn.setTempWorldType(0 + 2);
			creatingWorldIn.parseIntToStringTags();
			TextLoader.updateText(TextLanguage.t122, CREATE_WORLD_WORLD_TYPE + ": " + creatingWorldIn.stringTypes);
		}
		else if(creatingWorldIn.getTempWorldType() == 2)
		{
			creatingWorldIn.setTempWorldType(0);
			creatingWorldIn.parseIntToStringTags();
			TextLoader.updateText(TextLanguage.t122, CREATE_WORLD_WORLD_TYPE + ": " + creatingWorldIn.stringTypes);
		}
	}
	
	public void changeGamemode(Craftix craftix)
	{
		CreatingWorld creatingWorldIn = craftix.menuScene.getCreatingWorld();
		if(creatingWorldIn.getTempGamemode() == 0)
		{
			creatingWorldIn.setTempGamemode(0 + 1);
			creatingWorldIn.parseIntToStringTags();
			TextLoader.updateText(TextLanguage.t121, CREATE_WORLD_GAMEMODE + ": " + creatingWorldIn.stringModes);
		}
		else if(creatingWorldIn.getTempGamemode() == 1)
		{
			creatingWorldIn.setTempGamemode(0);
			creatingWorldIn.parseIntToStringTags();
			TextLoader.updateText(TextLanguage.t121, CREATE_WORLD_GAMEMODE + ": " + creatingWorldIn.stringModes);
		}
	}
}	
