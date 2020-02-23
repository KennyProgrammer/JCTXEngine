package com.kenny.craftix.client.entity;

import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.renderer.models.TexturedModel;
import com.kenny.craftix.init.EntityInit;

public class EntityBlock extends GameObject 
{

	public EntityBlock(int index, String nameIn, String modelIn, String textureIn, float x, float y, float z, float rX,
			float rY, float rZ, float size) 
	{
		super(index, nameIn, modelIn, textureIn, x, z, size);
	}

	protected static final String BLOCK_MODEL_LOCATION = "ModelBox";
	private TexturedModel object;
	/**Load a obj loader and parse the verteces, indeces, normals vectors and create the model.*/
	protected Loader loader = new Loader();
	/**Position X entity*/
	private float posX;
	/**Position Y entity*/
	private float posY;
	/**Position Z entity*/
	private float posZ;
	/**Rotation entity on X axis*/
	public float rotX;
	/**Rotation entity on Y axis*/
	public float rotY;
	/**Rotation entity on Z axis*/
	public float rotZ;
	public float scale;
	public Vector3f position;
	public EntityInit entityInit = new EntityInit();

	

	/**
	 * Generate block in the world.
	 */
	public void generate()
	{
		this.entityInit.blocks.add(this);
	}

	/**
	 * Return the back block object has a component. Not a actually model.
	 */
	public TexturedModel getObject()
	{
		return this.object;
	}

	public float getPosX() 
	{
		return posX;
	}

	public float getPosY() 
	{
		return posY;
	}
	
	public float getPosZ() 
	{
		return posZ;
	}
	
	public float getRotX() 
	{
		return rotX;
	}

	public float getRotY() 
	{
		return rotY;
	}
	
	public float getRotZ() 
	{
		return rotZ;
	}
}
