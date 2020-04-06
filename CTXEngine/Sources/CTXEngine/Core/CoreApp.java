package CTXEngine.Core;

import static CTXEngine.Core.SimplePrint.*;
import static CTXEngine.Core.CoreBase.*;

import org.lwjgl.glfw.GLFW;
import CTXEngine.Core.CoreException;
import CTXEngine.Windows.IWindow;
import CTXEngine.Windows.WinWindow;

/**
 * <b>CTXEngine</b> is an engine in which I'm writing a game and the actual core is
 * a game. It is written in C++, Java with Open Gl and other helps libraries, such a
 * window system - GLFW, connection with OpenGL - LWJGL native code, gl math library - JOML and
 * others.
 *
 * Basically this Core class is base of CTXEngine. Application class extends of this
 * Core class.
 *
 * @author Danila Dukhovenko (Kenny) and @KennyCopyrightTeam
 *
 * @version 0.1.20.1j - j is means java version of engine. 
 * 					  - c is means c/c++ version of engine.
 *
 * @since 0.1.20 core
 */
public abstract class CoreApp implements CoreAppImpl, CoreAppClientImpl
{
	public static class CoreConfigurations implements CoreConfigurationsImpl
	{
		private String  ctxEngineName;
		private String  ctxEngineVersion;
		public boolean ctxIsWindowed;
		public boolean ctxIsVsync;
		public boolean ctxIsResized;
		public String   ctxAppName;
		public String   ctxAppVersion;
		public int  	ctxWindowWidth;
		public int 		ctxWindowHeight;
		public boolean  ctxWindowFullscreen;
		
		/**
			Creates class core configuration with default engine and application
			settings and options.
			
			List with all CTX Engine settings a configuration to lunch. Each
			application, or game written on this engine can change this
			settings.
		*/
		public CoreConfigurations()
		{
			this.ctxEngineName = "CTXEngine";
			this.ctxEngineVersion = "0.1.20_2j";
			this.ctxAppName = "unknown";
			this.ctxAppVersion = "unknown";
			this.ctxWindowFullscreen = false;
			this.ctxWindowWidth = this.ctxWindowFullscreen ? 1600 : 1280;
			this.ctxWindowHeight = this.ctxWindowFullscreen ? 900 : 720;
			this.ctxIsWindowed = !(ctxWindowFullscreen);
			this.ctxIsVsync = this.ctxWindowFullscreen;
			this.ctxIsResized = !(ctxWindowFullscreen) & true;
		}
		
		/**
		 * Set configuration data from 'other' to this.
		 */
		public CoreApp set(CoreConfigurations other)
		{
			this.ctxEngineName = other.ctxEngineName;
			this.ctxEngineVersion = other.ctxEngineVersion;
			this.ctxAppName = other.ctxAppName;
			this.ctxAppVersion = other.ctxAppVersion;
			this.ctxWindowFullscreen = other.ctxWindowFullscreen;
			this.ctxWindowWidth = other.ctxWindowWidth;
			this.ctxWindowHeight = other.ctxWindowHeight;
			this.ctxIsWindowed = other.ctxIsWindowed;
			this.ctxIsVsync = other.ctxIsVsync;
			this.ctxIsResized = other.ctxIsResized;
			return get();
		}

		/**
		 * Get's engine name.
		 */
		@Override
		public String getEngineName() { return this.ctxEngineName; }
		
		/**
		 * Get's engine version.
		 */
		@Override
		public String getEngineVersion() { return this.ctxEngineVersion; }
		
		/**
		 * Get's application name.
		 */
		@Override
		public String getAppName() { return this.ctxAppName; }

		/**
		 * Get's application version.
		 */
		@Override
		public String getAppVersion() { return this.ctxAppVersion; }
		
		/**
		 * Get's window width of current window.
		 */
		@Override
		public int getWindowWidth() { return this.ctxWindowWidth; }
		
		/**
		 * Get's window height of current window.
		 */
		@Override
		public int getWindowHeight() { return this.ctxWindowHeight; }
		
		/**
		 * If is fullscreen then return true otherwise false.
		 */
		@Override
		public boolean isFullscreen() { return this.ctxWindowFullscreen; }
		
		/**
		 * If is windowed then return true otherwise false.
		 */
		@Override
		public boolean isWindowed() { return this.ctxIsWindowed; }
		
		/**
		 * If is Vsync mode then return true otherwise false.
		 */
		@Override
		public boolean isVSync() { return this.ctxIsVsync; }
		
		/**
		 * If is resized window then return true otherwise false.
		 */
		@Override
		public boolean isResized() { return this.ctxIsResized; }
	};
	
	/**This is instance of this core application.*/
	private static CoreApp instance;
	/**Reference to CoreConfiguration class.*/
	private CoreConfigurations coreConfig;
	/**Reference to WinWinow class.*/
	protected IWindow window;
	/**Check if this core engine is running.*/
	public boolean isRunning;
	/**Check if core window is has minimum size.*/
	public boolean isMinimized = false;
	/**This variable check if application has updated firstly.*/
	protected boolean firstUpdate = false;
	/**Time from last frame.*/
	protected float lastFrameTime = 0.0f;
	/**Delta or 'deltaTime' means with witch speed this application works.*/
	private Delta dTime;
	
	public CoreApp(CoreConfigurations configurations) throws CoreException
	{
		CoreApp.instance = this;
		this.dTime = new Delta();
		this.coreConfig = configurations;
		
		if(this.coreConfig == null)
		{
			CTX_ENGINE_INFO("Configurations not be found, initialized default config");
			this.coreConfig = new CoreConfigurations();
			
			if (this.coreConfig == null)
			{
				throw new CoreException("Failed to create configuration class!");
			}
		}
	}

	/**
	 * This method will actual run the CTXEngine.
	 * @throws CoreException 
	 */
	@Override
	public void run()
	{
		this.isRunning = true;

		try
		{
			if (this.isRunning)
			{
				this.initCoreEngine();
				this.initClient();
				this.onUpdate();
			}
			
		} catch (CoreException e) {e.printStackTrace(); }
		
		this.shutdown();
	}
	
	/**
	 * This method be initialize all core engine classes.
	 */
	@Override
	public void initCoreEngine() 
	{
		//load the window and create specific render context.
		this.window = WinWindow.create();
		((CTXEngine.Windows.WinWindow)this.window).setWindowCallbacks();
		if (CoreConfigurationsImpl.isLoadRenderEngine())
		{
			{
				//load render engine system platform
				CTXEngine.Graphics.RenderEngine.init();
			}
		}
		
		//load the ImGui (don't load because java doesen't has im gui.)
	}
	
	
	/**
	 * This method will shutdown the CTXEngine. (In Java use instead C++
	 * destructor.)
	 */
	@Override
	public void shutdown() 
	{
		if (!this.window.isCloseRequested())
		{
			//Our window is multi platform so don't need to  check each
			//platform with specific windowing system to close it.
			//Soon this part of code form ~~223 to ~~237 be removed.

			if (CTX_API_CURRENT == CTX_API_GL)
			{
				this.window.setCloseRequested(true);
			}
			else if (CTX_API_CURRENT == CTX_API_VK)
			{
				//this->window->setCloseRequestedVK(true);
			}
			else if (CTX_API_CURRENT == CTX_API_DX3D)
			{
				//this->window->setCloseRequestedDX3D(true);
			}
		}

		this.destroy();
	}
	
	/**
	 * This method will be delete all intitalized game engine stuff.
	 */
	@Override
	public void destroy() 
	{
		try 
		{
			this.window.destroyWindow();
			this.destroyClient();
			
			if (this.isRunning)
			{
				this.isRunning = false;
			}
			
		} catch (CoreException e) { e.printStackTrace(); }
		
	}
	
	/**
	 * This method will be use already intitalized objects and update it.
	 * @throws CoreException 
	 */
	@Override
	public void onUpdate() throws CoreException 
	{	
		while (!this.window.isCloseRequested())
		{
			//temporary: should be in specific platform.
			float currentTime = (float)GLFW.glfwGetTime();
			float dTimef = this.dTime.getDeltaTime();
			dTimef = currentTime - this.lastFrameTime;
			this.dTime.setDetlaTime(dTimef);
			this.lastFrameTime = currentTime;
			
			// update the client side i.e SomeApp.cpp or SomeApp2.cpp
			this.onUpdateClient(this.dTime);
			this.onRenderGlobal();

			this.window.handleInput();
			if (!this.isMinimized)
			{
				if (!this.firstUpdate)
				{
					GLFW.glfwSetWindowSize(this.window.getSystemWindow(), 
							this.coreConfig.ctxWindowWidth + 1, this.coreConfig.ctxWindowHeight + 1);
				}
				this.window.updateWindow();
			}

			if (!this.isRunning)
			{
				this.window.setCloseRequested(true);
				break;
			}

			this.firstUpdate = true;
		}
	}
	
	/**
	 * This method will be render global stuff, like I'm gui
	 */
	@Override
	public void onRenderGlobal() 
	{
		
	}
	
	/**
	 * This method will dispatch the events.
	 */
	@Override
	public void onEvent() 
	{
		this.onEventClient();
	}
	
	/**
	 * Return the static instnace of core application.
	 */
	public static CoreApp get()
	{
		return CoreApp.instance;
	}
	
	/**
	 * Return core configuration class / reference.
	 */
	public CoreConfigurations getConfigurations()
	{
		return this.coreConfig;
	}
	
	/**
	 * Return the window instance / reference.
	 */
	public IWindow getWindow()
	{
		return this.window;
	}
	
	/**
	 * Return the delta or 'deltaTime' variable.
	 */
	public float getDelta()
	{
		return this.dTime.getDeltaTime();
	}
	
	/**
	 * Return the timer / time instance.
	 */
	public float getLastFrameTime()
	{
		return this.lastFrameTime;
	}

};
