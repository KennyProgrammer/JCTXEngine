package com.kenny.craftix.command;

import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.gameplay.GameMode;

public class CommandGamemode extends Command
{
	protected WorldScene world;
	/**Init the gamemode class.*/
	protected GameMode gamemode;
	
	/**
	 * Change the gamemode for the player.
	 */
	public CommandGamemode(String commandNameIn, int commandIdIn) 
	{
		super(commandNameIn, commandIdIn);
	}
	
	public void runCommand(WorldScene worldIn, int gameModeIn) 
	{
		super.runCommand(worldIn);
		this.world = worldIn;
		this.gamemode = new GameMode(this.world.getPlayer(), gameModeIn);
	}


}
