package CTXEngine;

import static CTXEngine.Core.SimplePrint.*;

import CTXEngine.Core.CoreApp;
import CTXEngine.Core.CoreApp.CoreConfigurations;
import CTXEngine.Core.CoreBase;
import CTXEngine.Core.CoreException;

public class CTXEngine
{	
	public static final class Main
	{
		private static boolean mainIsCalled = false;
		
		/**
		 * Native calling main function from CTXEngine to create a client.
		 * Basically is will be extern of run core and return new application
		 * with will be set to this reference.
		 */
		public static void main0(CoreApp application) throws CoreException 
		{
			if(!Main.mainIsCalled)
			{
				//make CTXEngine flag 'isRunning' on true.
				CTXEngine.engineStart(); 
				//make *App flag 'isRunning' on true.
				CTXEngine.engineAppStart(application);
				
				Main.mainIsCalled = true;
			}
			else throw new CoreException("You're already create a instance of engine.");
		}
		
		/**
		 * Native calling main function from CTXEngine to create a client and
		 * set configuration.
		 */
		public static void main0(CoreApp application, CoreConfigurations configurations) throws CoreException 
		{
			main0(application.getConfigurations().set(configurations));
		}
	}
	
	private static boolean isRunning = false;
	
	/**
	 * Start CTXEngine and set current thread.
	 */
	private static final void engineStart() throws CoreException
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
	private static final void engineAppStart(CoreApp application) throws CoreException
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
