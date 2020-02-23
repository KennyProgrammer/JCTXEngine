package com.kenny.craftix.client.scenes;


import java.io.IOException;

import org.lwjgl.opengl.Display;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.audio.sound.SoundLoader;
import com.kenny.craftix.client.entity.EntityCamaraInMenu;

import com.kenny.craftix.client.gui.GuiAudio;
import com.kenny.craftix.client.gui.GuiCreditsMenu;
import com.kenny.craftix.client.gui.GuiGame;
import com.kenny.craftix.client.gui.GuiLanguage;
import com.kenny.craftix.client.gui.GuiLinkWarning;
import com.kenny.craftix.client.gui.GuiNews;
import com.kenny.craftix.client.gui.GuiQuit;
//import com.kenny.craftix.client.gui.GuiGraphics;
//import com.kenny.craftix.client.gui.GuiLatestUpdates;
//import com.kenny.craftix.client.gui.GuiOptionsMenu;
import com.kenny.craftix.client.gui.GuiRenderManager;
import com.kenny.craftix.client.gui.GuiScaled;
import com.kenny.craftix.client.gui.GuiScreen;
import com.kenny.craftix.client.gui.GuiSingleplayer;
import com.kenny.craftix.client.gui.GuiBackground.Pages;
import com.kenny.craftix.client.gui.button.ButtonRenderer;
import com.kenny.craftix.client.renderer.MainMenuRenderer;
import com.kenny.craftix.client.renderer.GlHelper.Framebuffer;
import com.kenny.craftix.client.renderer.postEffects.PanoramaBlur;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.client.shaders.FrameBuffer;
import com.kenny.craftix.init.EntityInit;

import com.kenny.craftix.utils.MouseHelper;
import com.kenny.craftix.world.skybox.SkyboxPanoramaShader;

public class MainMenuSceneOld extends Scene implements IReloadble
{
	/**Check if user now in main menu.*/
	public static boolean isInMainMenu;
	public boolean useRu = false;
	public boolean useEn = false;
	
	/**The Main Craftix Instance.*/
	public Craftix cx;
	/**Old initialization of the guis.*/
	private GuiGame guiGame;
	/**Initialize all texts to the game engine.*/
	public MouseHelper mouseHelper;
	/**Its a main gui-screen class and instance.*/
	private GuiScreen guiScreen;
	@SuppressWarnings("unused")
	private GuiScaled guiScaled = new GuiScaled();
	/**Load the guis of the main page menu screen.*/
	//public GuiMainMenu guiMainMenu = new GuiMainMenu();
	/**Load the gui screen with options.*/
	//public GuiOptionsMenu guiOptionsMenu = new GuiOptionsMenu();
	/**Load the gui screen with credits texts.*/
	public GuiCreditsMenu guiCreditsMenu = new GuiCreditsMenu();
	/**Load the gui screen with singleplayer texts.*/
	public GuiSingleplayer guiSingleplayerMenu = new GuiSingleplayer();
	/**Load the gui screen with language texts.*/
	public GuiLanguage guiLanguageMenu = new GuiLanguage();
	/**Load the gui screen with graphics texts.*/
	//public GuiGraphics guiGraphics = new GuiGraphics();
	/**Load the gui screen with audio texts.*/
	public GuiAudio guiAudio = new GuiAudio();
	//public GuiLatestUpdates guiLatestUpdates = new GuiLatestUpdates();
	public GuiNews guiEngineInfio = new GuiNews();
	public GuiLinkWarning guiWarning = new GuiLinkWarning();
	public GuiQuit guiQuit = new GuiQuit();
	public boolean loadText;
	public MainMenuRenderer panoramaRenderer;
	public EntityInit entityInit;
	public SkyboxPanoramaShader skyboxPanoramaShader;
	/**Audio Master's components.*/
	public float volume;
	public EntityCamaraInMenu camera;
	/**Multisamle the framebuffer for post-proccessing effects.*/
	public static FrameBuffer multisampleFrameBuffer;
	/**Outputs the converted frame buffer.*/
	public static FrameBuffer outputFrameBuffer;
	public static FrameBuffer outputFrameBuffer2;
	public ButtonRenderer buttonRenderer;
	public GuiRenderManager guiManager = new GuiRenderManager();
	
	public MainMenuSceneOld() 
	{
		super("MainMenuScene", 4000f);
	}
	
	@Override
	public void loadScene(Craftix cxIn) 
	{
		this.cx = cxIn;
		this.buttonRenderer = new ButtonRenderer();
		multisampleFrameBuffer = new FrameBuffer(Display.getWidth(), Display.getHeight());
		outputFrameBuffer = new FrameBuffer(Display.getWidth(), Display.getHeight(), FrameBuffer.DEPTH_TEXTURE);
		outputFrameBuffer2 = new FrameBuffer(Display.getWidth(), Display.getHeight(), FrameBuffer.DEPTH_TEXTURE);
		GuiRenderManager.renderGame = false;
		this.loadText = true;
		try 
		{
			SoundLoader.loadGameSounds();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		this.mouseHelper = new MouseHelper();
		//this.panoramaRenderer = new MainMenuRenderer(this.cx.cxLoader);
		this.entityInit = new EntityInit();
		this.guiScreen = new GuiScreen();
		this.skyboxPanoramaShader = new SkyboxPanoramaShader();
		this.guiGame = new GuiGame();
		//TextMaster.textMenu = true;
		this.camera = new EntityCamaraInMenu();
		PanoramaBlur.init(this.cx.cxLoader);
		this.entityInit.loadMainCraftixBox();
		//this.cx.getTextInit().loadEngineText(this.cx);
		this.guiScreen.drawBackground(this.cx);
		//this.guiMainMenu.initGui(this.cx);
		//this.guiQuit.loadQuitScreen(this.cx);
		//this.guiOptionsMenu.loadOptionsMenuScreen(this.cx, this);
		//this.guiEngineInfio.loadEngineInfoScreen(this.cx);
		//this.guiLatestUpdates.loadLatestUpdatesScreen(this.cx);
		//this.guiGraphics.loadGraphicsScreen(this.cx);
		this.guiAudio.loadAudioScreen(this.cx, this);
		//this.guiLanguageMenu.loadLanguageScreen(this.cx, this);
		//this.guiCreditsMenu.loadCreditsMenuScreen(this.cx);
		//.guiSingleplayerMenu.loadSingleplayerScreen(this.cx, this);
		//this.guiWarning.loadLinkWarningScreen(this.cx);
		//GuiRenderManager.renderGameOver = false;
		this.playSound();
	}
	
	@Override
	public void reloadScene() 
	{
		
	}
	
	public void checkOnClick(MainMenuSceneOld scene)
	{
		scene.cx.checkOnWork();
	}

	@Override
	public void renderScene() 
	{
		if(this.guiScreen == null) 
		{
			//try 
			//{
			//	//this.guiMainMenu.handleInput();
			//} 
			//catch (IOException e) 
			//{
			//	e.printStackTrace();
			//}
		}
		
		//this.panoramaRenderer.renderMainMenu(camera);
		this.skyboxPanoramaShader.setRotationSpeedSkybox(2f);
		multisampleFrameBuffer.bindFrameBuffer();
		multisampleFrameBuffer.unbindFrameBuffer();
		multisampleFrameBuffer.resolveToFrameBuffer(Framebuffer.COLOR_ATTACHMENT0, outputFrameBuffer);
		multisampleFrameBuffer.resolveToFrameBuffer(Framebuffer.COLOR_ATTACHMENT1, outputFrameBuffer2);
		PanoramaBlur.doPanoramaBlur(outputFrameBuffer.getColourTexture());
	
		//TextDraw.renderStates();
		this.cx.guiRenderer.render(this.guiGame.guisGameButtons);
		this.guiScreen.renderBackground(this.cx);
		//this.cx.guiRenderer.render(this.guiMainMenu.guisPanoramaBackgrond);
		//this.guiMainMenu.renderScreen();
		
		
		this.renderMainMenuTrigger();
	
		if(GuiRenderManager.renderLanguageMenu)
		{
			//this.guiLanguageMenu.updateButtons();
			//this.cx.guiRenderer.render(this.guiLanguageMenu.guisLanguageBackground);
			this.cx.guiRenderer.render(this.guiLanguageMenu.guisLanguageButtons);
		}
		
		this.guiScreen.updateGuiScale();
		//TextMaster.renderEngineText();
		this.update();
	}
	
	@Override
	public void update() 
	{
		this.guiScreen.onUpdate();
	}

	public void selectRussianLang()
	{
		this.useEn = false;
		this.useRu = true;
		if(useRu)
		{
			//TextMaster.textMenu = false;
			//TextInit textInit = new TextInit();
			//textInit.loadDefaultFonts();
			//textInit.ruRu.loadRuLocalization();
			//TextMaster.textMenu = true;
			//Craftix.LOGGER.info("Language set on: Russian!" );
		}
		else 
		{
			;
		}
	}

	
	public void selectEnglishLang()
	{
		this.useRu = false;
		this.useEn = true;
		if(useEn)
		{
			//TextMaster.textMenu = false;
			
			//TextInit textInit = new TextInit();
			//textInit.loadDefaultFonts();
			//textInit.usEn.loadUsEnLocalization();
			//TextMaster.textMenu = true;
			Craftix.LOGGER.info("Language set on: English!" );
		}
		else
		{
			;
		}
	}

	
	@Override
	public void otherSetup() 
	{
	}
	
	public void playSound()
	{
		this.volume = 1f;
		if(InGameSettings.useAudioIn)
		{
			SoundLoader.sourceMenu1.setVolume(volume);
			SoundLoader.sourceMenu1.setPitch(1f);
			SoundLoader.soundPlay(SoundLoader.sourceMenu1, SoundLoader.bufferMenu1);
			
		}
	}
	
	public void increasePlayingSound()
	{
		this.volume = volume + 0.1f;
	}
	
	public void decreasePlayingSound()
	{
		this.volume--;
	}

	@Override
	public void cleanUpScene() 
	{
		//this.cx.getTextRenderer().cleanUp();
		//TextMaster.cleanUp();
		this.cx.guiRenderer.cleanUp();
		SoundLoader.cleanUpSounds();
	}


	public GuiScreen getGuiScreen() 
	{
		return guiScreen;
	}
	
	public Craftix getCraftix()
	{
		return this.cx;
	}
	
	public void renderMainMenuTrigger()
	{	
		
		//==========
		
		if(Pages.isMainMenuPage)
		{
			//this.cx.guiRenderer.render(this.guiMainMenu.textures);
		}
		
		//==========
		
		
		
		if(GuiRenderManager.renderMainMenu)
		{
			//this.cx.guiRenderer.render(this.guiMainMenu.guisBackground);
			////this.guiMainMenu.renderScreen();
			//if(GuiRenderManager.renderLinkWarning && TextInit.initLinkPage)
			//{
				//this.cx.guiRenderer.render(this.guiWarning.guisBackground);
				//this.guiWarning.renderScreen();
			//}
			
		}
		if(GuiRenderManager.renderSingleplayerMenu)
		{
			//this.cx.guiRenderer.render(this.guiSingleplayerMenu.guisBackground);
			this.guiSingleplayerMenu.renderScreen();
		}
		if(GuiRenderManager.renderOptionsMenu)
		{
			//this.guiOptionsMenu.updateButtons();
			//this.cx.guiRenderer.render(this.guiOptionsMenu.guisBackground);
			////this.cx.guiRenderer.render(this.guiOptionsMenu.guisMenuOptionsButtons);
			//this.guiOptionsMenu.renderScreen();
		}
		if(GuiRenderManager.renderAudioMenu)
		{
			this.guiAudio.updateButtons();
			this.guiAudio.renderScreen();
			this.cx.guiRenderer.render(this.guiAudio.guisBackground);
			this.cx.guiRenderer.render(this.guiAudio.guisAudioButtons);
		}
		if(GuiRenderManager.renderGraphicMenu)
		{
			//this.guiGraphics.updateButtons();
			//this.cx.guiRenderer.render(this.guiGraphics.guisBackground);
			//this.cx.guiRenderer.render(this.guiGraphics.guisGraphicsButtons);
		}
		if(GuiRenderManager.renderCreditsMenu)
		{
			//this.cx.guiRenderer.render(this.guiCreditsMenu.guisBackground);
			this.guiCreditsMenu.renderScreen();
		}
		if(GuiRenderManager.renderEngineInfo)
		{
			//this.cx.guiRenderer.render(this.guiEngineInfio.guisBackground);
			this.guiEngineInfio.renderScreen();
		}
		if(GuiRenderManager.renderUpdatesMenu)
		{
			//this.cx.guiRenderer.render(this.guiLatestUpdates.guisBackground);
			//this.guiLatestUpdates.renderScreen();
		}
		if(GuiRenderManager.renderQuit)
		{
			//this.cx.guiRenderer.render(this.guiQuit.guisBackground);
			this.guiQuit.renderScreen();
		}
	}


}
