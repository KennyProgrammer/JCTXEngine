package com.kenny.craftix.client.gui.inventory;

import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.GuiQuad;
import com.kenny.craftix.client.gui.IGui;

public class GuiInventoryBackground extends GuiInventory implements IGui
{
	public List<GuiQuad> inventoryBack = new ArrayList<GuiQuad>();
	
	public GuiQuad gui_inventoryBack_background;
	
	public GuiInventoryBackground(String guiName, int guiId, Craftix craftixIn) 
	{
		super(guiName, guiId, craftixIn);
		this.cx = craftixIn;
		this.drawGuis();
		this.addToList();
		this.drawGuiButtons();
		
	}
	
	public void loadInventoryBackround(Craftix craftixIn)
	{
		
	}

	@Override
	public void drawGuis() 
	{
		this.gui_inventoryBack_background = drawGui(GUI_LOCATION + "inventory_background",  0.25f, -0.25f, 1.40f, 1.40f);
	}

	@Override
	public void drawGuiButtons() 
	{

	}

	@Override
	public void addToList() 
	{
		this.inventoryBack.add(gui_inventoryBack_background);
	}

	@Override
	public void updateButtons()
	{

	}
	
	
}
