package CTXEngine.Graphics.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL;

import static CTXEngine.Core.SimplePrint.*;
import static CTXEngine.Core.CoreBase.*;

import CTXEngine.Core.CoreException;
import CTXEngine.Graphics.GraphicsContext;

public class GLGraphicsContext extends GraphicsContext
{
	/**Specific OpenGL window (This is long where in (native .dll) struct GLFWwindow stored window data.).*/
	protected long glfwWindow;
	
	public static String OPEN_GL_VERSION;
	
	/**
	 * Create new open gl context use the GLFW window.
	 */
	public GLGraphicsContext(long glfwWindowIn) throws CoreException 
	{
		this.glfwWindow = glfwWindowIn;
		
		if (glfwWindowIn == 0)
		{
			glfwTerminate();
			throw new CoreException("Can't load gl window and create context, because is null!");
		}
	}

	/**
	 * Initialize open gl graphics context use glfw support functions. For 
	 * loader this init method can use any of OpenGL's loaders, such a GLEW,
	 * GLAD, glLoadLibrary. But for CTXEngine it be GLAD and his method
	 * gladLoadGLLoader().
	 * 
	 * @throws CoreException 
	 */
	@Override
	public void init() throws CoreException 
	{
		CTX_ENGINE_INFO("Creating Open Gl context...");
		if(CTX_RENDER_CONTEXT == CTX_GL)
		{
			glfwMakeContextCurrent(this.glfwWindow);
			
			//we dont load open gl with loader because LWJGL aleady loaded it.
			GL.createCapabilities();
			GLGraphicsContext.OPEN_GL_VERSION = glGetString(GL_VERSION);
			
			CTX_ENGINE_INFO("");
			CTX_ENGINE_INFO("=============================");
			CTX_ENGINE_INFO("      API Info: ", CTX_API_GL);
			CTX_ENGINE_INFO("=============================");
			CTX_ENGINE_INFO(" Vendor: ", glGetString(GL_VENDOR));
			CTX_ENGINE_INFO(" Render: ", glGetString(GL_RENDERER));
			CTX_ENGINE_INFO(" Driver: ", glGetString(GL_VERSION));
			CTX_ENGINE_INFO("");
			
		}
		else
		{
			throw new CoreException("Can't create Open GL context, because CTX_RENDER_CONTEXT = " + CTX_RENDER_CONTEXT);
		}
		
	}

	/**
	 * Destroy already initialized graphical context.
	 */
	@Override
	public void destroy() 
	{
		if(this.glfwWindow != 0)
		{
			glfwTerminate();
			this.glfwWindow = 0;
		}
	}

	/**
	 * Swap buffer means basically clear previous frame and draw new every frame,]
	 * from Open GL API.
	 */	
	@Override
	public void swapBuffers() 
	{
		glfwSwapBuffers(this.glfwWindow);
	}

}
