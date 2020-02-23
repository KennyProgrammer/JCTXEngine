package com.kenny.craftix.world;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.audio.sound.SoundLoader;
import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.entity.EntityRegister;
import com.kenny.craftix.client.entity.player.EntityPlayer;

import com.kenny.craftix.client.loader.TexturesLoader;
import com.kenny.craftix.client.particles.ParticleMaster;
import com.kenny.craftix.client.renderer.WorldRenderer;
import com.kenny.craftix.client.renderer.postEffects.PostProcessing;
import com.kenny.craftix.client.renderer.textures.ModelTexture;
import com.kenny.craftix.client.scenes.MainMenuScene;
import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.command.Command;
import com.kenny.craftix.gameplay.GameTime;
import com.kenny.craftix.init.ParticleInit;
import com.kenny.craftix.world.sun.Sun;
import com.kenny.craftix.world.terrain.Terrain;
import com.kenny.craftix.world.terrain.TerrainOptions;
import com.kenny.craftix.world.terrain.TerrainTexturePack;
import com.kenny.craftix.world.terrain.TerrainTexturePackLoader;
import com.kenny.craftix.world.water.Water;
import com.kenny.craftix.world.water.WaterRenderer;

public class World 
{
	public static final Logger LOGGER = LogManager.getLogger(Craftix.LOGGER_INSTANCE);
	/**This is a visualization of what the map will look like.*/
	private static final String BLEND_MAP = "maps/blendMap";
	private static final String HEIGHT_MAP = "maps/heightMap";
	public List<Terrain> terrains = new ArrayList<Terrain>();

	/**Is the core to load the rest of the game.*/
	private TexturesLoader textureLoader = new TexturesLoader();
	public Water water;
	public String worldName;
	/**Type of generation world. Like a biomes in Minecraft().run()*/
	private int worldType;
	/**Random number of player input seed generation.*/
	private int worldSeed;
	public int playerGamemode;
	/**Load the terrain options, before actually generation.*/
	public static TerrainOptions terrainOptions;
	/**Initialization the terrains.*/
	public Terrain terrain;
	protected Terrain worldTerrainPre;
	public Terrain worldTerrain2;
	public static Terrain worldTerrainStatic;
	public File worldDataFile;
	/**Save all data about this world.*/
	private SavingWorld savingWorld;
	/**Helping instance of this class.*/
	private EntityPlayer player;
	public World instance;
	public WorldScene worldScene;
	/**Help render object in the world to the screen.*/
	private WorldRenderer renderer;
	/**Instance of the commands.*/
	private Command command;
	/**Startup the time when player loading to the world.*/
	public GameTime worldTime;
	public EntityRegister entityRegister;
	protected int preparing;
	protected int loading;
	/**Check on cleanig up world renderers.*/
	public boolean isWorldCleanUp;
	/**Initiliaze for particles.*/
	protected ParticleInit particleInit = new ParticleInit();
	public SaveSlotLoader slotLoader;
	protected TerrainTexturePackLoader terrainTexturePackLoader;
	public Sun sunObject;
	
	public World(WorldScene worldSceneIn)
	{
		LOGGER.info("Preparing world: 5%");
		instance = this;
		this.worldScene = worldSceneIn;
		this.isWorldCleanUp = false;
		this.worldDataFile = new File(ResourceLocation.CRAFTIX_FOLDER);
		this.savingWorld = new SavingWorld(this.worldDataFile, this, worldSceneIn);
		this.slotLoader = new SaveSlotLoader(this.savingWorld);
		this.command = new Command();
		this.command.registerCommand();
		LOGGER.info("Preparing world: 10%");
		this.player = new EntityPlayer(this.worldScene.cx);
		this.renderer = new WorldRenderer(this.worldScene.cx, this.getPlayerCamera(), this);
		this.sunObject = new Sun(5F);
		LOGGER.info("Preparing world: 100%");
		
		LOGGER.info("Loading world: 5%");
		this.generateTerrain(0, -1);
		this.generateWater(0F, -5F, 0F);
		this.entityRegister = new EntityRegister(this, this.worldScene);
		LOGGER.info("Loading world: 75%");
		this.worldScene.timer.lastFPS = Craftix.getCurrentTime();
		this.worldTime = new GameTime(this.worldScene, this.renderer.getSkyboxRenderer());
		this.particleInit.loadParticles();
		PostProcessing.init(Display.getWidth(), Display.getHeight());
		LOGGER.info("Loading world: 100%");
	}
	
	public World init()
	{
		return this;
	}
	
	/**
	 * Generates the world itself and all the components with it such as
	 * terrane, water, player and then they can be displayed.
	 */
	public void generateWorld(Craftix craftixIn, TerrainOptions terrainOptions)
	{
	}
	
	public void generationType(MainMenuScene mainMenu)
	{
		CreatingWorld creatingWorld = mainMenu.getCreatingWorld();
		this.worldName = creatingWorld.getTempWorldName();
		this.worldType = creatingWorld.getTempWorldType();
		this.worldSeed = creatingWorld.getTempSeed();
		this.playerGamemode = creatingWorld.getTempGamemode();
		if(this.worldType == 0)
		{
			this.terrainOptions = new TerrainOptions(300, 100, 40, 50f, 6f, 0.1f, 16f);
		}
		else if(this.worldType == 1) 
		{
			this.terrainOptions = new TerrainOptions(300, 100, 25, 25f, 6f, 0.1f, 16f);
		}
		else if(this.worldType == 2) 
		{
			this.terrainOptions = new TerrainOptions(300, 100, 0, 0f, 6f, 0.1f, 16f);
		}
	}
	
	/**
	 * Load the textureMap, blendMap and actially the terrains.
	 */
	public void generateTerrain(int x, int z)
	{
		try
		{
			Craftix craftixIn = this.worldScene.cx;
			this.generationType(craftixIn.menuScene);
			this.terrainTexturePackLoader = new TerrainTexturePackLoader();
			
			LOGGER.info("Loading world: 15%");
			this.terrainTexturePackLoader.loadTerrainTexturePack();
			TerrainTexturePack texturePack = new TerrainTexturePack (
					
					this.terrainTexturePackLoader.textures.get(0), 
					this.terrainTexturePackLoader.textures.get(4), 
					this.terrainTexturePackLoader.textures.get(2), 
					this.terrainTexturePackLoader.textures.get(1)
					                                                );
			ModelTexture blendMap = new ModelTexture(this.textureLoader.loadTexture(BLEND_MAP));
		
			LOGGER.info("Loading world: 20%");
			this.terrain = new Terrain(craftixIn, x, z, texturePack, blendMap, HEIGHT_MAP);
			worldTerrainStatic = this.terrain;
			
			LOGGER.info("Loading world: 25%");
		}
		catch (Exception e) 
		{
			LOGGER.error("Generate terrain is failed.");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Generate water tile in the world and add it to the list of tiles.
	 */
	public void generateWater(float x, float y, float z)
	{
		LOGGER.info("Loading world: 40%");
		this.water = new Water(x, y, z); 
		this.worldScene.waters.add(new Water(x, z, y)); 
	}
	
	/**
	 * Return the instance of terrain options class.
	 */
	public TerrainOptions getTerrainOptions()
	{
		return this.terrainOptions;
	}
	
	/**
	 * Return the player gamemode value.
	 */
	public int getPlayerGamemode()
	{
		return this.playerGamemode;
	}
	
	/**
	 * Return the type of the world.
	 */
	public int getWorldType()
	{
		return this.worldType;
	}
	
	/**
	 * Return the world number on multipiing terrain vertices. Or for player's "Seed".
	 */
	public int getWorldSeed()
	{
		return this.worldSeed;
	}
	
	/**
	 * Return the saving world class instance.
	 */
	public SavingWorld getSavingWorld()
	{
		return this.savingWorld;
	}
	
	/**
	 * Return the movement playable player.
	 */
	public EntityPlayer getPlayer()
	{
		return this.player;
	}
	
	
	public Terrain getTerrain()
	{
		return terrain;
	}
	
	public static Terrain getTerrainStaticly()
	{
		return worldTerrainStatic;
	}
	
	public Terrain getTerrain2()
	{
		return worldTerrain2;
	}
	
	public Water getWater()
	{
		return water;
	}
	
	/**
	 * Return the command instance.
	 */
	public Command getCommandInstance()
	{
		return this.command;
	}
	
	/**
	 * Return the main renderer component for current world.
	 */
	public WorldRenderer getRenderer()
	{
		return this.renderer;
	}
	
	public WaterRenderer getWaterRenderer()
	{
		return this.getRenderer().getWaterRenderer();
	}
	
	/**
	 * Clean up all world renderers, and other component of it, when player,
	 * has left the world.
	 */
	public void cleanUpWorld()
	{
		this.getRenderer().cleanUp();	
	}
	
	/**
	 * Reloading the renderers and shader with it.
	 */
	public void reloadWorld()
	{
		if(this.isWorldCleanUp)
		{
			this.getRenderer().reloadRenderer(this.getPlayerCamera());
			this.isWorldCleanUp = false;
		}
	}
	
	/**
	 * This method controls some things in the game that can stop when 
	 * the player is paused. For example, all the game time will not 
	 * go in the singleplayer, and or skybox will not rotate.
	 */
	public void isPause()
	{
		this.worldScene.gamePause = this.worldScene.cx.status.isGamePause();
		
		if(!this.worldScene.gamePause)
		{
			this.getRenderer().getSkyboxRenderer().getSkyboxShader().setRotationSpeedSkybox(0.8f);
			this.particleInit.generateParticles();
			ParticleMaster.update(this.getPlayerCamera(), this.player);
			this.player.move(this.terrain);
			this.worldTime.increseWorldTime(this.worldScene, this.worldScene.cx);
			if(!SoundLoader.sourceInGameSound2.isPlaying())
				SoundLoader.continiuePlayingSound(SoundLoader.sourceInGameSound1);
		} else {
			this.getRenderer().getSkyboxRenderer().getSkyboxShader().setRotationSpeedSkybox(0.0f);
			SoundLoader.soundPause(SoundLoader.sourceInGameSound1);
		}
	}
	
	/**
	 * Return the player camera has a object.
	 */
	public EntityCamera getPlayerCamera() 
	{
		return this.player.getCamera();
	}
	
	/**
	 * Return the instance for particle rendering and initialization.
	 */
	public ParticleInit getParticleInit()
	{
		return this.particleInit;
	}
	
	/**
	 * Clean-up and save the world. In my lang is destroying world from, the memory
	 * Vao and Vbo, and back to main menu.
	 */
	public void saveAndCleanUpWorld()
	{
		this.savingWorld.saveWorld();
		this.slotLoader.fullSaveWorldToSlot();
		SaveSlotLoader.updateTextSaveData();
		SoundLoader.sourceInGameSound1.stop();
		this.worldScene.sounds.removeAll(this.worldScene.sounds);
		this.cleanUpWorld();
		this.player.deletePlayer();

		System.out.println("Object remove: " + this.player.getObjectName());
		while(!Display.isCloseRequested())
		{
			Display.update();
			Display.sync(120);
		}
	}
	
	public TerrainTexturePackLoader getTTPLoader()
	{
		return this.terrainTexturePackLoader;
	}
	
}
