package com.kenny.craftix.client;

public class ClientGetterInformation
{
	private final String clientName;
	private final String clientNameDemo;
	private final String clientVersion;
	private final String clientVersionType;
	
	public ClientGetterInformation(String clientNameIn, String clientNameDemo, String clientVersionIn, String clientVerisonTypeIn) 
	{
		this.clientName = clientNameIn;
		this.clientNameDemo = clientNameDemo;
		this.clientVersion = clientVersionIn;
		this.clientVersionType = clientVerisonTypeIn;
	}
	
	public String getClientName()
	{
		return this.clientName;
	}
	
	public String getClientNameDemo()
	{
		return this.clientNameDemo;
	}
	
	public String getClientVersion()
	{
		return this.clientVersion;
	}
	
	public String getClientVersionType()
	{
		return this.clientVersionType;
	}
}
