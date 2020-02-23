package com.kenny.craftix.client.gui;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.audio.sound.SoundLoader;
import com.kenny.craftix.client.gui.button.GuiButton;
import com.kenny.craftix.client.keyboard.KeyboardUserInput;
import com.kenny.craftix.client.keyboard.MouseUserInput;
import com.kenny.craftix.client.scenes.MainMenuScene;
import com.kenny.craftix.client.text.Text;
import com.kenny.craftix.main.Side;
import com.kenny.craftix.main.SideMachine;

/**
 * GuiBackground in future has been GuiScreen.
 */
@SideMachine(Side.CLIENT)
public abstract class GuiBackground extends GuiScreen
{
	/**Its a location folder of all guis in the engine.*/
	public static final String GUI_LOCATION = "guis/";
	protected static final String BUTTON_RECT_TEXTURE = GUI_LOCATION + "menu/button";
	protected static final String BUTTON_QUAD_TEXTURE = GUI_LOCATION + "menu/button_quad";
	protected static final String BUTTON_SCROLL_RECT_TEXTURE = GUI_LOCATION + "menu/button_scroll";
	protected static final String SCROLL_BAR = GUI_LOCATION + "menu/button_scroll_bar";
	/**This the same list Java but to realize i used guava lib.*/
	public static List<GuiButton> buttonList = new ArrayList<GuiButton>();
	public MouseUserInput mouseManager = new MouseUserInput();
	public KeyboardUserInput keyboardManager = new KeyboardUserInput();
	/**Link on the browse addrees.*/
	private URI clickedLinkURI;
	public List<GuiQuad> texturesList = new ArrayList<GuiQuad>();
	/**Its width of the screen gui.*/
	public float width;
	/**Its height of the screen gui.*/
	public float height;
	public GuiButton selectedButton;
	private boolean onButton;
	/**Necessary to control the number of clicks or taps on the screen.*/
	public int clickValue;
	/**When an event occurs when the button is clicked.*/
	public int eventButton;
	private long lastMouseEvent;
	/**The Main Game instance.*/
	public Craftix cx;
	public MainMenuScene scene;
	public Text selectedText;
	/**Check if current gui loaded.*/
	public static boolean loaded;
	
	/**
	 * Here be init all gui components. Like a width and height of the gui screen.
	 */
	public void initGui(Craftix craftixIn)
	{
		this.cx = craftixIn;
		this.width = 1f;
		this.height = 1f;
		this.scene = this.cx.menuScene;
		this.loadFinallyToCache();
	}
	
	/**
	 * Helps T extends GuiQuad implementation to add texture to the list of the textures. Now
	 * this method i not use, because on all guis contains your list of textures.
	 */
	public <C extends GuiQuad> C addTexture(C textureIn)
	{
		this.texturesList.add(textureIn);
		return textureIn;
	}
	
	/**
	 * Now this method render only list of buttons.
	 */
	public void renderScreen()
	{
		this.cx.guiRenderer.renderButton(buttonList, true);
	}
	
	/**
     * Event occurring when the button is pressed.
     */
    protected void buttonClick(GuiButton button) throws IOException
    {
    	this.playButtonSound();
    }
    protected void buttonClick(GuiButton button, Craftix craftixIn) throws IOException
    {
    	this.cx = craftixIn;
    	this.playButtonSound();
    }
    
    protected void buttonEventAfterClick(GuiButton button)
    {

    }
    
    protected void onHoverEvent(GuiButton button)
    {
    }
    
    protected void buttonMoveClick(int mouseButton, long lastClick)
    {
    }
    
    /**
     * Fired when the mouse button is released.
     */
    public void buttonReleased()
    {
    }
   
    
    public void animationEvent(GuiButton button)
    {
    }
	
    /**
     * Distributes clicks and events for each button. And does this 
     * only when the user press 0 mouse button.
     */
	protected void mouseClickEvent(int mouseButton) throws IOException
	{
		if(mouseButton == 0)
		{
			for (int i = 0; i < buttonList.size(); ++i)
	        {
				GuiButton guibutton = buttonList.get(i);
				this.animationEvent(guibutton);
				
				if(guibutton.checkHover(this.cx))
				{
					this.selectedButton = guibutton;
					this.onHoverEvent(guibutton);
					this.buttonClick(guibutton);
					this.buttonClick(guibutton, this.cx);
				}
	        }
		}
	}
	
	/**
	 * Used when you want to perform an action on the release button.
	 */
	protected void mouseReleaseEvent(int state)
	{
		if(this.selectedButton != null && state == 0)
		{
			this.selectedButton.buttonReleased();
			this.selectedButton = null;
		}
	}

	
	/**
	 * Collects events of mouse and keyboard in the future and retains 
	 * their use.
	 */
	public void handleInput(Craftix craftixIn) throws IOException
	{
		if(Mouse.isCreated())
		{
			while(Mouse.next())
			{
				this.controllMouse();
			}
		}
		
		if(Keyboard.isCreated())
		{
			while(Keyboard.next())
			{
				this.controllKeyboard(craftixIn);
			}
		}
	}
	
	/**
	 * Responsible for clicking the mouse. Controls the stages of pressing and 
	 * release and also makes it so that when you click on the mouse was only 
	 * one click.
	 */
	public void controllMouse() throws IOException
	{
		int k = Mouse.getEventButton();
		
		if(this.onButton == false)
		{
			if(Mouse.getEventButtonState())	
			{
				if(this.clickValue++ > 0)
				{
					return;
				}
				
				this.eventButton = k;
				this.lastMouseEvent = Craftix.getCurrentTime();
				this.mouseClickEvent(k);
				this.mouseManager.mainMenuButtonPreesed(this.cx);

			}
			else if(k != -1)
			{
				if(--this.clickValue > 0)
				{
					return;
				}
				
				this.eventButton = -1;
				this.mouseReleaseEvent(k);
			}
			else if(this.eventButton != -1 && this.lastMouseEvent > 0L)
			{
				
				long l = Craftix.getCurrentTime() - lastMouseEvent;
				this.buttonMoveClick(this.eventButton, l);
				
			}
		}
	}
	
	public void controllKeyboard(Craftix craftixIn) throws IOException
	{
		char c = Keyboard.getEventCharacter();
		KeyboardUserInput k = new KeyboardUserInput();
		
		if(Keyboard.getEventKey() == 0 && c >= ' ' || Keyboard.getEventKeyState())
		{
			//this.keyTyped(c, Keyboard.getEventKey());
		}
		
		if(craftixIn.status.isGameMenu())
		k.mainMenuKeysPreesed(this.cx);
		
		if(craftixIn.status.isGameWorld())
		k.gameplayKeysPressed(craftixIn);
		
	}
	
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		if(keyCode == 1)
		{
			if(this.cx.guiScreen == null)
			{
				//this.cx.setIngameFocus();
			}
		}
	}
	
	public void isClickedOnURI(boolean result, int id)
	{
		try 
		{
			this.clickedLinkURI = new URI("https://vk.com/club175351714");
		} 
		catch (URISyntaxException e) 
		{
			e.printStackTrace();
		}
		if(id == 21022003)
		{
			if(result)
			{
				this.openWebLink(this.clickedLinkURI);
			}
		}
		this.clickedLinkURI = null;
	}
	
	/**
	 * Opens the page to which the link was specified.
	 */
	private void openWebLink(URI urlAddress)
	{
		try
		{
			Class<?> clazz = Class.forName("java.awt.Desktop");
			Object object = clazz.getMethod("getDesktop").invoke((Object) null);
			clazz.getMethod("browse", URI.class).invoke(object, urlAddress);
		}
		catch(Throwable t1)
		{
			Throwable t = t1.getCause();
			LOGGER.error("Can't opend this link: {}", (Object)(t == null ? "<UNKNOWN>" : t.getMessage()));
		}
	}
	
	public void eventAfterClick()
	{
	}
	
	/**
	 * Playing on button click event.
	 */
	public void playButtonSound()
	{
		SoundLoader.soundPause(SoundLoader.sourceButtonClick);
		SoundLoader.soundPlay(SoundLoader.sourceButtonClick, 
				SoundLoader.bufferButtonClick);
	}
	
	/**
	 * If true return the true value, and loaded the current gui component.
	 */
	public static boolean isLoaded()
	{
		return loaded;
	}
	
	/**
	 * Set loaded stage.
	 */
	public GuiBackground setLoaded(boolean isLoadedIn)
	{
		loaded = isLoadedIn;
		return this;
	}
	
	/**
	 * Load finally data gui to memory, for small laggy update.
	 */
	public void loadFinallyToCache()
	{
		if(!this.isLoaded())
		{
			this.loaded = true;
			this.loaded = false;
		}
	}
	
	public static class Pages
	{
		/**
		 * Main Gui Screen Pages.
		 */
		public static boolean isMainMenuPage = true;
		public static boolean isSingleplayerPage;
		public static boolean isMultiplayerPage;
		public static boolean isDisconnectedPage;
		public static boolean isConnectingPage;
		public static boolean isNewsPage;
		public static boolean isNews1 = true, isNews2, isNews3, isNew4;
		public static boolean isProfilePage;
		public static boolean isLinkWarning;
		public static boolean isQuitPage;
		//public static boolean isCreditsPage;
		public static boolean isCreateWorldPage;
		public static boolean isLoadWorldPage;
		public static boolean isOptionsPage;
		public static boolean isGlOptionsPage;
		public static boolean isGraphicsPage;
		public static boolean isLanguagePage;
		public static boolean isOtherOptionsPage;
		public static boolean isControlsPage;
		public static boolean isControlsKeyChangePage;
		public static boolean isLoadingWorldPage;
		public static boolean isSaveWorldPage;
		public static boolean isMainGamePausePage;
		public static boolean isHelpPage;
		public static boolean isGameOverPage;
		public static boolean isGameWorld;
		public static boolean isTestPage;
		
		/**
		 * Transition from page to page with multipy screen.
		 */
		
		public static boolean fromLangToOptions;
		
		
		public static void cleanUp()
		{
			//if(isMainMenuPage)
			//{
				//GuiMainMenu.removePage();
				//isMainMenuPage = false;
			//}
			if(isSingleplayerPage)
			{
				GuiSingleplayer.removePage();
				isSingleplayerPage = false;
			}
			if(isMultiplayerPage)
			{
				GuiMultiplayer.removePage();
				isMultiplayerPage = false;
			}
			if(isDisconnectedPage)
			{
				GuiDisconnected.removePage();
				isDisconnectedPage = false;
			}
			if(isConnectingPage)
			{
				isConnectingPage = false;
			}
			if(isProfilePage)
			{
				GuiProfile.removePage();
				isProfilePage = false;
			}
			if(isLinkWarning)
			{
				GuiLinkWarning.removePage();
				isLinkWarning = false;
			}
			if(isQuitPage)
			{
				GuiQuit.removePage();
				isQuitPage = false;
			}
			//if(isCreditsPage)
			//{
				GuiCreditsMenu.removePage();
			//	//isCreditsPage = false;
			//}
			if(isCreateWorldPage)	
			{
				GuiCreateWorld.removePage();
				isCreateWorldPage = false;
			}
			if(isLoadWorldPage)
			{
				GuiLoadWorld.removePage();
				isLoadWorldPage = false;
			}
			if(isOptionsPage)
			{
				GuiOptions.removePage();
				isOptionsPage = false;
			}
			if(isGlOptionsPage)
			{
				GuiGlOptions.removePage();
				isGlOptionsPage = false;
			}
			if(isGraphicsPage)
			{
				GuiGraphics.removePage();
				isGraphicsPage = false;
			}
			if(isLanguagePage)
			{
				GuiLanguage.removePage();
				isLanguagePage = false;
			}
			if(isOtherOptionsPage)
			{
				GuiOtherOptions.removePage();
				isOtherOptionsPage = false;
			}
			if(isControlsPage)
			{
				GuiControls.removePage();
				isControlsPage = false;
			}
			if(isControlsKeyChangePage)
			{
				GuiControlsKeyChange.removePage();
				isControlsKeyChangePage = false;
			}
			if(isLoadingWorldPage)
			{
				isLoadingWorldPage = false;
			}
			if(isSaveWorldPage)
			{
				isSaveWorldPage = false;
			}
			if(isMainGamePausePage)
			{
				GuiInGameMenu.removePage();
				isMainGamePausePage = false;
			}
			isHelpPage = false;
			isGameOverPage = false;
			isGameWorld = false;
			isTestPage = false;
			isNewsPage = false;
			isNews1 = false; 
			isNews2 = false; 
			isNews3 = false; 
			isNew4 = false;
			
		}
	}
	
}
