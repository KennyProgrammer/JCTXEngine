package Craftix;

import Craftix.CrafixApp;

import CTXEngine.Core.CoreApp;
import CTXEngine.Core.CoreException;

public class Main
{
	public static void main(String[] args) throws CoreException 
	{
		CTXEngine.Main.main0(new CrafixApp(new CoreApp.CoreConfigurations()));
	}
}
