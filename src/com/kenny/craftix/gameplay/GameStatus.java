package com.kenny.craftix.gameplay;

/**
 * This class is responsible for the stage of the game at the moment
 * @author Kenny
 */
public class GameStatus 
{
	/**Check if game in pause.*/
	private boolean gamePause;
	/**Check if now loading menu stage.*/
	private boolean gameMenu;
	/**Check if now loading world stage.*/
	private boolean gameWorld;
	private boolean isEditor;
	private boolean isServer;
	
	public boolean isServer()
	{
		return this.isServer;
	}
	
	public boolean isGamePause() 
	{
		return gamePause;
	}
	
	public void setGamePause(boolean gamePause) 
	{
		this.gamePause = gamePause;
	}
	
	public boolean isGameMenu() 
	{
		return gameMenu;
	}
	
	public void setGameMenu(boolean gameMenu) 
	{
		this.gameMenu = gameMenu;
	}
	
	public boolean isGameWorld() 
	{
		return gameWorld;
	}
	
	public void setGameWorld(boolean gameWorld) 
	{
		this.gameWorld = gameWorld;
	}
	
	public void setServer(boolean isServer)
	{
		this.isServer = isServer;
	}

	public boolean isEditor() 
	{
		return isEditor;
	}

	public void setEditor(boolean isEditor)
	{
		this.isEditor = isEditor;
	}
}

