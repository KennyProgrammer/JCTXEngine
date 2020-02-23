package com.kenny.craftix.server.world;

import java.util.Random;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.main.Side;
import com.kenny.craftix.main.SideMachine;
import com.kenny.craftix.server.CraftixMP;

@SideMachine(Side.SERVER)
public class WorldServer 
{
	private WorldScene worldClient;
	public CraftixMP cxMp;
	public Craftix cx;
	//private int worldSeed;
	public Random random = new Random();
	
	/**
	 * Run the server world.
	 */
	public void runServerWorld(Craftix craftixIn)
	{
		this.cx = craftixIn;
		this.cxMp = craftixIn.getServer();
		//this.worldSeed = this.random.nextInt(1000000000);
		
	}
	
	public WorldScene getWorldClient()
	{
		return this.worldClient;
	}
	
	//public int getSeed()
	//{
		//return worldSeed;
	//}
}
