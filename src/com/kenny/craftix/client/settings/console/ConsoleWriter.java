package com.kenny.craftix.client.settings.console;

import com.kenny.craftix.client.CraftixOld;

/**
 * This class i will be use because im not fully setting the Apache Log4j library.
 * 
 * @author Kenny
 *
 */
public class ConsoleWriter 
{
	public void writeConsolePrefixInfo(String clazz, String message)
	{
		System.out.println("[tt.cc.ms] " + "[" + clazz + "/INFO] " + "[" + CraftixOld.TITLE + "]" +
				": " + message);
	}
	
	public void writeConsolePrefixWarn(String clazz, String message)
	{
		System.out.println("[tt.cc.ms] " + "[" + clazz + "/WARN] " + "[" + CraftixOld.TITLE + "]" +
				": " + message);
	}
	
	public void writeConsolePrefixError(String clazz, String message)
	{
		System.out.println("[tt.cc.ms] " + "[" + clazz + "/ERROR] " + "[" + CraftixOld.TITLE + "]" +
				": " + message);
	}
	
	public void writeConsolePrefixFatal(String clazz, String message)
	{
		System.out.println("[tt.cc.ms] " + "[" + clazz + "/FATAL] " + "[" + CraftixOld.TITLE + "]" +
				": " + message);
	}
	
	public void writeConsolePrefixInfo(String message)
	{
		System.out.println("[tt.cc.ms] " + "[main/INFO] " + "[" + CraftixOld.TITLE + "]" +
				": " + message);
	}
	
	public void writeConsolePrefixWarn(String message)
	{
		System.out.println("[tt.cc.ms] " + "[main/WARN] " + "[" + CraftixOld.TITLE + "]" +
				": " + message);
	}
	
	public void writeConsolePrefixError(String message)
	{
		System.out.println("[tt.cc.ms] " + "[main/ERROR] " + "[" + CraftixOld.TITLE + "]" +
				": " + message);
	}
	
	public void writeConsolePrefixFatal(String message)
	{
		System.out.println("[tt.cc.ms] " + "[main/FATAL] " + "[" + CraftixOld.TITLE + "]" +
				": " + message);
	}
	
	public void printInfo(String message)
	{
		this.writeConsolePrefixInfo(message);
	}
	
}
