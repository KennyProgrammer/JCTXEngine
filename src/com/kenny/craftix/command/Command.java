package com.kenny.craftix.command;

import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.scenes.WorldScene;

public class Command 
{
	/**This is id of the command.*/
	private int commandId;
	/**This is name of the current command.*/
	private String commandName;
	public WorldScene world;
	public CommandKill commandKill;
	public CommandTeleport commandTp;
	public CommandRespawn commandRespawn;
	public CommandTimeSet commandTimeSet;
	public CommandGamemode commandChangeGameMode;
	public CommandSpawnEntity commandSpawnEntity;
	public List<Command> commandRegisterList = new ArrayList<Command>();
	
	public Command(String commandNameIn, int commandIdIn)
	{
		this.commandName = commandNameIn;
		this.commandId = commandIdIn;
	}
	
	/**
	 * Simply instance of the class.
	 */
	public Command() 
	{
	}

	/**
	 * Output a message when using the command.
	 */
	public String sendMessageOnUse(String message)
	{
		String allTextCommand;
		allTextCommand = this.commandName + "[" + message + "]";
			return allTextCommand;
	}
	
	/**
	 * Here will be performed all that is needed for a particular command.
	 */
	public void runCommand(WorldScene worldIn)
	{
		this.world = worldIn;
	}
	
	/**
	 * Registy all the added commands.
	 */
	public void registerCommand()
	{
		this.commandKill = new CommandKill("Kill", 1);
		this.commandTp = new CommandTeleport("Teleport", 2);
		this.commandRespawn = new CommandRespawn("Respawn", 3);
		this.commandTimeSet = new CommandTimeSet("TimeSet", 4);
		this.commandChangeGameMode = new CommandGamemode("GameMode", 5);
		this.commandSpawnEntity = new CommandSpawnEntity("SpawnEntity", 6);
		
	}
	
	
	/**
	 * Get the name of the current command.
	 */
	public String getCommandName()
	{
		return this.commandName;
	}
	
	/**
	 * Get the ind of the current command.
	 */
	public int getCommandId()
	{
		return this.commandId;
	}
	
	
}
