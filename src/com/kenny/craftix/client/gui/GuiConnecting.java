package com.kenny.craftix.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.Craftix;

public class GuiConnecting extends GuiBackground
{
	public List<GuiQuad> textures = new ArrayList<GuiQuad>();
	public static boolean progress25 = true, progress50 = false, progress75 = false;

	protected static GuiQuad t1, t2, t3, t4;
	
	@Override
	public void initGui(Craftix craftixIn)
	{
		super.initGui(craftixIn);
		
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/in_background", 0f, 0f, this.width * 1.8f, this.height * 1.4f));
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/loading_bar_border", 0.01f, -0.3f, this.width / 2.5f, this.height / 3.15f));
		t4 = new GuiQuad(GUI_LOCATION + "menu/loading_bar", -0.18f, -0.3f, this.width / 5.5f, this.height / 3.15f);
		this.textures.add(t4);
	}
	
}
