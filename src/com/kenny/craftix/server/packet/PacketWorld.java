package com.kenny.craftix.server.packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.main.Side;
import com.kenny.craftix.main.SideMachine;
import com.kenny.craftix.server.IPacket;

@SideMachine(Side.SERVER)
public class PacketWorld implements IPacket
{
	protected WorldScene worldScene;
	public static ObjectInputStream in;
	public static ObjectOutputStream o;
	
	/**
     * Reads the raw packet data from the data stream.
     */
	@Override
	public void readPacketData(WorldScene worldIn, ObjectInputStream inIn) 
	{
		this.worldScene = worldIn;
		in = inIn;
		
		int generationSeed = this.worldScene.worldSeed;
		try
		{
			generationSeed = Integer.parseInt((String)in.readObject());
			//System.out.println("Reading!!!");
		} 
		catch (NumberFormatException| ClassNotFoundException | IOException e) 
		{
			e.printStackTrace();
		}
	}

	/**
     * Writes the raw packet data to the data stream.
     */
	@Override
	public void writePacketData(WorldScene worldIn, ObjectOutputStream outIn) 
	{
		this.worldScene = worldIn;
		o = outIn;
		try 
		{
			o.writeObject("" + this.worldScene.worldSeed);
			o.flush();
			//System.out.println("Writing!!!");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void runPacket() {}

}
