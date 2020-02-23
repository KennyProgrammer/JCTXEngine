package com.kenny.craftix.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.util.Log;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.kenny.craftix.client.audio.AudioMaster;
import com.kenny.craftix.client.gui.GuiAdder;
import com.kenny.craftix.client.gui.GuiBackground;
import com.kenny.craftix.client.gui.GuiInGameMenu;
import com.kenny.craftix.client.gui.GuiLoadingSplash;
import com.kenny.craftix.client.gui.GuiMainMenu;
import com.kenny.craftix.client.gui.GuiRenderer;
import com.kenny.craftix.client.gui.GuiScale;
import com.kenny.craftix.client.gui.GuiSingleplayer;
import com.kenny.craftix.client.gui.GuiBackground.Pages;
import com.kenny.craftix.client.gui.button.ButtonRenderer;
import com.kenny.craftix.client.language.Language;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.loader.PngDecoder;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.GlHelperError;
import com.kenny.craftix.client.renderer.GlHelper.Framebuffer;
import com.kenny.craftix.client.renderer.postEffects.PostProcessing;
import com.kenny.craftix.client.renderer.textures.TextureManager;
import com.kenny.craftix.client.resources.StillWorking;
import com.kenny.craftix.client.scenes.LoadingScreen;
import com.kenny.craftix.client.scenes.MainMenuSceneOld;
import com.kenny.craftix.client.scenes.MainMenuScene;
import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.client.settings.KeyBinding;
import com.kenny.craftix.client.settings.Session;
import com.kenny.craftix.client.shaders.FrameBuffer;
import com.kenny.craftix.client.text.Text;
import com.kenny.craftix.client.text.TextChat;
import com.kenny.craftix.client.text.TextLanguage;
import com.kenny.craftix.client.text.TextLoader;
import com.kenny.craftix.client.text.render.FontRenderer;
import com.kenny.craftix.gameplay.GameStatus;
import com.kenny.craftix.main.GameConfiguration;
import com.kenny.craftix.main.Side;
import com.kenny.craftix.main.SideMachine;
import com.kenny.craftix.server.CraftixMP;
import com.kenny.craftix.utils.KennyCopyright;
import com.kenny.craftix.utils.MouseHelper;
import com.kenny.craftix.utils.Timer;
import com.kenny.craftix.utils.UserProfileManager;
import com.kenny.craftix.utils.Util;
import com.kenny.craftix.utils.crash.Crash;
import com.kenny.craftix.utils.data.DataFixManager;
import com.kenny.craftix.utils.data.DataFixer;
import com.kenny.launcher.UsernameManager;

/**
 * @author Kenny and @KennyCopyrightTeam
 */
@KennyCopyright
@SideMachine(Side.CLIENT)
public class Craftix 
{
	/**Main Game Engine Configuration. Title, Version etc...*/
	protected static final String TITLE = "Craftix";
	protected static final String TITLE_DEMO = "Craftix Demo";
	protected static final String VERSION = " 0.0.19 - Editor Beta";
	protected static final String VERSION_TYPE = "Alpha";
	/**Called in other classes where logging is required.*/
	public static final String LOGGER_INSTANCE = "Craftix";
	public static final Logger LOGGER = LogManager.getLogger(LOGGER_INSTANCE);
	/**Something happens it your run engine on Mac OS.*/
	public static final boolean IS_RUNNING_ON_MAC = Util.getOSType() == Util.EnumOS.OSX;
	/**Available screen resolutions on the Mac OS.*/
	private static final List<DisplayMode> MAC_DISPLAY_MODES = Lists.newArrayList
			(new DisplayMode(2560, 1600), 
					new DisplayMode(2880, 1800));
	public String craftixFolder = ".craftix";
	public String assetsFolder = "assets/";
	/**
	 * Main Pre-Initialization folders, resoloution from Game Configuration.
	 */
	/**Assets folder file. (Its not assets folder in jar file. Its folder for custom assets.)*/
	private final File fileAssets;
	/**Resourcepacks folder file.*/
	private final File fileResourcepacks;
	private final String engineVersion;
	/**Type of engine version. 'Alpha, Beta, Release'*/
	private final String engineVersionType;
	private final Session engineSession;
	/**If its demo that means its just demonstraction futures, or new stuff.*/
	private final boolean isDemoVersion;
	/**Close the game engine when user click on exit display bar.*/
	private boolean ON_EXIT_CLOSE;
	/**Checks for the pause stage. If the game is paused, some rendering moments stop.*/
	public  boolean isGamePause;
	private boolean jvm64bit;
	/**Checks when the game engine is running.*/
	private boolean isRunning;
	private boolean isOutMemoryRunning;
	public File dataFile;
	/**Location of the engine options file.*/
	private File optionsFile;
	private File userdataFile;
	/**Location of lang's localization files.*/
	private File langEnFile;
	private File langRuFile;
	/**Load the language the the engine.*/
	private Language language;
	public InGameSettings inGameSettings;
	/**Instal instance crash manager.*/
	public Crash crash = new Crash();
	/**Folder '.craftix' with files and options.*/
	public File cxDataDir;
	private final DataFixer dataFixer;
	private GameConfiguration gameConfiguration;
	public boolean fullscreen;
	 /**Vertical Syncroniztion for a fullscreen mode window.*/
    public boolean vSync;
    private TextureManager textureManager;
	/**
	 * This resolution of the screen.
	 */
	public int displayWidth;
	public int displayHeight;
	public LoadingScreen loading;
	long systemTime = getCurrentTime();
	private Timer timer = new Timer();
	public MouseHelper mouseHelper = new MouseHelper();
	public GameStatus status = new GameStatus();
	/**Main Menu Scene.*/
	public MainMenuSceneOld mainMenu;
	/**World scene.*/
	private WorldScene worldScene;
	//private boolean isInMenuScene = true;
	/**Check if user in the loaded world.*/
	//private boolean isInWorldScene = false;
	public boolean quitGameScene = false;
	public boolean backToMenu = false;
	/**Does the actual gameplay have focus. If so then mouse and keys will effect the player instead of menus. */
	private boolean inGameHasFocus;
	/**Core of the game engine. Help load textures, models, and same on..*/
	public Loader cxLoader;
	/**Initialize all text in game enigne.*/
	/**Render all characters and effect on text with shader and same text.*/
	private FontRenderer textRenderer;
	/**Render the guis into the game engine.*/
	public GuiRenderer guiRenderer;
	public ButtonRenderer buttonRenderer;
	/**This main instance of the 'Craftix' class.*/
	public static Craftix* INSTANCE;
	private GameConfiguration gameConfig;
	public UsernameManager usernameManager;
	public GuiAdder guiAdder = new GuiAdder();
	public GuiLoadingSplash guiLoading = new GuiLoadingSplash();
	/**Init the Mainmenu gui screen page.*/
	public GuiMainMenu guiMainScreen = new GuiMainMenu(null);
	/**New Main Menu Scene.*/
	public MainMenuScene menuScene;
	/**The Gui Screen Object.*/
	public GuiBackground guiScreen;
	public TextLanguage textLang = new TextLanguage();
	public TextChat textChat = new TextChat();
	/**Ratio of the screen. (Width / Height)*/
	private float aspectRatio;
	private FrameBuffer msFrameBuffer, frameBufferCx, frameBufferCx2;
	protected GuiScale guiScale;
	public UserProfileManager userProfile;
	/**Now all info about client you can get with ClientGetterInformation.*/
	public ClientGetterInformation clientInfo;
	/**Initialise a server side of the engine.*/
	private CraftixMP craftixMp;
	public Exception exError;
	
	public Craftix(GameConfiguration gameConfiguration)
	{
		INSTANCE = this;
		this.gameConfig = gameConfiguration;
		this.cxDataDir = gameConfiguration.folderInfo.cxDataDir;
		this.fileAssets = gameConfiguration.folderInfo.assetsDir;
		this.fileResourcepacks = gameConfiguration.folderInfo.resourcepackDir;
		this.engineVersion = gameConfiguration.engineInfo.engineVersion;
		this.engineVersionType = gameConfiguration.engineInfo.typeOfVersion;
		this.isDemoVersion = gameConfiguration.engineInfo.isDemo;
		this.engineSession = gameConfiguration.userInfo.session;
		this.displayWidth = gameConfiguration.displayInfo.displayWidth > 0 ?
				gameConfiguration.displayInfo.displayWidth : 1;
		this.displayHeight = gameConfiguration.displayInfo.displayHeight > 0 ?
				gameConfiguration.displayInfo.displayHeight : 1;
		this.fullscreen = gameConfiguration.displayInfo.fullscreen;
		ClientGetterInformation clientGetterInformation = new ClientGetterInformation(TITLE, TITLE_DEMO, VERSION, VERSION_TYPE);
		this.clientInfo = clientGetterInformation;
		Locale.setDefault(Locale.ROOT);
		ResourceLocation.setCraftixProfileFolder();
		ImageIO.setUseCache(false);
		this.dataFixer = DataFixManager.createFixer();
		this.userdataFile = new File(ResourceLocation.CRAFTIX_FOLDER);
		this.usernameManager = new UsernameManager(this, this.userdataFile);
		this.cxLoader = new Loader();
		this.loading = new LoadingScreen();
		this.mainMenu = new MainMenuSceneOld();
		this.menuScene = new MainMenuScene(this);
		this.worldScene = new WorldScene();
		CraftixMP craftixServer = new CraftixMP(this);
		this.craftixMp = craftixServer;
	}
	
	/***
	* Run the hole game engine. Call super classes and load the scenes.
	 * @throws LWJGLException 
	*/
	public void run() throws LWJGLException
	{
		this.isRunning = true;
		
		try
		{
			if(this.isRunning())
			{
				this.init();
			}
			else
			{
				this.shutdown(0);
			}
		}
		catch(Exception e)
		{
			this.crash.crashManager(1);
			LOGGER.error("Failed load the game engine!");
			e.printStackTrace();
			this.shutdown(-1);
		}
		
		while(true)
		{
			if(!this.isRunning() && this.isOutMemoryRunning)
			{
				try
				{
					LOGGER.info("Run client on small components, because you computer is out of memory!");
				}
				catch(OutOfMemoryError noMemoryE)
				{
					LOGGER.fatal("Crash occurred due to lack of memory on the computer.");
					noMemoryE.printStackTrace();
					System.gc();
				}
			}
		}
	}
	
	/**
	 * Load the game: Initialization: Loading the main menu scene-screen.
	 * @throws LWJGLException
	 */
	private void init() throws LWJGLException, IOException
	{
		try
		{
			LOGGER.info("============= Craftix Info =============");
			this.langEnFile = new File(ResourceLocation.CRAFTIX_FOLDER);
			this.langRuFile = new File(ResourceLocation.CRAFTIX_FOLDER);
			this.optionsFile = new File(ResourceLocation.CRAFTIX_FOLDER);
			LOGGER.info("Current User: {}", (Object)this.engineSession.getUsername());
			LOGGER.info("LWJGL Version: " + (Object)Sys.getVersion());
			LOGGER.info("System 64 bit: " + (Object)Sys.is64Bit());
			LOGGER.info("Game resolution: " + InGameSettings.displayWidthIn + " x " + InGameSettings.displayHeightIn);
			this.getDateToString();
			this.isJvm64bit();
			this.startTimerHackThread();
			this.disablePngDecoderInfo();
			this.inGameSettings = new InGameSettings(this, this.optionsFile);
			this.language = new Language(this, this.langEnFile, this.langRuFile);
			this.setDisplayIcon();
			this.initDisplayMode();
			this.createDisplay();
			this.guiScale = new GuiScale();
			GlHelperError.initOpenGlErrorManager();
			this.checkGLError("Pre startup");
			GlHelper.enableTexture2d();
			this.guiRenderer = new GuiRenderer(this);
			this.buttonRenderer = new ButtonRenderer();
			this.textRenderer = new FontRenderer();
			LOGGER.info("Game options configuration succes loading.");
			this.msFrameBuffer = new FrameBuffer(Display.getWidth(), Display.getHeight());
			this.frameBufferCx = new FrameBuffer(Display.getWidth(), Display.getHeight(), FrameBuffer.DEPTH_TEXTURE);
			this.frameBufferCx2 = new FrameBuffer(Display.getWidth(), Display.getHeight(), FrameBuffer.DEPTH_TEXTURE);
			LOGGER.info("Frame Buffer Object set on display resolution.");
			TextLoader.init();
			this.loading.updateLoadingScreen(this);
			this.inGameSettings.setMultisampleOption();
			for (int j = 0; j < Math.min(10, this.timer.elapsedTicks); ++j)
		    {
		        this.runTick();
		    }
			this.loadDefaultLanguage();
			AudioMaster.init();
			this.guiAdder.guiInit(this);
			this.textureManager = new TextureManager();
			this.setResizeble(true);
			this.language.loadLanguageFile();
			this.textLang.loadEnigneText(this);
			this.sceneLoader();
			
		}
		catch(Exception e)
		{
			this.exError = e;
			LOGGER.error("Can't initialize the game engine! Check the stack trace.");
			this.crash.crashOverhead(this);
			this.setDisplayParameters(960, 540, false, false);
			GuiMainMenu m = new GuiMainMenu(null);
			m.initGui(this);
			Text t = new Text("Game Crash!!!", 0.2F, 0.2F, true);
			Text t_link = null, t_exception = null;
			if(!Crash.crashModelNotFound)
			{
				t_link = new Text("vk group", -0.003F, 0.498F, true); t_link.setColour(1, 0, 0);
				t_exception = new Text("Java Error: " +  e.toString() + "\n" + " - Game main .craftix folder not found! Check if"
						+ "  have it on disk C. If not download it for vk group. And then just put this .craftix folder on disc C on "
						+ " computer.", 0.2F, 0.4F, true);
			}
			else if(e.toString() == "java.lang.IllegalStateException: Zero length vector")
			{
				t_exception = new Text("Java Error: " +  e.toString() + "\n" + " - This error comes out because some object was not called at the right"
						+ " time. Here it is recommended to find the root of the problem by stack trace. And if next to the error is the inscription "
						, 0.2F, 0.4F, true); t_link = new Text("", -0.003F, 0.498F, true);
			} else {
				t_exception = new Text("Java Error: " +  e.toString() + "\n" + " - Model file not found. If it happens in Eclipse IDE, then just refresh project." 
			+ " Else check resources folder on availability model .obj file.", 0.2F, 0.4F, true); t_link = new Text("", -0.003F, 0.498F, true);
			}
			TextLoader.addText(t); TextLoader.addText(t_link);
			TextLoader.addText(t_exception);
			Pages.cleanUp();
			while(!Display.isCloseRequested())
			{
				this.updateFramebuffer();
				this.guiRenderer.render(m.background);
				TextLoader.render();
				t.r(true); t_link.r(true);
				t_exception.r(true);
				Display.update();
				while(Mouse.next())
				{
					if(Mouse.getX() >= 243 && Mouse.getX() <= 326 && Mouse.getY() >= 253 && Mouse.getY() <= 273 && Mouse.isButtonDown(0))
						this.guiLoading.isClickedOnURI(true, 21022003);
				}
			}
			
			this.destroyLwjglContexts(true, true);
			this.exitController(-1);
			
		}
	}
	
	/**
	 * Loads scenes in the correct order and if necessary continues from the desired point.
	 * @throws LWJGLException 
	 */
	private void sceneLoader() throws LWJGLException
	{
		this.worldScene.gamePause = false;
		this.status.setGameWorld(false);
		this.status.setGameMenu(true);
		this.backToMenu = false; 
		this.quitGameScene = false;
		if(this.status.isGameMenu() && !this.status.isGameWorld() && !this.status.isEditor())
		{
			this.menuScene.loadScene(this);
			this.loading.removeAfterUpdate();
			try 
			{
				this.menuScene.updateScene();
			} 
			catch (Exception e) 
			{
				this.crash.crashManager(2);
				this.saveAllGameOptions();
				LOGGER.error("Display in main menu not start updating.");
				e.printStackTrace();
			}
			
			if(!this.status.isGameWorld() && this.status.isGameMenu())
			{
				this.closeDisplay();
			}
			
		}
		if(this.status.isGameWorld() && !this.status.isGameMenu())
		{
			//this.guiLoading.initGui(this);
			this.checkGLError("World Loading");
			this.worldScene.loadScene(this);
			this.updateGameDisplay();
			this.saveAllGameOptions();
			this.isGamePause = false;
			this.quitTheScene();
			
			if(!this.status.isGameMenu())
			{
				this.closeDisplay();
			}
		}
		this.saveAllGameOptions();
		
	}
	
	public void sceneReloader() throws LWJGLException
	{
		this.worldScene.gamePause = false;
		this.status.setGameWorld(false);
		this.status.setGameMenu(true);
		this.backToMenu = false; 
		this.quitGameScene = false;
		
		if(this.status.isGameMenu())
		{
			try
			{
				this.menuScene.updateScene();
			}
			catch (Exception e)
			{
				LOGGER.error("An error occurred while reloading the scene.");
				e.printStackTrace();
			}
		}
		if(this.status.isGameWorld() && !this.status.isGameMenu())
		{
			Pages.isMainGamePausePage = false;
			this.worldScene.reloadScene();
			this.updateGameDisplay();
			this.worldScene.cleanUpScene();
			this.saveAllGameOptions();
			this.isGamePause = false;
			this.quitTheScene();
			
			if(!this.status.isGameMenu())
			{
				this.closeDisplay();
			}
		}
		this.saveAllGameOptions();
		
	}
	
	/**
	 * Create a display with use LWJGL. Load title, version and other parameters.
	 * @throws LWJGLException 
	 */
	private void createDisplay() throws IOException, LWJGLException
	{
		ContextAttribs attribs = new ContextAttribs(3, 3);
		attribs.withForwardCompatible(true);
		attribs.withProfileCore(true);
		
		try 
		{	
			LOGGER.info("Creating Lwjgl contexts...");
			this.setTitle(this.getTitle(), this.engineVersion);
			this.setResizeble(false);
			Display.create(new PixelFormat().withDepthBits(24), attribs);
			GlHelper.enableMultisample();
		} 
		catch (LWJGLException lwgjlE) 
		{
			this.crash.crashManager(10);
			this.saveAllGameOptions();
			LOGGER.fatal("Cannot create a display", (Throwable)lwgjlE);
			
			try 
			{
				Thread.sleep(1000L);
			} 
			catch (InterruptedException var3) 
			{
				var3.printStackTrace();
			}
			
			if(this.fullscreen)
			{
				this.updateDisplayMode();
			}
			
			Display.create();
		
		}
		
		GlHelper.glViewport(0, 0, this.getDisplayWidth(), this.getDisplayHeight());
		this.timer.initTime();
	} 
	
	/**
	 * Initialization the display mode.
	 */
	protected void initDisplayMode() throws LWJGLException
	{
		this.displayWidth = InGameSettings.displayWidthIn;
		this.displayHeight = InGameSettings.displayHeightIn;
		this.fullscreen = InGameSettings.useFullscreenIn;
		
		if(this.fullscreen)
		{
			this.setFullscreen(true);
			DisplayMode displayMode = Display.getDisplayMode();
			this.displayWidth = Math.max(1, displayMode.getWidth());
			this.displayHeight = Math.max(1, displayMode.getHeight());
		}
		else
		{
			this.setDisplayParameters(this.displayWidth, this.displayHeight, false, true);
			Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));
		}
		this.aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
	}
	
	private void updateDisplayMode() throws LWJGLException
	{
		Set<DisplayMode> set = Sets.<DisplayMode>newHashSet();
		Collections.addAll(set, Display.getAvailableDisplayModes());
		DisplayMode displayMode = Display.getDesktopDisplayMode();
		
		if(!set.contains(displayMode) && Util.getOSType() == Util.EnumOS.OSX)
		{
			label52:
				
			for(DisplayMode displayMode1 : MAC_DISPLAY_MODES)	
			{
				boolean flag = true;
				
				for (DisplayMode displayMode2 : set)
				{
					if(displayMode2.getBitsPerPixel() == 32 
							&& displayMode2.getWidth() == displayMode1.getWidth() && 
							displayMode2.getHeight() == displayMode1.getHeight())
					{
						flag = false;
						break;
					}
				}
				
				if(!flag)
				{
					@SuppressWarnings("rawtypes")
					Iterator iterator = set.iterator();
					DisplayMode displayMode3;
					
					while(true)
					{
						if(!iterator.hasNext())
						{
							continue label52;
						}
						
						displayMode3 = (DisplayMode)iterator.next();
						
						if (displayMode3.getBitsPerPixel() == 32 && 
								displayMode3.getWidth() == displayMode1.getWidth() / 2 && 
								displayMode3.getHeight() == displayMode1.getHeight() / 2)
						{
							break;
						}
					}
					
					displayMode = displayMode3;
				}
			}
		}
		
		Display.setDisplayMode(displayMode);
		this.displayWidth = displayMode.getWidth();
		this.displayHeight = displayMode.getHeight();
	}
	
	/**
	 * Controlls the display mode when user click on button in the game options menu.
	 * Set the fullscreen mode enable or disable.
	 * @throws LWJGLException
	 */
	public void initDisplayMode(int width, int height, boolean fullscreen) throws LWJGLException
	{
        if ((Display.getDisplayMode().getWidth() == width) && 
        		(Display.getDisplayMode().getHeight() == height) &&
        		(Display.isFullscreen() == fullscreen)) 
        		{
        			return;
        		}
        try
        {
            DisplayMode targetDisplayMode = null;
             
            if (fullscreen) 
            {
            	InGameSettings.enableVsync();
            	DisplayMode[] fullscreenModes = Display.getAvailableDisplayModes();
                int freq = 0;
                 
                for (int i = 0; i < fullscreenModes.length; i++) 
                {
                    DisplayMode current = fullscreenModes[i];
                     
                    if ((current.getWidth() == width) && (current.getHeight() == height)) 
                    {
                        if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) 
                        {
                            if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) 
                            {
                                targetDisplayMode = current;
                                freq = targetDisplayMode.getFrequency();
                            }
                        }
 
                        if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
                            (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) 
                        {
                            targetDisplayMode = current;
                            break;
                        }
                    }
                }
            } 
            else 
            {
            	InGameSettings.disableVsync();
                targetDisplayMode = new DisplayMode(width, height);
            }
            if (targetDisplayMode == null)
            {
            	Display.setDisplayMode(new DisplayMode(1600, 900));
            	LOGGER.info("Failed to find value mode: " + width + "x" + height + " Fullscreen = " + fullscreen);
            	LOGGER.info("Display set to default resolution: 1600 x 900.");
                return;
            }
            Display.setDisplayMode(targetDisplayMode);
            Display.setFullscreen(fullscreen);
            Display.setResizable(false);
             
        } 
        catch (LWJGLException e) 
        {
        	this.crash.crashManager(10);
            LOGGER.info("Unable to setup mode: " + width + "x" + height + " fullscreen = " + fullscreen + e);
        }
    }

	
	/**
	 * Standard update display method.
	 */
	public void updateDisplay()
	{
		Display.update();
		Display.sync(getCurrentFps());
	}
	
	/**
	 * Update when user in game menu. Render stuff in a game menu (Main page, options, 
	 * framebuffers, etc...)
	 */
	public void updateMainMenu() throws LWJGLException
	{
		this.setResizeble(InGameSettings.resizeDisplayIn);
		
		while(!Display.isCloseRequested() && !this.status.isGameWorld())
		{
			this.mainMenu.renderScene();
			this.updateFps();
			this.controllFullscreenMode();
			this.updateDisplay();
			
			if(!this.isRunning() && Display.isCloseRequested())
			{
				this.mainMenu.cleanUpScene();
			}
		}
	}
	
	public void updateSaveWorldDisplay()
	{
		boolean show = true;
		this.guiLoading.initGui(this);
		
		while(!Display.isCloseRequested() && show)
		{
			this.updateFramebuffer();
			if(Pages.isSaveWorldPage)
				this.guiRenderer.render(guiLoading.texturesSave);
			this.textLang.renderEnigneText(this);
			TextLoader.render();
			this.timer.setTime();
			this.updateDisplay();
			Pages.isSaveWorldPage = false;
			show = false;
		}
	}
	
	/**
	 * Update the loading splash screen beetween main menu and created world.
	 */
	public void updateLoadingSplashDisplay()
	{
		Pages.isLoadingWorldPage = true;
		boolean showLoadingScreen = true;

		if(Pages.isLoadingWorldPage)
		{
			this.guiRenderer.render(guiLoading.textures);
			Pages.isSingleplayerPage = false;
			GuiSingleplayer.removePage();
		
			if(GuiLoadingSplash.progress75)
			{
				Pages.isGameOverPage = false;
			}
			
		}
		
		
		while(!Display.isCloseRequested() && showLoadingScreen)
		{
			this.textLang.renderEnigneText(this);
			TextLoader.render();
			this.timer.setTime();
			this.updateFps();
			this.updateDisplay();
			if(GuiLoadingSplash.progress75 == false && GuiLoadingSplash.progress50 == false 
					&& GuiLoadingSplash.progress25 == false)
			{
				Pages.isLoadingWorldPage = false;
			}
			showLoadingScreen = false;
		}
	}
	
	/**
	 * Update a display each frame.
	 */
	public void updateGameDisplay()
	{
		while(!Display.isCloseRequested() && !this.quitGameScene)
		{
			Pages.isLoadingWorldPage = false;
			this.textLang.renderEnigneText(this);
			this.textChat.renderChatWords();
			this.rendererGameLoop();
			this.timer.setTime();
			this.updateFps();
			this.updateDisplay();
			isGamePause = false;
		}
	
		if(!this.isRunning() && this.status.isGameWorld())
		{
			this.worldScene.cleanUpScene();
		}
		
	}
	
	/***
	 * Render entire a game engine. Render world, guis, entities.
	 */
	public void rendererGameLoop()
	{
		this.worldScene.renderScene();
	}
	
	/**
	 * Destroy display and exit thread.
	 */
	public void closeDisplay()
	{
		LOGGER.info("Clean-Up all framebuffers.");
		if(this.status.isGameWorld())
		{
			this.worldScene.getRenderer().cleanUp();
		} 
		else if(this.status.isGameMenu())
		{
			this.menuScene.getRenderer().cleanUp();
		}
		AudioMaster.shutdownAudioMaster();
		this.destroyLwjglContexts(true, true);
		this.shutdown(0);
	}
	
	/**
	 * Render the mouse in game cursor to the screen.
	 */
	public void renderCursor()
	{
		if(InGameSettings.renderCursorIn)
		{
			this.guiRenderer.render(this.menuScene.guiMainScreen.cursor);
			this.menuScene.guiMainScreen.updateCursorPosition();
			this.setIngameFocus();
		} else
			this.setIngameNotInFocus();
	}
	
	/**
	 * Render all post-proccessing effects.(Blur, contrast, etc)
	 * This method affects the overall loading of all post-effects. If you disable this method, 
	 * the boolean's in "PostEffectsInit" will not work. You can disable this method if
	 * you used "multisampleFramebuffer.resloveToScreen".
	 */
	public  void renderPostProcessingEffects()
	{
		if(InGameSettings.usePostEffectsIn)
		PostProcessing.doPostProcessing(this.getFramebuffer01().getColourTexture()); 
	
		else
		{
			//LOGGER.info("All post-proccessing effect has be disabled. And not load!");
		}
	}
	
	/**
	 * Set window icon on the display on top of application and on windows start panel.
	 */
	@StillWorking
	private void setDisplayIcon()
	{
		try 
		{
			Display.setIcon(new ByteBuffer[] 
			{
				loadIcon(getClass().getResource(ResourceLocation.LOGO_FOLDER + "logo16x16.png")),
				loadIcon(getClass().getResource(ResourceLocation.LOGO_FOLDER + "logo32x32.png")),
			});
		} 
		catch (IOException e) 
		{
			LOGGER.error("Can't load that display icon!");
			e.printStackTrace();
		}
	}
	
	private static ByteBuffer loadIcon(URL url) throws IOException 
	{
	    InputStream is = url.openStream();
	    try 
	    {
	    	PngDecoder decoder = new PngDecoder(is);
	        ByteBuffer bb = ByteBuffer.allocateDirect(decoder.getWidth() * decoder.getHeight() * 4);
	        decoder.decode(bb, decoder.getWidth() * 4, PngDecoder.Format.RGBA);
	        bb.flip();
	        return bb;
	    } 
	    finally 
	    {
	        is.close();
	    }
	}
	
	/**
	 * Update frame per seconds checker.
	 */
	public void updateFps()
	{	
		if (getCurrentTime() - this.timer.lastFPS > 1000) 
		{
			Display.setTitle(TITLE + VERSION + " | FPS: " + this.timer.fps);
            this.timer.fps = 0;
            this.timer.lastFPS += 1000;
		}
       this.timer.fps++;
	}
	
	/**
	 * Save all game engine options with a "options.txt" file.
	 */
	public void saveAllGameOptions()
	{
		this.optionsFile = new File(ResourceLocation.CRAFTIX_FOLDER);
		this.inGameSettings.saveOptions();
	}

	
	/**
	 * Retrun the game current time in miliseconds.
	 */
	public static long getCurrentTime()
	{
		return Sys.getTime() * 1000L / Sys.getTimerResolution();
	}
	
	/**
	 * Check whether the display is changeble.
	 */
	private void setResizeble(boolean resizeble)
	{
		Display.setResizable(resizeble);
	}
	
	/**
	 * Check whether the display is visible.
	 */
	public void setVisible(boolean isVisible)
	{
		if(isVisible)
		{
			Display.isVisible();
		}
		else 
		{
			LOGGER.warn("Display not visible. Set the 'true' on setVisible().");
		}
	}
	
	/**
	 * Set the title and get verison for the application.
	 */
	private void setTitle(String title, String version)
	{
		Display.setTitle(title + version);
	}
	
	/**
	 * Initialise the the config for game engine.
	 */
	public void gameCongifurationInit() 
	{
		this.cxDataDir = this.gameConfiguration.folderInfo.cxDataDir;
		Locale.setDefault(Locale.ROOT);
		
	}
	
	/**
	 * Disable the info desc PngDecoder Util message.
	 */
	private void disablePngDecoderInfo()
	{
		Log.setVerbose(false);
	}
	
	/**
	 * Load the default language to the buffer.
	 */
	public void loadDefaultLanguage()
	{
		MainMenuSceneOld mainScene = new MainMenuSceneOld();
		mainScene.useRu = true;
	}
	
	/**
	 * If in the process nothing not is happening the thread falls asleep on time.
	 */
	private void startTimerHackThread()
    {
        Thread thread = new Thread("Timer hack thread!")
        {
            public void run()
            {
                while (Craftix.this.isRunning())
                {
                    try
                    {
                        Thread.sleep(2147483647L);
                    }
                    catch (InterruptedException var2)
                    {
                        var2.printStackTrace();
                    }
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }
	
	/**
	 * Check for an OpenGL error. If there is an error in the console writes an error ID.
	 */
	private void checkGLError(String message)
	{
		int i = GlHelper.glGetError();
		
		if(i != 0)
		{
			String s = GLU.gluErrorString(i);
			LOGGER.error("============= GL ERROR =============");
			
			LOGGER.error("@ {}", (Object) message);
			LOGGER.error("{}: {}", Integer.valueOf(s), i);
			
		}
	}
	
	/**
	 * Check if Java Virtual Machime instlled on 64bit os system. And load game better.
	 * @return
	 */
	private boolean isJvm64bit()
    {
        String[] astring = new String[] {"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};

        for (String s : astring)
        {
            String s1 = System.getProperty(s);

            if (s1 != null && s1.contains("64"))
            {
                return true;
            }
        }

        return false;
    }
	
	/**
	 * Print date to the console with a string.
	 */
	public void getDateToString()
	{
		Date date = new Date();
		LOGGER.info("Now date: " + date.toString());
	}
	
	/**
    * Resets the player keystate, disables the ingame focus, and ungrabs the mouse cursor.
    */
	public void setIngameNotInFocus()
	{
		if(this.inGameHasFocus)
		{
			this.inGameHasFocus = false;
			this.mouseHelper.ungrabMouseCursor();
		}
	}
	
	/**
	 * Check whether the display is fullscreen or not. If display not fullscreen he be loaded
	 * in the windowed mode.
	 */
	protected void setDisplayAndFullscreenMode(boolean fullscreen, boolean displayMode) 
			throws LWJGLException
	{
		if(displayMode && !fullscreen)
		{
			this.setVSync(false);
			Display.setDisplayMode(new DisplayMode(getDisplayWidth(), getDisplayHeight()));
		}
		if(fullscreen && !displayMode)
		{
			this.setVSync(true);
			Display.setFullscreen(fullscreen);
		}
		if(!fullscreen && !displayMode)
		{
			this.setVSync(false);
			LOGGER.error("I cannot select the type of display to load.");
			
		}
	}
	
	/***
	 * Set the verical Sync for fullscreen mode.
	 */
	public void setVSync(boolean isSync)
	{
		this.vSync = isSync;
		Display.setVSyncEnabled(isSync);
	}
	
	/**
     * Will set the focus to ingame if the Craftix window is the active with focus. Also clears any GUI screen
     * currently displayed.
     */
	public void setIngameFocus()
	{
		if(Display.isActive())
		{
			if(!this.inGameHasFocus)
			{
				this.inGameHasFocus = true;
				this.mouseHelper.grabMouseCursor();
			}
		}
	}
	
	/**
	 * Quit the actilly game scene when now running. Works how always.
	 * @throws LWJGLException
	 */
	public void quitTheScene() throws LWJGLException
	{
		if(this.backToMenu)
		{
			this.returnToMainMenu();
		}
		
	}
	
	public void returnToMainMenu() throws LWJGLException
	{
		this.sceneReloader();
	}
	
	/**
	 * Set the game window in fullscreen mode.
	 * @param isFullscreen
	 */
	private void setFullscreen(boolean isFullscreen)
	{
		try 
		{
			Display.setFullscreen(isFullscreen);
		} 
		catch (LWJGLException e) 
		{
			LOGGER.error("Cannot set the display in fullscreen mode!");
			e.printStackTrace();
		}
	}
	
	protected void checkWindowResize()
	{
		if(!this.fullscreen && Display.wasResized())
		{
			int i = this.displayWidth;
			int j = this.displayHeight;
			this.displayWidth = Display.getWidth();
			this.displayHeight = Display.getHeight();
			
			 if (this.displayWidth != i || this.displayHeight != j)
	         {
	             if (this.displayWidth <= 0)
	             {
	                 this.displayWidth = 1;
	             }

	             if (this.displayHeight <= 0)
	             {
	                 this.displayHeight = 1;
	             }

	             this.resize(this.displayWidth, this.displayHeight);
	         }
		}
	}
	
	 /**
     * Called to resize the screen.
     */
	public void resize(int width, int height)
	{
		this.displayWidth = Math.max(1, width);
		this.displayHeight = Math.max(1, height);
	}
	
	
	/**
	 * This method working perfectlly for now.
	 */
	public void controllFullscreenMode() throws LWJGLException
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_F12))
		{
			Display.setFullscreen(true);
			InGameSettings.useFullscreenIn = true;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_F12))
		{
			Display.setFullscreen(false);
			InGameSettings.useFullscreenIn = false;
		}
		
	}
	
	/**
	 * Controls on the proper use of statuses of the output of the engine.
	 * @param status - status of the error. For example 0 its a normal exit.
	 */
	public void exitController(int status)
	{
		if(status == -1)
		{
			System.exit(-1);
		}
		else if(status == 0)
		{
			System.exit(0);
		}
		else if(status == 1)
		{
			System.exit(1);
		}
		else if(status >= 2 || status <= -2)
		{
			LOGGER.error("Exit status " + status + " not currect.");
			LOGGER.error("Only 1, 0, -1 statuses can be used.");
		}
	}
	
	/**
	 * Closes the program when the user clicks on the cross on the display.
	 */
	public void exitOnCross(int status)
	{
		this.ON_EXIT_CLOSE = true;
		if(this.ON_EXIT_CLOSE)
		{
			AudioMaster.shutdownAudioMaster();
			this.exitController(status);
		}
	}
	
	/**
	 * Exits the game engine after clean-up all buffers and shaders.
	 */
	private void shutdown(int status)
	{
		LOGGER.info("Shutting down internal servers...");
		this.isRunning = false;
		this.exitController(status);
	}
	
	/**
     * Get the display with for future used.
     * @return - the display width from settings class.
     */
	public int getDisplayWidth() 
	{
		return InGameSettings.displayWidthIn;
	}

	 /**
     * Get the height with for future used.
     * @return - the display height from settings class.
     */
	public int getDisplayHeight() 
	{
		return InGameSettings.displayHeightIn;
	}
	
	/**
	 * Puts a fps limit in some game scenes such as the menu because the menu 
	 * is 60 as 120.
	 * @return limitFramerate - is current fps in the limit.
	 */
	public int getCurrentFps() 
	{
		int currentFps = 0;
		if(this.status.isGameWorld())
		{
			currentFps = InGameSettings.maxFpsIn;
		}
		if(this.status.isGameMenu())
		{
			currentFps = InGameSettings.limitFpsMenuIn;
		}
		
		return currentFps;
		
	}
	
	/**
	 * Normalization for correct use of the mouse in the game. Special use for gui's texture. Or for
	 * example buttons.
	 */
	public static Vector2f getNormalizedMouseCoords()
	{
		float normaizedX = -1.0f + 2.0f * (float) Mouse.getX() / (float) Display.getWidth();
		float normaizedY = 1.0f - 2.0f * (float) Mouse.getY() / (float) Display.getHeight();
			return new Vector2f(normaizedX, normaizedY);
	}
	
	/**
	 * Inverse because in LWJGL y coord is inverse.
	 */
	public static Vector2f getInverseNormalizedMouseCoords()
	{
		float normaizedX = -1.0f + 2.0f * (float) Mouse.getX() / (float) Display.getWidth();
		float normaizedY = -1.0f + 2.0f * (float) Mouse.getY() / (float) Display.getHeight();
			return new Vector2f(normaizedX, normaizedY);
	}
	
	private void runTick() throws IOException
	{
		if(this.getWorldScene().guiScreenOld != null)
		{
			try
			{
				this.getWorldScene().guiScreenOld.handleInput();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
		if(this.getWorldScene().guiScreenOld == null)
		{
			this.runTickMouse();
		}
	}
	
	private void runTickMouse() throws IOException
	{
		while(Mouse.next())
		{
			int i = Mouse.getEventButton();
			KeyBinding.setKeyBindState(i - 100, Mouse.getEventButtonState());
			
			if(Mouse.getEventButtonState())
			{
				KeyBinding.onTick(i - 100);
			}
			
			long j = getCurrentTime() - this.systemTime;
			
			if(j <= 200L)
			{
				int k = Mouse.getEventDWheel();
				
				if(k != 0)
				{
					
				}
				
				if(this.getWorldScene().guiScreenOld == null)
				{
					if(!this.inGameHasFocus && Mouse.getEventButtonState())
					{
						this.setIngameFocus();
					}
				}
				else if(this.getWorldScene().guiScreenOld != null)
				{
					this.getWorldScene().guiScreenOld.getButton().onButtonClick();
				}
				
			}
			
			
		}

	}
	
	public void checkOnWork()
	{
		LOGGER.info("Click Success!");
	}
	
	/**
	 * Get the texture manager and return it.
	 */
	public TextureManager getTextureManager()
	{
		this.textureManager = new TextureManager();
		return textureManager;
	}
	
	/**
	 * Get the craftix engine version when it running.
	 */
	public static String getVersion()
	{
		return VERSION;
	}
	
	/**
	 * Allows you to get in the 'InGameSettings' for all other classes.
	 */
	public InGameSettings getInGameSettings()
	{
		return this.inGameSettings;
	}
	
	public Language getLang()
	{
		return this.language;
	}
	
	public DataFixer getDataFixer()
	{
		return this.dataFixer;
	}
	
	public boolean isJava64bit()
    {
        return this.jvm64bit;
    }
	
	/**
	 * Get the WorldScene for other classes.
	 */
	public WorldScene getWorldScene()
	{
		return this.worldScene;
	}
	
	/**
	 * Get the 'TextInit' class for future used in scenes classes.
	 * @return - the main textInit instance.
	 */
	//public TextInit getTextInit()
	//{
	//	return this.textInit;
	//}
	
	/**
	 * Get the 'TextRenderer' to display text in the engine.
	 * @return - the main textRender instance.
	 */
	public FontRenderer getTextRenderer()
	{
		return this.textRenderer;
	}

	public boolean isRunning() 
	{
		return isRunning;
	}
	
	public Session getSession()
	{
		return this.engineSession;
	}
	
	public GameConfiguration getGameConfig()
	{
		return this.gameConfig;
	}

	public File getFileAssets() 
	{
		return fileAssets;
	}

	public File getFileResourcepacks() 
	{
		return fileResourcepacks;
	}

	/**
	 * Get the engine version.
	 */
	public String getEngineVersion() 
	{
		return engineVersion;
	}

	/**
	 * Get the type of the version.
	 */
	public String getEngineVersionType() 
	{
		return engineVersionType;
	}

	/**
	 * Get just the gemo version of the engine.
	 * @return
	 */
	public boolean isDemoVersion() 
	{
		return isDemoVersion;
	}
	
	public MainMenuSceneOld getMenu()
	{
		return this.mainMenu;
	}
	
	public static String getTitle()
	{
		return TITLE;
	}
	
	/**
	 * Return a main game engine instance.
	 */
	public static Craftix& getCraftixInstance()
	{
		return *INSTANCE;
	}
	
	/**
	 * Return the ratio of the display screen in pixels for projection matrix.
	 */
	public float getAspectRatio()
	{
		return this.aspectRatio;
	}
	
	public GuiScale getScaleManager()
	{
		return this.guiScale;
	}
	
	/***
	 * Here just returns a multisample framebuffers for scenes.
	 */
	public FrameBuffer getMsFramebuffer()
	{
		return this.msFrameBuffer;
	}
	
	public FrameBuffer getFramebuffer01()
	{
		return this.frameBufferCx;
	}
	
	public FrameBuffer getFramebuffer02()
	{
		return this.frameBufferCx2;
	}
	
	/**
	 * Bind, unbind and resolve to another frame buffer object every frame.
	 */
	public void updateFramebuffer()
	{
		this.msFrameBuffer.bindFrameBuffer();
		this.msFrameBuffer.unbindFrameBuffer();
		this.msFrameBuffer.resolveToFrameBuffer(Framebuffer.COLOR_ATTACHMENT0, this.frameBufferCx);
		this.msFrameBuffer.resolveToFrameBuffer(Framebuffer.COLOR_ATTACHMENT1, this.frameBufferCx2);
	}
	
	/**
	 * Back user to main menu screen, before saved and clean other game stuff.
	 */
	public void backToMenu()
	{
		Pages.isMainGamePausePage = false;
		GuiInGameMenu.removePage();
		this.worldScene.saveWorld();
		if(this.worldScene.getPlayer().flag2)
			this.worldScene.getPlayer().flag2 = false;
		this.status.setGamePause(false);
		this.status.setGameWorld(false);
		this.status.setGameMenu(true);
		this.backToMenu = true;
		this.menuScene.isGoToNexScene = false;
		this.quitGameScene = true;
		Pages.isMainMenuPage = true;
		GuiMainMenu.addPage();
		this.menuScene.updateScene();

	}
	
	public void running(boolean isRunning)
	{
		this.isRunning = isRunning;
	}
	
	/**
	 * Set the main displays paramets, like a resolution and etc.
	 * @throws LWJGLException 
	 */
	private void setDisplayParameters(int width, int height, boolean isFullscreen, boolean isResizeble) throws LWJGLException 
	{
		Display.setDisplayMode(new DisplayMode(width, height));
		Display.setResizable(isResizeble);
		Display.setFullscreen(isFullscreen);
	}
	
	/**
	 * Destroy all Lwjgl contexts, for example: Display context, or Open Al Audio master context.
	 */
	public void destroyLwjglContexts(boolean openGlContext, boolean openAlContext)
	{
		if(AL.isCreated() && openAlContext)
		{
			AL.destroy();
		}
		else if(Display.isCreated() && openGlContext)
		{
			Display.destroy();
		}
		LOGGER.info("Destroying Lwjgl contexts...");
	}
	
	/**
	 * Connect client side with server and get his instance.
	 */
	public CraftixMP getServer()
	{
		return this.craftixMp;
	}
	
}
