package com.kenny.craftix.client.scenes;

public interface IReloadble extends IScene
{
	/**
	 * This method is required to reload the scene, or return to another scene. This 
	 * method is implemented in the transition from the world to the main menu.
	 */
	public void reloadScene();
}
