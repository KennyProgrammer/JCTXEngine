package com.kenny.craftix.client.scenes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.audio.Source;
import com.kenny.craftix.client.audio.sound.SoundLoader;
import com.kenny.craftix.client.collision.AxisAlignedBB;
import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.entity.EntityRegister;
import com.kenny.craftix.client.entity.Light;
import com.kenny.craftix.client.entity.player.EntityPlayer;
import com.kenny.craftix.client.entity.player.PlayerSkin;
import com.kenny.craftix.client.gui.GuiBackground;
import com.kenny.craftix.client.gui.GuiControls;
import com.kenny.craftix.client.gui.GuiControlsKeyChange;
import com.kenny.craftix.client.gui.GuiCreateWorld;
import com.kenny.craftix.client.gui.GuiGame;
import com.kenny.craftix.client.gui.GuiGameOver;
import com.kenny.craftix.client.gui.GuiGlOptions;
import com.kenny.craftix.client.gui.GuiGraphics;
import com.kenny.craftix.client.gui.GuiInGameMenu;
import com.kenny.craftix.client.gui.GuiLanguage;
import com.kenny.craftix.client.gui.GuiLoadingSplash;
import com.kenny.craftix.client.gui.GuiMainMenu;
import com.kenny.craftix.client.gui.GuiOptions;
import com.kenny.craftix.client.gui.GuiOtherOptions;
import com.kenny.craftix.client.gui.GuiPlayerStats;
import com.kenny.craftix.client.gui.GuiRenderManager;
import com.kenny.craftix.client.gui.GuiScreen;
import com.kenny.craftix.client.gui.GuiBackground.Pages;
import com.kenny.craftix.client.gui.inventory.GuiInventory;
import com.kenny.craftix.client.keyboard.KeyboardUserInput;
import com.kenny.craftix.client.particles.ParticleMaster;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.WorldRenderer;
import com.kenny.craftix.client.renderer.GlHelper.Framebuffer;
import com.kenny.craftix.client.renderer.postEffects.PostProcessing;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.client.text.TextLanguage;
import com.kenny.craftix.command.Command;
import com.kenny.craftix.gameplay.GameTime;
import com.kenny.craftix.gameplay.event.EventList;
import com.kenny.craftix.init.EntityInit;
import com.kenny.craftix.init.ParticleInit;
import com.kenny.craftix.inventory.Inventory;
import com.kenny.craftix.main.Side;
import com.kenny.craftix.main.SideMachine;
import com.kenny.craftix.server.CraftixMP;
import com.kenny.craftix.server.PacketBuffer;
import com.kenny.craftix.server.world.WorldServer;
import com.kenny.craftix.server.world.WorldServerReceive;
import com.kenny.craftix.server.world.WorldServerSend;
import com.kenny.craftix.utils.MousePicker;
import com.kenny.craftix.utils.Timer;
import com.kenny.craftix.world.SaveSlotLoader;
import com.kenny.craftix.world.World;
import com.kenny.craftix.world.skybox.SkyboxSunMoonShader;
import com.kenny.craftix.world.water.WaterRenderer;
import com.kenny.craftix.world.water.Water;

@SideMachine(Side.CLIENT)
public class WorldScene extends Scene implements IReloadble, IWorldScene
{
	protected static final Logger LOGGER = LogManager.getLogger(Craftix.LOGGER_INSTANCE);
	public List<Water> waters = new ArrayList<Water>();
	public List<Light> lights = new ArrayList<Light>();
	public List<Source> sounds = new ArrayList<Source>();
	public Craftix cx;
	/**Old initialization of the guis.*/
	private GuiGame guiPlayerStatsO;
	/**Load all particle in the game*/
	public ParticleInit particleInit;
	private WaterRenderer waterRenderer;
	@SuppressWarnings("unused")
	private SkyboxSunMoonShader skyboxSunMoonShader;
	/**Load all entities to the game*/
	private EntityInit entityInit;
	/**Load all terrain and water to the game.*/
	public World world;
	public Light pickerLight;
	/**It displays everything visually in the game. Fog, shaders, etc.*/
	private WorldRenderer worldRenderer;
	public Timer timer = new Timer();
	/**Put the mouse from 2d in 3d space world.*/
	private MousePicker picker;
	/**Just generate and return random numbers.*/
	public Random random = new Random(676452);
	/**Its a terrain point for set the lights or pickers*/
	public Vector3f terrainPoint;
	public GuiScreen guiScreenOld;
	private GuiInGameMenu guiInGameMenu = new GuiInGameMenu();
	private GuiGameOver guiGameOver = new GuiGameOver();
	/**This not setup a inventory gui its just instance for some methods.*/
	public GuiInventory guiInventory;
	private Inventory invenotry = new Inventory("Instance", 9999, this.cx);
	private Command command;
	public GameTime worldTime;
	public static boolean inGameOptions = false;
	public boolean gamePause;
	public PlayerSkin playerSkin;
	public TextLanguage textLanguage = new TextLanguage();
	/**Init the Mainmenu gui screen page.*/
	public GuiMainMenu guiMainScreen;
	public GuiOptions guiOptions = new GuiOptions();
	public GuiGraphics guiGraphics = new GuiGraphics();
	public GuiOtherOptions guiOtherOptions = new GuiOtherOptions();
	public GuiControls guiControls = new GuiControls();
	public GuiControlsKeyChange guiControlsKeyChange = new GuiControlsKeyChange();
	public GuiLanguage guiLanguage = new GuiLanguage();
	public GuiGlOptions guiGlOptions = new GuiGlOptions();
	public GuiPlayerStats guiPlayerStats = new GuiPlayerStats();
	/**This Gui Screen Object.*/
	public GuiBackground guiScreen;
	public KeyboardUserInput keyboard = new KeyboardUserInput();
	/**Check if player actually loaded and rendered in the world.*/
	public boolean playerIn;
	/**Entity base register helper. Help registered entity in the list.*/
	protected EntityRegister entityRegister;
	private CraftixMP cxMp;
	protected PacketBuffer packetBuffer;
	/**Server world side connection instance.*/
	public WorldServer worldServer;
	/**Set generation world for server world.*/
	public int worldSeed;
	public AxisAlignedBB aabbPlayer;
	public static boolean isLoadWorld;
	public static boolean worldInitCompleted = false;
	/**Load all playlist of scene music.*/
	private boolean loadPlaylist;
	//
	private EntityCamera worldCamera;
	//
	
	public WorldScene()
	{
		super("WorldScene", 4500f);
	}
	
	@Override
	public void loadScene(Craftix craftixIn) 
	{
		this.cx = craftixIn;
		this.guiMainScreen = (GuiMainMenu) new GuiMainMenu(null);
		this.cxMp = this.cx.getServer();
		this.worldServer = new WorldServer();
		this.packetBuffer = new PacketBuffer(this.cx);
		this.worldSeed = this.random.nextInt(1000000000);
		if(this.cx.status.isServer())
		{
			this.cxMp.isConnected = true;
		
			if(this.cxMp.isHost)
			{
				new WorldServerSend(this.cx, this.cxMp.out);
				new WorldServerReceive(this.cx, this.cxMp.in);
			} 
			else if(!this.cxMp.isHost)
			{
				new WorldServerReceive(this.cx, this.cxMp.in);
				new WorldServerSend(this.cx, this.cxMp.out);
			}
		}
		
		this.playerSkin = new PlayerSkin("skins/playerSkin4");
		this.world = new World(this);
		this.otherSetup();
		this.scenePlaylist(true);
		
	}
	
	@Override
	public void renderScene() 
	{
		if(this.guiScreen == null) 
		{
			try 
			{
				this.guiMainScreen.handleInput(this.cx);
			} 
			catch (IOException e) 
			{
				LOGGER.warn("Can't create a handle input system!");
				e.printStackTrace();
			}
		}
		
		this.world.getRenderer().renderShadowMap(this.objects, this.world.getRenderer().getSunLightSource());
		GlHelper.enableClipDistance();
		this.renderReflectionScene();
		this.renderRefractionScene();
		GlHelper.disableClipDistance();
		this.world.getWaterRenderer().unbindWaterFramebuffer();
		if(InGameSettings.useFboIn)
		{
			this.cx.getMsFramebuffer().bindFrameBuffer();
		}
		this.world.getRenderer().renderWorld(this, this.world.getRenderer().createClipPlaneVector(0F, 1F, 0F, 100000F));
		if(InGameSettings.useFboIn)
		{
			this.cx.getMsFramebuffer().unbindFrameBuffer();
			this.cx.getMsFramebuffer().resolveToFrameBuffer(Framebuffer.COLOR_ATTACHMENT0, this.cx.getFramebuffer01());
			this.cx.getMsFramebuffer().resolveToFrameBuffer(Framebuffer.COLOR_ATTACHMENT1, this.cx.getFramebuffer02());
			if(InGameSettings.usePostEffectsIn)
				this.cx.renderPostProcessingEffects();
		}
		
		this.world.getPlayer().onLivingUpdate();
		this.world.isPause();
		this.playNextSound();
		
	}
	
	/**
	 * Render a part of the scene with the reflection for the water. It turns out it loads 
	 * the sky, and a small number of objects.
	 */
	public void renderReflectionScene()
	{
		this.world.getRenderer().getWaterFramebuffer().bindReflectionFrameBuffer();
		float distance = 2 * (this.world.getPlayerCamera().getPosition().y - this.waters.get(0).getHeight());
		this.world.getPlayerCamera().getPosition().y -= distance;
		this.world.getPlayerCamera().invertPicth();
		this.world.getRenderer().renderWorld(this, this.world.getRenderer().createClipPlaneVector(0, 1F, 0F, -this.waters.get(0).getHeight() + 1.0F));
		this.world.getPlayerCamera().getPosition().y += distance;
		this.world.getPlayerCamera().invertPicth();
	}
	
	/**
	 * Render everything that is under water. It turns out that some terrane bushes and a 
	 * small number of objects in low resolution.
	 */
	public void renderRefractionScene()
	{
		this.world.getRenderer().getWaterFramebuffer().bindRefractionFrameBuffer();
		this.world.getRenderer().renderWorld(this, this.world.getRenderer().createClipPlaneVector(0F, -1F, 0F, this.waters.get(0).getHeight() + 0.2F));
	}

	
	public void onUpdate()
	{
		this.guiScreenOld.onUpdate();
		this.guiPlayerStats.updateAnimationRTL(this.getPlayer(), 4);
		this.guiPlayerStats.updateAnimationLTR(this.getPlayer(), 2);
		this.guiPlayerStats.updateAnimationRTL(this.getPlayer(), 5);
		
		if(Keyboard.isKeyDown(Keyboard.KEY_P))
		{
			this.getPlayer().setObjectSize(1.1F);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
		{
			if(!this.cx.isGamePause)
			{
				if(this.getPlayer().useUnlimitedMode)
				{;}
				else
				{
					this.getPlayer().healthIn = this.getPlayer().healthIn + 0.5f;
				}
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
		{
			if(!this.cx.isGamePause)
			{
				if(this.getPlayer().useUnlimitedMode)
				{;}
				else
				{
					this.getPlayer().healthIn = this.getPlayer().healthIn - 0.5f;
					if(this.getPlayer().healthIn <= 0.1f)
						EventList.eventPlayerDeadHealth = true;
				}
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_9))
		{
			if(!this.cx.isGamePause)
			{
				if(this.getPlayer().useUnlimitedMode)
				{;}
				else
				{
					this.getPlayer().playerHunger = this.getPlayer().playerHunger - 0.5f;
				}
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_0))
		{
			if(!this.cx.isGamePause)
			{
				if(this.getPlayer().useUnlimitedMode)
				{;}
				else
				{
					this.getPlayer().playerHunger = this.getPlayer().playerHunger + 0.5f;
				}
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_Z))
		{this.command.commandKill.runCommand(this);}
		if(Keyboard.isKeyDown(Keyboard.KEY_X))
		{this.command.commandChangeGameMode.runCommand(this, 1);}
		if(Keyboard.isKeyDown(Keyboard.KEY_C))
		{this.command.commandChangeGameMode.runCommand(this, 0);}
		if(Keyboard.isKeyDown(Keyboard.KEY_V))
		{this.command.commandSpawnEntity.runCommand(0, entityInit.entityOakTree, terrainPoint.x, terrainPoint.y, terrainPoint.z, 0f, 0f, 0f, 1f);}
		this.increaseSunLightPosition(Keyboard.KEY_NUMPAD1, Keyboard.KEY_NUMPAD3, Keyboard.KEY_NUMPAD4, Keyboard.KEY_NUMPAD6, Keyboard.KEY_NUMPAD7, Keyboard.KEY_NUMPAD9);

	}
	
	/**
	 * Render each screen page according to the conditions.
	 */
	private void renderScreenPages()
	{
		this.getPlayer().renderUnderwaterBackgroundEffect();
		if(this.getPlayer().gamemode.currentGamemode == 0)
		{
			this.cx.guiRenderer.render(this.guiPlayerStats.texturesH);
			if(this.getPlayer().playerBreath < 100f)
				this.cx.guiRenderer.render(this.guiPlayerStats.texturesB);
		}
		
		if(GuiRenderManager.renderInventoryBar)
		{
			this.cx.guiRenderer.render(this.guiInventory.inventoryBar.inventoryBar);
		}
		if(GuiRenderManager.renderInventoryBack)
		{
			this.cx.guiRenderer.render(this.guiInventory.inventoryBackground.inventoryBack);
		}
		if(GuiRenderManager.renderInventoryFlyMode)
		{
			this.cx.guiRenderer.render(this.guiInventory.inventoryFlyingMode.inventory);
		}
		
		if(Pages.isMainGamePausePage)
		{
			this.cx.isGamePause = true;
			this.cx.guiRenderer.render(this.guiInGameMenu.textures);	
		}
		if(Pages.isLanguagePage || Pages.isOptionsPage || Pages.isGlOptionsPage || Pages.isGraphicsPage || Pages.isOtherOptionsPage || Pages.isControlsPage || Pages.isControlsKeyChangePage)
		{
			this.cx.guiRenderer.render(this.guiMainScreen.background);
		}
		if(Pages.isOptionsPage)
		{
			this.cx.guiRenderer.render(this.guiOptions.textures);
		}
		if(Pages.isGraphicsPage)
		{
			this.cx.guiRenderer.render(this.guiGraphics.textures);
		}
		if(Pages.isOtherOptionsPage)
		{
			this.cx.guiRenderer.render(this.guiOtherOptions.textures);
		}
		if(Pages.isControlsPage)	
		{
			this.cx.guiRenderer.render(this.guiControls.textures);
		}
		if(Pages.isControlsKeyChangePage)
		{
			this.cx.guiRenderer.render(this.guiControlsKeyChange.textures);
		}
		if(Pages.isLanguagePage)
		{
			this.cx.guiRenderer.render(this.guiLanguage.textures);
		}
		if(Pages.isGlOptionsPage)
		{
			this.cx.guiRenderer.render(this.guiGlOptions.textures);
		}
		if(Pages.isGameOverPage)
		{
			this.cx.guiRenderer.render(this.guiGameOver.textures);
		}
	}

	public void saveWorld()
	{
		Pages.isSaveWorldPage = true;
		this.cx.updateSaveWorldDisplay();
	}

	@Override
	public void reloadScene() 
	{
		this.entityInit = new EntityInit();
		this.world = new World(this);
			
		GuiCreateWorld.removePage();
		GuiLoadingSplash.progress25 = true;
		this.cx.guiLoading.setAnimationBar();
		this.cx.updateLoadingSplashDisplay();
		GuiLoadingSplash.progress25 = false;
		this.world.generateWorld(this.cx, this.world.getTerrainOptions());
		GuiLoadingSplash.progress50 = true;
		this.cx.guiLoading.setAnimationBar();
		this.cx.updateLoadingSplashDisplay();
		GuiLoadingSplash.progress50 = false;
		this.entityInit.loadEntity(this);
		GuiLoadingSplash.progress75 = true;
		this.cx.guiLoading.setAnimationBar();
		this.particleInit.loadParticles();
		//this.worldRenderer = new WorldRenderer(this.cx, this.getCamera());
		ParticleMaster.init(this.worldRenderer.getProjectionMatrix());
		PostProcessing.init(Display.getWidth(), Display.getHeight());
		//this.generateEntitiesOnTerrain();
		this.otherSetup();
		this.cx.updateLoadingSplashDisplay();
		GuiLoadingSplash.progress75 = false;
		GuiRenderManager.renderGame = true;
		SoundLoader.soundPlay(SoundLoader.sourceInGameSound2, SoundLoader.bufferInGameSound2);
		
	}
	
	@Override
	public void otherSetup() 
	{
		/**Setup lights sources*/		 //10000, 10000, -10000
		
		this.pickerLight = (new Light(new Vector3f(293, 7, -305), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
		
		this.lights.add(pickerLight);
		this.lights.add(new Light(new Vector3f(185, 10, -293), new Vector3f(2, 0, 0), new Vector3f(1, 0.01f, 0.002f)));
		this.lights.add(new Light(new Vector3f(370, 17, -300), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
		this.lights.add(new Light(new Vector3f(293, 7, -305), new Vector3f(2, 2, 0), new Vector3f(1, 0.01f, 0.002f)));
		
		/**Setup MousePicker*/
		//this.picker = new MousePicker(this.getCamera(), this.worldRenderer.getProjectionMatrix(), this.world.worldTerrain);

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
	 * Generate entity on terrain height.
	 *
	public void generateEntitiesOnTerrain()
	{
		for (int i = 0; i < 2500; i++) 
		{
			if(i % 20 == 0)
			{
				float x = random.nextFloat() * 2000 - 0;
				float z = random.nextFloat() * -2000;
				float y = this.world.worldTerrain.getHeightOfTerrain(x, z);
				this.entityRegister.objects.add(new Ob(this.entityInit.entityFern, random.nextInt(4), new Vector3f(x,y,z), 0, random.nextFloat() * 360,
						0, 0.9f));
			}
			if(i % 10 == 0)
			{
				float x = random.nextFloat() * 2000 - 0;
				float z = random.nextFloat() * -2000;
				float y = this.world.worldTerrain.getHeightOfTerrain(x, z);
				this.entityRegister.objects.add(new EntityOld(this.entityInit.entityPineTree, x,y,z, 0, random.nextFloat() * 360,
						0, 5f));
			}
			if(i % 10 == 0)
			{
				float x = random.nextFloat() * 2000 - 0;
				float z = random.nextFloat() * -2000;
				float y = this.world.worldTerrain.getHeightOfTerrain(x, z);
				this.entityRegister.objects.add(new EntityOld(this.entityInit.entityOakTree, x,y,z, 0, random.nextFloat() * 360,
						0, 0.9f));
			}
			if(i % 5 == 0)
			{
				float x = random.nextFloat() * 2000 - 0;
				float z = random.nextFloat() * -2000;
				float y = this.world.worldTerrain.getHeightOfTerrain(x, z);
				this.entityRegister.objects.add(new EntityOld(this.entityInit.entityGrass, random.nextInt(16), new Vector3f(x,y,z), 0, random.nextFloat() * 360,
						0, 1.8f));
			}
			if(i % 15 == 0)
			{
				float x = random.nextFloat() * 2000 - 0;
				float z = random.nextFloat() * -2000;
				float y = this.world.worldTerrain.getHeightOfTerrain(x, z);
				this.entityRegister.objects.add(new EntityOld(this.entityInit.entityCherryTree, random.nextInt(16), new Vector3f(x,y,z), 0, random.nextFloat() * 360,
						0, 2f));
			}
			
			if(i % 80 == 0)
			{
				float x = random.nextFloat() * 2000 - 0;
				float z = random.nextFloat() * -2000;
				float y = this.world.worldTerrain.getHeightOfTerrain(x, z);
				this.entityRegister.objects.add(new EntityOld(this.entityInit.entityLattern, random.nextInt(16), new Vector3f(x,y,z), 0, random.nextFloat() * 360,
						0, 1f));
			}
			
		}
	}
	*/

	@Override
	public void cleanUpScene() 
	{
		InGameSettings.usePoligonModeIn = false;
		InGameSettings.disableTriangleMode();
		GuiRenderManager.renderOptionsMenu = false;
		PostProcessing.cleanUp();
		ParticleMaster.cleanUp();
		this.waterRenderer.cleanUp();
		this.worldRenderer.cleanUp();
		
	}

	@Override
	public void renderOptions() 
	{
		
	}
	
	public void increaseSunLightPosition(int keyXp, int keyXm,
			int keyYp, int keyYm, int keyZp, int keyZm)
	{
		if(Keyboard.isKeyDown(keyXp)) 
		{
			float x = this.world.getRenderer().getSunLightSource().getPosition().x;
			float y;
			float z;
			x = x + 10;
			y = this.world.getRenderer().getSunLightSource().getPosition().y;
			z = this.world.getRenderer().getSunLightSource().getPosition().z;
			this.world.getRenderer().getSunLightSource().setPosition(new Vector3f(x, y, z));
		}
		if(Keyboard.isKeyDown(keyXm)) 
		{
			float x = this.world.getRenderer().getSunLightSource().getPosition().x;
			float y;
			float z;
			x =  x - 10;
			y = this.world.getRenderer().getSunLightSource().getPosition().y;
			z = this.world.getRenderer().getSunLightSource().getPosition().z;
			this.world.getRenderer().getSunLightSource().setPosition(new Vector3f(x, y, z));
		}
		if(Keyboard.isKeyDown(keyYp)) 
		{
			float x;
			float y = this.world.getRenderer().getSunLightSource().getPosition().y;
			float z;
			x = this.world.getRenderer().getSunLightSource().getPosition().x;
			y = y + 10;
			z = this.world.getRenderer().getSunLightSource().getPosition().z;
			this.world.getRenderer().getSunLightSource().setPosition(new Vector3f(x, y, z));
		}
		if(Keyboard.isKeyDown(keyYm)) 
		{
			float x;
			float y = this.world.getRenderer().getSunLightSource().getPosition().y;
			float z;
			x = this.world.getRenderer().getSunLightSource().getPosition().x;
			y = y - 10;
			z = this.world.getRenderer().getSunLightSource().getPosition().z;
			this.world.getRenderer().getSunLightSource().setPosition(new Vector3f(x, y, z));
		}
		if(Keyboard.isKeyDown(keyZp)) 
		{
			float x;
			float y;
			float z = this.world.getRenderer().getSunLightSource().getPosition().z;
			x = this.world.getRenderer().getSunLightSource().getPosition().x;
			y = this.world.getRenderer().getSunLightSource().getPosition().y;
			z = z + 100;
			this.world.getRenderer().getSunLightSource().setPosition(new Vector3f(x, y, z));
		}
		if(Keyboard.isKeyDown(keyZm)) 
		{
			float x;
			float y;
			float z = this.world.getRenderer().getSunLightSource().getPosition().z;
			x = this.world.getRenderer().getSunLightSource().getPosition().x;
			y = this.world.getRenderer().getSunLightSource().getPosition().y;
			z = z - 100;
			this.world.getRenderer().getSunLightSource().setPosition(new Vector3f(x, y, z));
		}
	}
	
	public void setGamePause(boolean gamePauseIn)
	{
		this.gamePause = gamePauseIn;
	}
	
	public EntityPlayer getPlayer()
	{
		return this.world.getPlayer();
	}
	
	public WorldRenderer getRenderer()
	{
		return this.world.getRenderer();
	}
	
	/**
	 * Return the actually time in miliseconds current world or worldScene.
	 */
	public float getWorldTime()
	{
		return this.getRenderer().getSkyboxRenderer().getDayCycleTime();
	}
	
	/**
	 * Changes the time every second or simply sets the specified time.
	 */
	public float increaseWorldTime(float increaseTimeIn)
	{
		return this.getRenderer().getSkyboxRenderer().setDayCicleTime(increaseTimeIn);
	}
	
	/**
	 * Return the instance of the commands.
	 */
	public Command getTheCommand()
	{
		return this.command;
	}
	
	/**
	 * Return the main light source in the scene "Sun".
	 */
	public Light getSunLightSource()
	{
		return this.world.getRenderer().getSunLightSource();
	}

	public Inventory getInvenotry() 
	{
		return invenotry;
	}
	
	/**
	 * Check if player actually loaded to the world.
	 */
	public void playerIn(boolean flag)
	{
		 this.playerIn = flag;
	}
	
	public EntityRegister getGameObject()
	{
		return this.entityRegister;
	}
	
	/**
	 * Return the main standard camera for testing.
	 */
	public EntityCamera getStandardCamera()
	{
		return this.worldCamera;
	}
	
	/**
	 * Return the curent playable world. The Foundation of all other components of the world.
	 */
	public World getWorld()
	{
		return this.world;
	}
	
	/**
	 * Current scene music playlist.
	 */
	public void scenePlaylist(boolean loadPlaylistIn)
	{
		this.loadPlaylist = loadPlaylistIn;
		if(this.loadPlaylist)
		{
			this.sounds.add(SoundLoader.sourceInGameSound1);
			this.sounds.add(SoundLoader.sourceInGameSound2);
		}
		else 
		{
			if(!this.sounds.isEmpty())
				this.sounds.removeAll(this.sounds);
		}
	}
	
	public void playFromPlaylist()
	{
		int i = 0;
		int j = 0;
		if(!this.sounds.isEmpty() && !this.sounds.get(i).isPlaying()) 
		{
			this.sounds.get(i).play(SoundLoader.bufferInGameSound1);
			if(!this.sounds.get(i).isPlaying())
			{
				i = i + 1;
				j = j + 1;
			}
		}
		if(i == 1 && j == 1)
		{
			this.sounds.get(i).play(SoundLoader.bufferInGameSound2);
		}
	}
	
	public void playNextSound() 
	{
		if(!this.sounds.isEmpty())
		{
			if(!this.sounds.get(0).isPlaying())
				this.sounds.get(0).play(SoundLoader.bufferInGameSound1);
			if(!this.sounds.get(0).isPlaying())
				this.sounds.get(1).play(SoundLoader.bufferInGameSound2);
		}
	}
}
