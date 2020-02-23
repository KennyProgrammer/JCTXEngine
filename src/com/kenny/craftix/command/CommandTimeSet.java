package com.kenny.craftix.command;

import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.scenes.WorldScene;

public class CommandTimeSet extends Command
{

	public WorldScene world;

	public CommandTimeSet(String commandNameIn, int commandIdIn) 
	{
		super(commandNameIn, commandIdIn);
	}
	
	public void runCommand(WorldScene worldIn, float timeIn) 
	{
		this.world = worldIn;
		this.world.increaseWorldTime(timeIn);
		this.world.getSunLightSource().setPosition(new Vector3f(0f, 0f, 1f));
	}
	
	public void runCommand(WorldScene worldIn, int time) 
	{
		this.world = worldIn;
		if(time == 0)
		{
			this.world.increaseWorldTime(0000f);
		}
		if(time == 1)
		{
			this.world.increaseWorldTime(4000f);
		}
		if(time == 2)
		{
			this.world.increaseWorldTime(8000f);
		}
		if(time == 3)
		{
			this.world.increaseWorldTime(16000f);
		}
		if(time == 4)
		{
			this.world.increaseWorldTime(20000f);
		}
	}
	
}
