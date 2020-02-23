package com.kenny.craftix.inventory;

import com.kenny.craftix.client.entity.player.EntityPlayer;

public interface IInventory 
{
	/**
	 * Check if this inventory is empty.
	 */
	boolean isEmpty();
	
	/**
	 * Return the number of slot in the current inventory.
	 */
	int getSizeInventory();
	
	/**
	 * Return the maximum number of the items/entitys in the current slot.
	 */
	int getInventoryStackLimit();
	
	/**
	 * Simple event "openInventory".
	 */
	void openInventory(EntityPlayer player);

	/**
	 * Simple event "closeInventory".
	 */
	void closeInventory(EntityPlayer player);
	
	void markDirty();
	
	int getField(int id);

    void setField(int id, int value);

    int getFieldCount();

    /**
     * Clear the inventory.
     */
    void clear();
}

