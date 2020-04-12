package CTXEngine.Graphics;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * is an image applied (mapped) to the surface of a shape or
 * polygon. This may be a bitmap image or a procedural
 * texture. They may be stored in common image file formats,
 * referenced by 3d model formats or material definitions,
 * and assembled into resource bundles.
 */
public abstract class Texture 
{
	/**Texture rendering options definitions / ints.*/
	public static final int CTX_TEXTURE_TILING_NONE = 0;
	public static final int CTX_TEXTURE_TILING_DEFAULT = 1;
	public static final int CTX_TEXTURE_TILING_TO_SCALE = 2;
	
	/**
	 * Set texture data into void pointer block of memory (in Java with ByteBuffer) 
	 * with size.
	 */
	public abstract void setTextureData(ByteBuffer texData, int size);
	
	/**
	 * Set texture data into void pointer block of memory (in Java with IntBuffer) 
	 * with size.
	 */
	public abstract void setTextureData(IntBuffer texData, int size);
	
	/*
	 * Bind texture id and prepare to use.
	 */
	public abstract void bind(int slot);
	
	/*
	 * Bind texture id and prepare to use.
	 */
	public abstract void bind();
	
	/**
	 * Unbind texture id.
	 */
	public abstract void unBind(int slot);
	
	/**
	 * Unbind texture id.
	 */
	public abstract void unBind();
	
	/**
	 * Return the id of this texture.
	 */
	public abstract int getId();
	
	/**
	 * Return type of this texture.
	 */
	public abstract int getType();
	
	/**
	 * Return the width of this texture.
	 */
	public abstract int getWidth();
	
	/**
	 * Return the height of this texture.
	 */
	public abstract int getHeight();
	
	/**
	 * Return the format of this texture.
	 */
	public abstract int  getInternalFormat();
	
	/**
	 * Return the format of this texture.
	 */
	public abstract int  getFormat();
	
	/**
	 * Return channels of this texture.
	 */
	public abstract int  getChannels();
	
	/**
	 * Return the resource folder path where stored texture.
	 */
	public abstract String getResource();
}
