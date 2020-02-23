package com.kenny.craftix.client.gui.inventory;

import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.GuiQuad;
import com.kenny.craftix.client.gui.IGui;

public class GuiFlyingMode extends GuiInventory implements IGui
{
	public List<GuiQuad> inventory = new ArrayList<GuiQuad>();
	
	public GuiQuad gui_inventory_player;
	
	public GuiFlyingMode(String guiName, int guiId, Craftix craftixIn) 
	{
		super(guiName, guiId, craftixIn);
		this.cx = craftixIn;
		this.drawGuis();
		this.addToList();
		this.drawGuiButtons();
	}
	
	public void loadFlyingModeScreen(Craftix craftixIn) 
	{
	}

	@Override
	public void drawGuis() 
	{
		this.gui_inventory_player = drawGui(GUI_LOCATION + "inventory_flyMode", 0.2f, -0.4f, 0.65f, 1.2f);
	}

	@Override
	public void drawGuiButtons() 
	{

	}

	@Override
	public void addToList() 
	{
		this.inventory.add(gui_inventory_player);
	}

	@Override
	public void updateButtons() 
	{
	}
}
