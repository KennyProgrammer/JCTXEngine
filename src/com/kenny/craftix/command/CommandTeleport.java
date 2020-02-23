package com.kenny.craftix.command;

import com.kenny.craftix.client.scenes.WorldScene;

public class CommandTeleport extends Command
{

	public WorldScene world;

	public CommandTeleport(String commandNameIn, int commandIdIn) 
	{
		super(commandNameIn, commandIdIn);
	}

	public void runCommand(WorldScene worldIn, float posX, float posY, float posZ) 
	{
		this.world = worldIn;
		this.world.getPlayer().teleportPlayer(posX, posY, posZ);
	}
	
}
