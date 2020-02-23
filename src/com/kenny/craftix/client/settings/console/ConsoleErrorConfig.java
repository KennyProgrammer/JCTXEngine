package com.kenny.craftix.client.settings.console;

public class ConsoleErrorConfig 
{
	public static boolean errorShaderNoFoundUniform;
	
	public void errorManager(boolean activeManager)
	{
		if(activeManager)
		{
			this.errorShaders();
		}
	}
	
	public void errorShaders()
	{
		errorShaderNoFoundUniform = false;
	}
}
