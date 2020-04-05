package CTXEngine.Graphics;

import org.joml.Vector4f;

/**
 * Render Engine API is main abstract class to contains main functions and methods
 * for rendering. Basically is CTXEnigne Rendering API interface, and this interface
 * be realized with specific graphical rendering API, such a OpenGL, Vulcan or DirectX3D.
 */
public abstract class RenderEngineAPI 
{
	/**
	 * This enum class constrains all support rendering api for this render
	 * engine.
	 */
	public static enum Api
	{
		none("none"),
		opengl("opengl"),
		vulcan("vulcan"),
		directx("directx");
		
		private String i;
		
		Api(String i) { this.i = i; }
		
		public String get() { return this.i; }
	};
	
	/**Instance of this class*/
	private static RenderEngineAPI.Api instance = RenderEngineAPI.Api.opengl;
	
	/**
	 * It will install all specific for rendering api functions.
	 */
	public abstract void init();
	
	/**
	 * Set viewport of renderer framebuffer. Where x, y is position and width and height 
	 * is resolution.
	 */
	public abstract void setViewport(int x, int y, int width, int height);
	
	/**
	 * Start to test each pixel on depth (z axis).
	 */
	public abstract void enableDepthTesting();
	
	/**
	 * Stop to test each pixel on depth (z axis).
	 */
	public abstract void disableDepthTesting();
	
	/**
	 * Enable alpha channel blending.
	 */
	public abstract void enableAlphaBlending();
	
	/**
	 * Disable alpha channel blending.
	 */
	public abstract void disableAlphaBlending();
	
	/**
	 * Clear colour from current or previous rendered frame.
	 */
	public abstract void clearColor(Vector4f colorIn);
	
	/**
	 * Clear storage of pixels, or depths.
	 */
	public abstract void clear();
	
	/**
	 * This method will be draw input vao mesh uses only arrays in to the screen.
	 */
	public abstract void drawArrays(final VertexArrayObject vaoMesh);
	
	/**
	 * This method will be draw input vao mesh uses vertex indices in to the screen.
	 */
	public abstract void drawIndices(final VertexArrayObject vaoMesh);
	
	/**
	 * This method will be draw input vao mesh uses vertex indices in to the screen.
	 */
	public abstract void drawIndices(final VertexArrayObject vaoMesh, int indexCount);
	
	/**
	 * Return static instance of this class.
	 */
	public static Api getAPI()
	{
		return instance;
	}
}
