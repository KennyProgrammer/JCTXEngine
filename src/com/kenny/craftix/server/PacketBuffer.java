package com.kenny.craftix.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.server.packet.PacketPlayer;
import com.kenny.craftix.server.packet.PacketWorld;

public class PacketBuffer 
{
	public PacketPlayer packetPlayer;
	public PacketWorld packetWorld;
	public Craftix cx;
	
	public PacketBuffer(Craftix craftixIn) 
	{
		this.cx = craftixIn;
		this.packetWorld = new PacketWorld();
		this.packetPlayer = new PacketPlayer();
	}
	
	public void preGenerationReceiveData()
	{
		this.packetWorld.readPacketData(this.cx.getWorldScene(), CraftixMP.in);
	}
	
	public void preGenerationSendData()
	{
		this.packetWorld.writePacketData(this.cx.getWorldScene(), CraftixMP.out);
	}
	
	public void receiveData(ObjectInputStream in)
	{
		this.packetWorld.readPacketData(this.cx.getWorldScene(), in);
		this.packetPlayer.readPacketData(this.cx.getWorldScene(), in);
	}
	
	public void sendData(ObjectOutputStream out)
	{
		this.packetWorld.writePacketData(this.cx.getWorldScene(), out);
		this.packetPlayer.writePacketData(this.cx.getWorldScene(), out);
	}
}
