package CTXEngine.Graphics;

import static CTXEngine.Core.CoreBase.CTX_ENGINE_ASSERT;

/**
 * There are exactly 6 distinct sets of 2D images, all of the same size. They
 * act as 6 faces of a cube.
 */
public abstract class TextureCube extends Texture
{
	/**
	 * Create 6 distinct sets of 2D images, all of the same size. They
	 * act as 6 faces of a cube.
	 */
	public static TextureCube create(final String resourceIn)
	{
		switch (RenderEngine.getAPI().get())
		{
			case "none":
				CTX_ENGINE_ASSERT(false, "Can't create texture cube, because render api not define!");
					return null;
			case "opengl":
				return null;
			case "vulcan":
				CTX_ENGINE_ASSERT(false, "Can't create texture cube, because vulcan api not support yet!");
					return null;
			case "directx":
				CTX_ENGINE_ASSERT(false, "Can't create texture cube, because directx api not support yet!");
					return null;
		}
		CTX_ENGINE_ASSERT(false, "Can't create texture cube, because api is unknown!");
			return null;
	}
	
	/**
	 * Create 6 distinct sets of 2D images, all of the same size. They
	 * act as 6 faces of a cube.
	 */
	public static TextureCube create(int width, int height, int depth) 
	{
		switch (RenderEngine.getAPI().get())
		{
			case "none":
				CTX_ENGINE_ASSERT(false, "Can't create texture cube, because render api not define!");
					return null;
			case "opengl":
				return null;
			case "vulcan":
				CTX_ENGINE_ASSERT(false, "Can't create texture cube, because vulcan api not support yet!");
					return null;
			case "directx":
				CTX_ENGINE_ASSERT(false, "Can't create texture cube, because directx api not support yet!");
					return null;
		}
		CTX_ENGINE_ASSERT(false, "Can't create texture cube, because api is unknown!");
			return null;
	}
}
