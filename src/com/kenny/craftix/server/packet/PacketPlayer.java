package com.kenny.craftix.server.packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.main.Side;
import com.kenny.craftix.main.SideMachine;
import com.kenny.craftix.server.IPacket;

@SideMachine(Side.SERVER)
public class PacketPlayer implements IPacket
{
	protected WorldScene world;
	public static ObjectInputStream in;
	public static ObjectOutputStream o;
	
	/**
     * Reads the raw packet data from the data stream.
     */
	@Override
	public void readPacketData(WorldScene worldIn, ObjectInputStream inIn) 
	{
		this.world = worldIn;
		in = inIn;
		try 
		{
			this.world.getPlayer().healthIn = Float.parseFloat((String)in.readObject());
		} 
		catch (NumberFormatException | ClassNotFoundException | IOException e) 
		{
			e.printStackTrace();
		}
	}

	/**
     * Writes the raw packet data to the data stream.
     */
	@Override
	public void writePacketData(WorldScene worldIn, ObjectOutputStream out) 
	{
		this.world = worldIn;
		o = out;
		try 
		{
			o.writeObject("" + this.world.getPlayer().healthIn);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void runPacket() 
	{
	}

}
