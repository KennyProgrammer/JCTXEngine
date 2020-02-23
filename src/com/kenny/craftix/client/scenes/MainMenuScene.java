package com.kenny.craftix.client.scenes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.audio.sound.SoundLoader;
import com.kenny.craftix.client.entity.EntityCamaraInMenu;
import com.kenny.craftix.client.entity.GameObject;
import com.kenny.craftix.client.entity.Light;
import com.kenny.craftix.client.entity.player.PlayerSkin;
import com.kenny.craftix.client.gui.GuiBackground;
import com.kenny.craftix.client.gui.GuiControls;
import com.kenny.craftix.client.gui.GuiControlsKeyChange;
import com.kenny.craftix.client.gui.GuiCreateWorld;
import com.kenny.craftix.client.gui.GuiCreditsMenu;
import com.kenny.craftix.client.gui.GuiDisconnected;
import com.kenny.craftix.client.gui.GuiGlOptions;
import com.kenny.craftix.client.gui.GuiGraphics;
import com.kenny.craftix.client.gui.GuiLanguage;
import com.kenny.craftix.client.gui.GuiLinkWarning;
import com.kenny.craftix.client.gui.GuiLoadWorld;
import com.kenny.craftix.client.gui.GuiMainMenu;
import com.kenny.craftix.client.gui.GuiMultiplayer;
import com.kenny.craftix.client.gui.GuiNews;
import com.kenny.craftix.client.gui.GuiOptions;
import com.kenny.craftix.client.gui.GuiOtherOptions;
import com.kenny.craftix.client.gui.GuiProfile;
import com.kenny.craftix.client.gui.GuiQuit;
import com.kenny.craftix.client.gui.GuiRenderer;
import com.kenny.craftix.client.gui.GuiSingleplayer;
import com.kenny.craftix.client.gui.GuiSlider;
import com.kenny.craftix.client.gui.GuiBackground.Pages;
import com.kenny.craftix.client.gui.GuiConnecting;
import com.kenny.craftix.client.keyboard.KeyboardUserInput;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.renderer.MainMenuRenderer;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.client.shaders.FrameBufferLoader;
import com.kenny.craftix.client.text.TextChat;
import com.kenny.craftix.client.text.TextLanguage;
import com.kenny.craftix.client.text.TextLoader;
import com.kenny.craftix.init.EntityInit;
import com.kenny.craftix.init.PostEffectsInit;
import com.kenny.craftix.utils.Timer;
import com.kenny.craftix.world.CreatingWorld;
import com.kenny.craftix.world.skybox.SkyboxPanoramaShader;

public class MainMenuScene extends Scene
{
	/**Main Craftix Instance.*/
	public Craftix cx;
	/**Check if user in current scene.*/
	public boolean isCurrentScene;
	public boolean isGoToNexScene;
	/**Load the frameBuffer for this scene.*/
	public FrameBufferLoader frameBufferLoader;
	protected Loader loader = new Loader();
	/**Set the time in the current scene.*/
	private Timer timer = new Timer();
	public MainMenuRenderer mainMenuRenderer;
	public SkyboxPanoramaShader skyboxPanoramaShader;
	public EntityCamaraInMenu camera;
	public PlayerSkin playerSkin;
	public EntityInit entityInit;
	/**The Gui Screen Object.*/
	public GuiBackground guiScreen;
	/**Init the Mainmenu gui screen page.*/
	public GuiMainMenu guiMainScreen;
	/**Init the News gui screen page.*/
	public GuiNews guiNews = new GuiNews();
	/**Init the Player Profile gui screen page.*/
	public GuiProfile guiProfile = new GuiProfile();
	/**Init the Link Warning gui screen page.*/
	public GuiLinkWarning guiLinkWarning = new GuiLinkWarning();
	/**Init the Quit gui screen page.*/
	public GuiQuit guiQuit = new GuiQuit();
	/**Init the Singleplayer gui screen page.*/
	public GuiSingleplayer guiSingleplayer = new GuiSingleplayer();
	public GuiMultiplayer guiMultiplayer = new GuiMultiplayer();
	public GuiConnecting guiConnecting = new GuiConnecting();
	public GuiDisconnected guiDisconnected = new GuiDisconnected();
	/**Init the Credits gui screen page.*/
	public GuiCreditsMenu guiCredits = new GuiCreditsMenu();
	public GuiCreateWorld guiCreateWorld = new GuiCreateWorld();
	/**Init the really big funcionality gui load world.*/
	public GuiLoadWorld guiLoadWorld = new GuiLoadWorld();
	public GuiOptions guiOptions = new GuiOptions();
	public GuiGlOptions guiGlOptions = new GuiGlOptions();
	/**Init the Graphics gui screen page.*/
	public GuiGraphics guiGraphics = new GuiGraphics();
	public GuiLanguage guiLanguage = new GuiLanguage();
	public GuiControls guiControls = new GuiControls();
	public GuiControlsKeyChange guiControlsChange = new GuiControlsKeyChange();
	public GuiOtherOptions guiOtherOptions = new GuiOtherOptions();
	/**List of all light in this scene.*/
	private List<Light> lights = new ArrayList<Light>();
	/**This is a main light source*/
	public Light mainLight;
	protected GuiSlider slider;
	private KeyboardUserInput keyInput = new KeyboardUserInput();
	public TextLanguage textLang = new  TextLanguage();
	public TextChat textChat = new TextChat();
	/**Prev user data before world be created.*/
	private CreatingWorld creatingWorld;
	protected GameObject profilePlayer;
	
	public MainMenuScene(Craftix craftixIn) 
	{
		super("CraftixGuiScreenScene", 0);
		this.cx = craftixIn;
		this.creatingWorld = new CreatingWorld();
	}
	
	public void loadScene(Craftix craftixIn)
	{
		this.cx = craftixIn;
		this.guiMainScreen = (GuiMainMenu) new GuiMainMenu(this.guiCredits).setLoaded(true);
		this.cx.status.setGameMenu(true);
		this.guiMainScreen.inOptionMenuPage = false;
		try 
		{
			SoundLoader.loadGameSounds();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		this.playerSkin = new PlayerSkin("skins/playerSkin4");
		this.mainMenuRenderer = new MainMenuRenderer(this.cx);
		this.skyboxPanoramaShader = new SkyboxPanoramaShader();
		this.entityInit = new EntityInit();
		this.camera = new EntityCamaraInMenu();
		this.entityInit.loadProfilePlayer(this);
		this.guiMainScreen.initGui(this.cx);
		this.guiSingleplayer.initGui(this.cx);
		this.guiMultiplayer.initGui(this.cx);
		this.guiDisconnected.initGui(this.cx);
		this.guiConnecting.initGui(this.cx);
		this.guiCreateWorld.initGui(this.cx);
		this.guiLoadWorld.initGui(this.cx);
		this.guiQuit.initGui(this.cx);
		this.guiNews.initGui(this.cx);
		this.guiProfile.initGui(this.cx);
		this.guiLinkWarning.initGui(this.cx);
		this.guiCredits.initGui(this.cx);
		this.guiOptions.initGui(this.cx);
		this.guiGlOptions.initGui(this.cx);
		this.guiGraphics.initGui(this.cx);
		this.guiLanguage.initGui(this.cx, this);
		this.guiOtherOptions.initGui(this.cx);
		this.guiControls.initGui(this.cx);
		this.guiControlsChange.initGui(this.cx);
		this.isCurrentScene = true;
		this.guiOptions.saveAllGameOptionsInit(this);
		this.textChat.loadChatWords();
		this.createProfilePlayer();
		this.playSound();
		this.cx.setIngameFocus();
	}
	
	public void updateScene()
	{
		this.otherSetup();
		
		while(!Display.isCloseRequested() && this.cx.status.isGameMenu())
		{
			this.onUpdate();
			this.guiMainScreen.getEditor().updateEditorPre();
			this.timer.setTime();
			this.cx.updateFps();
			this.cx.updateDisplay();
		}
		this.isCurrentScene = false;
		if(!this.isCurrentScene && !this.isGoToNexScene)
		{
			this.cx.closeDisplay();
			this.cx.exitController(0);
		}
		
	}
	
	private void onUpdate()
	{	
		this.keyInput.mainMenuKeysPreesedNotWhile(this.cx);
		if(this.guiScreen == null) 
		{
			try 
			{
				this.guiMainScreen.handleInput(this.cx);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
		this.mainMenuRenderer.render(this.lights, this.camera);
		this.skyboxPanoramaShader.setRotationSpeedSkybox(3f);
		this.cx.updateFramebuffer();
		if(Pages.isProfilePage)
		{
			this.skyboxPanoramaShader.setRotationSpeedSkybox(1.5f);
			this.mainMenuRenderer.renderProfilePlayer(this, this.objectsM, this.lights, this.camera);
			if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			{
				this.playerSkin.setSkin("skins/playerSkin4");
			}
			this.camera.moveCamera(0.02f, 0.02f, 0.02f, 0.02f, 0.02f, 0.02f);
			this.profilePlayer.setPosition(new Vector3f(5F, 5f, 0F));
			
		} else
		{
			//this.entityInit.profilePlayer.setRotY(-35.0f);
		}
		this.textLang.renderEnigneText(this.cx);
		this.textChat.renderChatWords();
		this.renderScreenPages();
		this.cx.guiRenderer.renderButton(this.guiScreen.buttonList, true);
		TextLoader.render();
		this.cx.renderCursor();
		this.startupEventAfterClick();
		PostEffectsInit.controllBrightness(InGameSettings.brightnessIn);
		
	}
	
	private void startupEventAfterClick()
	{
		this.guiQuit.eventAfterClick();
		this.guiSingleplayer.eventAfterClick();
		this.guiLoadWorld.eventAfterClick();
		this.guiDisconnected.eventAfterClick();
		this.guiOptions.eventAfterClick();
		this.guiCredits.eventAfterClick();
		this.guiDisconnected.clearAllPrevieusData();
	}
	
	/**
	 * Render each screen page according to the conditions.
	 */
	public void renderScreenPages()
	{
		GuiRenderer guiRenderer = this.cx.guiRenderer;
		guiRenderer.render(this.guiMainScreen.panorama);
		if(this.guiCredits.isLoaded() || Pages.isSingleplayerPage || Pages.isMultiplayerPage || Pages.isLanguagePage || Pages.isOptionsPage || Pages.isGlOptionsPage || Pages.isGraphicsPage || Pages.isOtherOptionsPage || Pages.isControlsPage || 
				Pages.isControlsKeyChangePage || Pages.isDisconnectedPage || Pages.isCreateWorldPage || Pages.isLoadWorldPage || GuiMainMenu.hasntLicensePage)
			guiRenderer.render(this.guiMainScreen.background);
		
		if(GuiMainMenu.loaded)
			guiRenderer.render(this.guiMainScreen.textures);
		if(Pages.isSingleplayerPage)
			guiRenderer.render(this.guiSingleplayer.textures);
		if(Pages.isMultiplayerPage)
			guiRenderer.render(this.guiMultiplayer.textures);
		if(this.guiCredits.isLoaded())
			guiRenderer.render(this.guiCredits.textures);
		if(Pages.isQuitPage)
			guiRenderer.render(this.guiQuit.textures);
		if(Pages.isNewsPage)
			guiRenderer.render(this.guiNews.textures);
		if(Pages.isProfilePage)
			guiRenderer.render(this.guiProfile.textures);
		if(Pages.isLinkWarning)
			guiRenderer.render(this.guiLinkWarning.textures);
		if(Pages.isOptionsPage)
			guiRenderer.render(this.guiOptions.textures);
		if(Pages.isGlOptionsPage)
			guiRenderer.render(this.guiGlOptions.textures);
		if(Pages.isGraphicsPage)
			guiRenderer.render(this.guiGraphics.textures);
		if(Pages.isLanguagePage)
			guiRenderer.render(this.guiLanguage.textures);
		if(Pages.isOtherOptionsPage)
			guiRenderer.render(this.guiOtherOptions.textures);
		if(Pages.isControlsPage)
			guiRenderer.render(this.guiControls.textures);
		if(Pages.isControlsKeyChangePage)
			guiRenderer.render(this.guiControlsChange.textures);
		if(Pages.isConnectingPage)
			guiRenderer.render(this.guiConnecting.textures);
		if(Pages.isDisconnectedPage)
			guiRenderer.render(this.guiDisconnected.textures);
		if(Pages.isCreateWorldPage)
			guiRenderer.render(this.guiCreateWorld.textures);
		if(Pages.isLoadWorldPage)
			guiRenderer.render(this.guiLoadWorld.textures);
	}
	
	public void renderCursor()
	{
		if(InGameSettings.renderCursorIn)
		{
			this.cx.guiRenderer.render(this.guiMainScreen.cursor);
			this.guiMainScreen.updateCursorPosition();
			this.cx.setIngameFocus();
		} else
			this.cx.setIngameNotInFocus();
	}
	
	@Override
	public void otherSetup() 
	{
		this.mainLight = new Light(new Vector3f(10000, 10000, -10000), new Vector3f(1.3f, 1.3f, 1.3f));
		this.lights.add(this.mainLight);
	}
	
	public void playSound()
	{
		float volume = 1f;
		if(InGameSettings.useAudioIn)
		{
			SoundLoader.sourceMenu1.setVolume(volume);
			SoundLoader.sourceMenu1.setPitch(1f);
			SoundLoader.soundPlay(SoundLoader.sourceMenu1, SoundLoader.bufferMenu1);
			
		}
	}
	
	/**
	 * Return prev data value's, before world be created.
	 */
	public CreatingWorld getCreatingWorld()
	{
		return this.creatingWorld;
	}
	
	public MainMenuRenderer getRenderer()
	{
		return this.mainMenuRenderer;
	}
	
	public void createProfilePlayer()
	{
		this.profilePlayer = new GameObject(1, "profile_entity_player", "entity_player", "skins/playerSkin4", 0F, 0F, 0F, 1F);
		this.objectsM.add(profilePlayer);
	}

}
