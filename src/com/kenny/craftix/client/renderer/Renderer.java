package com.kenny.craftix.client.renderer;

import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.entity.Light;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.loader.TexturesLoader;
import com.kenny.craftix.client.renderer.GlHelper.Depth;
import com.kenny.craftix.client.renderer.GlHelper.Texture;

/**
 * Now I've made an abstract class for rendering various engine 
 * components. Here will be the most basic points that are needed 
 * to display objects, such as the creation of various viewable 
 * matrices and so on. Now this class will be the parent for the 
 * entire rendering.
 * 
 * @author Kenny
 */
public abstract class Renderer 
{
	/**Field of view-the angular space visible to the eye with a fixed view 
	 * and a fixed head.*/
	protected float fov;
	/**This is the closest viewing distance from the object.*/
	protected float nearPlane;
	/**This is the far distance viewed by the object area.*/
	protected float farPlane;
	/**This is the division of the screen width by the height in pixels.*/
	public float aspectRatio;
	/**Used lwjgl utils to create this matrix.*/
	protected Matrix4f projectionMatrix;
	public Vector4f clipPlane;
	/**Main instance for all engine.*/
	public Craftix cx;
	/**The auxiliary component is required to store the data in Vbo.*/
	public Loader openGlObjectsLoader;
	public TexturesLoader textureLoader;
	protected boolean isCleanUpLoader;
	
	public Renderer(Craftix craftixIn) 
	{
		this.cx = craftixIn;
		this.openGlObjectsLoader = craftixIn.cxLoader;
		this.textureLoader = new TexturesLoader();
	}
	
	public Renderer(Craftix craftixIn, EntityCamera cameraIn) 
	{
		this(craftixIn);
	}
	
	/**
	 * Prepares data to create a projection matrix to display something.
	 */
	public void setDataToProjectionMatrix(float fovIn, float nearPlaneIn, float farPlaneIn)
	{
		this.fov = fovIn;
		this.nearPlane = nearPlaneIn;
		this.farPlane = farPlaneIn;
		this.aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		if(this.aspectRatio == 0F)
		{
			this.aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		}
	}
	
	/**
	 * Create projection matrix. They are nothing more than 4x4 matrices, 
	 * which are designed so that when you multiply a 3D point in camera 
	 * space by one of these matrices, you end up with a new point which 
	 * is the projected version of the original 3D point onto the canvas.
	 */
	public void createProjectionMatrix(Matrix4f projectionMatrix)
    {
		float aspectRatio = this.aspectRatio;
		float scaleY = (float) ((1f / Math.tan(Math.toRadians(this.getFov() / 2f))));
		float scaleX = scaleY / aspectRatio;
		float frustumLength = this.getFarPlane() - this.getNearPlane();
		projectionMatrix.m00 = scaleX;
		projectionMatrix.m11 = scaleY;
		projectionMatrix.m22 = -((this.getFarPlane() + this.getNearPlane()) / frustumLength);
		projectionMatrix.m23 = -1f;
		projectionMatrix.m32 = -((2 * this.getNearPlane() * this.getFarPlane()) / frustumLength);
		projectionMatrix.m33 = 0;
    }
	
	/**
	 * Creates a simple clipping line under or above which certain effects or objects will not be 
	 * rendered. For example, the refractive index of water.
	 */
	public Vector4f createClipPlaneVector(float x, float heightIn, float z, float weigthIn)
	{
		this.clipPlane = new Vector4f(x, heightIn, z, weigthIn);
			return this.clipPlane;
	}

	
	/**
	 * Prepares everything for rendering. Clears previous buffers and 
	 * includes depth test.
	 */
	public void prepare(float r, float g, float b, float a)
	{
		GlHelper.enableDepthTest();
		GlHelper.glClear(Texture.COLOR_BUFFER_BIT | Depth.DEPTH_BUFFER_BIT);
		GlHelper.glClearColor(r, g, b, a);
	}
	
	/**
	 * After all the preparations, it just renders a picture of the frame.
	 */
	public void render(List<Light> lights)
	{
	}
	
	/**
	 * Clean up base renderer component loader.
	 */
	public void cleanUp()
	{
		if(this.isCleanUpLoader) 
		{
			this.openGlObjectsLoader.cleanUpOpenGlDataObjects();	
			this.textureLoader.cleanUpTextureOpenGlObjects();
			this.isCleanUpLoader = false;
		}
		this.cx.LOGGER.info("Clean Up renderer!");
	}
	
	/**
	 * Return the fov value of current renderer.
	 */
	public float getFov() 
	{
		return this.fov;
	}

	/**
	 * Return the near plane value of current renderer.
	 */
	public float getNearPlane() 
	{
		return this.nearPlane;
	}

	/**
	 * Return the far plane value of current renderer.
	 */
	public float getFarPlane() 
	{
		return this.farPlane;
	}

	/**
	 * Return the loader component.
	 */
	public Loader getLoader()
	{
		return this.openGlObjectsLoader;
	}
	
	public void cleanUpLoader()
	{
		this.isCleanUpLoader = true;
	}
	
}
