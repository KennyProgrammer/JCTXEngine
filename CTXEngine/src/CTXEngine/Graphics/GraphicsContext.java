package CTXEngine.Graphics;

import CTXEngine.Core.CoreException;

/**
 * A graphics context represents a drawing destination. It contains drawing parameters
 * and all device-specific information that the drawing system needs to perform any subsequent
 * drawing commands. A graphics context defines basic drawing attributes such as the colors to
 * use when drawing, the clipping area, line width and style information, font information, compositing
 * options, and several others
 * 
 * In shorter graphics context provided us how a programmers to graphical
 * API, such a OpenGl, Vulcan, or DirectX3D.
 * 
 * @autor Kenny (Danil Dukhovenko)
 */
public abstract class GraphicsContext 
{	
	/**
	 * Initialize current graphical context, i.e provides to graphical API.
	 * @throws CoreException 
	 */
	public abstract void init() throws CoreException;
	
	/**
	 * Destroy already initialized graphical context.
	 */
	public abstract void destroy();
	
	/**
	 * Swap buffer means basically clear previous frame and draw new every frame.
	 */
	public abstract void swapBuffers();
}
