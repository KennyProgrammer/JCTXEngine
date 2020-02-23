package com.kenny.craftix.command;

import com.kenny.craftix.client.scenes.WorldScene;

public class CommandRespawn extends Command
{

	public WorldScene world;
	public CommandTeleport commandTp = new CommandTeleport(this.getCommandName(), this.getCommandId());

	public CommandRespawn(String commandNameIn, int commandIdIn) 
	{
		super(commandNameIn, commandIdIn);
	}
	
	public void runCommand(WorldScene worldIn, float resPosX, float resPosY, float resPosZ) 
	{
		this.world = worldIn;
		this.commandTp.runCommand(worldIn, resPosX, resPosY, resPosZ);
		this.world.getPlayer().setHealth(100f);
		this.world.getPlayer().setPlayerAlive(true);
		this.world.getPlayer().flag2 = false;
	}

}
