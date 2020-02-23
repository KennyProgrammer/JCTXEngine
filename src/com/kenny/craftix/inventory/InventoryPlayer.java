package com.kenny.craftix.inventory;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.inventory.GuiInventoryBar;

public class InventoryPlayer extends Inventory
{
	public InventoryPlayer(Craftix craftixIn) 
	{
		super("InventoryPlayer", 27, craftixIn);
	}
	
	@Override
	public void connectInventoryWithGui(Craftix craftixIn) 
	{
		super.connectInventoryWithGui(craftixIn);
		this.guiInventory.inventoryBar = new GuiInventoryBar("InventoryPlayerBar", 2, craftixIn);
	}
	
	@Override
	public int getSlotsCount() 
	{
		return super.getSlotsCount();
	}

}
