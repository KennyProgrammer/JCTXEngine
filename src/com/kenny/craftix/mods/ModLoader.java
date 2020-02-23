package com.kenny.craftix.mods;
/**
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.renderer.GlHelper;

public class ModLoader 
{
	private Loader loader;
	public static String modName;
	private static final Logger MOD_LOGGER = LogManager.getLogger(modName);
	
	public void getModTexturesList()
	{
		this.loader = new Loader();
	}
	
	/**
	public int loadModFile(String file)
	{	
		this.getModTexturesList();
		Texture texture = null;
		try 
		{
			texture = TextureLoader.getTexture("PNG", Class.class.getResourceAsStream
					(ResourceLocation.TEXTURE_FOLDER + file + ".png"));
			GlHelper.glGenerateMipmapping(GL11.GL_TEXTURE_2D);
			GlHelper.glTexParametri(GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GlHelper.glTexParametrf(GL14.GL_TEXTURE_LOD_BIAS, -2.4f);
			if(GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic)
			{
				float amount = Math.min(4f, 
						GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
				GlHelper.glTexParametrf(EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, amount);
			}else
			{
				MOD_LOGGER.info("Anisotropic Filtering not supported!");
			}
		} 
		catch (FileNotFoundException e) 
		{
			MOD_LOGGER.error("File not found in: " + ResourceLocation.TEXTURE_FOLDER + " folder.");
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		int textureID = texture.getTextureID();
		this.loader.textures.add(textureID);
			return textureID;
	}
	
}
**/
