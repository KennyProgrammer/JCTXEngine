package com.kenny.craftix.client.entity;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.client.renderer.models.TexturedModel;
import com.kenny.craftix.client.renderer.textures.ModelTexture;
import com.kenny.craftix.world.World;

public class GameObjectOld
{
	protected static final Logger LOGGER = LogManager.getLogger(Craftix.LOGGER_INSTANCE);
	/**Position object on X axis.*/
	public float posX;
	/**Position object on Y axis.*/
	public float posY;
	/**Position object on Z axis.*/
	public float posZ;
	/**Rotation object along the X axis*/
	private float rotX;
	/**Rotation object along the Y axis*/
	private float rotY;
	/**Rotation object along the Z axis*/
	private float rotZ;
	/**Current size of the object.*/
	private float size;
	/**Number of frames for multitexturing the model for objects.*/
	private int texIndex;
	/**Name of the object.*/
	private String objectName;
	/**Random event with a object. Scale, position, rotation.*/
	protected Random rand;
	/**Set true if object created in the world.*/
	protected boolean isGenerated;
	/**Get the reference of the world.*/
	public World worldIn;
	/**Instance of the textured model for object.*/
	private TexturedModel texturedModel;
	protected Model model;
	protected ModelTexture modelTexture;
	private Vector3f position;
	
	public GameObjectOld(int texIndexIn, Model modelIn, ModelTexture modelTextureIn,
			float x, float y, float z, float rX, float rY, float rZ, float size) 
	{
		this.texIndex = texIndexIn;
		this.model = modelIn;
		this.modelTexture = modelTextureIn;
		this.texturedModel = new TexturedModel(modelIn, modelTextureIn);
		//this.setPosition(0.0F, 0.0F, 0.0F);
		//this.setRotation(0.0F, 0.0F, 0.0F);
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.rotX = rX;
		this.rotY = rY;
		this.rotZ = rZ;
		this.size = size;
		this.isGenerated = true;
		this.position = new Vector3f(posX, posY, posZ);
	}
	
	/**
	 * Change or set new position to the object. 
	 */
	public void setPosition(float newPosX, float newPosY, float newPosZ)
	{
		this.posX = newPosX;
		this.posY = newPosY;
		this.posZ = newPosZ;
	}
	
	/**
	 * Change or set new rotation angles to the object. 
	 */
	public void setRotation(float newRotX, float newRotY, float newRotZ)
	{
		this.rotX = newRotX;
		this.rotY = newRotY;
		this.rotZ = newRotZ;
	}
	
	/**
	 * Moves the object along the specified path when updating.
	 */
	public void increasePosition(float dX, float dY, float dZ)
	{
		this.posX += dX;
		this.posY += dY;
		this.posZ += dZ;
	}
	
	/**
	 * Set size of the current object.
	 */
	public void setObjectSize(float size)
	{
		this.size = size;
	}
	
	/**
	 * Rotate the object along the specified path when updating.
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
	public void rotationOnlyOneAxis(String axis)
	{
		if(axis == "x")
			this.rotX = 1;
		else if(axis == "y")
			this.rotY = 1;
		else if(axis == "z")
			this.rotZ = 1;
		else 
			LOGGER.error("Can't find usfull axis.");
			
	}
	
	/**
	 * Offset the texture along the x-axis for multittextruning objects.
	 */
	public float getTextureXOffset()
	{
		int column = texIndex % texturedModel.getTexture().getNumberOfRows();
		return (float) column / (float) texturedModel.getTexture().getNumberOfRows();
	}
	
	/**
	 * Offset the texture along the y-axis for multittextruning objects.
	 */
	public float getTextureYOffset()
	{
		int row = texIndex % texturedModel.getTexture().getNumberOfRows();
		return (float) row / (float) texturedModel.getTexture().getNumberOfRows();
	}
	
	/**
	 * Get the position of 3d vector from all vertices of the object.
	 */
	public Vector3f getPositionVector()
	{
		return this.position;
	}
	
	/**
	 * Return the current world in which now plays player.
	 */
	public World getWorld()
	{
		return this.worldIn;
	}
	
	/***
	 * Return the name object at the string.
	 */
	public String getObjectName()
	{
		return this.objectName;
	}
	
	/**
	 * Get the size of the object.
	 */
	public float getObjectSize()
	{
		return this.size;
	}
	
	/**
	 * Return the current model with texture/textureId to object.
	 */
	public TexturedModel getObjectModel()
	{
		return this.texturedModel;
	}
	
	//================FUTURE COMPONENTS============//
	
	public void createCube()
	{
	}
	
	public void createSphere()
	{
	}
	
	public void createCone()
	{
	}
	
	//=============================================//
	
	public float getRotX()
	{
		return this.rotX;
	}
	
	public float getRotY()
	{
		return this.rotY;
	}
	
	public float getRotZ()
	{
		return this.rotZ;
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
	
	
}
