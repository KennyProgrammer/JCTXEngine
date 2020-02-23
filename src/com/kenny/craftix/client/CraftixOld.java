package com.kenny.craftix.client;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.management.openmbean.KeyAlreadyExistsException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.ImageIOImageData;
import org.newdawn.slick.util.Log;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.audio.AudioMaster;
import com.kenny.craftix.client.audio.Source;
import com.kenny.craftix.client.audio.sound.SoundLoader;
import com.kenny.craftix.client.entity.GameObject;
import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.entity.Light;
import com.kenny.craftix.client.entity.player.EntityPlayer;
import com.kenny.craftix.client.entity.player.PlayerMovement;
import com.kenny.craftix.client.gui.GuiCreditsMenu;
import com.kenny.craftix.client.gui.GuiGame;
import com.kenny.craftix.client.gui.GuiInGameMenu;
import com.kenny.craftix.client.gui.GuiLoadingSplash;
import com.kenny.craftix.client.gui.GuiQuad;
import com.kenny.craftix.client.gui.GuiRenderManager;
import com.kenny.craftix.client.gui.GuiRenderer;
import com.kenny.craftix.client.gui.GuiScaled;
import com.kenny.craftix.client.gui.button.GuiAbstractButton;
import com.kenny.craftix.client.gui.button.IButton;
import com.kenny.craftix.client.language.Language;
import com.kenny.craftix.client.loader.GameState;
import com.kenny.craftix.client.loader.IconLoader;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.loader.PngDecoder;
import com.kenny.craftix.client.particles.Particle;
import com.kenny.craftix.client.particles.ParticleMaster;
import com.kenny.craftix.client.particles.ParticleSystem;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.WorldRenderer;
import com.kenny.craftix.client.renderer.GlHelper.Framebuffer;
import com.kenny.craftix.client.renderer.GlHelper.Texture;
import com.kenny.craftix.client.renderer.postEffects.PanoramaBlur;
import com.kenny.craftix.client.renderer.postEffects.PostProcessing;
import com.kenny.craftix.client.renderer.textures.TextureManager;
import com.kenny.craftix.client.resources.StillWorking;
import com.kenny.craftix.client.scenes.MainMenuSceneOld;
import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.client.settings.console.ConsoleWriter;
import com.kenny.craftix.client.shaders.FrameBuffer;
import com.kenny.craftix.init.EntityInit;
import com.kenny.craftix.init.ParticleInit;
import com.kenny.craftix.init.PostEffectsInit;
//import com.kenny.craftix.init.TextInit;
import com.kenny.craftix.main.GameConfiguration;
import com.kenny.craftix.main.Main;
import com.kenny.craftix.utils.KennyCopyright;
import com.kenny.craftix.utils.MouseHelper;
import com.kenny.craftix.utils.MousePicker;
import com.kenny.craftix.utils.Timer;
import com.kenny.craftix.utils.Util;
import com.kenny.craftix.utils.data.DataFixManager;
import com.kenny.craftix.utils.data.DataFixer;
import com.kenny.craftix.world.World;
import com.kenny.craftix.world.skybox.SkyboxRenderer;
import com.kenny.craftix.world.skybox.SkyboxShader;
import com.kenny.craftix.world.water.WaterRenderer;
import com.kenny.craftix.world.water.WaterShader;
import com.kenny.craftix.world.water.Water;

/**
 * @author Kenny and @KennyCoprightTeam
 */
@SuppressWarnings({ "unused" })
@KennyCopyright
public class CraftixOld
{
	/**Main Game Configuration. Title, Version etc...*/
	public static final String TITLE = "Craftix";
	public static final String TITLE_DEMO = "Craftix (Bugging Build)";
	public static final String VERSION = " 0.0.15b";
	public static final Logger LOGGER = LogManager.getLogger(TITLE);
	public static boolean ON_EXIT_CLOSE;
	private static final List<DisplayMode> MAC_DISPLAY_MODES = Lists.newArrayList
			(new DisplayMode(2560, 1600), 
					new DisplayMode(2880, 1800));
	/**All useds lists for some stuff.*/
	private List<Light> lights = new ArrayList<Light>();
	public List<GuiQuad> guis = new ArrayList<GuiQuad>();
	public List<GuiQuad> buttons = new ArrayList<GuiQuad>();
	public List<Water> waters = new ArrayList<Water>();
	/**Checks if the window is resizeble.*/
	public boolean isResizeble;
	/**Checks if game running or not.*/
	public boolean isRunning;
	public boolean isRunningWithLauncher = false;
	/**If crushed game stop running.*/
	public boolean isCrushed;
	/**Display temp width*/
    private int tempWidth;
    /**Display temp height*/
    private int tempHeight;
    /**Vertical Syncroniztion for a fullscreen mode window.*/
    public boolean vSync;
	/**Fps cap in game*/
	public int old_fps;
	/**RGB light source*/
	private float lightR = 1.3f;
	private float lightG = 1.3f;
	private float lightB = 1.3f;
	private static Timer timer = new Timer();
	public MouseHelper mouseHelper;
	/**Is the core to load the rest of the game.*/
	public Loader loader;
	/**Load all terrain and water to the game.*/
	public World world;
	public TextureManager textureManager;
	public IconLoader iconLoader;
	/**It displays everything visually in the game. Fog, shaders, etc.*/
	private WorldRenderer worldRenderer;
	/**Just generate and return random numbers.*/
	public Random random = new Random(676452);
	/**Its a terrain point for set the lights or pickers*/
	private Vector3f terrainPoint;
	/**Setup the main player*/
	public EntityPlayer player;
	private PlayerMovement playerInput;
	private EntityCamera camera;
	/**This is a main light source*/
	public Light sun;
	public Light pickerLight;
	/**Help render guis to the screen*/
	private static GuiRenderer guiRenderer;
	/**Load guis to the game*/
	private GuiGame guiInit;
	/**Load all entities to the game*/
	private EntityInit entityInit;
	/**Put the mouse from 2d in 3d space world.*/
	private MousePicker picker;
	/**Load a water shader and render them*/
	private WaterShader waterShader;
	private WaterRenderer waterRenderer;
	/**Load a skybox shader and render them*/
	private SkyboxShader skyboxShader;
	/**Help loading the text and render him on the screen*/
	//public TextInit textInit;
	/**Load all particle in the game*/
	public ParticleInit particleInit;
	public int fpsUp;
	private boolean jvm64bit;
	/**Sets the water framebuffer.*/
	private FrameBuffer waterFrameBuffer;
	/**Init the framebuffer for a display.*/
	private FrameBuffer displayFrameBuffer;
	/**Multisamle the framebuffer for post-proccessing effects.*/
	public static FrameBuffer multisampleFrameBuffer;
	/**Outputs the converted frame buffer.*/
	public static FrameBuffer outputFrameBuffer;
	private FrameBuffer outputFrameBuffer2;
	private final DataFixer dataFixer;
	/**Location of the engine options file.*/
	private static File optionsFile;
	/**Location of lang's localization files.*/
	private static File langEnFile;
	/**Check if user in game menu.*/
	public static boolean isInMenuScene = true;
	/**Check if user in the loaded world.*/
	public static boolean isInWorldScene = false;
	public static boolean quitGameScene = false;
	public static boolean backToMenu = false;
	/**Does the actual gameplay have focus. If so then mouse and keys will effect the player instead of menus. */
	private boolean inGameHasFocus;
	public boolean fullscreen;
	/**
	 * This resolution of the screen.
	 */
	public int displayWidth;
	public int displayHeight;
	/**Checks for the pause stage. If the game is paused, some rendering moments stop.*/
	public static boolean isGamePause;
	private static InGameSettings inGameSettings;
	public File cxDataDir;
	//private GuiMainMenu guiMainMenu = new GuiMainMenu();
	private GuiCreditsMenu guiCreditsMenu = new GuiCreditsMenu();
	private GuiInGameMenu guiInGameMenu = new GuiInGameMenu();
	/**Initialization the main menu scene. Load in on main screen.*/
	private MainMenuSceneOld mainMenuScene = new MainMenuSceneOld();
	public static GuiLoadingSplash guiLoading = new GuiLoadingSplash();
	private WorldScene worldScene = new WorldScene();
	/**
	 * Checks at what point in the menu what stage to show on the screen or another render.
	 */
	
	/**Sound loading using LWJGL OpenAl API.*/
	private SoundLoader soundLoader = new SoundLoader();
	/**Load the language the the engine.*/
	private Language language;
	
	/**This is a main instance for the game.*/
	public CraftixOld instance;
	//private FontRenderer textRenderer;
	
	public CraftixOld(GameConfiguration gameConfig)
	{
		instance = this;
		this.cxDataDir = gameConfig.folderInfo.cxDataDir;
		Locale.setDefault(Locale.ROOT);
		this.dataFixer = DataFixManager.createFixer();
	}
	
	public CraftixOld()
	{
		instance = this;
		Locale.setDefault(Locale.ROOT);
		this.dataFixer = DataFixManager.createFixer();
	}
	
	/***
	 * Run the hole game engine. Call super classes and load the scenes.
	 */
	public void run() 
	{
		this.isRunning = true;
		this.isCrushed = false;
		
		try
		{
			this.preInit();
		}
		catch (Throwable t)
		{
			LOGGER.error("Failed load the game!");
			
			try
			{
				Exception e = new Exception("");
				LOGGER.info("Figuring out the problem...");
				e.printStackTrace();
			}
			catch(Exception e)
			{	
			}
				return;
		}
	
		while(true)
		{
			if(this.isRunning == true && !this.isCrushed)
			{
				try
				{
					
				}
				catch(OutOfMemoryError e)
				{
					LOGGER.fatal("Crash occurred due to lack of memory on the computer.");
					System.gc();
				}
				
			}
		}
		
	}
	
	public void loadDefaultFont()
	{
		this.loader = new Loader();
		//this.textInit.fontCandara = new FontType(this.loader.loadFontAtlas("candara"), "candara");
	}
	
	 /**
     * Starts the game: Pre-initialization: load the window, sets resolution. 
	 * @throws IOException 
     */
	
	public void preInit() throws LWJGLException
	{
		try
		{
			LOGGER.info("============= Craftix Info =============");
			//ResourceLocation.setCraftixProfileFolder(this);
			langEnFile = new File(ResourceLocation.CRAFTIX_FOLDER);
			optionsFile = new File(ResourceLocation.CRAFTIX_FOLDER);
			//this.language = new Language(langEnFile);
			//inGameSettings = new InGameSettings(this, optionsFile);
			LOGGER.info("LWJGL Version: " + (Object)Sys.getVersion());
			LOGGER.info("System 64 bit: " + (Object)Sys.is64Bit());
			LOGGER.info("Game resolution: " + InGameSettings.displayWidthIn + " x " + InGameSettings.displayHeightIn);
			LOGGER.info("Current Time:" + (Object)Sys.getTime());
			LOGGER.info("Game options configuration succes loading.");
			this.startTimerHackThread();
			this.disablePngDecoderInfo();
			this.isJvm64bit();
			this.setDisplayIcon();
			this.initDisplayMode();
			this.createDisplay();
			this.checkGLError("Pre startup");
			GlHelper.enableTexture2d();
			LOGGER.info("Display Framebuffer set on display resolution.");
			this.loadDefaultLanguage();
			AudioMaster.init();
			LOGGER.info("Pre-initialization success complited..");
			this.init();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			this.crushed();
		}
	}
	
	/**
	 * Load the game: Initialization: Loading the main menu scene-screen.
	 * @throws LWJGLException
	 */
	public void init() throws LWJGLException
	{	
		try 
		{
			//WorldScene.isGamePause = false;
			isInWorldScene = false; 
			isInMenuScene = true;
			backToMenu = false; 
			quitGameScene = false;
			this.setIngameNotInFocus();
			//this.mainMenuScene.loadScene();
			this.updateMainMenu();
			saveAllGameOptions();
			this.loader = new Loader();
			//this.guiRenderer = new GuiRenderer(this.loader);
			///this.guiLoading.loadLoadingScreen();
			if(isInWorldScene && !isInMenuScene)
			{
				this.checkGLError("World Loading");
				//this.worldScene.loadScene();
				this.updateGameDisplay();
				saveAllGameOptions();
				this.worldScene.cleanUpScene();
				isGamePause = false;
				this.quitTheScene();	
				this.quitTheApplication();
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			this.crushed();
		}
		
		if(Display.isCloseRequested() && !quitGameScene && !backToMenu && isRunning)
		{
			this.shutdown();
		}
	}
	
	/**
	 * Post-Initialization: reload the hole scene after back to main menu. And re-generate world,
	 * water, and other game engine stuff. 
	 */
	public void postInit() throws LWJGLException
	{	
		try 
		{
			isInWorldScene = false; isInMenuScene = true;
			backToMenu = false; quitGameScene = false;
			this.mainMenuScene.reloadScene();
			this.updateMainMenu();
			saveAllGameOptions();
			if(isInWorldScene && !isInMenuScene)
			{
				//GuiRenderManager.renderInGameMenu = false;
				//if(GuiRenderManager.renderInGameMenu)
				//{TextInit.removeInGamePausePage();}
				this.worldScene.reloadScene();
				this.timer.lastFPS = CraftixOld.getCurrentTime();
				this.updateGameDisplay();
				saveAllGameOptions();
				this.worldScene.cleanUpScene();
				this.quitTheScene();
				this.quitTheApplication();
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			
			this.crushed();
		}
		
		//if(Display.isCloseRequested() && !quitGameScene && !backToMenu && isRunning)
		//{
		//	this.shutdown();
		//}
	}
	
	/**
	 * Create a display with use LWJGL. Load title, version and other parameters.
	 * @throws LWJGLException 
	 */
	public void createDisplay() throws IOException, LWJGLException
	{
		if(this.isRunning && !isCrushed)
		{

			ContextAttribs attribs = new ContextAttribs(3, 3);
			attribs.withForwardCompatible(true);
			attribs.withProfileCore(true);
			
			try 
			{	
				this.setTitle(TITLE, VERSION);
				this.setResizeble(false);
				
				Display.create(new PixelFormat().withDepthBits(24), attribs);
				inGameSettings.setMultisampleOption();
				
			} 
			catch (LWJGLException e) 
			{
				LOGGER.fatal("Cannot create a display");
				saveAllGameOptions();
				
				try 
				{
					Thread.sleep(1000L);
				} 
				catch (InterruptedException var3) 
				{
					;
				}
				
				if(this.fullscreen)
				{
					this.updateDisplayMode();
				}
				
				Display.create();
			
			}
			
			GlHelper.glViewport(0, 0, getDisplayWidth(), getDisplayHeight());
			this.timer.initTime();
		}
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
			Display.setResizable(false);
			Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));
		}
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
            LOGGER.info("Unable to setup mode: " + width + "x" + height + " fullscreen = " + fullscreen + e);
        }
    }

	/**
	 * Standard update display method.
	 */
	public static void updateDisplay()
	{
		Display.update();
		Display.sync(getFps());
	}

	/**
	 * Update when user in game menu. Render stuff in a game menu (Main page, options, 
	 * framebuffers, etc...)
	 */
	
	public void updateMainMenu() throws LWJGLException
	{
		this.setResizeble(InGameSettings.resizeDisplayIn);
		
		while(!Display.isCloseRequested() && this.isRunning && !isInWorldScene)
		{
			this.mainMenuScene.renderScene();
			this.timer.setTime();
			this.updateFps();
			this.controllFullscreenMode();
			this.updateDisplay();
			
			if(!this.isRunning && Display.isCloseRequested())
			{
				this.mainMenuScene.cleanUpScene();
			}
		}
	
		if(!isInWorldScene && isInMenuScene)
			this.exitOnCross();
	}
	
	public static void updateLoadingSplashDisplay()
	{
		GuiRenderManager.renderLoadingSplash = true;
		boolean showLoadingScreen = true;
		boolean showLoadingText = true;
		//TextMaster.renderLoadingSplash();

		if(GuiRenderManager.renderLoadingSplash)
		{
			
			guiRenderer.render(guiLoading.textures);
			if(GuiLoadingSplash.progress25)
			{
				//guiRenderer.render(guiLoading.guisLoadingState1);
			}
			if(GuiLoadingSplash.progress50)
			{
				//guiRenderer.render(guiLoading.guisLoadingState2);
			}
			if(GuiLoadingSplash.progress75)
			{
				//guiRenderer.render(guiLoading.guisLoadingState3);
				//GuiRenderManager.renderGameOver = false;
			}
			//guiRenderer.render(guiLoading.guisLoadingButtons);
		}
		
		while(!Display.isCloseRequested() && showLoadingScreen)
		{
			timer.setTime();
			updateFps();
			updateDisplay();
			if(GuiLoadingSplash.progress75 == false && GuiLoadingSplash.progress50 == false 
					&& GuiLoadingSplash.progress25 == false)
			{
				GuiRenderManager.renderLoadingSplash = false;
				//TextInit.removeLoadingWorldPage();
			}
			showLoadingScreen = false;
		}
	}
	
	/**
	 * Update a display each frame.
	 */
	public void updateGameDisplay()
	{	
		this.worldScene.otherSetup();
		//this.worldScene.generateEntitiesOnTerrain();

		while(!Display.isCloseRequested() && !quitGameScene)
		{
	
			this.rendererGameLoop();
			this.timer.setTime();
			this.updateFps();
			this.updateDisplay();
			isGamePause = false;
		}
		
		//if(!GuiRenderManager.renderInGameMenu) 
		//{
			//if(!GuiRenderManager.renderGameOver)
			//{
			//	 exitOnCross();
			//}
		//}
		
	}
	
	/**
	 * Update the game Gui Buttons.
	 */
	public void updateGuiButtons()
	{	
		this.guiInit.updateButtons();
	}
	
	/***
	 * Render entire a game engine. Render world, guis, entities.
	 */
	public void rendererGameLoop()
	{
		this.worldScene.renderScene();
		
	}

	
	public static void updateFps()
	{	
		if (CraftixOld.getCurrentTime() - timer.lastFPS > 1000) 
		{
			//Display.setTitle("FPS: " + this.timer.fps);
            timer.fps = 0;
            timer.lastFPS += 1000;
        }
        timer.fps++;
	}
	
	/**
	 * Render on top of terrain and for coords mouse object.
	 */
	public void renderMousePicker(boolean isRender)
	{
		if(isRender)
		{
			this.entityInit.pickerLamp.setPosition(this.terrainPoint);
			this.pickerLight.setPosition(new Vector3f(this.terrainPoint.x, this.terrainPoint.y, this.terrainPoint.z));
		}
		else
		{
			;
		}
	}
	
	/**
	 * Render a shadow map into the game.
	 */
	public void renderShadowMapScreen(boolean renderShadowMap)
	{
		if(renderShadowMap)
		{
			this.guiInit.gui_shadowMap = new GuiQuad(this.worldRenderer.getShadowMapTexture(),
					new Vector2f(0.5f, 0.5f), new Vector2f(0.5f, 0.5f));
			//this.guiMainMenu.guisBackground.add(this.guiInit.gui_shadowMap);
		}
		else
		{
			;
		}
	}
	
	/**
	 * Load the inverted reflection screen for test. This test is needed 
	 * to make sure that the water reflects the whole world. 
	 */
	public void loadReflectionScreen(boolean isReflected)
	{
		if(isReflected)
		{
			//this.guiInit.gui_reflectionScreen = new Gui(this.waterFrameBuffer.getReflectionTexture(), 
					// new Vector2f(-0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
				//this.guiMainMenu.guisBackground.add(this.guiInit.gui_reflectionScreen);
		}
		else 
		{
			;
		}
	}
	
	/**
	 * Load the inverted refraction screen for test. This test is needed 
	 * to make sure that the water reflects the whole world. 
	 */
	public void loadRefractionScreen(boolean isRefracted)
	{
		if(isRefracted)
		{
			this.guiInit.gui_refractionScreen = new GuiQuad(this.waterFrameBuffer.getRefractionTexture(), 
					 new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
				//this.guiMainMenu.guisBackground.add(this.guiInit.gui_refractionScreen);
		}
		else 
		{
			;
		}
	}
	
	/**
	 * Render all post-proccessing effects.(Blur, contrast, etc)
	 * This method affects the overall loading of all post-effects. If you disable this method, 
	 * the boolean's in "PostEffectsInit" will not work. You can disable this method if
	 * you used "multisampleFramebuffer.resloveToScreen".
	 */
	public static void renderPostProcessingEffects()
	{
		//if(InGameSettings.usePostEffectsIn)
		//PostProcessing.doPostProcessing(WorldScene.outputFrameBuffer.getColourTexture(), 
				//WorldScene.outputFrameBuffer.getColourTexture());
		//else
		//{
			//LOGGER.info("All post-proccessing effect has be disabled. And not load!");
		//}
	}
	
	/**
	 * Distroy display and exit thread.
	 */
	public void closeDisplay(boolean hasCrash)
	{
		this.isCrushed = hasCrash;
		LOGGER.info("Clean-Up all framebuffers.");
		LOGGER.info("Shutting down internal servers...");
		LOGGER.info(GLU.gluErrorString(GlHelper.glGetError()));
		AudioMaster.shutdownAudioMaster();
		Display.destroy();
		System.exit(hasCrash ? 1 : 0);
	}
	

	/**
	 * Quit the actilly game scene when now running.
	 * @throws LWJGLException
	 */
	public void quitTheScene() throws LWJGLException
	{
		if(backToMenu)
		{
			this.returnToMainMenu();
		}
		
	}
	
	/**
	 * Close Application 2.0! This is that same shuttdown() method but he close the
	 * application when player in the game scene. 
	 */
	public void quitTheApplication()
	{
		if(!this.isRunning)
		{
			this.checkGLError("Clean-Up Frame Buffer");
			GlHelper.disableTexture2d();
			LOGGER.info("Clean-Up Framebuffers...");
			outputFrameBuffer.cleanUp();
			this.outputFrameBuffer2.cleanUp();
			this.multisampleFrameBuffer.cleanUp();
			//TextMaster.cleanUp();
			PostProcessing.cleanUp();
			PanoramaBlur.cleanUp();
			ParticleMaster.cleanUp();
			this.guiRenderer.cleanUp();
			this.waterFrameBuffer.cleanUp();
			this.waterShader.cleanUp();
			this.worldRenderer.cleanUp();
			this.loader.cleanUpOpenGlDataObjects();
			LOGGER.info("All buffers has been Clean-Up.");
			Thread.yield();
			saveAllGameOptions();
			this.closeDisplay(isCrushed == false);
		}
	}
	
	
	/**
	 * This mehod return user back to main menu when the preess button "returnToMenu".
	 * But now its litte be a buggy. Maybe be crash.
	 * 
	 * @throws LWJGLException
	 */
	
	public void returnToMainMenu() throws LWJGLException
	{
		this.postInit();
	}
	
	/**
	 * Just close entire application/game.
	 */
	public void shutdown()
	{
		AudioMaster.shutdownAudioMaster();
		
		if(isInWorldScene && !isInMenuScene)
		{
			LOGGER.info("Clean-Up all framebuffers.");
			LOGGER.info("Shutting down internal servers...");
			this.isRunning = false;
			System.exit(1);
		}
		if(isInMenuScene && !isInWorldScene)
		{
			this.isRunning = false;
			this.closeDisplay(false);
			saveAllGameOptions();
			System.exit(1);
		}
	}
	
	/**
	 * Save all game engine options with a "options.txt" file.
	 */
	public static void saveAllGameOptions()
	{
		optionsFile = new File(ResourceLocation.CRAFTIX_FOLDER);
		inGameSettings.saveOptions();
	}
	
	/**
	 * Retrun the game current time in miliseconds.
	 */
	public static long getCurrentTime()
	{
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}
	
	/**
	 * Set window icon. Work is actually comlpite!
	 */
	private void setDisplayIcon()
	{
		try 
		{
			Display.setIcon(new ByteBuffer[] 
			{
				loadIcon(getClass().getResource(ResourceLocation.LOGO_FOLDER + "logo_16x16.png")),
				loadIcon(getClass().getResource(ResourceLocation.LOGO_FOLDER + "logo_32x32.png")),
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
			this.crushed();
		}
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
	
	
	private void startTimerHackThread()
    {
        Thread thread = new Thread("Timer hack thread!")
        {
            public void run()
            {
                while (CraftixOld.this.isRunning)
                {
                    try
                    {
                        Thread.sleep(2147483647L);
                    }
                    catch (InterruptedException var2)
                    {
                        ;
                    }
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }
	
	/**
	 * If game crushed then game has close.
	 */
	private void crushed()
	{
		LOGGER.error("Game has crushed! Check the stackTrace!.");
		this.isCrushed = true;
		inGameSettings.saveOptions();
		this.shutdown();
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
			;
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
	 * This method not be complited.
	 */
	@StillWorking
	private void controllFullscreenMode()
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_F12))
		{
			InGameSettings.useFullscreenIn = true;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_F11))
		{
			InGameSettings.useFullscreenIn = false;
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
	 * Disable the info desc PngDecoder Util message.
	 */
	private void disablePngDecoderInfo()
	{
		Log.setVerbose(false);
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
	
	/**
	 * Toggles the fullscreen mode.
	 */
	public void toggleFullscreen()
	{
		try
		{
			this.fullscreen = !this.fullscreen;
			InGameSettings.useFullscreenIn = this.fullscreen;
			
			if(this.fullscreen)
			{
				this.displayWidth = Display.getDisplayMode().getWidth();
				this.displayHeight = Display.getDisplayMode().getHeight();
				
				if(this.displayWidth <= 0)
				{
					this.displayWidth = 1;
				}
				
				if(this.displayHeight <= 0)
				{
					this.displayHeight = 1;
				}
			}
			else
			{
				Display.setDisplayMode(new DisplayMode(this.tempWidth, this.tempHeight));
				this.displayWidth = this.tempWidth;
				this.displayHeight = this.tempHeight;
				
				 if (this.displayWidth <= 0)
	             {
	                 this.displayWidth = 1;
	             }

	             if (this.displayHeight <= 0)
	             {
	                 this.displayHeight = 1;
	             }
			}
			
			this.setFullscreen(this.fullscreen);
			this.setVSync(InGameSettings.useVSync);
			this.updateDisplay();
	
		}
		catch(Exception e)
		{
			LOGGER.error("Can't toggle fullscreen.", (Throwable) e);
		}
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
	 * Normalization for correct use of the mouse in the game. Special use for gui's texture. Or for
	 * example buttons.
	 */
	public static Vector2f getNormalizedMouseCoords()
	{
		float normaizedX = -1.0f + 2.0f * (float) Mouse.getX() / (float) Display.getWidth();
		float normaizedY = 1.0f - 2.0f * (float) Mouse.getY() / (float) Display.getHeight();
			return new Vector2f(normaizedX, normaizedY);
	}
	
	@StillWorking
	public void reloadResources()
	{
		this.skyboxShader.cleanUp();
	}
	
	 /**
     * Returns whether we're in full screen or not.
     */
    public boolean isFullScreen()
    {
        return InGameSettings.useFullscreenIn;
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
	 * Get the fps capability when game running.
	 * @return - the fps capability.
	 */
	public static int getFps() 
	{
		return InGameSettings.maxFpsIn;
	}
	
	/**
	 * Get the craftix engine version when it running.
	 */
	public String getVersion()
	{
		return VERSION;
	}
	
	/**
	 * Return this boolean when the game in pause at the moment.
	 */
	public boolean isGamePaused()
    {
        return isGamePause;
    }
	
	public Timer getTime()
	{
		return this.timer;
	}
	
	public Light getSun()
	{
		return sun;
	}
	
	public void setSun(Light sun)
	{
		this.sun = sun;
	}
	
	public void setLightR(float lightR) 
	{
		this.lightR = lightR;
	}

	public void setLightG(float lightG) 
	{
		this.lightG = lightG;
	}

	public void setLightB(float lightB) 
	{
		this.lightB = lightB;
	}

	public float getLightR() 
	{
		return lightR;
	}

	public float getLightG() 
	{
		return lightG;
	}

	public float getLightB() 
	{
		return lightB;
	}
	
	public boolean isJava64bit()
    {
        return this.jvm64bit;
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
	 * Load the default language to the buffer.
	 */
	public void loadDefaultLanguage()
	{
		MainMenuSceneOld mainScene = new MainMenuSceneOld();
		mainScene.useRu = true;
	}
	
	public DataFixer getDataFixer()
	{
		return this.dataFixer;
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
	 * Closes the program when the user clicks on the cross on the display.
	 */
	public void exitOnCross()
	{
		ON_EXIT_CLOSE = true;
		if(ON_EXIT_CLOSE)
		{
			AudioMaster.shutdownAudioMaster();
			System.exit(0);
		}
	}
	
}