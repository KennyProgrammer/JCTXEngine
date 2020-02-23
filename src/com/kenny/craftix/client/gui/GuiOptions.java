package com.kenny.craftix.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiButton;
import com.kenny.craftix.client.scenes.MainMenuScene;
import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.client.settings.InGameSettings;

public class GuiOptions extends GuiBackground
{
	public List<GuiQuad> textures = new ArrayList<GuiQuad>();
	protected static GuiButton b1, b2, b3, b4, b5, b6, b7;
	public static GuiButton sb1;
	protected MainMenuScene scene;
	private static final InGameSettings.Options[] OPTIONS = new InGameSettings.Options[] {InGameSettings.Options.OPTION_FOV};
	protected GuiGlOptions guiGlOptions = new GuiGlOptions();
	protected GuiLanguage guiLanguage = new GuiLanguage();
	protected GuiGraphics guiGraphics = new GuiGraphics();
	protected GuiOtherOptions guiOtherOptions = new GuiOtherOptions();
	public GuiScale scaleManager = new GuiScale();
	protected GuiControls guiControls = new GuiControls();
	
	public static float ANIMATION_TIME = 0.0f;
	public static boolean f = false;
	
	public void initGui(Craftix craftixIn) 
	{
		int l = 0;
		float i = this.scaleManager.getScaleX();
		float j =  this.scaleManager.getScaleY();
		
		if(!GuiMainMenu.inOptionMenuPage)
		{
			super.initGui(craftixIn);
			
			for (InGameSettings.Options options : OPTIONS)
			{
				if(options.getEnumFloat())
				{
					sb1 = new GuiSlider(options.returnEnumOrdinal(), SCROLL_BAR, 0.02f + l, -0.3f, options, "button.scroll.fov");
					
				}
			}
			
			++l;
			
			this.textures.add(new GuiQuad(GUI_LOCATION + "menu/gui_border", 0.05f, 0.63f, this.width / 4f, this.height / 10));
			this.textures.add(new GuiQuad(BUTTON_SCROLL_RECT_TEXTURE, 0f, -0.3f, 0.26f - i, 0.08f - j));
			
			b1 = new GuiButton(29, BUTTON_RECT_TEXTURE, 0.68f, -0.72f, 2, "button.back");
			b2 = new GuiButton(30, BUTTON_RECT_TEXTURE, -0.3f, 0.3f, 0, "button.graphics");
			b3 = new GuiButton(31, BUTTON_RECT_TEXTURE, 0.3f, 0.3f, 0, "button.controls");
			b4 = new GuiButton(32, BUTTON_RECT_TEXTURE, -0.3f, 0.1f, 0, "button.audio");
			b5 = new GuiButton(33, BUTTON_RECT_TEXTURE, 0.3f, 0.1f, 0, "button.language");
			b6 = new GuiButton(34, BUTTON_RECT_TEXTURE, -0.3f, -0.1f, 0, "button.other");
			b7 = new GuiButton(35, BUTTON_RECT_TEXTURE, 0.3f, -0.1f, 0, "button.openglSettings");
		} else {
			super.initGui(craftixIn);
			this.textures.add(new GuiQuad(BUTTON_SCROLL_RECT_TEXTURE, 0f, -0.3f, 0.26f - i, 0.08f - j));
			this.textures.add(new GuiQuad(GUI_LOCATION + "menu/gui_border", 0.05f, 0.63f, this.width / 4f, this.height / 10f));
		}
		
	}
	
	public void saveAllGameOptionsInit(MainMenuScene sceneIn)
	{
		this.scene = sceneIn;
	}
	
	@Override
	protected void buttonClick(GuiButton button, Craftix craftixIn) throws IOException 
	{
		super.buttonClick(button, craftixIn);
		
		if(button.id == 29)
		{
			if(!WorldScene.inGameOptions)
			{
				GuiOptions.removePage();
				Pages.isOptionsPage = false;
				Pages.isMainMenuPage = true;
				GuiMainMenu.addPage();
				InGameSettings.saveOptions();
			} 
			else 
			{
				GuiOptions.removePage();
				Pages.isOptionsPage = false;
				WorldScene.inGameOptions = false;
				Pages.isMainGamePausePage = true;
				GuiInGameMenu.addPage();
				InGameSettings.saveOptions();
			}
			
		}
		
		if(button.id == 30)
		{
			GuiOptions.removePage();
			Pages.isOptionsPage = false;
			Pages.isGraphicsPage = true;
			f = true;
		}
		
		if(button.id == 31)
		{
			GuiOptions.removePage();
			Pages.isOptionsPage = false;
			Pages.isControlsPage = true;
			GuiControls.addPage();
		}
		
		if(button.id == 33)
		{
			GuiOptions.removePage();
			Pages.isOptionsPage = false;
			Pages.fromLangToOptions = true;
			Pages.isLanguagePage = true;
			GuiLanguage.addPage();
		}
		
		if(button.id == 34)
		{
			GuiOptions.removePage();
			Pages.isOptionsPage = false;
			Pages.isOtherOptionsPage = true;
			GuiOtherOptions.addPage();
		}
		
		if(button.id == 35)
		{
			GuiOptions.removePage();
			Pages.isOptionsPage = false;
			Pages.isGlOptionsPage = true;
			GuiGlOptions.addPage();
		}
		
		this.guiGlOptions.buttonClick(button);
		this.guiLanguage.buttonClick(button);
		this.guiControls.buttonClick(button);
		this.guiOtherOptions.buttonClick(button);
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
		buttonList.add(b7);
		buttonList.add(sb1);
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
		buttonList.remove(b7);
		buttonList.remove(sb1);
	}
	
	public void eventAfterClick()
	{
		if(!Pages.isOptionsPage && f)
		{
			GuiGraphics.addPage();
			GuiOptions.f = false;
		}
	}
}
