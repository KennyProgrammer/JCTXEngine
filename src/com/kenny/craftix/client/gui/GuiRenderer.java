package com.kenny.craftix.client.gui;

import java.io.IOException;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiButton;
import com.kenny.craftix.client.gui.button.GuiOptionButton;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.GlHelper.Blend;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.client.renderer.models.ModelGui;
import com.kenny.craftix.client.renderer.textures.TextureManager;
import com.kenny.craftix.utils.color.Colour;
import com.kenny.craftix.utils.math.Maths;

public class GuiRenderer
{
	/**Its a basic quid*/
	public final Model quad;
	public static final float SCREEN_SCALE_X = 1F;
	public static final float SCREEN_SCALE_Y = 1F;
	/**Get gui shader for renderer class*/;
	public GuiShader shader;
	private ModelGui modelGui;
	private Loader loader = new Loader();
	public Craftix cx;
	private Colour borderColour = new Colour(1.0F, 0.7F, 0.7F, 0.0F);
	
	public GuiRenderer(Craftix craftixIn)
	{        
		this.modelGui = new ModelGui();
		this.quad = this.loader.loadToVao(this.modelGui.getModelQuad(), 2);
		this.shader = new GuiShader();
		this.cx = craftixIn;
	}
	
	/**
	 * Render guis with triangle's strips.
	 */
	public void render(List<GuiQuad> guis)
	{
		this.shader.start();
		GlHelper.glBindVertexArray(this.quad.getVaoID());
		GlHelper.glEnableVertexAttribArray(0);
		for (GuiQuad gui : guis) 
		{
			TextureManager.activeTexture0();
			TextureManager.bindTexture2d(gui.getTextureId());
			GlHelper.enableBlend();
			GlHelper.glBlendFunction(Blend.SRC_ALPHA, Blend.ONE_MINUS_SRC_ALPHA);
			GlHelper.disableDepthTest();
			Matrix4f matrix = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
			this.shader.loadTransformation(matrix);
			this.shader.loadScreenScale(new Vector2f(SCREEN_SCALE_X, SCREEN_SCALE_Y));
			//clamp to edges
			
			GL11.glTexParameteri(3553, 10242, 33071);
		    GL11.glTexParameteri(3553, 10243, 33071);
			
			//clamp to border
			
			GL11.glTexParameteri(3553, 10242, 33069);
		    GL11.glTexParameteri(3553, 10243, 33069);
		    GL11.glTexParameter(3553, 4100, this.borderColour.getAsFloatBuffer());
			
			TextureManager.glDrawTrinangleStrips(0, this.quad.getVertexCount());
		}
		GlHelper.enableDepthTest();
		GlHelper.disableBlend();
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glBindVertexArray(0);
		this.shader.stop();
	}
	
	public void renderButton(List<GuiButton> guisButtons, boolean isUpdate)
	{
		this.shader.start();
		GlHelper.glBindVertexArray(this.quad.getVaoID());
		GlHelper.glEnableVertexAttribArray(0);
		for (GuiButton guiButton : guisButtons) 
		{
			TextureManager.activeTexture0();
			TextureManager.bindTexture2d(guiButton.getTextureId());
			GlHelper.enableBlend();
			GlHelper.glBlendFunction(Blend.SRC_ALPHA, Blend.ONE_MINUS_SRC_ALPHA);
			GlHelper.disableDepthTest();
			Matrix4f matrix = Maths.createTransformationMatrix(guiButton.getPosition(), guiButton.getScale());
			this.shader.loadTransformation(matrix);
			TextureManager.glDrawTrinangleStrips(0, this.quad.getVertexCount());
			
			if(isUpdate && guiButton.enable)
			{
				guiButton.checkHover(this.cx);
				try 
				{
					guiButton.controllMouse();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
			
		}
		GlHelper.enableDepthTest();
		GlHelper.disableBlend();
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glBindVertexArray(0);
		this.shader.stop();
	}
	
	public void renderButton(List<GuiOptionButton> guisButtons, boolean isUpdate, boolean isNull)
	{
		this.shader.start();
		GlHelper.glBindVertexArray(this.quad.getVaoID());
		GlHelper.glEnableVertexAttribArray(0);
		for (GuiOptionButton guiButton : guisButtons) 
		{
			TextureManager.activeTexture0();
			TextureManager.bindTexture2d(guiButton.getTextureId());
			GlHelper.enableBlend();
			GlHelper.glBlendFunction(Blend.SRC_ALPHA, Blend.ONE_MINUS_SRC_ALPHA);
			GlHelper.disableDepthTest();
			Matrix4f matrix = Maths.createTransformationMatrix(guiButton.getPosition(), guiButton.getScale());
			this.shader.loadTransformation(matrix);
			TextureManager.glDrawTrinangleStrips(0, this.quad.getVertexCount());
			
			if(isUpdate && guiButton.enable)
			{
				guiButton.checkHover(this.cx);
				try {
					guiButton.controllMouse();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		GlHelper.enableDepthTest();
		GlHelper.disableBlend();
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glBindVertexArray(0);
		this.shader.stop();
	}
	
	/**
	 * Render guis with triangle's strips.
	 */
	public void render(GuiQuad gui)
	{
		this.shader.start();
		GlHelper.glBindVertexArray(this.quad.getVaoID());
		GlHelper.glEnableVertexAttribArray(0);
		TextureManager.activeTexture0();
		TextureManager.bindTexture2d(gui.getTextureId());
		GlHelper.enableBlend();
		GlHelper.glBlendFunction(Blend.SRC_ALPHA, Blend.ONE_MINUS_SRC_ALPHA);
		GlHelper.disableDepthTest();
		Matrix4f matrix = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
		this.shader.loadTransformation(matrix);
		TextureManager.glDrawTrinangleStrips(0, this.quad.getVertexCount());
		GlHelper.enableDepthTest();
		GlHelper.disableBlend();
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glBindVertexArray(0);
		this.shader.stop();
	}
	
	
	public void cleanUp()
	{
		this.shader.cleanUp();
	}
}
