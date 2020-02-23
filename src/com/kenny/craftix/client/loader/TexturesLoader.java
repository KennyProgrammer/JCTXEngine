package com.kenny.craftix.client.loader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.opengl.TextureLoader;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.loader.PngDecoder.Format;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.GlHelper.Texture;
import com.kenny.craftix.client.renderer.textures.TextureData;
import com.kenny.craftix.client.renderer.textures.TextureManager;

public class TexturesLoader
{
	protected static final Logger LOGGER = LogManager.getLogger(Craftix.LOGGER_INSTANCE);
	/**If texture file will not be found then game load this texture.*/
	private static final String NO_FOUND_TEXTURE_FILE = "no_texture";
	/**List of textures id's.*/
	public List<Integer> textures = new ArrayList<Integer>();
	
	/**
	 * Load texture in Vertex Array Object (Vao) to model meshes.
	 */
	public int loadTexture(String file)
	{	
		org.newdawn.slick.opengl.Texture texture = null;
		try 
		{
			texture = TextureLoader.getTexture("PNG", Class.class.getResourceAsStream
					(ResourceLocation.TEXTURE_FOLDER + file + ".png"));
			GlHelper.glGenerateMipmapping();
			GlHelper.glTexParametri(Texture.TEXTURE_MIN_FILTER, Texture.LINEAR_MIPMAP_LINEAR);
			GlHelper.glTexParametrf(Texture.TEXTURE_LOD_BIAS, -0.4f);
			if(GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic)
			{
				float amount = Math.min(4f, 
						GlHelper.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
				GlHelper.glTexParametrf(EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, amount);
			}
			else
				LOGGER.info("Anisotropic Filtering not supported!");
			
		} 
		catch (Exception notFoundFile) 
		{
			LOGGER.error("Texture " + file + " not found in: " + ResourceLocation.TEXTURE_FOLDER + "folder.");
			try 
			{
				texture = TextureLoader.getTexture("PNG", Class.class.getResourceAsStream
						(ResourceLocation.TEXTURE_FOLDER + NO_FOUND_TEXTURE_FILE + ".png"));
				GlHelper.glGenerateMipmapping();
				GlHelper.glTexParametri(Texture.TEXTURE_MIN_FILTER, Texture.LINEAR_MIPMAP_LINEAR);
				GlHelper.glTexParametrf(Texture.TEXTURE_LOD_BIAS, -0.4f);
				if(GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic)
				{
					float amount = Math.min(4f, 
							GlHelper.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
					GlHelper.glTexParametrf(EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, amount);
				}
				else
					LOGGER.info("Anisotropic Filtering not supported!");
				
			} catch (Exception e2) {}
		}
		
		int textureID = texture.getTextureID();
		this.textures.add(textureID);
			return textureID;
	}
	
	/**
	 * Remove the texture id from the list of textures.
	 */
	public void removeTexture(int textureId)
	{
		this.textures.remove(textureId);
	}
	
	/**
	 * Load the font texture file the same how it load for normal texture loader.
	 *
	 * @param file - loacation of font atlas file.
	 */
	public int loadFontAtlas(String file)
	{	
		org.newdawn.slick.opengl.Texture texture = null;
		try 
		{
			texture = TextureLoader.getTexture("PNG", Class.class.getResourceAsStream
					(ResourceLocation.FONT_FOLDER + file + ".png"));
			GlHelper.glGenerateMipmapping();
			GlHelper.glTexParametri(Texture.TEXTURE_MIN_FILTER, Texture.LINEAR_MIPMAP_LINEAR);
			GlHelper.glTexParametrf(Texture.TEXTURE_LOD_BIAS, 0f);
		} 
		catch (FileNotFoundException e) 
		{
			LOGGER.error("Text Atlas not found in: " + ResourceLocation.TEXTURE_FOLDER + " folder.");
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		int textureID = texture.getTextureID();
		textures.add(textureID);
			return textureID;
	}
	
	/**
	 * Load a 3d cube when be located all textures for skybox map.
	 */
	public int loadCubeMap(String[] textureFiles) 
	{
		int texID = GlHelper.glGenTextures();
		TextureManager.activeTexture0();
		TextureManager.bindTextureCubeMap(texID);
		
		for (int i = 0; i < textureFiles.length; i++) 
		{
			TextureData data = decodeTextureFile(textureFiles[i]);
			GlHelper.glTexImage2D(Texture.TEXTURE_CUBE_MAP_POSI_X + i, 0, Texture.RGBA, data.getWidth(), data.getWidth(), 
					0, Texture.RGBA, Texture.UNSIGNED_BYTE, data.getBuffer());
		}
		GlHelper.glTexParametri(Texture.TEXTURE_CUBE_MAP, Texture.TEXTURE_MAG_FILTER, Texture.LINEAR);
		GlHelper.glTexParametri(Texture.TEXTURE_CUBE_MAP, Texture.TEXTURE_MIN_FILTER, Texture.LINEAR);
		GlHelper.glTexParametri(Texture.TEXTURE_CUBE_MAP, Texture.TEXTURE_WRAP_S, Texture.CLAMP_TO_EDGE);
		GlHelper.glTexParametri(Texture.TEXTURE_CUBE_MAP, Texture.TEXTURE_WRAP_T, Texture.CLAMP_TO_EDGE);
		
		this.textures.add(texID);
			return texID;
	}
	
	public int loadCubeMapNew(String[] textureFiles) 
	{
		int texID = GlHelper.glGenTextures();
		TextureManager.activeTexture0();
		TextureManager.bindTextureCubeMap(texID);
		
		for (int i = 0; i < textureFiles.length; i++) 
		{
			TextureData data = decodeTextureFileNew(ResourceLocation.RESOURCEPACKS_FOLDER + 
					textureFiles[i] + ".png");
			GlHelper.glTexImage2D(Texture.TEXTURE_CUBE_MAP_POSI_X + i, 0, Texture.RGBA, data.getWidth(), data.getWidth(), 
					0, Texture.RGBA, Texture.UNSIGNED_BYTE, data.getBuffer());
		}
		GlHelper.glTexParametri(Texture.TEXTURE_CUBE_MAP, Texture.TEXTURE_MAG_FILTER, Texture.LINEAR);
		GlHelper.glTexParametri(Texture.TEXTURE_CUBE_MAP, Texture.TEXTURE_MIN_FILTER, Texture.LINEAR);
		GlHelper.glTexParametri(Texture.TEXTURE_CUBE_MAP, Texture.TEXTURE_WRAP_S, Texture.CLAMP_TO_EDGE);
		GlHelper.glTexParametri(Texture.TEXTURE_CUBE_MAP, Texture.TEXTURE_WRAP_T, Texture.CLAMP_TO_EDGE);
		
		this.textures.add(texID);
			return texID;
	}
	
	/**
	 * Decode texture file to Open Gl coordinate system, and load them how .png file format.
	 *
	 * @param file - location of the file.
	 */
	private TextureData decodeTextureFile(String file) 
	{
		int width = 0;
		int height = 0;
		ByteBuffer buffer = null;
		try 
		{
			InputStream in = Class.class.getResourceAsStream
					(ResourceLocation.TEXTURE_FOLDER + file + ".png");
			PngDecoder decoder = new PngDecoder(in);
			width = decoder.getWidth();
			height = decoder.getHeight();
			buffer = ByteBuffer.allocateDirect(4 * width * height);
			decoder.decode(buffer, width * 4, Format.RGBA);
			buffer.flip();
			in.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			LOGGER.info("Tried to load the texture: " + file + " but it didn't work!");
			System.exit(-1);
		}
		return new TextureData(buffer, width, height);
	}
	
	private TextureData decodeTextureFileNew(String file) 
	{
		int width = 0;
		int height = 0;
		ByteBuffer buffer = null;
		try 
		{
			FileInputStream in = new FileInputStream(file);
			PngDecoder decoder = new PngDecoder(in);
			width = decoder.getWidth();
			height = decoder.getHeight();
			buffer = ByteBuffer.allocateDirect(4 * width * height);
			decoder.decode(buffer, width * 4, Format.RGBA);
			buffer.flip();
			in.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			LOGGER.info("Tried to load the texture: " + file + " but it didn't work!");
			System.exit(-1);
		}
		return new TextureData(buffer, width, height);
	}
	
	/**
	 * Delete texture Open Gl data from system memory and clear the cache.
	 */
	public void cleanUpTextureOpenGlObjects()
	{
		for (int texture : textures) 
		{
			GlHelper.glDeleteTextures(texture);
		}
	}
}