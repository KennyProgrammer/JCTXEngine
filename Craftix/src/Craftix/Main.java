package Craftix;

import Craftix.CrafixApp;
import CTXEngine.CTXEngine;
import CTXEngine.Core.CoreApp;
import CTXEngine.Core.CoreException;

public class Main
{
	public static void main(String[] args) throws CoreException 
	{
		//make CTXEngine flag 'isRunning' on true.
		CTXEngine.engineStart(); 
		//make *App flag 'isRunning' on true.
		CTXEngine.engineAppStart(new CrafixApp(new CoreApp.CoreConfigurations()));
	}
	
}
