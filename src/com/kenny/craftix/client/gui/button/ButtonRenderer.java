package com.kenny.craftix.client.gui.button;

import java.io.IOException;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;

import com.kenny.craftix.client.gui.GuiRenderer;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.GlHelper.Blend;
import com.kenny.craftix.client.renderer.textures.TextureManager;
import com.kenny.craftix.utils.math.Maths;

public class ButtonRenderer 
{
	public Loader loader = new Loader();
	public GuiRenderer buttonRenderer;
	
	public void renderButton(List<GuiButton> buttons, boolean isUpdate)
	{
			//this.buttonRenderer = new GuiRenderer(this.loader);
			this.buttonRenderer.shader.start();
			GlHelper.glBindVertexArray(this.buttonRenderer.quad.getVaoID());
			GlHelper.glEnableVertexAttribArray(0);
			for(GuiButton button : buttons)
			{
				TextureManager.activeTexture0();
				TextureManager.bindTexture2d(button.getTextureId());
				GlHelper.enableBlend();
				GlHelper.tryBlendFuncSeperate(Blend.SRC_ALPHA, Blend.ONE_MINUS_SRC_ALPHA, Blend.ONE, Blend.ZERO);
				GlHelper.glBlendFunction(Blend.SRC_ALPHA, Blend.ONE_MINUS_SRC_ALPHA);
				GlHelper.disableDepthTest();
				Matrix4f matrix = Maths.createTransformationMatrix(button.getPosition(), button.getScale());
				this.buttonRenderer.shader.loadTransformation(matrix);
				TextureManager.glDrawTrinangleStrips(0, this.buttonRenderer.quad.getVertexCount());
			
			GlHelper.enableDepthTest();
			GlHelper.disableBlend();
			GlHelper.glDisableVertexAttribArray(0);
			GlHelper.glBindVertexArray(0);
			this.buttonRenderer.shader.stop();
			
			if(isUpdate && button.enable)
			{
				
				try {
					button.controllMouse();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
}
