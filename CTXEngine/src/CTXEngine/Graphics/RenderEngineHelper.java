package CTXEngine.Graphics;

import static CTXEngine.Core.SimplePrint.*;

import org.joml.Vector4f;

import CTXEngine.Graphics.GL.GLRenderEngineAPI;

/**
 * This class contains up-header rendering api functions, that out
 * side actual scenes. In other words MAIN BASE render functions.
 */
public final class RenderEngineHelper 
{
	/**Set current render engine. (for now it be Open GL)*/
	private static RenderEngineAPI renderEngineApi = new GLRenderEngineAPI();
	
	public RenderEngineHelper() 
	{
		if(renderEngineApi == null)
		{
			CTX_ENGINE_ERROR("Render engine Api is not set or equals NULL / null / nullptr.");
			Runtime.getRuntime().exit(-1);
		}
	}
	
	/**
	 * It will install all our engine functions for rendering.
	 */
	public static void init()
	{
		renderEngineApi.init();
	}
	
	/**
	 * 	Set viewport of basic renderer framebuffer. Where x, y is position,
	 * and width, height is resolution.
	 */
	public static void setViewport( int x,  int y, int width, int height)
	{
		renderEngineApi.setViewport(x, y, width, height);
	}
	
	/**
	 * Start to test each pixel on depth (z axis).
	 */
	public static void enableDepthTesting()
	{
		renderEngineApi.enableDepthTesting();
	};
	
	/**
	 * Stop to test each pixel on depth (z axis).
	 */
	public static void disableDepthTesting()
	{
		renderEngineApi.disableDepthTesting();
	};
	
	/*
	 * Enable alpha channel blending.
	 */
	public static void enableAlphaBlending()
	{
		renderEngineApi.enableAlphaBlending();
	};
	
	/**
	 * Disable alpha channel blending.
	 */
	public static void disableAlphaBlending()
	{
		renderEngineApi.disableAlphaBlending();
	};
	
	/**
	 * 	Clear colour from current or previous rendered frame.
	 */
	public static void clearColor(Vector4f colorIn)
	{
		renderEngineApi.clearColor(colorIn);
		//RenderEngineHelper::clearColourFromEngine = true;
	};
	
	/**
	 * 	Clear storage of pixels, or depths.
	 */
	public static void clear()
	{
		renderEngineApi.clear();
		//RenderEngineHelper::clearFromEngine = true;
	};
	
	/**
	 * 	This method will be draw input vao mesh uses only arrays in to the
	 *  screen.
	 */
	public static void drawArrays(final VertexArrayObject vaoMesh)
	{
		renderEngineApi.drawArrays(vaoMesh);
	};
	
	/**
	 * This method will be draw input vao mesh uses vertex indices in to the
	 * screen.
	 */
	public static void drawIndices(final VertexArrayObject vaoMesh)
	{
		renderEngineApi.drawIndices(vaoMesh);
	};
}
