package com.kenny.craftix.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiButton;
import com.kenny.craftix.client.scenes.MainMenuScene;
import com.kenny.craftix.client.settings.InGameSettings;


public class GuiLanguage extends GuiBackground
{

	public List<GuiQuad> textures = new ArrayList<GuiQuad>();
	public List<GuiQuad> guisLanguageButtons = new ArrayList<GuiQuad>();
	
	public MainMenuScene scene;
	public Craftix cx;
	protected static GuiButton b1, b2, b3;
	
	
	public void initGui(Craftix craftixIn, MainMenuScene sceneIn)
	{
		super.initGui(craftixIn);
		this.scene = sceneIn;
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/gui_background_small", 0.12f, -0.45f, this.width / 2.1f, this.height));
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/gui_border", -0.74f, 0.63f, this.width / 5, this.height / 10));
		
		b1 = new GuiButton(26, BUTTON_RECT_TEXTURE, 0.68f, -0.72f, 2, "button.back");
		b2 = new GuiButton(27, BUTTON_RECT_TEXTURE, -0.0f, 0.1f, 2, "button.lang.en");
		b3 = new GuiButton(28, BUTTON_RECT_TEXTURE, -0.0f, -0.1f, 2, "button.lang.ru");
	}
	
	@Override
	protected void buttonClick(GuiButton button) throws IOException 
	{
		super.buttonClick(button);
		if(button.id == 26)
		{
			GuiLanguage.removePage();
			Pages.isLanguagePage = false;
			if(Pages.fromLangToOptions)
			{
				Pages.isOptionsPage = true;
				Pages.fromLangToOptions = false;
				GuiOptions.addPage();
			} else {
				Pages.isMainMenuPage = true;
				GuiMainMenu.addPage();
			}
		}
		
		if(button.id == 27)
		{
			InGameSettings.languageIn = 0;
		}
		
		if(button.id == 28)
		{
			InGameSettings.languageIn = 1;
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

	/**
	@Override
	public void drawGuiButtons() 
	{
		/**
		 * This is custom X and Y values for a for multi-size buttons.
		 */
	/**
		float x = 0f, y = 0f;
		if(InGameSettings.guiScaleSmallIn)
		{x = 0.009f; y = 0.012f;}
		
		if(InGameSettings.guiScaleMediumIn)
		{x = 0.01f; y = 0.01f;}
		
		if(InGameSettings.guiScaleLargeIn)
		{x = 0f; y = 0f;}
		
		this.button_language_back = new GuiAbstractButton("guis/menu/button_medium_base", 
				new Vector2f(0.72f,-0.72f), -0.04f, 0f) 
		{
			public void onClick(IButton button) 
			{
				GuiScaled.isButtonYesNo = true;
				TextInit textInit = new TextInit();
				textInit.loadDefaultFonts();
				if(InGameSettings.hasError)
				{
					textInit.initErrorsMessages(textInit.loader);
				}
				GuiRenderManager.renderLanguageMenu = false;
				GuiRenderManager.renderOptionsMenu = true;
			}
			public void isVisible(boolean visibleIn) {}
		};
		this.button_language_back.show(guisLanguageButtons);
		
		this.button_language_en = new GuiAbstractButton("guis/menu/button_base", 
				new Vector2f(-0.0f, 0.1f), -0.10f + x, -0.03f + y)  
		
		{
			public void onClick(IButton button) 
			{
				MainMenuScene mainScene = new MainMenuScene();
		
				InGameSettings.languageIn = false;
				if(InGameSettings.languageIn == false)
				{
					mainScene.selectEnglishLang();
				}
				Language.selectLang = true;
				
				
		
			}
			public void isVisible(boolean visibleIn) {}
		};
			this.button_language_en.show(guisLanguageButtons);
			
			this.button_language_ru = new GuiAbstractButton("guis/menu/button_base", 
					new Vector2f(-0.0f, -0.1f), -0.10f + x, -0.03f + y)  
			
			{
				public void onClick(IButton button) 
				{
					MainMenuScene mainScene = new MainMenuScene();
					InGameSettings.languageIn = true;
					if(InGameSettings.languageIn == true)
					{
						mainScene.selectRussianLang();
					}
		
					Language.selectLang = true;
				
					
					
				}
				public void isVisible(boolean visibleIn) {}
			};
			this.button_language_ru.show(guisLanguageButtons);
	}
	**/

}
