package com.kenny.craftix.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector4f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.entity.player.EntityPlayer;

public class GuiPlayerStats extends GuiBackground
{
	/**This list for health/hunger guis.*/
	public List<GuiQuad> texturesH = new ArrayList<GuiQuad>();
	/**This list for breath guis.*/
	public List<GuiQuad> texturesB = new ArrayList<GuiQuad>();
	public GuiQuad textureUnderwater;
	
	@Override
	public void initGui(Craftix craftixIn) 
	{
		super.initGui(craftixIn);
		this.texturesH.add(new GuiQuad(GUI_LOCATION + "game/icon_health", -0.9f, -0.8f, 0.05f, 0.10f));
		this.texturesH.add(new GuiQuad(GUI_LOCATION + "game/icon_hunger", -0.9f, -0.62f, 0.045f, 0.08f));
		this.texturesB.add(new GuiQuad(GUI_LOCATION + "game/icon_breath", 0.9f, -0.8f, 0.055f, 0.10f));
		this.texturesH.add(new GuiQuad(GUI_LOCATION + "game/bar_border", -0.65f, -0.825f, 0.2f, 0.1f));
		this.texturesH.add(new GuiQuad(GUI_LOCATION + "game/bar_border", -0.65f, -0.65f, 0.2f, 0.1f));
		this.texturesB.add(new GuiQuad(GUI_LOCATION + "game/bar_border", 0.725f, -0.825f, 0.2f, 0.1f));
		this.texturesH.add(new GuiQuad(GUI_LOCATION + "game/bar_green", -0.65f, -0.825f, 0.2f, 0.1f));
		this.texturesH.add(new GuiQuad(GUI_LOCATION + "game/bar_yellow", -0.65f, -0.65f, 0.2f, 0.1f));
		this.texturesB.add(new GuiQuad(GUI_LOCATION + "game/bar_blue", 0.725f, -0.825f, 0.2f, 0.1f));
		this.textureUnderwater = new GuiQuad(GUI_LOCATION + "game/water/underwater_background", 0F, 0F, this.width, this.height);
		this.textureUnderwater.setColor(new Vector4f(1, 1, 1, 0.5f));
	}
	
	/**
	 * Update the bar from backward to leftward position. (RTL)
	 */
	public void updateAnimationRTL(EntityPlayer player, int index)
	{
		float f = 0;
		if(index == 4)
			f = player.playerHealth;
		else if(index == 5)
			f = player.playerHunger;
		float xPos = -0.65f;
		float xScale = 0.20f;
		float xScaleA = 0.02f;
		float xPosA = xScaleA - 0.002f;
		if(f <= 100 && f >= 91)
		{
			this.texturesH.get(index).quadPos.x = xPos;
			this.texturesH.get(index).quadScale.x = xScale;
		}
		if(f <= 90 && f >= 81)
		{
			this.texturesH.get(index).quadPos.x = xPos - xPosA;
			this.texturesH.get(index).quadScale.x = xScale - xScaleA;
		}
		if(f <= 80 && f >= 71)
		{
			this.texturesH.get(index).quadPos.x = xPos - xPosA - 0.02f + 0.002f;
			this.texturesH.get(index).quadScale.x = xScale - xScaleA - 0.02f;
		}
		if(f <= 70 && f >= 61)
		{
			this.texturesH.get(index).quadPos.x = xPos - xPosA - 0.04f + 0.0041f;
			this.texturesH.get(index).quadScale.x = xScale - xScaleA - 0.04f;
		}
		if(f <= 60 && f >= 51)
		{
			this.texturesH.get(index).quadPos.x = xPos - xPosA - 0.06f + 0.0062f;
			this.texturesH.get(index).quadScale.x = xScale - xScaleA - 0.06f;
		}
		if(f <= 50 && f >= 41)
		{
			this.texturesH.get(index).quadPos.x = xPos - xPosA - 0.08f + 0.0083f;
			this.texturesH.get(index).quadScale.x = xScale - xScaleA - 0.08f;
		}
		if(f <= 40 && f >= 31)
		{
			this.texturesH.get(index).quadPos.x = xPos - xPosA - 0.10f + 0.0104f;
			this.texturesH.get(index).quadScale.x = xScale - xScaleA - 0.10f;
		}
		if(f <= 30 && f >= 21)
		{
			this.texturesH.get(index).quadPos.x = xPos - xPosA - 0.12f + 0.0125f;
			this.texturesH.get(index).quadScale.x = xScale - xScaleA - 0.12f;
		}
		if(f <= 20 && f >= 11)
		{
			this.texturesH.get(index).quadPos.x = xPos - xPosA - 0.14f + 0.0146f;
			this.texturesH.get(index).quadScale.x = xScale - xScaleA - 0.14f;
		}
		if(f <= 10 && f >= 1)
		{
			this.texturesH.get(index).quadPos.x = xPos - xPosA - 0.16f + 0.0167f;
			this.texturesH.get(index).quadScale.x = xScale - xScaleA - 0.16f;
		}
		if(f <= 0)
		{
			this.texturesH.get(index).quadPos.x = xPos - xPosA - 0.18f + 0.0188f;
			this.texturesH.get(index).quadScale.x = xScale - xScaleA - 0.18f;
		}
	}
	
	/**
	 * Update the bar from leftward to backward position. (LTR)
	 */
	public void updateAnimationLTR(EntityPlayer player, int index)
	{
		float f = player.playerBreath;
		float xPos = 0.725f;
		float xScale = 0.20f;
		float xScaleA = 0.02f;
		float xPosA = xScaleA - 0.010f;
		if(f <= 100 && f >= 91)
		{
			this.texturesB.get(index).quadPos.x = 0.725f;
			this.texturesB.get(index).quadScale.x = 0.2f;
		}        
		if(f <= 90 && f >= 81)
		{
			this.texturesB.get(index).quadPos.x = 0.725f + 0.02f - 0.01f;
			this.texturesB.get(index).quadScale.x = 0.20f - 0.02f;
		}
		if(f <= 80 && f >= 71)
		{
			this.texturesB.get(index).quadPos.x = 0.725f + 0.04f - 0.021f;
			this.texturesB.get(index).quadScale.x = 0.20f - 0.04f;
		}
		if(f <= 70 && f >= 61)
		{
			this.texturesB.get(index).quadPos.x = 0.725f + 0.06f - 0.032f;
			this.texturesB.get(index).quadScale.x = 0.20f - 0.06f;
		}
		if(f <= 60 && f >= 51)
		{
			this.texturesB.get(index).quadPos.x = 0.725f + 0.08f - 0.043f;
			this.texturesB.get(index).quadScale.x = 0.20f - 0.08f;
		}
		if(f <= 50 && f >= 41)
		{
			this.texturesB.get(index).quadPos.x = 0.725f + 0.10f - 0.054f;
			this.texturesB.get(index).quadScale.x = 0.20f - 0.10f;
		}
		if(f <= 40 && f >= 31)
		{
			this.texturesB.get(index).quadPos.x = 0.725f + 0.12f - 0.065f;
			this.texturesB.get(index).quadScale.x = 0.20f - 0.12f;
		}
		if(f <= 30 && f >= 21)
		{
			this.texturesB.get(index).quadPos.x = 0.725f + 0.14f - 0.076f;
			this.texturesB.get(index).quadScale.x = 0.20f - 0.14f;
		}
		if(f <= 20 && f >= 11)
		{
			this.texturesB.get(index).quadPos.x = 0.725f + 0.16f - 0.087f;
			this.texturesB.get(index).quadScale.x = 0.20f - 0.16f;
		}
		if(f <= 10 && f >= 1)
		{
			this.texturesB.get(index).quadPos.x = 0.725f + 0.18f - 0.098f;
			this.texturesB.get(index).quadScale.x = 0.20f - 0.18f;
		}
		if(f <= 0)
		{
			this.texturesB.get(index).quadPos.x = 0.725f + 0.20f - 0.100f;
			this.texturesB.get(index).quadScale.x = 0.20f - 0.20f;
		}
	}
}
