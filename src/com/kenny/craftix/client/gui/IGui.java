package com.kenny.craftix.client.gui;

public interface IGui 
{
	/**Loads the added guis into the game.*/
	public void drawGuis();
	/**Loads the added guis-buttons textures into the game.*/
	public void drawGuiButtons();
	/**Adds the generated guis to the list in order that the engine had seen them and 
	 * was reflected in the game.*/
	public void addToList();
	/**Update the button gui each frame per tick.*/
	public void updateButtons();
}
