package com.kenny.craftix.client.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import com.google.common.base.Splitter;
import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.GuiYesNo;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.RenderDistance;
import com.kenny.craftix.client.settings.console.ConsoleErrorConfig;
import com.kenny.craftix.client.settings.nbt.NBTTagCompound;
import com.kenny.craftix.utils.data.FixTypes;
import com.kenny.craftix.utils.math.Maths;

@SuppressWarnings("deprecation")
public class InGameSettings
{
	public static final Splitter COLON_SPLITTER = Splitter.on(':');
	private static final Logger LOGGER = LogManager.getLogger("Settings");
	private static final String SETTINGS_CRAFTIX_VERSION = "0.0.19";
	public static boolean hasError;
	/**This is width of the display.*/
	public static int displayWidthIn;
	/**This is height of the display*/
	public static int displayHeightIn;
	public static boolean resizeDisplayIn;
	/**Responsible for large size Gui.*/
	public static boolean guiScaleLargeIn = false;
	/**Responsible for medium size Gui.*/
	public static boolean guiScaleMediumIn = false;
	/**Responsible for small size Gui.*/
	public static boolean guiScaleSmallIn = false;
	public static int guiScaleIn = 3;
	/**Its a current fps on the display when game has running.*/
	public static int maxFpsIn;
	public static int limitFpsMenuIn;
	/**Used to adjust the fullscreen mode in the game.*/
	public static boolean useFullscreenIn = true;
	public static boolean useVSync;
	/**Used to adjust the post-processing effect in the game.*/
	public static boolean usePostEffectsIn;
	/**If framebuffer is enable, than you can useds all post-proccessing effects.*/
	public static boolean useFboIn = false;
	public static boolean useShadowGuiIn;
	public static boolean usePoligonModeIn;
	public static boolean useAudioIn;
	public static boolean useMultisampleIn;
	/**Show the skybox on the game with rotation around player.*/
	public static boolean renderSkyBoxIn;
	/**Rendering the water tile in game engine.*/
	public static boolean renderWaterIn;
	public static float sliderIn;
    private static File optionsFile;
    public static String language;
    public static int languageIn;
    public static float fovIn;
    public static boolean fpsTabOnMenuIn;
    /**Render in game cursor or if false render standard Windows cursor or Mac OS cursor.*/
    public static boolean renderCursorIn;
    public static int renderDistanceIn;
    /**Render shadows on the entitys, player, game objects.*/
    public static boolean useShadowsIn;
    public static boolean renderParticlesIn;
    /**This is post processing effect.*/
    public static boolean useContrastIn;
    public static boolean useBloomIn;
    /**This is a brightness of the world scene. Use how post-processing effect.*/
    public static int brightnessIn;
    public static boolean useGaussianBlurIn;
    /**This is integers for movement entity.*/
    public static int keyboardMovementForwardIn;
    public static int keyboardMovementBackwardIn;
    public static int keyboardMovementLeftwardIn;
    public static int keyboardMovementRightwardIn;
    public static int keyboradMovementRunIn;
    public static int keyboradMovementJumpIn;
    public static int keyboardPauseIn;
    public static boolean slot0;
    public static boolean slot1;
    public static boolean slot2;
    public static boolean slot3;
    public static boolean slot4;
    
    public ConsoleErrorConfig consoleConfig = new ConsoleErrorConfig();
    private Craftix cx;
	
	/**
	 * Sets the default engine settings on first launch. If user load game already then,
	 * be load the options "txt" file.
	 */
	public InGameSettings(Craftix cxIn, File optionsFileIn)
    {
    	this.cx = cxIn;
    	this.optionsFile = new File(optionsFileIn, "options.txt");
    	this.checkIfOptionsFileExist();
    	this.loadDefaultEngineOptions();
    	this.consoleConfig.errorManager(false);
    	this.loadOptions();
    	this.eventButtonsYesNo();
    	this.saveOptions();
    }
    
    public InGameSettings()
    {
    	
    }
    
	public void loadDefaultEngineOptions()
	{
		this.setDisplayMode();
		this.disableFullscreen();
		enableVsync();
		this.enableAudio();
		this.disableFbo();
		this.disableMultisample();
		this.enableRenderSkyBox();
		this.enableRenderWater();
		this.setDefaultLanguage();
		this.setFov();
		this.setRenderDistance();
		
	}
	
	/**
	 * Set resolution of the display on first load if display not in fullscreen.
	 */
	public void setDisplayMode()
	{
		this.setDisplayWidth();
		this.setDisplayHeight();
		this.setLimitFps();
		this.setCurrentFps();
		this.enableResizeDisplay();
	}
	
	protected void setKeyboardKeys()
	{
	}
	
	private void setDisplayWidth()
	{
		displayWidthIn = 960;
	}
	
	private void setDisplayHeight()
	{
		displayHeightIn = 540;
	}
	
	private void setCurrentFps()
	{
		maxFpsIn = 1000;
	}
	
	private void setLimitFps()
	{
		limitFpsMenuIn = 60;
	}
	
	private void setFov()
	{
		fovIn = 70;
	}
	
	/**
	 * Set the multisample option for a entity's egdes.
	 */
	public void setMultisampleOption()
	{
		if(useMultisampleIn)
		{
			GlHelper.enableMultisample();
		} else {
			GlHelper.disableMultisample();
		}
	}
	
	/**
	 * Enable to render shadows.
	 */
	public void enableShadows()
	{
		useShadowsIn = true;
	}
	
	/**
	 * Disable to render shadows.
	 */
	public void disableShadows()
	{
		useShadowsIn = false;
	}
	
	/**
	 * Enable the multisaple anti-analisizig.
	 */
	public void enableMultisample()
	{
		useMultisampleIn = true;
	}
	
	/**
	 * Disable the multisaple anti-analisizig.
	 */
	public void disableMultisample()
	{
		useMultisampleIn = false;
	}
	
	/**
	 * Enabled the fullscreen mode.
	 */
	public void enableFullscreen()
	{
		useFullscreenIn = true;
	}
	
	/**
	 * Disable the fullscreen mode.
	 */
	public void disableFullscreen()
	{
		useFullscreenIn = false;
	}
	
	public void enableResizeDisplay()
	{
		resizeDisplayIn = true;
	}
	
	public void disableResizeDisplay()
	{
		resizeDisplayIn = false;
	}
	
	/**
	 * Enabled the vertical synczronization for a fullscreen mode.
	 */
	public static void enableVsync()
	{
		if(useFullscreenIn)
		{
			useVSync = true;
			Display.setVSyncEnabled(true);
		}
	}
	
	/**
	 * Disable the vertical synczronization for a windowed mode.
	 */
	public static void disableVsync()
	{
		if(!useFullscreenIn)
		{
			useVSync = false;
			Display.setVSyncEnabled(false);
		}
	}
	
	/**
	 * Enabled the framebuffer object.
	 */
	public void enableFbo()
	{
		useFboIn = true;
	}
	
	/**
	 * Disable the framebuffer object.
	 */
	public void disableFbo()
	{
		useFboIn = false;
	}
	
	/**
	 * Enabled the post-processing effects, like a contrast, blur or bloom.
	 */
	public void enablePostEffects()
	{
		usePostEffectsIn = true;
	}
	
	/**
	 * Disable the post-processing effects.
	 */
	public void disablePostEffects()
	{
		usePostEffectsIn = false;
	}
	
	/**
	 * Enabled render the skybox on the game.
	 */
	public void enableRenderSkyBox()
	{
		renderSkyBoxIn = true;
	}
	
	/**
	 * Disable render the skybox on the game.
	 */
	public void disableRenderSkyBox()
	{
		renderSkyBoxIn = false;
	}
	
	/**
	 * Enabled render the water tile.
	 */
	public void enableRenderWater()
	{
		renderWaterIn = true;
	}
	
	/**
	 * Disable render the water tile.
	 */
	public void disableRenderWater()
	{
		renderWaterIn = false;
	}
	
	public static void enableTriangleMode()
	{
		if(usePoligonModeIn)
		{
			GlHelper.enablePoligonMode();
		}
	}
	
	public static void disableTriangleMode()
	{
		if(!usePoligonModeIn)
		{
			GlHelper.disablePoligonMode();
		}
	}
	
	public void enableShadowGui()
	{
		useShadowGuiIn = true;
	}
	
	public void disableShadowGui()
	{
		useShadowGuiIn = false;
	}
	
	public void enableAudio()
	{
		useAudioIn = true;
	}
	
	public void disableAudio()
	{
		useAudioIn = false;
	}
	
	public void setDefaultLanguage()
	{
		languageIn = 0;
	}
	
	public float setRenderDistance()
	{
		RenderDistance d = new RenderDistance(5);
		renderDistanceIn = d.getRenderDistance();
			return renderDistanceIn;
	}

	public static void setGuiScaleLarge()
	{guiScaleLargeIn = true; guiScaleMediumIn = false; guiScaleSmallIn = false;}
	
	public static void setGuiScaleMedium()
	{guiScaleMediumIn = true; guiScaleSmallIn = false; guiScaleLargeIn = false;}
	
	public static void setGuiScaleSmall()
	{guiScaleSmallIn = true; guiScaleMediumIn = false; guiScaleLargeIn = false;}
	
	public static String craftixAutors()
	{
		String mainAutor;
		String othersAutors;
		String autorText;
		mainAutor = "Kenny";
		othersAutors = "Bogdan, ThinMatrix, TheChernoProject";
		autorText = mainAutor + ", " + othersAutors;
		
			return autorText;
	}
	
	public void eventButtonsYesNo()
	{
		GuiYesNo.checkYesFullscreen();
		GuiYesNo.checkYesAudio();
		GuiYesNo.checkYesFramebuffer();
		GuiYesNo.checkYesRenderSkybox();
		GuiYesNo.checkYesRenderWater();
	}
	
	/**
	 * Checks if the options file has exist.
	 */
	public void checkIfOptionsFileExist()
	{
		try
		{
			this.optionsFile.setWritable(true);
			
			if(!this.optionsFile.exists())
			{
				LOGGER.info("Options file not extis.");
				
				try
				{
					this.loadDefaultEngineOptions();
					this.optionsFile.createNewFile();
					this.saveOptions();
					this.loadOptions();
				}
				catch(Exception e)
				{
					hasError = true;
					LOGGER.error("Cant't load and create the 'options.txt' file! Make sure");
					LOGGER.error("you have a folder ' .craftix ' in 'appdata/roaming'", (Throwable)e);
					e.printStackTrace();
				}
				
				return;
			}
			
		}
		catch(Exception e)
		{
		}
	}
	
	public void increaseFov(boolean increase)
	{
		if(increase)
			fovIn += 1;
		else
			fovIn -= 1;
		if(fovIn > 110)
			fovIn = 110;
		if(fovIn < 30)
			fovIn = 30;
	}
	
	public void loadOptions()
	{
		try
		{	
			List<String> list = IOUtils.readLines(new FileInputStream(this.optionsFile));
			NBTTagCompound nbt = new NBTTagCompound();
			
			for(String s: list)
			{
				try
				{
					Iterator<String> iterator = COLON_SPLITTER.omitEmptyStrings().limit(2).split(s).iterator();
					nbt.setString(iterator.next(), iterator.next());
				}
				catch(Exception e)
				{
					LOGGER.warn("Skipping bad option: {}", (Object)s);
				}
			}
			
			nbt = this.dataFix(nbt);
			
			for(String s1 : nbt.getKeySet())
			{
				String s2 = nbt.getString(s1);
				
				try
				{
					if("guiScale".equals(s1))
					{
						guiScaleIn = Integer.parseInt(s2);
						if(guiScaleIn == 0)
						{
							setGuiScaleLarge();
						}
						if(guiScaleIn == 1)
						{
							setGuiScaleSmall();
						}
						if(guiScaleIn == 2)
						{
							setGuiScaleMedium();
						}
						if(guiScaleIn == 3)
						{
							setGuiScaleLarge();
						}
					}
					
					/**If true its ru lang if false its en lang.*/
					if("language".equals(s1))
					{
						languageIn = Integer.parseInt(s2);
					}
					
					if("fullscreenMode".equals(s1))
					{
						useFullscreenIn = "true".equals(s2);
					}
					
					if("resizebleMode".equals(s1))
					{
						resizeDisplayIn = "true".equals(s2);
					}
					
					if("framebuffer".equals(s1))
					{
						useFboIn = "true".equals(s2);
					}
					
					if("multisample".equals(s1))
					{
						useMultisampleIn = "true".equals(s2);
					}
					
					if("renderSkybox".equals(s1))
					{
						renderSkyBoxIn = "true".equals(s2);
					}
					
					if("renderWater".equals(s1))
					{
						renderWaterIn = "true".equals(s2);
					}
					
					if("maxFps".equals(s1))
					{
						maxFpsIn = Integer.parseInt(s2);
					}
					
					if("displayWidth".equals(s1))
					{
						displayWidthIn = Integer.parseInt(s2);
						if(displayWidthIn < 960)
						{
							displayWidthIn = 960;
							LOGGER.warn("Display width not be smaller that " + displayWidthIn);
							LOGGER.warn("Width set by: " + displayWidthIn);
						}
					}
					
					if("displayHeight".equals(s1))
					{
						displayHeightIn = Integer.parseInt(s2);
						if(displayHeightIn < 540)
						{
							displayHeightIn = 540;
							LOGGER.warn("Display height not be smaller that " + displayHeightIn);
							LOGGER.warn("Height set by: " + displayHeightIn);
						}
					}
					
					if("audio".equals(s1))
					{
						useAudioIn = "true".equals(s2);
					}
					
					if("fov".equals(s1))
					{
						fovIn = this.parseFloat(s2);
						if(fovIn < 30.0f || fovIn > 110.0f)
						{
							fovIn = 70.0f;
						}
					}
					
					if("fpsTabOnMenu".equals(s1))
					{
						fpsTabOnMenuIn = "true".equals(s2);
					}
					
					if("renderCursor".equals(s1))
					{
						renderCursorIn = "true".equals(s2);
					}
					if("renderDistance".equals(s1))
					{
						renderDistanceIn = Integer.parseInt(s2);
					}
					if("shadows".equals(s1))
					{
						useShadowsIn = "true".equals(s2);
					}
					if("renderParticles".equals(s1))
					{
						renderParticlesIn = "true".equals(s2);
					}
					if("usePostEffects".equals(s1))
					{
						usePostEffectsIn = "true".equals(s2);
					}
					if("useContrast".equals(s1))
					{
						useContrastIn = "true".equals(s2);
					}
					if("useBrightFilter".equals(s1))
					{
						useBloomIn = "true".equals(s2);
					}
					if("brightness".equals(s1))
					{
						brightnessIn = Integer.parseInt(s2);
					}
					if("useGaussianBlur".equals(s1))
					{
						useGaussianBlurIn = "true".equals(s2);
					}
					
					this.playerMovementOptions(s1, s2);
					this.saveSlotsOptions(s1, s2);
				
				}
				catch (Exception e)
				{
					LOGGER.warn("Skipping bad option: {}:{}", s1, s2);
				}
			}
			
		}
		catch(Exception e)
		{
			LOGGER.error("Failed to load options", (Throwable)e);
		}
	}
	
	public void playerMovementOptions(String s1, String s2)
	{
		if("keyboard.movement.forward".equals(s1))
		{
			keyboardMovementForwardIn = Integer.parseInt(s2);
		}
		if("keyboard.movement.backward".equals(s1))
		{
			keyboardMovementBackwardIn = Integer.parseInt(s2);
		}
		if("keyboard.movement.leftward".equals(s1))
		{
			keyboardMovementLeftwardIn = Integer.parseInt(s2);
		}
		if("keyboard.movement.rightward".equals(s1))
		{
			keyboardMovementRightwardIn = Integer.parseInt(s2);
		}
		if("keyboard.movement.run".equals(s1))
		{
			keyboradMovementRunIn = Integer.parseInt(s2);
		}
		if("keyboard.movement.jump".equals(s1))
		{
			keyboradMovementJumpIn = Integer.parseInt(s2);
		}
		if("keyboard.pause".equals(s1))
		{
			keyboardPauseIn = Integer.parseInt(s2);
		}
	}
	
	public void saveSlotsOptions(String s1, String s2)
	{
		if("slot0".equals(s1))
		{
			slot0 = "true".equals(s2);
		}
		if("slot1".equals(s1))
		{
			slot1 = "true".equals(s2);
		}
		if("slot2".equals(s1))
		{
			slot2 = "true".equals(s2);
		}
		if("slot3".equals(s1))
		{
			slot3 = "true".equals(s2);
		}
		if("slot4".equals(s1))
		{
			slot4 = "true".equals(s2);
		}
	}

	/**
	 * Saves the options to options file.
	 */
	public static void saveOptions()
	{
		PrintWriter printwriter = null;
		
		try
		{
			printwriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(optionsFile), StandardCharsets.UTF_8));
			printwriter.println("[Craftix Engine Options]");
			printwriter.println("craftixVersion:" + SETTINGS_CRAFTIX_VERSION);
			printwriter.println("autors:" + craftixAutors());
			printwriter.println("displayWidth:" + displayWidthIn);
			printwriter.println("displayHeight:" + displayHeightIn);
			printwriter.println("resizebleMode:" + resizeDisplayIn);
			printwriter.println("[Graphics Options]");
			printwriter.println("fullscreenMode:" + useFullscreenIn);
			printwriter.println("framebuffer:" + useFboIn);
			printwriter.println("usePostEffects:" + usePostEffectsIn);
			printwriter.println("useContrast:" + useContrastIn);
			printwriter.println("useGaussianBlur:" + useGaussianBlurIn);
			printwriter.println("useBrightFilter:" + useBloomIn);
			printwriter.println("multisample:" + useMultisampleIn);
			printwriter.println("vSync:" + useVSync);
			printwriter.println("guiScale:" + guiScaleIn);
			printwriter.println("renderSkybox:" + renderSkyBoxIn);
			printwriter.println("renderWater:" + renderWaterIn);
			printwriter.println("maxFps:" + maxFpsIn);
			printwriter.println("renderDistance:" + renderDistanceIn);
			printwriter.println("fov:" + fovIn);
			printwriter.println("shadows:" + useShadowsIn);
			printwriter.println("renderParticles:" + renderParticlesIn);
			printwriter.println("brightness:" + brightnessIn);
			printwriter.println("[Audio Options]");
			printwriter.println("audio:" + useAudioIn);
			printwriter.println("[Language Options]");
			printwriter.println("language:" + languageIn);
			printwriter.println("[Other Options]");
			printwriter.println("fpsTabOnMenu:" + fpsTabOnMenuIn);
			printwriter.println("renderCursor:" + renderCursorIn);
			printwriter.println("[Keyboard Options]");
			printwriter.println("keyboard.movement.forward:" + keyboardMovementForwardIn);
			printwriter.println("keyboard.movement.backward:" + keyboardMovementBackwardIn);
			printwriter.println("keyboard.movement.leftward:" + keyboardMovementLeftwardIn);
			printwriter.println("keyboard.movement.rightward:" + keyboardMovementRightwardIn);
			printwriter.println("keyboard.movement.run:" + keyboradMovementRunIn);
			printwriter.println("keyboard.movement.jump:" + keyboradMovementJumpIn);
			printwriter.println("keyboard.pause:" + keyboardPauseIn);
			printwriter.println("[Save Slots Options]");
			printwriter.println("slot0:" + slot0);
			printwriter.println("slot1:" + slot1);
			printwriter.println("slot2:" + slot2);
			printwriter.println("slot3:" + slot3);
			printwriter.println("slot4:" + slot4);
		}
		catch(Exception e)
		{
			LOGGER.error("Failed to save options", (Throwable)e);
			e.printStackTrace();
		}
		finally
		{
			IOUtils.closeQuietly((Writer)printwriter);
		}
	}
	
	/**
     * Sets a key binding and then saves all settings.
     */
    public void setOptionKeyBinding(KeyBinding key, int keyCode)
    {
        key.setKeyCode(keyCode);
       // this.saveOptions();
    }
	
	 private NBTTagCompound dataFix(NBTTagCompound p_189988_1_)
	    {
	        int i = 0;

	        try
	        {
	            i = Integer.parseInt(p_189988_1_.getString("craftixVersion"));
	        }
	        catch (RuntimeException var4)
	        {
	            ;
	        }
	        	return this.cx.getDataFixer().process(FixTypes.OPTIONS, p_189988_1_, i);
	    }
	 
	 	/**
	     * Parses a string into a float.
	     */
	    public float parseFloat(String str)
	    {
	        if ("true".equals(str))
	        {
	            return 1.0F;
	        }
	        else
	        {
	            return "false".equals(str) ? 0.0F : Float.parseFloat(str);
	        }
	    }
	 
	 public static void setOptionFloatValue(InGameSettings.Options option, float valueIn)
	 {
		 if(option == InGameSettings.Options.OPTION_RENDER_DISTANCE)
		 {
			 renderDistanceIn = (int) valueIn;
		 }
	 }
	    
	 public float getOptionFloatValue(InGameSettings.Options option)
	 {
		 float nullOptionValue = 0f;
		 
		 if(option == InGameSettings.Options.OPTION_RENDER_DISTANCE)
		 {
			 return renderDistanceIn;
		 }
		 else
		 {
			 return nullOptionValue;
		 }
	 }
	 
	 public static enum Options
	 {
		 OPTION_FULLSCREEN("option.fullscreen", false, true),
		 OPTION_RENDER_DISTANCE("option.renderDistance", true, false, 1.0F, 12.0F, 1.0F),
		 OPTION_FOV("option.fov", true, false, 30.0F, 110.0F, 1.0F);
		 
		 private String string;
		 private boolean enumFloat;
		 private boolean enumBoolean;
		 private float valueCurrent;
		 private float valueMin;
		 private float valueMax;
		 
		 private Options(String optionName, boolean isFloat, boolean isBool)
		 {
			 this(optionName, isFloat, isBool, 0.0F, 1.0F, 0.0F);
		 }
		 
		 private Options(String optionName, boolean isFloat, boolean isBool, float valMin, float valMax, float valStep)
		 {
			 this.string = optionName;
			 this.enumFloat = isFloat;
			 this.enumBoolean = isBool;
			 this.valueCurrent = valStep;
			 this.valueMin = valMin;
			 this.valueMax = valMax;

		 }

		 public String getString() 
		 {
			return string;
		 }
		 
		 public float getMaxValue()
		 {
			 return this.valueMax;
		 }
		 
		 public float getMinValue()
		 {
			 return this.valueMin;
		 }
		 
		 public float getValue()
		 {
			 return this.valueCurrent;
		 }
		 
		 public boolean getEnumFloat()
	     {
	         return this.enumFloat;
	     }

	     public boolean getEnumBoolean()
	     {
	         return this.enumBoolean;
	     }
	     
	     public int returnEnumOrdinal()
	     {
	         return this.ordinal();
	     }
		 
		 public float normalizeValue(float value)
	     {
	         return Maths.clamp((this.snapToStepClamp(value) - this.valueMin) / (this.valueMax - this.valueMin), 0.0F, 1.0F);
	     }

	     public float denormalizeValue(float value)
	     {
	         return this.snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * Maths.clamp(value, 0.0F, 1.0F));
	     }

	     public float snapToStepClamp(float value)
	     {
	         value = this.snapToStep(value);
	         return Maths.clamp(value, this.valueMin, this.valueMax);
	     }
	     
	     private float snapToStep(float value)
	     {
	         if (this.valueCurrent > 0.0F)
	         {
	             value = this.valueCurrent * (float)Math.round(value / this.valueCurrent);
	         }

	         return value;
	     }

	 }
}
