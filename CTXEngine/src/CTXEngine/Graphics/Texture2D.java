package CTXEngine.Graphics;

import static CTXEngine.Core.SimplePrint.*;
import static CTXEngine.Core.CoreBase.*;

import CTXEngine.Graphics.GL.GLTexture2D;

/**
 * Images in this texture all are 2-dimensional. They have width and height,
 * but no depth.
 */
public abstract class Texture2D extends Texture
{
	/*Param effect on tiling texture.*/
	private float tiling = CTX_TEXTURE_TILING_NONE;
	/*This is texture tiling factor if tiling is enabled.*/
	private float tilingFactor = 10;
	
	/**
	 * Create simple 2-dimensional texture.
	 */
	public static Texture2D create(final String resourceIn)
	{
		switch (RenderEngine.getAPI().get())
		{
			case "none":
				CTX_ENGINE_ASSERT(false, "Can't create texture, because render api not define!");
					return null;
			case "opengl":
				return new GLTexture2D(resourceIn);
			case "vulcan":
				CTX_ENGINE_ASSERT(false, "Can't create texture, because vulcan api not support yet!");
					return null;
			case "directx":
				CTX_ENGINE_ASSERT(false, "Can't create texture, because directx api not support yet!");
					return null;
		}
		CTX_ENGINE_ASSERT(false, "Can't create texture, because api is unknown!");
			return null;
	}
	
	/**
	 * Create simple 2-dimensional texture.
	 */
	public static Texture2D create(int width, int height)
	{
		switch (RenderEngine.getAPI().get())
		{
			case "none":
				CTX_ENGINE_ASSERT(false, "Can't create texture, because render api not define!");
					return null;
			case "opengl":
				return new GLTexture2D(width, height);
			case "vulcan":
				CTX_ENGINE_ASSERT(false, "Can't create texture, because vulcan api not support yet!");
					return null;
			case "directx":
				CTX_ENGINE_ASSERT(false, "Can't create texture, because directx api not support yet!");
					return null;
		}
		CTX_ENGINE_ASSERT(false, "Can't create texture, because api is unknown!");
			return null;
	}
	
	/**
	 * Return the titling variable.
	 */
	public final float getTiling() 
	{
		return this.tiling;
	}

	/**
	 * Return this tiling factor variable. 
	 */
	public final float getTilingFactor()
	{
		return this.tilingFactor;
	}
	
	/**
	 * Set the tiling, and after that texture can use tiling on object. If mode if one is
	 * default mode with factor, if two then is tiling to object scale.
	 */
	public final void setTiling(float useTilingMode) 
	{
		if (useTilingMode > 2 || useTilingMode < 0)
		{
			CTX_ENGINE_WARN("This tiling mode" + useTilingMode +" not supported!");
			this.tiling = CTX_TEXTURE_TILING_DEFAULT;
		}
		else
		{
			this.tiling = useTilingMode;
		}
	}
	
	/**
	 * Set the tiling factor if tiling is not disabled.
	 */
	public final void setTilingFactor(float factor)
	{
		this.tilingFactor = factor;
	}
	
}
