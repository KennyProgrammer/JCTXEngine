package com.kenny.craftix.inventory;

public class Slot 
{
	/**This is a id of the slot in the inventory.*/
	private final int slotId;
	/**This is the inventory we want to extract the slot from.*/
	public final IInventory inventory;
	/**This is number a slot (In inventory its a index)*/
	public int slotNumber;
	/**Position slot on the screen on X axis.*/
	public int xPos;
	/**Position slot on the screen on Y axis.*/
	public int yPos;
	
	public Slot(IInventory inventoryIn, int id, int xPosition,
			int yPosition) 
	{
		this.inventory = inventoryIn;
		this.slotId = id;
		this.xPos = xPosition;
		this.yPos = yPosition;
	}
	
	 /**
     * Called when the stack in a Slot changes
     */
    public void onSlotChanged()
    {
        this.inventory.markDirty();
    }
    
    /**
     * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case
     * of armor slots)
     */
    public int getSlotStackLimit()
    {
        return this.inventory.getInventoryStackLimit();
    }

	public int getSlotId() 
	{
		return slotId;
	}
}
