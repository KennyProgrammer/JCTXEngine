package com.kenny.craftix.server;

public class ServerGetterInformation 
{
	private final String serverVersion;
	private final String serverVersionType;
	
	public ServerGetterInformation(String serverVersionIn, String serverVersionTypeIn) 
	{
		this.serverVersion = serverVersionIn;
		this.serverVersionType = serverVersionTypeIn;
	}
	
	public String getServerVersion()
	{
	 	return this.serverVersion;
	}
	
	public String getServerVerisonType() 
	{
		return this.serverVersionType;
	}
}
