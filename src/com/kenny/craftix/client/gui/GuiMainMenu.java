package com.kenny.craftix.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiButton;
import com.kenny.craftix.editor.Editor;
import com.kenny.craftix.editor.gui.GuiSwitchEditor;

/**
 * Main Menu Gui screen page is the main class for all components of the menu.
 * 
 * @author Kenny
 */
public class GuiMainMenu extends GuiBackground
{
	/**List of the Main Menu textures.*/
	public List<GuiQuad> textures = new ArrayList<GuiQuad>();
	public List<GuiQuad> panorama = new ArrayList<GuiQuad>();
	public List<GuiQuad> background = new ArrayList<GuiQuad>();
	public GuiQuad cursor;
	protected static GuiButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b10;
	protected GuiNews guiNews = new GuiNews();
	protected GuiProfile guiProfile = new GuiProfile();
	protected GuiLinkWarning guiLinkWarning = new GuiLinkWarning();
	protected GuiQuit guiQuit = new GuiQuit();
	protected GuiCreditsMenu guiCredits = new GuiCreditsMenu();
	protected GuiSingleplayer guiSingleplayer = new GuiSingleplayer();
	protected GuiMultiplayer guiMultiplayer = new GuiMultiplayer();
	protected GuiOptions guiOptions = new GuiOptions();
	protected GuiGraphics guiGraphics = new  GuiGraphics();
	protected GuiInGameMenu guiPause = new GuiInGameMenu();
	protected GuiGameOver guiGameOver = new GuiGameOver();
	protected Editor editor;
	public static boolean inOptionMenuPage = false;
	public static boolean hasntLicensePage = false;
	
	public GuiMainMenu(GuiCreditsMenu guiCreditsIn)
	{
		this.guiCredits = guiCreditsIn;
	}
	
	/**
	 * Init all Gui and UI components.
	 */
	public void initGui(Craftix craftixIn) 
	{
		super.initGui(craftixIn);
		this.editor = new Editor(craftixIn);
		if(!inOptionMenuPage)
		{
			this.panorama.add(new GuiQuad(GUI_LOCATION + "menu/panorama_overlay", 0f, 0f, this.width, this.height + 0.1f));
			this.background.add(new GuiQuad(GUI_LOCATION + "menu/in_background", 0f, 0f, this.width * 1.8f, this.height * 1.4f));
			this.cursor = new GuiQuad(GUI_LOCATION + "menu/cursor3", 0f, 0f, 0.022f, 0.038f);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24 || calendar.get(2) + 1 == 1 && calendar.get(5) == 1)
		    {
				this.textures.add(new GuiQuad(GUI_LOCATION + "menu/title/craftix_logo_ny", 0f, 0.6f, this.width / 2, this.height / 5));
		    } else
		    {
		    	this.textures.add(new GuiQuad(GUI_LOCATION + "menu/title/craftix_logo", 0f, 0.6f, this.width / 2, this.height / 5));
		    }
			
			b1 = new GuiButton(1, BUTTON_RECT_TEXTURE, 0f, 0.0f, 0, "button.singleplayer");
			b2 = new GuiButton(2, BUTTON_RECT_TEXTURE, 0f, -0.2f, 0, "button.multiplayer"); 
			b3 = new GuiButton(3, BUTTON_RECT_TEXTURE, 0f, -0.4f, 0, "button.options");     
			b4 = new GuiButton(4, BUTTON_RECT_TEXTURE, 0f, -0.6f, 0, "button.credits");   
			b5 = new GuiButton(5, BUTTON_RECT_TEXTURE, 0f, -0.8f, 0, "button.exit");       
			b6 = new GuiButton(6, BUTTON_RECT_TEXTURE, -0.8f, -0.4f, 2, "button.news");    
			b7 = new GuiButton(7, BUTTON_RECT_TEXTURE, -0.8f, -0.6f, 2, "button.profile"); 
			b8 = new GuiButton(8, BUTTON_RECT_TEXTURE, 0.8f, -0.4f, 2, "button.website");   
			b9 = new GuiButton(9, BUTTON_RECT_TEXTURE, 0.8f, -0.6f, 2, "button.editor");
			b10 = new GuiButton(25, GUI_LOCATION + "menu/button_lang", -0.35f, -0.8f, 3, "button_language");
			
			addPage();
		} else 
			this.background.add(new GuiQuad(GUI_LOCATION + "menu/in_background", 0f, 0f, this.width * 1.8f, this.height * 1.4f));
	}
	
	
	/**
     * Event occurring when the button is pressed. I dont why its not working in other buttonEvent
     * methods.
     */
	@Override
	protected void buttonClick(GuiButton button, Craftix craftixIn) throws IOException 
	{
		super.buttonClick(button);
		
		if(button.id == 1)
		{
			if(craftixIn.getSession().getUserLicense())
			{
				GuiMainMenu.removePage();
				Pages.isMainMenuPage = true;
				this.setLoaded(false);
				Pages.isSingleplayerPage = true;
				GuiSingleplayer.addPage();
			} else {
				hasntLicensePage = true;
				if(hasntLicensePage)
				{
					GuiMainMenu.removePage();
					Pages.isMainMenuPage = false;
					
				}
			}
		}
		
		if(button.id == 2)
		{
			GuiMainMenu.removePage();
			Pages.isMainMenuPage = false;
			Pages.isMultiplayerPage = true;
			GuiMultiplayer.addPage();
		}
		
		if(button.id == 3)
		{
			GuiMainMenu.removePage();
			Pages.isMainMenuPage = false;
			Pages.isOptionsPage = true;
			GuiOptions.addPage();
		}
		
		if(button.id == 4)
		{
			GuiMainMenu.removePage();
			Pages.isMainMenuPage = false;
			this.guiCredits.setLoaded(true);
			GuiCreditsMenu.addPage();
		}
		
		if(button.id == 5)
		{
			GuiMainMenu.removePage();
			Pages.isMainMenuPage = false;
			Pages.isQuitPage = true;
			GuiQuit.addPage();
		}
		
		if(button.id == 6)
		{
			GuiMainMenu.removePage();
			Pages.isMainMenuPage = false;
			Pages.isNewsPage = true;
			GuiNews.addPage();
		}
		
		if(button.id == 7)
		{
			GuiMainMenu.removePage();
			Pages.isMainMenuPage = false;
			Pages.isProfilePage = true;
			GuiProfile.addPage();
		}
		
		if(button.id == 8)
		{
			if(!Pages.isLinkWarning)
			{
				Pages.isLinkWarning = true;
				GuiLinkWarning.addPage();
			}
			
		}
		
		if(button.id != 8)
		{
			Pages.isLinkWarning = false;
			GuiLinkWarning.removePage();
		}
		
		if(button.id == 9)
		{
			GuiMainMenu.removePage();
			Pages.isMainMenuPage = false;
			this.editor.initEditor(craftixIn);
			com.kenny.craftix.editor.gui.GuiEditorBackground.Pages.isSwitchPage = true;
			GuiSwitchEditor.addPage();
		}
		
		if(button.id == 25)
		{
			GuiMainMenu.removePage();
			Pages.isMainMenuPage = false;
			Pages.isLanguagePage = true;
			GuiLanguage.addPage();
		}
		
		this.guiSingleplayer.buttonClick(button, craftixIn);
		this.guiMultiplayer.buttonClick(button, craftixIn);
		this.guiCredits.buttonClick(button);
		this.guiQuit.buttonClick(button);
		this.guiNews.buttonClick(button);
		this.guiProfile.buttonClick(button);
		this.guiLinkWarning.buttonClick(button);
		this.guiOptions.buttonClick(button, craftixIn);
		this.guiGraphics.buttonClick(button);
		this.guiPause.buttonClick(button, craftixIn);
		this.guiGameOver.buttonClick(button, craftixIn);
		this.editor.getGui().buttonClick(button, craftixIn);
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
		buttonList.add(b8);
		buttonList.add(b9);
		buttonList.add(b10);
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
		buttonList.remove(b8);
		buttonList.remove(b9);
		buttonList.remove(b10);
	}
	
	
	/**
	 * Update cursor position every frame.
	 */
	public void updateCursorPosition()
	{
		cursor.setPosition(Craftix.getInverseNormalizedMouseCoords());
	}
	
	public Editor getEditor()
	{
		return this.editor;
	}
}
