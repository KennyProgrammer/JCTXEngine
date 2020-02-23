package com.kenny.craftix.client.entity;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.collision.AxisAlignedBB;
import com.kenny.craftix.client.entity.player.EntityPlayer;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.loader.TexturesLoader;
import com.kenny.craftix.client.renderer.OBJLoader;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.client.renderer.models.TexturedModel;
import com.kenny.craftix.client.renderer.textures.ModelTexture;
import com.kenny.craftix.client.scenes.Scene;
import com.kenny.craftix.client.settings.nbt.NBTTagCompound;
import com.kenny.craftix.client.settings.nbt.NBTTagDouble;
import com.kenny.craftix.client.settings.nbt.NBTTagFloat;
import com.kenny.craftix.client.settings.nbt.NBTTagList;
import com.kenny.craftix.world.World;

/**
 * The main class for all objects in the engine. It allows you to create 
 * an object in the world and also use models for downloading. All basic 
 * commands such as changing the position or turning the object has in itself.
 * 
 * @author Kenny
 */
public class GameObject
{
	protected static final Logger LOGGER = LogManager.getLogger(Craftix.LOGGER_INSTANCE);
	public static final AxisAlignedBB FULL_CUBE_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	/**Position object on X axis.*/
	public float posX;
	/**Position object on Y axis.*/
	public float posY;
	/**Position object on Z axis.*/
	public float posZ;
	/**Rotation object along the X axis*/
	protected float rotX;
	/**Rotation object along the Y axis*/
	public float rotY;
	/**Rotation object along the Z axis*/
	protected float rotZ;
	/**Current size of the object.*/
	private float size;
	/**Name set for a textures/models easing loading files.*/
	private String name;
	/**Instance of the world.*/
	private World world;
	/**We get the textured model for object*/
	public TexturedModel texturedModel;
	/**Position vector between two points.*/
	private Vector3f position;
	private int textureIndex = 0;
	/**This is entity UUID.*/
	private UUID entityUniqueID;
	/**Model of the object.*/
	private Model model;
	/**Model texture quad of the object.*/
	private ModelTexture modelTexture;
	protected Loader openGlObjectsloader = new Loader();
	protected TexturesLoader textureLoader = new TexturesLoader();
	/**Makes the object physical, that is, it establishes a collision, etc.*/
	protected boolean hasPhysicalObject;
	/**Check if entity render in the 3d space.*/
	private boolean render;
	
	public GameObject(int index, String nameIn, String modelIn, String textureIn, float x, float z, float size) 
	{
		this.name = nameIn;
		this.model = OBJLoader.loadObjModel(modelIn, this.openGlObjectsloader);
		this.modelTexture = new ModelTexture(this.textureLoader.loadTexture(textureIn));
		this.texturedModel = new TexturedModel(this.model, this.modelTexture);
		this.textureIndex = index;
		this.posX = x;
		this.posY = World.getTerrainStaticly().getHeightOfTerrain(x, z);
		this.posZ = z;
		this.rotX = 0F;
		this.rotY = 0F;
		this.rotZ = 0F;
		this.size = size;
		this.position = new Vector3f(this.posX, this.posY, this.posZ);
		this.hasPhysicalObject = true;
		this.render = true;
	}
	
	public GameObject(int index, String nameIn, String modelIn, String textureIn, float x, float y, float z, float size) 
	{
		this(index, nameIn, modelIn, textureIn, x, z, size);
	}
	
	/**
	 * Creates a simplified constructor of a game object, where the index 
	 * of the texture set to 1 and the name of the model file and texture 
	 * comes from the name of the object.
	 */
	public GameObject(String nameIn, float x, float y, float z, float size)
	{
		this(1, nameIn, nameIn, nameIn, x, y, z, size);
	}
	
	public GameObject(String nameIn, float x, float z, float size)
	{
		this(1, nameIn, nameIn, nameIn, x, World.getTerrainStaticly().getHeightOfTerrain(x, z), z, size);
	}
	
	/**
	 * Genereate a object in the 3d space/world. When generating, the object 
	 * will have a position of a Y, always a position of terrain.
	 */
	public GameObject generateObject(Scene sceneIn)
	{
		sceneIn.objects.add(this);
		return this;
	}
	
	public GameObject generateObject(Scene sceneIn, boolean isMenu)
	{
		if(!isMenu)
			sceneIn.objects.add(this);
		else
			sceneIn.objectsM.add(this);
		return this;
	}
	
	/**
	 * Generate object with settings positions.
	 */
	public void generateObject(Scene sceneIn, float x, float z)
	{
		this.setPosition(new Vector3f(x, World.getTerrainStaticly().getHeightOfTerrain(x, z), z));
		sceneIn.objects.add(this);
	}
	
	/**
	 * Remove object from the world scene.
	 */
	public void removeObject(Scene sceneIn)
	{
		sceneIn.objects.remove(this);
	}
	
	public void collision(EntityPlayer player)
	{
		float x = this.getPositionVector().x;
		float z = this.getPositionVector().z;
		float playerPreX = player.getPositionVector().x;
		float playerPreZ = player.getPositionVector().z;
		float playerX = player.getPositionVector().x;
		float playerZ = player.getPositionVector().z;
		
		if(x == playerX)
		{
			playerX = 0f;
		}
			
	}
	
	/**
	 * Offset the texture along the x-axis for multittextruning objects.
	 */
	public float getTextureXOffset()
	{
		int column = textureIndex % this.texturedModel.getTexture().getNumberOfRows();
		return (float) column / (float) this.texturedModel.getTexture().getNumberOfRows();
	}
	
	/**
	 * Offset the texture along the y-axis for multittextruning objects.
	 */
	public float getTextureYOffset()
	{
		int row = textureIndex % this.texturedModel.getTexture().getNumberOfRows();
		return (float) row / (float) this.texturedModel.getTexture().getNumberOfRows();
	}
	/**
	 * Moves the object along the specified path when updating.
	 */
	
	public void increasePosition(float dX, float dY, float dZ)
	{
		this.position.x += dX;
		this.position.y += dY;
		this.position.z += dZ;
	}
	
	/**
	 * Rotate the object along the specified axis when updating.
	 */
	public void increaseRotation(float dX, float dY, float dZ)
	{
		this.rotX += dX;
		this.rotY += dY;
		this.rotZ += dZ;
	}
	
	/**
	 * Rotates an object around only one axis.
	 */
	public void rotationOnlyOneAxis(String axis, float rot)
	{
		if(axis == "x")
			this.rotX = rot;
		else if(axis == "y")
			this.rotY = rot;
		else if(axis == "z")
			this.rotZ = rot;
		else 
			LOGGER.error("Can't find usfull axis.");
			
	}
	
	/**
	 * Set the rotation of the object on x, y, and z axis.
	 */
	public void setRotation(float rX, float rY, float rZ)
	{
		this.rotX = rX;
		this.rotY = rY;
		this.rotZ = rZ;
	}
	
	/**
	 * Set the position of the object on x, y, and z axis. If on terrain, then
	 * y value be on terrain position, else on your setting position.
	 */
	public void setPosition(float x, float y, float z, boolean onTerrain)
	{
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
		if(onTerrain)
			this.position.y = World.getTerrainStaticly().getHeightOfTerrain(x, z);
	}
	
	/**
	 * Set size of the current object.
	 */
	public GameObject setObjectSize(float size)
	{
		this.size = size;
		return this;
	}
	
	/**
	 * Reloaded all the data model of the object and its texture, and re-loaded.
	 */
	public void reloadGameObjectAssets(String modelIn, String textureIn)
	{
		this.model = OBJLoader.loadObjModel(modelIn, this.openGlObjectsloader);
		this.modelTexture = new ModelTexture(this.textureLoader.loadTexture(textureIn));
		this.texturedModel = new TexturedModel(this.model, this.modelTexture);
	}
	
	/**
	 * Reloaded all the data model of the object and its texture, and re-loaded.
	 */
	public void reloadGameObjectAssets(String name)
	{
		this.model = OBJLoader.loadObjModel(name, this.openGlObjectsloader);
		this.modelTexture = new ModelTexture(this.textureLoader.loadTexture(name));
		this.texturedModel = new TexturedModel(this.model, this.modelTexture);
	}
	
	/**
	 * Enable collision for this current object.
	 */
	public GameObject enableCollision()
	{
		this.hasPhysicalObject = true;
			return this;
	}
	
	/**
	 * Disable collision for this current object.
	 */
	public GameObject disableCollision()
	{
		this.hasPhysicalObject = false;
			return this;
	}
	
	public GameObject setRenderState(boolean isRenderIn)
	{
		this.render = isRenderIn;
			return this;
	}
	
	/**
	 * Return render variable. If true then object render, else if not.
	 */
	public boolean isRender()
	{
		return this.render;
	}
	
	/**
	 * Get the position of 3d vector from all vertices of the object.
	 */
	public Vector3f getPositionVector() 
	{
		return position;
	}
	
	/**
	 * Returns the model of the object itself.
	 */
	public Model getObjectModel()
	{
		return this.model;
	}
	
	/**
	 * Returns the model texture quad.
	 */
	public ModelTexture getObjectModelTexture()
	{
		return this.modelTexture;
	}
	
	/**
	 * Return the name of the gameobject. Used how ids.
	 */
	public String getObjectName()
	{
		return this.name;
	}
	
	/**
	 * Get the current world for object.
	 */
	public World getWorld()
	{
		return this.world;
	}
	
	public float getX()
	{
		return this.posX;
	}
	
	public float getY()
	{
		return this.posY;
	}
	
	public float getZ()
	{
		return this.posZ;
	}
	
	/**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound compound) {
	}
	
	public NBTTagCompound writeToNbt(NBTTagCompound nbt)
	{
		try
		{
			nbt.setTag("Pos", this.newDoubleNBTList(this.posX, this.posY, this.posZ));
			nbt.setTag("Rotation", this.newFloatNBTList(this.rotX, this.posY, this.posZ));
			nbt.setUniqueId("UUID", this.getUniqueID());
			
			this.writeEntityToNBT(nbt);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return nbt;
	}
	
	 /**
     * creates a NBT list from the array of doubles passed to this function
     */
    protected NBTTagList newDoubleNBTList(double... numbers)
    {
        NBTTagList nbttaglist = new NBTTagList();

        for (double d0 : numbers)
        {
            nbttaglist.appendTag(new NBTTagDouble(d0));
        }

        return nbttaglist;
    }

    /**
     * Returns a new NBTTagList filled with the specified floats
     */
    protected NBTTagList newFloatNBTList(float... numbers)
    {
        NBTTagList nbttaglist = new NBTTagList();

        for (float f : numbers)
        {
            nbttaglist.appendTag(new NBTTagFloat(f));
        }

        return nbttaglist;
    }
    
    /**
     * Returns the UUID of this entity.
     */
    public UUID getUniqueID()
    {
        return this.entityUniqueID;
    }

	public void setPosition(Vector3f position) 
	{
		this.position = position;
	}
	

	public float getRotX() 
	{
		return rotX;
	}

	public void setRotX(float rotX) 
	{
		this.rotX = rotX;
	}

	public float getRotY()
	{
		return rotY;
	}

	public void setRotY(float rotY) 
	{
		this.rotY = rotY;
	}

	public float getRotZ() 
	{
		return rotZ;
	}

	public void setRotZ(float rotZ) 
	{
		this.rotZ = rotZ;
	}

	public float getScale() 
	{
		return this.size;
	}
	
	 public AxisAlignedBB getBoundingBox(GameObject pos)
	 {
		 return FULL_CUBE_AABB;
	 }
	 
	public void setObjectName(String nameIn)
	{
		this.name = nameIn;
	}

	/**
	 * Return the boolean constant a hasPhysical.
	 */
	public boolean getPhysicalObject() 
	{
		return this.hasPhysicalObject;
	}
	
	/**
	 * Delete objects data when world has destroying.
	 */
	public GameObject deleteObject()
	{
		this.openGlObjectsloader.deleteVaoFromCache(this.getObjectModel().getVaoID());
		//this.textureLoader.cleanUpTextureOpenGlObjects();
			return this;
	}
}
