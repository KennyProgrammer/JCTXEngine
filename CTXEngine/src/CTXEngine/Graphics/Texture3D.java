package CTXEngine.Graphics;

import static CTXEngine.Core.CoreBase.CTX_ENGINE_ASSERT;

/**
 * Images in this texture all are 3-dimensional. They have width, height,
 * and depth.
 */
public abstract class Texture3D extends Texture
{
	/**
	 *  Create simple 3-dimensional texture.
	 */
	public static Texture3D create(final String resourceIn)
	{
		switch (RenderEngine.getAPI().get())
		{
			case "none":
				CTX_ENGINE_ASSERT(false, "Can't create texture, because render api not define!");
					return null;
			case "opengl":
				return null;
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
	 * Create simple 3-dimensional texture.
	 */
	public static Texture3D create(int width, int height, int depth) 
	{
		switch (RenderEngine.getAPI().get())
		{
			case "none":
				CTX_ENGINE_ASSERT(false, "Can't create texture, because render api not define!");
					return null;
			case "opengl":
				return null;
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
}
