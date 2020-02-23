package com.kenny.craftix.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.kenny.craftix.client.scenes.WorldScene;

public interface IPacket 
{
	/**
	 * Run the packet on network.
	 */
	public void runPacket();
	
	/**
     * Reads the raw packet data from the data stream.
     */
	public void readPacketData(WorldScene worldIn, ObjectInputStream in);
	
	/**
     * Writes the raw packet data to the data stream.
     */
	public void writePacketData(WorldScene worldIn, ObjectOutputStream o);
}
