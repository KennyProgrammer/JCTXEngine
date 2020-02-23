package com.kenny.craftix.client.entity;

import com.kenny.craftix.client.scenes.WorldScene;

public class GameObjectShine extends GameObject
{
	/**Folder where contains all specular maps textures.*/
	protected static final String SPECULAR_FOLDER = "maps/specularMap/";
	
	/**
	 * Creates a game object using various effects with light and reflections.
	 * 
	 * @param specularMapIn - file location of spec map.
	 * @param shineDamperIn - shine damper effect in float.
	 * @param reflectivityIn - reflectivity effect in float.
	 */
	public GameObjectShine(int index, String nameIn, String modelIn, String textureIn, float x, float z, float size,
			String specularMapIn, float shineDamperIn, float reflectivityIn, boolean fakeLightIn) 
	{
		super(index, nameIn, modelIn, textureIn, x, z, size);
		this.setSpecularMap(specularMapIn);
		this.setShineDamper(shineDamperIn);
		this.setReflectivity(reflectivityIn);
		this.setFakeLighting(fakeLightIn);
	}
	
	public GameObjectShine(String nameIn, float x, float z, float size, String specularMapIn, float shineDamperIn, float reflectivityIn,
			boolean fakeLightIn) 
	{
		this(1, nameIn, nameIn, nameIn, x, z, size, specularMapIn, shineDamperIn, reflectivityIn, fakeLightIn);
	}
	
	/**
	 * Load the specualr map effect on the game object. Uses for shine effects.
	 */
	public void setSpecularMap(String specularMapIn)
	{
		//maybe the normal file loader method. See in the last backup.
		this.getObjectModelTexture().setSpecularMap(this.textureLoader.loadTexture(SPECULAR_FOLDER + specularMapIn));
	}
	
	/**
	 * Sets the luminous intensity of a certain part of the object.
	 */
	public void setShineDamper(float shineDamperIn)
	{
		this.getObjectModelTexture().setShineDumper(shineDamperIn);
	}
	
	/**
	 * Sets the reflectivity from a special light source map to the ground.
	 */
	public void setReflectivity(float reflectivityIn)
	{
		this.getObjectModelTexture().setReflectivity(reflectivityIn);
	}
	
	/**
	 * If set true then from the object will come fake light. Namely that part 
	 * of model on which there is a specular map.
	 */
	public void setFakeLighting(boolean fakeLightIn)
	{
		this.getObjectModelTexture().setUseFakeLighting(fakeLightIn);
	}
	
	/**
	 * Generate shine object on 3d world space.
	 */
	public GameObjectShine generateObject(WorldScene worldScene)
	{
		worldScene.objects.add(this);
		return this;
	}

	/**
	 * Disable collision for a shine object.
	 */
	public GameObjectShine disableCollision()
	{
		this.hasPhysicalObject = false;
		return this;
	}
	
}
