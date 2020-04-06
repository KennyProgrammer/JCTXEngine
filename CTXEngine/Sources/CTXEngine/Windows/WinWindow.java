package CTXEngine.Windows;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryUtil.*;
import static CTXEngine.Core.CoreBase.*;
import static CTXEngine.Core.SimplePrint.*;
import static CTXEngine.Core.EventSystem.ClientEvent.*;
import static CTXEngine.Core.EventSystem.MouseEvent.*;
import static CTXEngine.Core.EventSystem.KeyEvent.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.glfw.GLFWCharCallbackI;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import CTXEngine.Core.CoreApp;
import CTXEngine.Core.CoreException;
import CTXEngine.Core.CoreUtils;
import CTXEngine.Core.EventSystem.Event;
import CTXEngine.Core.EventSystem.Event.EventData;
import CTXEngine.Graphics.GraphicsContext;
import CTXEngine.Graphics.GL.GLGraphicsContext;

public class WinWindow implements IWindow
{	
	/**This is sender booleans to send message from working callback.*/
	public static final boolean
		sendSetSizeCallbackMsg = false,
		sendSetCloseCallbackMsg = false,
		sendSetKeyCallbackMsg = false,
		sendSetCharCallbackMsg = false,
		sendSetMouseButtonCallbackMsg = false,
		sendSetCursorPosCallbackMsg = false,
		sendSetScrollCallback = false,
		sendSetFrameBufferSizeCallback = false;
	
	/**This is width of this window in pixels.*/
	public int width;
	/**This is height of this window in pixels.*/
	public int height;
	/**This is main title of this window.*/
	public String title;
	/**Check if this window in fullscreen mode or not.*/
	public boolean fullscreen;
	/**Check if this window is resizeable or not.*/
	public boolean resized;
	/**Check if glfw library is initialized.*/
	public boolean glfwInitialized;
	/**Graphical context.*/
	public GraphicsContext graphicsContext;
	/**This is WINDOWS GLFW window reference.*/
	public long window;
	/**This is video mode of this window.*/
	public GLFWVidMode videoMode;
	
	/**
	 *	Return interface to created window.
	 */
	public static IWindow create()
	{
		return new WinWindow();
	}
	
	public WinWindow() 
	{
		try 
		{
			this.setTitle(CoreApp.get().getConfigurations().getAppName()); // move params to game configuration
			this.setResized(CoreApp.get().getConfigurations().isResized());
			this.setWindowResolution(CoreApp.get().getConfigurations().getWindowWidth(), 
									  CoreApp.get().getConfigurations().getWindowHeight()); // move params to game configuration
			this.setFullscreen(CoreApp.get().getConfigurations().isFullscreen());
			this.createWindow();
			this.setIcon("logo_16x16.png", "logo_32x32.png");
			this.setWindowCallbacks();
			this.setVsync(CoreApp.get().getConfigurations().isVSync());
			
		} catch (CoreException e) { e.printStackTrace(); }
	}
	
	/*
	 * Return the current os window, like windows GLFW of mac .. or linux ..
	 */
	@Override
	public final long getSystemWindow() 
	{
		return this.window;
	}

	/*
     *  Initialize the WINDOWS GLFW library for window. If library is not
	 *  initialize, then application will be closed.
	 */
	@Override
	public void initWinSystemAPI() 
	{
		if (!glfwInit())
		{
			this.glfwInitialized = false;
		}
		else
		{
			glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.out));
			this.glfwInitialized = true;
		}

		if (!this.glfwInitialized)
		{
			CTX_ENGINE_ERROR("Glfw window is not initialized.");
			CTX_ENGINE_INFO("Shuttdown internal servers...");
			System.exit(-1);
		}
	}

	@Override
	public void createWindow() throws CoreException 
	{
		//Here this import, because WinWindow not specific Open GL Renderer,
		//its just provider to open context, in future its will be done for all window
		//systems.
		
		//we don't 'using' here, because we already import all classes.
		CTX_ENGINE_INFO("Creating window " + this.width + "x" + this.height + "...");
		
		this.initWinSystemAPI();

		glfwWindowHint(GLFW_RESIZABLE, this.resized ? 1 : 0);
		glfwWindowHint(GLFW_SCALE_TO_MONITOR, 1);
		glfwWindowHint(GLFW_DECORATED, 1);
		
		/* Create a windowed mode window and its OpenGL context */
		this.window = glfwCreateWindow(this.width, this.height, this.title, this.fullscreen ? glfwGetPrimaryMonitor() : NULL, NULL);
	
		if(CTX_WINDOW_SYS_GL == 1)
			this.graphicsContext = new GLGraphicsContext(this.window);
//		else if(CTX_WINDOW_SYS_VK == 1)
//			this.graphicsContext = new VKGraphicsContext(this.window);
//		else if(CTX_WINDOW_SYS_DX3D == 1)
//			this.graphicsContext = new DX3DGraphicsContext(this.window);
			
		this.graphicsContext.init();
		
		this.videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		glfwWindowHint(GLFW_RED_BITS, this.videoMode.redBits());
		glfwWindowHint(GLFW_GREEN_BITS, this.videoMode.greenBits());
		glfwWindowHint(GLFW_BLUE_BITS, this.videoMode.blueBits());
		glfwWindowHint(GLFW_REFRESH_RATE, this.videoMode.refreshRate());
																//maybe error (C++ &data)
		//glfwSetWindowUserPointer(this.window, JNINativeInterface.NewGlobalRef(new WinWindow()));
		glfwSetWindowTitle(this.window, this.title);
		glfwSetWindowSize(this.window, this.width, this.height);
		glfwSetWindowSizeLimits(this.window, 427, 240, 1600, 900);
		glfwSetWindowPos(this.window, ((this.videoMode.width() - this.width) / 2),
			((this.videoMode.height() - this.height) / 2));
		
		
	}

	/**
	 * Swap the buffers and update window main framebuffer every frame.
	 * @throws CoreException 
	 */
	@Override
	public void updateWindow() throws CoreException 
	{
		if (this.graphicsContext == null)
		{
			CTX_ENGINE_ERROR("I can't update the next frame because the graphic context");
			CTX_ENGINE_ERROR("was not created or was created with an error.");
			throw new CoreException("Can't update!");
		}
		this.graphicsContext.swapBuffers();
	}

	/**
	 * Destroy the main window.
	 * @throws CoreException 
	 */
	@Override
	public void destroyWindow() throws CoreException 
	{
		if (this.graphicsContext == null)
		{
			CTX_ENGINE_ERROR("I can't destroy this window because the graphic context");
			CTX_ENGINE_ERROR("was not created or was created with an error.");
			throw new CoreException("Can't destroy!");
		}
		this.graphicsContext.destroy();
	}
	

	/**
	 * This method if handle input from mouse, keyboard, joystick and other
	 * gaming controllers.
	 */
	@Override
	public void handleInput()
	{
		glfwPollEvents();
	}
	
	/**
	 * Set width and height resolution for window in pixels.
	 */
	public void setWindowResolution(int widthIn, int heightIn)
	{
		this.setWidth(widthIn);
		this.setHeight(heightIn);
	}

	/**
	 * Set the main title for this window.
	 */
	public void setTitle(String titleIn)
	{
		this.title = titleIn;
	}
	
	/**
	 * Set the width for this window in pixels.
	 */
	public void setWidth( int widthIn)
	{
		this.width = widthIn;
	}
	
	/**
	 * 	Set the height for this window in pixels.
	 */
	public void setHeight( int heightIn)
	{
		this.height = heightIn;
	}
	
	/**
	 * Set the fullscreen mode for this window.
	 */
	public void setFullscreen(boolean isFullscreenIn)
	{
		this.fullscreen = isFullscreenIn;
	}
	
	/**
	 * 	Set the resizeble mode of this window or disabled it.
	 */
	public void setResized(boolean isResized)
	{
		this.resized = isResized;
	}

	/**
	 * Set vertical synchronization.
	 */
	@Override
	public void setVsync(boolean vSync) 
	{
		if (vSync)
		{
			glfwSwapInterval(1);
		}
		else
		{
			glfwSwapInterval(0);
		}
	}

	/**
	 * Load the icons from resources and set icon on top of WINDOWS window.
	 */
	@Override
	public void setIcon(String path16, String path32) 
	{
		//stb load 

		ByteBuffer icon16;
		ByteBuffer icon32;
		try {
		    icon16 = CoreUtils.ioResourceToByteBuffer(path16, 2048);
		    icon32 = CoreUtils.ioResourceToByteBuffer(path32, 4096);
		} catch (Exception e) {
		    throw new RuntimeException(e);
		}


		IntBuffer w = memAllocInt(1);
		IntBuffer h = memAllocInt(1);
		IntBuffer comp = memAllocInt(1);

		try ( GLFWImage.Buffer icons = GLFWImage.malloc(2) ) 
		{
		    ByteBuffer pixels16 = stbi_load_from_memory(icon16, w, h, comp, 4);
		    icons
		        .position(0)
		        .width(w.get(0))
		        .height(h.get(0))
		        .pixels(pixels16);

		    ByteBuffer pixels32 = stbi_load_from_memory(icon32, w, h, comp, 4);
		    icons
		        .position(1)
		        .width(w.get(0))
		        .height(h.get(0))
		        .pixels(pixels32);

		    icons.position(0);
		    glfwSetWindowIcon(window, icons);

		    stbi_image_free(pixels32);
		    stbi_image_free(pixels16);
		}
	}

	/**
	 * Set close requested trigger and stop / start update the window.
	 */
	@Override
	public void setCloseRequested(boolean closeRequested) 
	{
		glfwSetWindowShouldClose(this.window, closeRequested);
	}
	
	/**
	 * Set and create callbacks for this window.
	 */
	public void setWindowCallbacks()
	{
		//only Java's event storage, because java doesn't have propertly functional
		//interface.
		EventData data = new Event.EventData();
		
		//Sets the size callback of the specified window, which is called when
		//the window is resized.
		glfwSetWindowSizeCallback(this.window, new GLFWWindowSizeCallbackI() 
		{
			@Override
			public void invoke(long window, int width, int height) 
			{
				WindowResizeEvent eRes = new WindowResizeEvent(width, height);
				if (sendSetSizeCallbackMsg)
				{
					CTX_ENGINE_INFO(eRes.toString());
				}
				data.callback(eRes);
				eRes = null;
			}

		});
		
		//Sets the close callback of the specified window, which is called 
		//when the user attempts to close the window, for example by clicking 
		//the close widget inthe title bar. 
		glfwSetWindowCloseCallback(this.window, new GLFWWindowCloseCallbackI() 
		{
			@Override
			public void invoke(long window) 
			{
				WindowCloseEvent eClose = new WindowCloseEvent();
				if (sendSetCloseCallbackMsg)
				{
					CTX_ENGINE_INFO("[" + eClose.toString() + "] Destorying window...");
				}
				else
				{
					CTX_ENGINE_INFO("Destorying GLFW window... ");
				}
				data.callback(eClose);
				eClose = null;
			}
		});
		
		//This callback calls when user press, release or hold key.
		glfwSetKeyCallback(this.window, new GLFWKeyCallback() 
		{
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) 
			{
				switch (action)
				{
					case GLFW_PRESS:
					{
						KeyPressedEvent eKeyPressed = new KeyPressedEvent(key, 0);
						if (sendSetKeyCallbackMsg)
						{
							CTX_ENGINE_INFO(eKeyPressed.toString());
						}
						data.callback(eKeyPressed);
						eKeyPressed = null;
						break;
					}
					case GLFW_RELEASE:
					{
						KeyReleasedEvent eKeyReleased = new KeyReleasedEvent(key);
						if (sendSetKeyCallbackMsg)
						{
							CTX_ENGINE_INFO(eKeyReleased.toString());
						}
						data.callback(eKeyReleased);
						eKeyReleased = null;
						break;
					}
					case GLFW_REPEAT:
					{
						KeyPressedEvent eKeyHold = new KeyPressedEvent(key, 1);
						if (sendSetKeyCallbackMsg)
						{
							CTX_ENGINE_INFO(eKeyHold.toString());
						}
						data.callback(eKeyHold);
						eKeyHold = null;
						break;
					}
					
				}
			}
		});
		
		//Sets the character callback of the specified window, which is called 
		//when a Unicode character is input. 
		glfwSetCharCallback(this.window, new GLFWCharCallbackI() 
		{
			@Override
			public void invoke(long window, int codepoint) 
			{
				KeyTypedEvent eKeyTyped = new KeyTypedEvent(codepoint);
				if (sendSetCharCallbackMsg)
				{
					CTX_ENGINE_INFO(eKeyTyped.toString());
				}
				data.callback(eKeyTyped);
				eKeyTyped = null;
			}
		});
		
		//This callback calls when user press, release or hold mouse
		//button.
		glfwSetMouseButtonCallback(this.window, new GLFWMouseButtonCallback() 
		{
			@Override
			public void invoke(long window, int button, int action, int mods) 
			{
				switch (action)
				{
					case GLFW_PRESS:
					{
						MouseButtonPressedEvent eMouseButtonPressed = new MouseButtonPressedEvent(button);
						if (sendSetMouseButtonCallbackMsg)
						{
							CTX_ENGINE_INFO(eMouseButtonPressed.toString());
						}
						data.callback(eMouseButtonPressed);
						eMouseButtonPressed = null;
						break;
					}
					case GLFW_RELEASE:
					{
						MouseButtonReleasedEvent eMouseButtonReleased = new MouseButtonReleasedEvent(button);
						if (sendSetMouseButtonCallbackMsg)
						{
							CTX_ENGINE_INFO(eMouseButtonReleased.toString());
						}
						data.callback(eMouseButtonReleased);
						eMouseButtonReleased = null;
						break;
					}
					case GLFW_REPEAT:
					{
						MouseButtonHoldEvent eMouseButtonHold = new MouseButtonHoldEvent(button);
						if (sendSetMouseButtonCallbackMsg)
						{
							CTX_ENGINE_INFO(eMouseButtonHold.toString());
						}
						data.callback(eMouseButtonHold);
						eMouseButtonHold = null;
						break;
					}
				}
			}
		});
		
		//This callback calls when user move the mouse onto the screen, and
		//save information into local class with x and y positions.
		glfwSetCursorPosCallback(this.window, new GLFWCursorPosCallback()
		{
			@Override
			public void invoke(long window, double x, double y) 
			{
				MouseMoveEvent eMouseMove = new MouseMoveEvent((float)x, (float)y);
				if (sendSetCursorPosCallbackMsg)
				{
					CTX_ENGINE_INFO(eMouseMove.toString());
				}
				data.callback(eMouseMove);
				eMouseMove = null;
			}
		});
		
		//This callback calls when user scroll the mouse onto the screen, and
		//save information into local class with offX and offY positions.
		glfwSetScrollCallback(this.window, new GLFWScrollCallback() 
		{
			@Override
			public void invoke(long window, double offsetX, double offsetY) 
			{
				MouseScrolledEvent eMouseScroll = new MouseScrolledEvent((float)offsetX, (float)offsetY);
				if (sendSetScrollCallback)
				{
					CTX_ENGINE_INFO(eMouseScroll.toString());
				}
				data.callback(eMouseScroll);
				eMouseScroll = null;
			}
		});
	}

	/**
	 * Return the title from top of this window in char pointers.
	 */
	@Override
	public String getTitle() 
	{
		return this.title;
	}

	/**
	 * Return the width of this window.
	 */
	@Override
	public int getWidth() 
	{
		return this.width;
	}

	/**
	 * Return the height of this window.
	 */
	@Override
	public int getHeight() 
	{
		return this.height;
	}

	/**
	 * Check if this window is not need to close, if true, then window
	 * automatically closed.
	 */
	@Override
	public boolean isCloseRequested()
	{
		return glfwWindowShouldClose(this.window);
	}

	/**
	 * Check if this window in fullscreen.
	 */
	@Override
	public boolean isFullscreen() 
	{
		return this.fullscreen;
	}

	/**
	 * Check if this window in resized.
	 */
	@Override
	public boolean isResized() 
	{
		return this.resized;
	}

}
