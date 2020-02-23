package com.kenny.craftix.client.entity;

import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.loader.TexturesLoader;
import com.kenny.craftix.client.renderer.OBJLoader;
import com.kenny.craftix.client.renderer.models.TexturedModel;
import com.kenny.craftix.client.renderer.textures.ModelTexture;
import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.world.World;

public class EntityRegister 
{
	public World world;
	public WorldScene worldScene;
	/**Simple standard Open Gl objects loader.*/
	public Loader openGlObjectsLoader = new Loader();
	/**Simple help load this textures to entitys.*/
	public TexturesLoader textureLoader = new TexturesLoader();
	public List<Integer> generatedEntitys = new ArrayList<Integer>();
	
	public GameObject ENTITY_OAK_TREE;
	public GameObject ENTITY_PINE_TREE;
	public GameObjectShine ENTITY_CHERRY_TREE;
	public GameObject ENTITY_BOX;
	public GameObject ENTITY_BARREL;
	public GameObject ENTITY_LAMP;
	
	public EntityRegister(World worldIn, WorldScene worldscene) 
	{
		this.world = worldIn;
		this.worldScene = worldscene;
		worldIn.LOGGER.info("Loading world: 50%");
		
		this.ENTITY_OAK_TREE = new GameObject("oak_tree", 0F, 0F, 0.5F).generateObject(this.worldScene);
		this.ENTITY_PINE_TREE = new GameObject("pine_tree", 10F, 0F, 4.0F).generateObject(this.worldScene);
		this.ENTITY_CHERRY_TREE = new GameObjectShine("cherry_tree", 20F, 0F, 2.5F, "cherryS", 10F, 0.5F, false).generateObject(this.worldScene);
		this.ENTITY_BOX = new GameObject("box", 30F, 0F, 2F).generateObject(this.worldScene);
		this.ENTITY_BARREL = new GameObject("barrel", 40F, 0F, 0.5F).generateObject(this.worldScene);
		this.ENTITY_LAMP = new GameObject("lamp", 50F, 0F, 1F).generateObject(this.worldScene);
	}
	
	/**
	 * Registry entity model with the texture in the game engine. Load model with ObjLoader, and
	 * texture with loader loadTexture method. 
	 * 
	 * @param entityModel - current entity modelTexture object.
	 * @param modelIn - string model path in.
	 * @param textureIn - string texture math in.
	 */
	public void registryEntityModel(TexturedModel entityModel, String modelIn, String textureIn)
	{
		entityModel = new TexturedModel(OBJLoader.loadObjModel(modelIn, this.openGlObjectsLoader), 
				new ModelTexture(this.textureLoader.loadTexture(textureIn)));
	}
	
	/**
	 * Generate entity in the current world. Simply create a empty entity object, add to him
	 * modelTexture object, and finally add into the list of the entities to render in the screen.
	 * 
	 * @param entity - current entity object.
	 * @param model - add the modelTexture on the entity.
	 * @param posY - y will always be the height of the terrain.
	 */
	public void generateEntity(GameObject entity, String modelIn, String textureIn, float x, float z, float size)
	{
		float y = this.world.getTerrain().getHeightOfTerrain(x, z);
		
	}
	
}
