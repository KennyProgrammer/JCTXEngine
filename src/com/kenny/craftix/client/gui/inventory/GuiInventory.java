package com.kenny.craftix.client.gui.inventory;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.GuiRenderManager;
import com.kenny.craftix.client.gui.GuiScreen;

public class GuiInventory extends GuiScreen
{
	/**Location of Guis-Inventory textures.*/
	public static final String GUI_LOCATION = "guis/game/inventory/";
	public boolean inInventory;
	/**Name of the current gui inventory screen.*/
	private String guiName;
	/**Id / number of the current gui inventory screen.*/
	private int guiId;
	
	public GuiInventoryBar inventoryBar;
	public GuiInventoryBackground inventoryBackground;
	public GuiFlyingMode inventoryFlyingMode;
	
	public GuiInventory(String guiName, int guiId, Craftix craftixIn)
	{
		this.cx = craftixIn;
		this.guiName = guiName;
		this.guiId = guiId;
	}
	/**
	 * This type instance for guiRegistry method.
	 */
	public GuiInventory() {}
	
	public void guiInventoryRegistry()
	{
		this.inventoryBackground = new GuiInventoryBackground("InventoryBackground", 0, cx);
	}
	
	/**
	 * Render inventory background texture.
	 */
	public void renderBackground()
	{
		GuiRenderManager.renderInventoryBack = true;
	}
	
	/**
	 * Remove the rendered inventory background texture form screen.
	 */
	public void removeBackground() 
	{
		GuiRenderManager.renderInventoryBack = false;
	}
	
	public String getGuiName()
	{
		return this.guiName;
	}
	
	public int getGuiId()
	{
		return this.guiId;
	}
	
	public boolean isClosed()
	{
		return this.inInventory = false;
	}
	
	public boolean isOpened()
	{
		return this.inInventory = true;
	}

}
