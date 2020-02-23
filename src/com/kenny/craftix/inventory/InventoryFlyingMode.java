package com.kenny.craftix.inventory;

import org.lwjgl.input.Keyboard;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.entity.player.EntityPlayer;
import com.kenny.craftix.client.gui.GuiRenderManager;
import com.kenny.craftix.client.gui.inventory.GuiFlyingMode;

public class InventoryFlyingMode extends Inventory
{
	public InventoryFlyingMode(Craftix craftixIn) 
	{
		super("InventoryPlayerFlyingMode", 45, craftixIn);
	}

	@Override
	public void connectInventoryWithGui(Craftix craftixIn) 
	{
		super.connectInventoryWithGui(craftixIn);
		this.guiInventory.inventoryFlyingMode = new GuiFlyingMode("InventoryPlayerFlyingMode", 1, this.cx);
	}
	
	@Override
	public void openInventory(EntityPlayer player) 
	{
		super.openInventory(player);
		if(GuiRenderManager.renderInventoryFlyMode)
		{
			this.cx.guiRenderer.render(this.guiInventory.inventoryFlyingMode.inventory);
			this.guiInventory.inventoryFlyingMode.updateButtons();
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_E))
		{
			this.guiInventory.inInventory = true;
			this.guiInventory.renderBackground();
			GuiRenderManager.renderInventoryFlyMode = true;
		}
		
	}
	
	@Override
	public void closeInventory(EntityPlayer player) 
	{
		super.closeInventory(player);
		if(this.guiInventory.inInventory)
		{
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			{
				GuiRenderManager.renderInventoryFlyMode = false;
				this.guiInventory.removeBackground();
				this.guiInventory.inInventory = false;
			}
		}
		
	}
	
	@Override
	public int getSlotsCount()
	{
		return super.getSlotsCount();
	}
	
	
}
