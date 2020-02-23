package com.kenny.craftix.client.gui.inventory;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.GuiQuad;
import com.kenny.craftix.client.gui.IGui;

import java.util.ArrayList;
import java.util.List;

public class GuiInventoryBar extends GuiInventory implements IGui
{	
	public GuiInventoryBar(String guiName, int guiId, Craftix craftixIn) 
	{
		super(guiName, guiId, craftixIn);
		this.cx = craftixIn;
		this.drawGuis();
		this.addToList();
		this.drawGuiButtons();
	}

	public List<GuiQuad> inventoryBar = new ArrayList<GuiQuad>();
	
	public GuiQuad gui_inventoryBar_bar;
	
	public void loadInventoryBarScreen(Craftix craftixIn)
	{
	}
	
	@Override
	public void drawGuis() 
	{
		this.gui_inventoryBar_bar = drawGui(GUI_LOCATION + "player/inventory_player_bar", 0.2f, -0.9f, 0.65f, 0.30f);
	}

	@Override
	public void drawGuiButtons() 
	{

	}

	@Override
	public void addToList() 
	{
		this.inventoryBar.add(gui_inventoryBar_bar);
	}

	@Override
	public void updateButtons() 
	{

	}

}
