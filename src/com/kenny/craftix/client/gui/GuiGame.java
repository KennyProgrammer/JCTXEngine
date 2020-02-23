package com.kenny.craftix.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.entity.player.EntityPlayer;
import com.kenny.craftix.client.gui.button.GuiAbstractButton;
import com.kenny.craftix.client.gui.button.IButton;
import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.client.shaders.FrameBuffer;
import com.kenny.craftix.gameplay.GameMode;

/***
 * ITS REALLY OLD CLASS FOR GUIS. FROM VERSION 0.0.6. NOW ITS AVALIBLE BUT IN FUTURE I REMOVE THIS
 * CLASS OR MOVE IN INTO THE GUIS FOLDER AND CHANGE MORE STUFF IN THIS CLASS.
 * 
 * @author Kenny
 */
public class GuiGame extends GuiScreen implements IGui, IGuiUpdateble
{
	protected FrameBuffer waterFramebuffer;
	protected GameMode gamemode;
	
	public List<GuiQuad> guisLoadingScreen = new ArrayList<GuiQuad>();
	public List<GuiQuad> guisGameBackground = new ArrayList<GuiQuad>();
	public List<GuiQuad> guisGameButtons = new ArrayList<GuiQuad>();

	/**Game Stuff*/
	public GuiQuad gui_logo;
	public GuiQuad gui_healthBorder;
	public GuiQuad gui_health_bar;
	public GuiQuad gui_health_bar_back;
	public GuiQuad gui_reflectionScreen;
	public GuiQuad gui_refractionScreen;
	public GuiQuad gui_shadowMap;
	
	/**Init all the gui-buttons texture for main game loop.*/
	public GuiAbstractButton button_first_person;
	public GuiAbstractButton button_thrid_person;
	public GuiAbstractButton button_survivalMode;
	public GuiAbstractButton button_flyingMode;
	
	public WorldScene worldIn;
	public EntityPlayer player;
	
	public void loadGameGuiScreen(Craftix craftixIn)
	{
		this.cx = craftixIn;
		this.drawGuis();
		this.addToList();
		this.drawGuiButtons();
	}
	
	@Override
	public void drawGuis()
	{	
		this.gui_logo = drawGui("guis/game/logo_engine", 0.80f, 0.75f, 0.15f, 0.25f);
		this.gui_healthBorder = drawGui("guis/game/health_empty",  -0.75f, -0.9f, 0.20f, 0.35f);
		this.gui_health_bar_back = drawGui("guis/game/health_bar_back",  -0.71f, -0.9f, 0.204f, 0.35f);
		this.gui_health_bar = drawGui("guis/game/health_bar",  -0.71f, -0.9f, 0.204f, 0.35f);
	}
	
	public void healthBarAnimation()
	{
		this.worldIn = new WorldScene();
		
		//if(this.worldIn.player.playerHealth == 50)
		//{
		//	this.gui_health.setScale(new Vector2f(0.10f, 0.25f));
		//}
	}
	
	@SuppressWarnings("unused")
	@Override
	public void drawGuiButtons()
	{
		/**
		 * This is custom X and Y values for a for multi-size buttons.
		 */
		float x = 0f, y = 0f;
		float posX = 0f, posY = 0f;
		if(InGameSettings.guiScaleSmallIn)
		{
			x = -0.024f; y = 0.03f;
			posX = -0.1f; posY = 0f;
		}
		if(InGameSettings.guiScaleMediumIn)
		{
			x = -0.014f; y = 0.005f;
			posX = -0.05f; posY = 0f;
		}
		if(InGameSettings.guiScaleLargeIn)
		{
			x = 0.006f; y = 0.006f;
			posX = 0f; posY = 0f;
		}
		
		this.button_first_person = new GuiAbstractButton("guis/menu/button_first_person",
					new Vector2f(-0.06f, 0.85f), -0.20f - x, 0.03f - y) 
		{
	
			
			public void onClick(IButton button) 
			{
				
				EntityCamera camera = new EntityCamera(cx.getWorldScene().getPlayer());
				camera.isFirstPersonCamera = true; camera.isThridPersonCamera = false;
			}
			public void isVisible(boolean visibleIn) {}
			
		};
		this.button_first_person.show(guisGameButtons);
		
		this.button_thrid_person = new GuiAbstractButton("guis/menu/button_thrid_person",
				new Vector2f(0.06f, 0.85f), -0.20f - x, 0.03f - y) 
		{

			
			public void onClick(IButton button) 
			{
			
				EntityCamera camera = new EntityCamera(cx.getWorldScene().getPlayer());
				camera.isFirstPersonCamera = false; camera.isThridPersonCamera = true;
			}
			public void isVisible(boolean visibleIn) {}
		
		};
		this.button_thrid_person.show(guisGameButtons);
		
		this.button_survivalMode = new GuiAbstractButton("guis/menu/button_survivial_mode",
				new Vector2f(0.18f, 0.85f), -0.20f - x, 0.03f - y) 
		{

			public void onClick(IButton button) 
			{
				gamemode = new GameMode(cx.getWorldScene().getPlayer(), 0);
			}
			public void isVisible(boolean visibleIn) {}
		
		};
		this.button_survivalMode.show(guisGameButtons);
		
		this.button_flyingMode = new GuiAbstractButton("guis/menu/button_flying_mode",
				new Vector2f(0.30f, 0.85f), -0.20f - x, 0.03f - y) 
		{

			public void onClick(IButton button) 
			{
				gamemode = new GameMode(cx.getWorldScene().getPlayer(), 1);
			}
			public void isVisible(boolean visibleIn) {}
		
		};
		this.button_flyingMode.show(guisGameButtons);
		
	}
		

	
	/**
	 * Add to array list all the guis when be created
	 */
	@Override
	public void addToList()
	{
		this.guisGameBackground.add(gui_logo);
		//this.guisGameBackground.add(gui_healthBorder);
		//this.guisGameBackground.add(gui_health_bar_back);
		//this.guisGameBackground.add(gui_health_bar);
		
	}

	@Override
	public void updateButtons() 
	{
		this.button_first_person.update();
		this.button_thrid_person.update();
		this.button_survivalMode.update();
		this.button_flyingMode.update();
	}

	@Override
	public void onUpdate() 
	{	
		this.healthBarAnimation();
	}
}
