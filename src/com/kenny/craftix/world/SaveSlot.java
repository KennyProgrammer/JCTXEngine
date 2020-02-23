package com.kenny.craftix.world;

public class SaveSlot 
{
	/**Id of the slot.*/
	private int id;
	/**Check if slot be actually created, to put in then world save data with gui.*/
	public boolean isCreated;
	/**Check if slot empty or not.*/
	public boolean isEmpty;

	public SaveSlot(int id) 
	{
		this.id = id;
		this.isCreated = true;
		this.isEmpty = true;
	}
	
	/**
	 * Set the boolean to false if slot is not empty.
	 */
	public void setIsntEmpty()
	{
		this.isEmpty = false;
	}
	
	/**
	 * Return the id of current slot.
	 */
	public int getId()
	{
		return this.id;
	}
	
	/**
	 * Set the id for current slot.
	 */
	public void setId(int idIn)
	{
		this.id = idIn;
	}
}
