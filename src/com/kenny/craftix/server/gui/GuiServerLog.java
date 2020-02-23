package com.kenny.craftix.server.gui;

import com.kenny.craftix.main.Side;
import com.kenny.craftix.main.SideMachine;
import static com.kenny.craftix.client.text.TextLanguage.*;

import com.kenny.craftix.client.gui.GuiBackground.Pages;

@SideMachine(Side.SERVER)
public class GuiServerLog 
{
	public static boolean checkIpAddress;
	public static boolean clientJoinServer;
	public static boolean clientJoined;
	public static boolean clientUnknownIpAddress;
	public static boolean serverWaitClient;
	public static boolean serverClientConnect;
	public static boolean serverUnknownIpAdreess;
	
	public void updateServerLog()
	{
		this.isCheckIpAddress();
		this.isServerWaitClient();
		this.isClientJoinToServer();
		this.isClientUnknownIpAddress();
		this.isServerUnknownIpAddress();
	}
	
	/**
	 * All this methods called in update loop.
	 */
	
	public void isCheckIpAddress()
	{
		if(checkIpAddress)
		{
			t115.r(true);
		} else
		{
			t115.r(false);
		}
	}
	
	public void isClientJoinToServer()
	{
		if(clientJoinServer)
		{
			t117.r(true);
		} else
		{
			t117.r(false);
		}
	}
	
	public void isServerWaitClient()
	{
			if(serverWaitClient)
			{
				t116.r(true);
			}
		 else
			t116.r(false);
	}
	
	public void isClientUnknownIpAddress()
	{
		if(Pages.isDisconnectedPage)
		{
			if(clientUnknownIpAddress)
				t114.r(true);
		} else
			t114.r(false);
	}
	
	public void isServerUnknownIpAddress()
	{
		if(Pages.isDisconnectedPage)
		{
			if(serverUnknownIpAdreess)
				t113.r(true);
		} else
			t113.r(false);
	}
}
