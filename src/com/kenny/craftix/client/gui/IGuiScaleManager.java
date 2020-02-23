package com.kenny.craftix.client.gui;

/**
 * Gui Scale Manager responsible for managing the size of the Gui.
 * 
 * @author Kenny
 */
public interface IGuiScaleManager 
{
	/**Loads a stage or stages of "Gui" size.*/
	public void loadGuiScale();
	/**Initializes automatically the size of the "Gui" text, and "Gui" buttons with textures.*/
	public void initGuiScaleAuto();
	/**Initializes big size "Gui" text "Gui" and button textures.*/
	public void initGuiScaleLarge();
	/**Initializes medium size "Gui" text "Gui" and button textures.*/
	public void initGuiScaleMedium();
	/**Initializes small size "Gui" text "Gui" and button textures.*/
	public void initGuiScaleSmall();
}
