package com.kenny.craftix.gameplay;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.entity.player.EntityPlayer;

public class GameMode 
{
	/**Check if player in survivial mode.*/
	public boolean isGamemodeSurvivial;
	/**Check if player in flying / unlimited resources mode.*/
	public boolean isGamemodeUnlimited;
	public int currentGamemode;
	private EntityPlayer player;
	public static final Logger LOGGER = LogManager.getLogger(Craftix.LOGGER_INSTANCE);
	
	public GameMode(EntityPlayer playerIn, int gameModeIn) 
	{
		this.player = playerIn;
		if(gameModeIn == 0)
		{
			this.enableSurvivialMode();
		}
		if(gameModeIn == 1)
		{
			this.enableInlimitedMode();
		}
		if(gameModeIn > 1 || gameModeIn < 0)
		{
			LOGGER.warn("Gamemode cannot be greater than 2.");
		}
		
		this.currentGamemode = gameModeIn;
	}
	
	/**
	 * Enabled the suravial player mode. Survivial, collect resources, hunger....
	 */
	public void enableSurvivialMode()
	{
		this.player.useUnlimitedMode = false;
		this.player.setHealth(100f);
	}
	
	/**
	 * Enable the flying player mode. Unlimited resources, lives, hunger.... (Swim mode)))))))))))))
	 */
	public void enableInlimitedMode()
	{
		this.player.useUnlimitedMode = true;
		this.player.setHealth(100f);
		if(this.player.healthIn < 100)
		{
			//this.player.healthIn = 100;
		}
	}
}
