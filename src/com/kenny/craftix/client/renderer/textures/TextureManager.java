package com.kenny.craftix.client.renderer.textures;

import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.GlHelper.Texture;

public class TextureManager 
{
	/**
	 * Bind the current texture with operation GL_TEXTURE_2D.
	 * @param texture - current texture.
	 */
	public static void bindTexture2d(int texture)
	{
		GlHelper.glBindTexture(Texture.TEXTURE_2D, texture);
	}
	
	/**
	 * Bind the current cube map texture. Uses for skyboxes.
	 * @param texture - current cube map texture.
	 */
	public static void bindTextureCubeMap(int texture)
	{
		GlHelper.glBindTexture(Texture.TEXTURE_CUBE_MAP, texture);
	}
	
	/**
	 * Draws a triangle from point to point and so on.
	 */
	public static void glDrawTrinangles(int first, int count)
	{
		GlHelper.glDrawArrays(Texture.TRIANGLES, first, count);
	}
	
	/**
	 * Draws a triangle directly. The remaining triangles will be automatically drawn.
	 */
	public static void glDrawTrinangleStrips(int first, int count)
	{
		GlHelper.glDrawArrays(Texture.TRIANGLE_STRIP, first, count);
	}
	
	public static void activeTexture0()
	{GlHelper.glActiveTexture(Texture.TEXTURE0);}
	
	public static void activeTexture1()
	{GlHelper.glActiveTexture(Texture.TEXTURE1);}
	
	public static void activeTexture2()
	{GlHelper.glActiveTexture(Texture.TEXTURE2);}
	
	public static void activeTexture3()
	{GlHelper.glActiveTexture(Texture.TEXTURE3);}
	
	public static void activeTexture4()
	{GlHelper.glActiveTexture(Texture.TEXTURE4);}
	
	public static void activeTexture5()
	{GlHelper.glActiveTexture(Texture.TEXTURE5);}
	
	public static void activeTexture6()
	{GlHelper.glActiveTexture(Texture.TEXTURE6);}

			
}
