package CTXEngine.Graphics.GL;

import static CTXEngine.Core.CAndCppOperations.*;
import static org.lwjgl.opengl.GL30.*;

import CTXEngine.Graphics.RenderEngineAPI;
import CTXEngine.Graphics.VertexArrayObject;
import org.joml.Vector4f;

/**
 * This render engine be hold all commands and functions with
 * Open GL Rendering API.
 */
public class GLRenderEngineAPI extends RenderEngineAPI
{

	/**
	 * It will install all specific for rendering api functions.
	 */
	@Override
	public void init() 
	{
		GLHelper.hctxEnableDepthTesting();
		GLHelper.hctxEnableDepthTesting();
	}

	/**
	 * Set viewport of renderer framebuffer. Where x, y is position and width and 
	 * heightis resolution.
	 */
	@Override
	public void setViewport(int x, int y, int width, int height) 
	{
		GLHelper.hglViewport(x, y, width, height);
	}

	/**
	 * Start to test each pixel on depth (z axis).
	 */
	@Override
	public void enableDepthTesting() 
	{
		GLHelper.hctxEnableDepthTesting();
	}


	/**
	 * Stops to test each pixel on depth (z axis).
	 */
	@Override
	public void disableDepthTesting() 
	{
		GLHelper.hctxDisableDepthTesting();
	}

	/**
	 * Enable alpha channel blending.
	 */
	@Override
	public void enableAlphaBlending() 
	{
		GLHelper.hctxEnableAlphaBlendig();
	}

	/**
	 * Disable alpha channel blending.
	 */
	@Override
	public void disableAlphaBlending() 
	{
		GLHelper.hctxDisableAlphaBlendig();
	}

	/**
	 * Clear colour from current or previous rendered frame.
	 */
	@Override
	public void clearColor(Vector4f colorIn) 
	{
		GLHelper.hglClearColor(colorIn.x, colorIn.y, colorIn.z, colorIn.w);
	}

	/**
	 * This method will be draw input vao mesh uses only arrays in
	 * to the screen.
	 */
	@Override
	public void clear() 
	{
		GLHelper.hglClear(GLHelper.hDefaultClearMask);
	}

	/**
	 * This method will be draw input vao mesh uses only arrays in to the screen.
	 */
	@Override
	public void drawArrays(VertexArrayObject vaoMesh) 
	{
		GLHelper.hglDrawArrays(GL_TRIANGLES, 0,
				(vaoMesh.getVertexBuffers().get(0).getSize() / sizeof("float")) / vaoMesh.getVertexBuffers().get(0).getSize());
	}
	
	/**
	 * This method will be draw input vao mesh uses only arrays in to the screen.
	 */
	@Override
	public void drawIndices(VertexArrayObject vaoMesh) 
	{
		GLHelper.hglDrawElements(GL_TRIANGLES, vaoMesh.getIndexBuffers().get(0).getCount(), GL_UNSIGNED_INT,
				0);
	}
	
	@Override
	public void drawIndices(VertexArrayObject vaoMesh, int indexCount) 
	{
		int count = indexCount != 0 ? vaoMesh.getIndexBuffers().get(0).getCount() : indexCount;
		GLHelper.hglDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT,
				0);
	}

}
