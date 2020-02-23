package com.kenny.craftix.client.scenes;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.GuiBackground;
import com.kenny.craftix.client.gui.GuiQuad;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.GlHelper.Depth;
import com.kenny.craftix.client.renderer.GlHelper.Texture;

public class LoadingScreen extends GuiBackground
{
	private static final Logger LOGGER = LogManager.getLogger(Craftix.LOGGER_INSTANCE);
	public List<GuiQuad> loadingBackground = new ArrayList<GuiQuad>();
	public Craftix cx;
	public boolean isUpdate;
	public boolean isRender = true;
	
	public void startLoading()
	{
		GlHelper.glClear(Texture.COLOR_BUFFER_BIT | Depth.DEPTH_BUFFER_BIT);
		this.loadingBackground.add(new GuiQuad(GUI_LOCATION + "loading/loading_image", 0f, 0f, 1f, 1f));
		this.loadingBackground.add(new GuiQuad(GUI_LOCATION + "loading/logo_hd", 0f, 0.25f, 0.15f, 0.25f));
		this.loadingBackground.add(new GuiQuad(GUI_LOCATION + "loading/game_language", -0.75f, -0.85f, 0.25f, 0.10f));
		this.loadingBackground.add(new GuiQuad(GUI_LOCATION + "loading/game_developer", 0.75f, -0.85f, 0.25f, 0.10f));
		this.isUpdate = true;
	}
	
	public void endLoading()
	{
		LOGGER.info("Game Engine main components succes load!");
	}
	
	public void updateLoadingScreen(Craftix craftixIn) throws LWJGLException
	{
		this.cx = craftixIn;
		this.startLoading();
		while(!Display.isCloseRequested() && isUpdate)
		{
			this.cx.updateFramebuffer();
			this.cx.guiRenderer.render(loadingBackground);
			this.updateDisplay();
			this.isUpdate = false;
		}
		
	}
	
	public void removeAfterUpdate()
	{
		this.isUpdate = true;
		
		if(!Display.isCloseRequested() && isUpdate)
		{
			this.loadingBackground.clear();
			this.updateDisplay();
			this.isUpdate = false;
		}
		this.endLoading();
	}
	
	/**
	 * Standard update display method.
	 */
	public void updateDisplay()
	{
		Display.update();
		Display.sync(30);
	}

}
