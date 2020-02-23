package com.kenny.craftix.server.world;

import java.io.ObjectOutputStream;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.server.PacketBuffer;

public class WorldServerSend extends Thread
{
	public Craftix cx;
	/**The packet buffer merges all packets.*/
	public PacketBuffer packetBuffer;
	/**This is the input stream of the object that sends the data for the server.*/
	public ObjectOutputStream out;
	
	public WorldServerSend(Craftix craftix, ObjectOutputStream outIn) 
	{
		this.cx = craftix;
		out = outIn;
		this.packetBuffer = new PacketBuffer(this.cx);
		start();
	}
	
	/**
	 * Run the new send thread.
	 */
	public void run()
	{
		while(true)
		{
			this.sendData(out);	
		}	
	}
	
	/**
	 * Send all data from packets.
	 */
	public void sendData(ObjectOutputStream out)
	{
		this.packetBuffer.sendData(out);
	}
}
