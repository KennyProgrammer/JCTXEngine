package com.kenny.craftix.client.entity.player;

public class PlayerSkin 
{
	/**
	 * Skin of the player.
	 */
	public String skin;
	
	public PlayerSkin(String skinIn) 
	{
		this.skin = skinIn;
	}
	
	public String getSkin()
	{
		return this.skin;
	}
	
	public void setSkin(String skinIn)
	{
		this.skin = skinIn;
	}
}
