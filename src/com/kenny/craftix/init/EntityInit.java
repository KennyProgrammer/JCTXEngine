package com.kenny.craftix.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.entity.GameObject;
import com.kenny.craftix.client.entity.EntityBlock;
import com.kenny.craftix.client.entity.player.EntityPlayer;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.loader.TexturesLoader;
import com.kenny.craftix.client.renderer.OBJLoader;
import com.kenny.craftix.client.renderer.models.TexturedModel;
import com.kenny.craftix.client.renderer.normalMapping.NMObjLoader;
import com.kenny.craftix.client.renderer.textures.ModelTexture;
import com.kenny.craftix.client.scenes.MainMenuScene;
import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.world.World;

public class EntityInit 
{
	public static final Logger LOGGER = LogManager.getLogger(Craftix.LOGGER_INSTANCE);
	/**Just generate and return random numbers.*/
	public Random random = new Random(676452);
	public World world;
	/**Load the Open Gl Objects.*/
	private Loader openGlObejctsLoader = new Loader();
	/**Load the textures.*/
	private TexturesLoader textureLoader = new TexturesLoader();
	public List<GameObject> entityMainCraftixBox = new ArrayList<GameObject>();
	//public List<Entity> entities = new ArrayList<Entity>();
	public List<EntityBlock> blocks = new ArrayList<EntityBlock>();
	public List<GameObject> entitiesNm = new ArrayList<GameObject>();
	public List<GameObject> profilePlayerList = new ArrayList<GameObject>();
	public GameObject profilePlayer;
	private static final String COLOR_FOLDER = "colors/";
	/**
	 * This is List of all entities in the world.
	 */
	public GameObject box;
	public TexturedModel entityCraftixBox;
	public TexturedModel entityPlayer;
	public TexturedModel entityPineTree;
	public TexturedModel entityOakTree;
	public TexturedModel entityCherryTree;
	public TexturedModel entityGrass;
	public TexturedModel entityFern;
	public TexturedModel entityFlower;
	public TexturedModel entityLamp;
	public TexturedModel entityLampPicker;
	public TexturedModel entityBox;
	public TexturedModel entityBarrel;
	public TexturedModel entityCrate;
	public TexturedModel entityBoulder;
	public TexturedModel entityLattern;
	public TexturedModel entityModel;
	/**
	 * Generate list with mouse picker.
	 */
	public GameObject pickerLamp;
	public MainMenuScene scene;
	public WorldScene worldIn;
	
	/**
	 * Simple add entity in the world. Witch own model and texture.
	 * 
	 * @param modelFile - location model file.
	 * @param textureFile - location texture file.
	 */
	public TexturedModel addEntity(String modelFile, String textureFile)
	{
		return new TexturedModel(OBJLoader.loadObjModel(modelFile, this.openGlObejctsLoader), 
				new ModelTexture(this.textureLoader.loadTexture(textureFile)));
	}

	/**
	 * Add entity with normal mapping effect.
	 */
	public TexturedModel addEntityNormal(String modelFile, String textureFile)
	{
		return new TexturedModel(NMObjLoader.loadOBJ(modelFile, this.openGlObejctsLoader), 
				new ModelTexture(this.textureLoader.loadTexture(textureFile)));
	}
	
	public TexturedModel addEntityLight(String modelFile, String textureFile, TexturedModel type)
	{
		return new TexturedModel(OBJLoader.loadObjModel(modelFile, this.openGlObejctsLoader), 
				new ModelTexture(this.textureLoader.loadTexture(textureFile)));
	}

	/**
	 * Adds entities to the world with specified positions. It is better to use 
	 * one object.
	 *
	 * @param scale - scale entity.
	 */
	public GameObject addEntitySpec(TexturedModel entity, float posX, float posY, float posZ, 
			float scale)
	{
		//return(new Entity(entity, posX, posY, posZ , 0, 0, 0, scale));
		return null;
	}

	/**
	 * Load all entities in the world.
	 */
	public void loadEntity(WorldScene worldSceneIn)
	{
		this.openGlObejctsLoader = new Loader();
		this.worldIn = worldSceneIn;
		//this.world = new World(this.worldIn);
		
		try 
		{
			LOGGER.info("Processing Entitys...");
			//this.createEntities();
			//this.generateEntities();
			LOGGER.info("Entity loads success.");
		} 
		catch (Exception e) 
		{
			LOGGER.error("Failed load entitys(.");
			e.printStackTrace();
		}
		
	}
	
	public void loadMainCraftixBox()
	{	
	}
	
	public void loadProfilePlayer(MainMenuScene sceneIn)
	{
		this.scene = sceneIn;
		this.openGlObejctsLoader = new Loader();
		//this.createProfilePlayer();
		//this.generateProfilePlayer();
	}
	
	/**
	 * Create a player for a profile gui page.
	 */
	public void createProfilePlayer()
	{
		this.entityPlayer = addEntity(EntityPlayer.getPlayerModel(), this.scene.playerSkin.getSkin());		
		this.entityBox = addEntity("ModelBox", "box");
	}
	
	/**
	 * Generate a player for a profile gui page in the scene.
	 */
	public void generateProfilePlayer()
	{
		//this.profilePlayer = new Entity(this.entityPlayer, 1, 0.27f, -0.088f,-0.3f, 0, -35.0f, 0, 0.01f);
		//this.profilePlayerList.add(this.profilePlayer);
		//this.profilePlayerList.add(new Entity(this.entityBox, 0.27f, -0.1f, -0.3f, 0, 0, 0, 0.012f));
	}
	
	
	/**
	 * Load model and texture. And create a entity object.
	 */
	public void createEntities()
	{
		this.entityPlayer = addEntity(EntityPlayer.getPlayerModel(), this.worldIn.playerSkin.getSkin());		
		this.entityPineTree = addEntity("ModelPineTree", "pine_tree");
		this.entityOakTree = addEntity("ModelOakTree", "oak_tree");
		this.entityBox = addEntity("ModelBox", "box");
		this.entityModel = addEntity("mesh", COLOR_FOLDER + "red_color");
		
		this.entityLamp = addEntity("ModelLamp", "lamp");
				entityLamp.getTexture().setUseFakeLighting(true);
				entityLamp.getTexture().setHasTransparency(false);
				
		this.entityLampPicker = addEntity("ModelLamp", "lamp");
				entityLamp.getTexture().setUseFakeLighting(true);
				entityLamp.getTexture().setHasTransparency(false);
				
		this.entityFlower = addEntity("ModelFlower", "flower");
				entityFlower.getTexture().setHasTransparency(true);
				entityFlower.getTexture().setUseFakeLighting(true);
				
		this.entityGrass = addEntity("ModelGrass", "grass");
				entityGrass.getTexture().setHasTransparency(true);
				entityGrass.getTexture().setUseFakeLighting(true);
				entityGrass.getTexture().setNumberOfRows(4);
				
		this.entityFern = addEntity("ModelFern", "fern");
				entityFern.getTexture().setHasTransparency(true);
				entityFern.getTexture().setUseFakeLighting(false);
				entityFern.getTexture().setNumberOfRows(2);
				
		this.entityCherryTree = addEntity("ModelCherryTree", "cherry_tree");
				entityCherryTree.getTexture().setSpecularMap(this.textureLoader.loadTexture("maps/specularMap/cherryS"));
				entityCherryTree.getTexture().setHasTransparency(true);
				entityCherryTree.getTexture().setShineDumper(10);
				entityCherryTree.getTexture().setReflectivity(0.5f);
		
		this.entityLattern = addEntity("ModelLattern", "lattern");
				entityLattern.getTexture().setSpecularMap(this.textureLoader.loadTexture("maps/specularMap/lanternS"));
				
		this.entityBarrel = addEntityNormal("ModelBarrel", "barrel");
				entityBarrel.getTexture().setSpecularMap(this.textureLoader.loadTexture("maps/specularMap/barrelS"));		
				entityBarrel.getTexture().setNormalMap(this.textureLoader.loadTexture("maps/normalMap/barrelN"));
				entityBarrel.getTexture().setShineDumper(10);
				entityBarrel.getTexture().setReflectivity(0.5f);
				
		this.entityBoulder = addEntityNormal("ModelBoulder", "boulder");
				entityBoulder.getTexture().setNormalMap(this.textureLoader.loadTexture("maps/normalMap/boulderN"));
				entityBoulder.getTexture().setShineDumper(10);
				entityBoulder.getTexture().setReflectivity(0.5f);
				
		this.entityCrate = addEntityNormal("ModelCrate", "crate");
				entityCrate.getTexture().setNormalMap(this.textureLoader.loadTexture("maps/normalMap/crateN"));
				entityCrate.getTexture().setShineDumper(10);
				entityCrate.getTexture().setReflectivity(0.5f);	
			
				
		/**Other setups settings objects*/
		this.pickerLamp = addEntitySpec(entityLampPicker, 293, -6.8f, -305, 1);
	}
	
	public void generateEntities()
	{
		/**Custom Generate objects*/
		//entities.add(new Entity(entityModel, 0, 2, 0, 0, 0, 0, 1f));
		//entities.add(new Entity(entityBox, 0, 2f, 4f, 0, 0, 0, 2f));
		//entities.add(pickerLamp);
		//entities.add(new Entity(entityLamp, 185, -4.7f, -293, 0, 0, 0, 1f));
		//entities.add(new Entity(entityLamp, 370, 4.2f, -300, 0, 0, 0, 1f));
		//entities.add(new Entity(entityLamp, 293, -6.8f, -305, 0, 0, 0, 1f));
		//entities.add(new Entity(entityBox, 240f, 3.5f, -224f, 0, 0, 0, 2f));
		//entities.add(new Entity(entityBox, 240f, 1f, -234f, 0, 0, 0, 2f));
		//entities.add(new Entity(entityBox, 240f, -2f, -244f, 0, 0, 0, 2f));
		//entities.add(new Entity(entityBox, 240f, -5f, -254f, 0, 0, 0, 2f));
		//entitiesNm.add(new Entity(entityBarrel, 250f, 10.5f, -200, 0, 0, 0, 0.5f));
		//entitiesNm.add(new Entity(entityBarrel, 1220f, 14f, -1651f, 0, 0, 0, 0.5f));
		//entitiesNm.add(new Entity(entityBoulder,260f, 12.5f, -200, 0, 0, 0, 0.5f));
		//entitiesNm.add(new Entity(entityCrate, 270f, 13.5f, -200, 0, 0, 0, 0.04f));
		
	}
	
}
