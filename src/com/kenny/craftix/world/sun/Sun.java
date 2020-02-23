package com.kenny.craftix.world.sun;

import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.loader.TexturesLoader;
import com.kenny.craftix.client.renderer.textures.ModelTexture;

public class Sun 
{
	/**This distance can't be large that skybox.*/
	private static final float SUN_DISTANCE = 30f;
	
	private ModelTexture texture;
	/**Direction of the sun light*/
	private Vector3f lightDirection = new Vector3f(0, 1, 0);
	/**Scale of the sun.*/
	private float scale;
	public TexturesLoader textureLoader = new TexturesLoader();
	private ModelTexture sunTexture;
	
	public Sun(ModelTexture textureIn, float scaleIn) 
	{
		this.texture = textureIn;
		this.scale = scaleIn;
	}
	
	public Sun(float scaleIn)
	{
		this.sunTexture = new ModelTexture(this.textureLoader.loadTexture("sun/sun"));
		this.texture = this.sunTexture;
		this.scale = scaleIn;
	}
	
	/**
	 * Calculates a position for the sun, based on the light direction. The
	 * distance of the sun from the camera is fairly arbitrary, although care
	 * should be taken to ensure it doesn't get rendered outside the skybox.
	 * 
	 * @param camPos - The camera's position.
	 * @return The 3D world position of the sun.
	 */
	public Vector3f getWorldPosition(Vector3f camPos) 
	{
		Vector3f sunPos = new Vector3f(this.lightDirection);
		sunPos.negate();
		sunPos.scale(SUN_DISTANCE);
		return Vector3f.add(camPos, sunPos, null);
	}
	
	/**
	 * Set the direction for this sun vector.
	 */
	public void setLightDirection(float x, float y, float z)
	{
		this.lightDirection.set(x, y, z);
		this.lightDirection.normalise();
	}
	
	public Vector3f getLightDirection()
	{
		return this.lightDirection;
	}
	
	public float getScale()
	{
		return this.scale;
	}
	
	public ModelTexture getTexture() 
	{
		return texture;
	}
	
	public void setScale(float scaleIn)
	{
		this.scale = scaleIn;
	}
	
}
