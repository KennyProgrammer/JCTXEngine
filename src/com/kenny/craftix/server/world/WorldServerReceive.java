package com.kenny.craftix.server.world;

import java.io.ObjectInputStream;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.server.PacketBuffer;

public class WorldServerReceive extends Thread
{
	public Craftix cx;
	/**The packet buffer merges all packets.*/
	public PacketBuffer packetBuffer;
	/**This is the input stream of the object that receives the data for the server.*/
	public ObjectInputStream in;
	
	public WorldServerReceive(Craftix craftix, ObjectInputStream inIn) 
	{
		this.cx = craftix;
		in = inIn;
		this.packetBuffer = new PacketBuffer(this.cx);
		start();
	}
	
	/**
	 * Run the new recieve thread.
	 */
	public void run()
	{
		while(true)
		{
			this.receiveData(in);
		}	
	}
	
	/**
	 * Recieve all data from packets.
	 */
	public void receiveData(ObjectInputStream in)
	{
		this.packetBuffer.receiveData(in);
	}
	
}
