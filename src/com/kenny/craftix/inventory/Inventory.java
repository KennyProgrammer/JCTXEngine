package com.kenny.craftix.inventory;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.entity.player.EntityPlayer;
import com.kenny.craftix.client.gui.inventory.GuiInventory;

public class Inventory implements IInventory
{
	/**This is title on top of the gui-inventory screen.*/
	private String title;
	/**The count of the current inventory.*/
	private final int slotsCount;
	public GuiInventory guiInventory;
	protected InventoryFlyingMode inventoryFlyingMode;
	protected InventoryPlayer inventoryPlayer;
	/**Main instance of the Cratix Engine.*/
	public Craftix cx;
	
	public Inventory(String titleIn, int slotsCountIn, Craftix craftixIn) 
	{
		this.cx = craftixIn;
		this.title = titleIn;
		this.slotsCount = slotsCountIn;
	}

	/***
	 * Initialization all inventories.
	 */
	private void initInventory()
	{
		this.inventoryPlayer = new InventoryPlayer(this.cx);
		this.inventoryFlyingMode = new InventoryFlyingMode(this.cx);
	}
	
	/**
	 * Here be registered all the inventory in the engine.
	 */
	public void inventoryRegistry(GuiInventory guiInventoryIn, Craftix craftixIn)
	{
		this.initInventory();
		this.inventoryPlayer.connectInventoryWithGui(craftixIn);
		this.inventoryFlyingMode.connectInventoryWithGui(craftixIn);
	}
	
	public void connectInventoryWithGui(Craftix craftixIn)
	{
		this.cx = craftixIn;
		this.guiInventory = cx.getWorldScene().guiInventory;
	}
	
	/**
	 * Get the title of the inventory.
	 */
	public String getTitle() 
	{
		return title;
	}
	
	/**
	 * Get the title of the inventory.
	 */
	public int getSizeInventory()
	{
		return slotsCount;
	}

	public int getSlotsCount() 
	{
		return slotsCount;
	}

	@Override
	public boolean isEmpty() 
	{
		return false;
	}


	@Override
	public int getInventoryStackLimit() 
	{
		return 48;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
	}

	@Override
	public void closeInventory(EntityPlayer player) 
	{
	}

	@Override
	public void markDirty() 
	{
	}

	@Override
	public int getField(int id) 
	{
		return 0;
	}

	@Override
	public void setField(int id, int value) 
	{
	}

	@Override
	public int getFieldCount() 
	{
		return 0;
	}

	@Override
	public void clear() 
	{
	}	
}
