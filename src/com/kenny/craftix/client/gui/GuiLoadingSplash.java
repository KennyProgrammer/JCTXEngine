package com.kenny.craftix.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.scenes.WorldScene;

public class GuiLoadingSplash extends GuiBackground
{
	public List<GuiQuad> textures = new ArrayList<GuiQuad>();
	public List<GuiQuad> texturesSave = new ArrayList<GuiQuad>();
	public static boolean progress25 = true, progress50 = false, progress75 = false;
	protected static GuiQuad t1, t2, t3, t4, t5;

	@Override
	public void initGui(Craftix craftixIn) 
	{
		if(!Pages.isSaveWorldPage)
		{
			super.initGui(craftixIn);
			
			this.textures.add(new GuiQuad(GUI_LOCATION + "menu/in_background", 0f, 0f, this.width * 1.8f, this.height * 1.4f));
			this.textures.add(new GuiQuad(GUI_LOCATION + "menu/loading_bar_border", 0.01f, -0.3f, this.width / 2.5f, this.height / 3.15f));
			t4 = new GuiQuad(GUI_LOCATION + "menu/loading_bar", -0.18f, -0.3f, this.width / 5.5f, this.height / 3.15f);
			this.textures.add(t4);
		}
		if(Pages.isSaveWorldPage)
		{
			super.initGui(craftixIn);
			this.texturesSave.add(new GuiQuad(GUI_LOCATION + "menu/in_background", 0f, 0f, this.width * 1.8f, this.height * 1.4f));
		}
		
	}
	
	public void updateCurcle(Craftix cx)
	{
		while(!WorldScene.worldInitCompleted)
		{
			cx.updateLoadingSplashDisplay();
			WorldScene.worldInitCompleted = true;
		}
	}
	
	public void setAnimationBar()
	{
		if(progress25)
		{
			t4.setScale(new Vector2f(this.width / 7.5f, this.height / 3.15f));
			t4.setPosition(new Vector2f(-0.22f, -0.3f));
		}
		if(progress50)
		{
			t4.setScale(new Vector2f(this.width / 3.5f, this.height / 3.15f));
			t4.setPosition(new Vector2f(-0.09f, -0.3f));
		}
		if(progress75)
		{
			t4.setScale(new Vector2f(this.width / 2.5f, this.height / 3.15f));
			t4.setPosition(new Vector2f(0.01f, -0.3f));
		}
		
	}
	
}
