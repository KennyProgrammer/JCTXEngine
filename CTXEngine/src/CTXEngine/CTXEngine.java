package CTXEngine;

import static CTXEngine.Core.SimplePrint.*;

import CTXEngine.Core.CoreApp;
import CTXEngine.Core.CoreBase;
import CTXEngine.Core.CoreException;

public class CTXEngine
{	
	private static boolean isRunning = false;
	
	/**
	 * Start CTXEngine and set current thread.
	 */
	public static void engineStart() throws CoreException
	{
		CTX_ENGINE_INFO("Startup Craftix Engine...");
		Thread.currentThread().setName("CTXEngine");
		
		if(Thread.currentThread().getName() != "CTXEngine")
		{
			throw new CoreException("Thread has not currect name!");
		}
		CoreBase.init();
		CTXEngine.isRunning = true;
	}
	
	/**
	 * Start, connect client application to CTXEngine.
	 */
	public static void engineAppStart(CoreApp application) throws CoreException
	{
		if(CTXEngine.isRunning)
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
	}
	
	//Thanks you ThinMatrix)))) For everything!
}
