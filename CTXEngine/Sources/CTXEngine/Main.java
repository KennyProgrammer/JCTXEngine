package CTXEngine;

import static CTXEngine.Core.SimplePrint.*;

import CTXEngine.Core.CoreApp;
import CTXEngine.Core.CoreApp.CoreConfigurations;
import CTXEngine.Core.CoreBase;
import CTXEngine.Core.CoreException;

/**
 * This is main file to launch CTXEngine Core and then you're
 * Game Application. This red (other colour) error don't need to
 * put your's mind, because all this stuff fixed on compile and
 * link stage, in SomeApp.cpp after line 2-3.
 */
public final class Main
{
	public static int 
		CTX_PLATFORM_WINDOWS = 1,
		CTX_PLATFORM_LINUX   = 0,
		CTX_PLATFORM_MACOS   = 0,
		CTX_PLATFORM_ANDROID = 0,
		CTX_PLATFORM_IOS     = 0;
	
	private static boolean isRunning = false;
	private static boolean mainIsCalled = false;
	
	/**
	 * Native calling main function from CTXEngine to create a client.
	 * Basically is will be extern of run core and return new application
	 * with will be set to this reference.
	 */
	public static final int main0(CoreApp application) throws CoreException 
	{
		if(!Main.mainIsCalled)
		{
			if(CTX_PLATFORM_WINDOWS == 1)
			{
				//make CTXEngine flag 'isRunning' on true.
				CTX_ENGINE_INFO("Startup Craftix Engine...");
				Thread.currentThread().setName("CTXEngine");
				
				if(Thread.currentThread().getName() != "CTXEngine")
				{
					throw new CoreException("Thread has not currect name!");
				}
				CoreBase.init();
				isRunning = true;
				//make *App flag 'isRunning' on true.
				if(isRunning)
				{
					if (application == null)
					{
						CTX_ENGINE_ERROR("Shutdown Craftix Engine...");
						throw new CoreException("Engine runs without any application client. Abort...");
					}
					application.run();
					
					if (!application.isRunning)
					{
						application.shutdown();
					}
					else
					{
						application.isRunning = false;
						application.shutdown();
					}
					
					CTX_ENGINE_INFO("Shutdown Craftix Engine...");
				}
				else
				{
					throw new CoreException("Can't start application, because engine not started!");
				}
				
				Main.mainIsCalled = true;
			}
			else throw new CoreException("You're already create a instance of engine.");
		}
		else if(CTX_PLATFORM_ANDROID == 1)
		{
			
		}
		else if(CTX_PLATFORM_IOS == 1)
		{
			
		}
		else if(CTX_PLATFORM_LINUX == 1)
		{
			
		}
		else if(CTX_PLATFORM_MACOS == 1)
		{
			
		}
		
		return 0;
	}
	
	/**
	 * Native calling main function from CTXEngine to create a client and
	 * set configuration.
	 */
	public static final int main0(CoreApp application, CoreConfigurations configurations) throws CoreException 
	{
		return main0(application.getConfigurations().set(configurations));
	}
}
//Thanks you ThinMatrix)))) For everything!

