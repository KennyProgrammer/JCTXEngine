package com.kenny.craftix.server.socket;

import org.lwjgl.opengl.Display;

import com.kenny.craftix.main.Side;
import com.kenny.craftix.main.SideMachine;

@SideMachine(Side.SERVER)
public class SocketUpdateDisplay 
{
	private int serverFps;
	
	public void updateDipslay()
	{
		this.setServerFps(120);
		
		while(!Display.isCloseRequested())
		{
			Display.sync(this.getSeverFps());
			Display.update();
		}
	}
	
	public void updateDisplayOnce()
	{
		Display.update();
	}
	
	public void setServerFps(int fps)
	{
		this.serverFps = fps;
	}
	
	public int getSeverFps()
	{
		return this.serverFps;
	}
}
