package com.kenny.craftix.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kenny.craftix.utils.UserProfileManager;

public class ResourceLocation 
{
	public static UserProfileManager userProfile = new UserProfileManager();
	
	/***
	 * Location for the resources (assets).
	 */
	public static final ResourceLocation ASSETS_FOLDER = new ResourceLocation("assets");
	public static final ResourceLocation MODEL_FOLDER = new ResourceLocation(ASSETS_FOLDER, "models/");
	public static final ResourceLocation TEXTURE_FOLDER = new ResourceLocation(ASSETS_FOLDER, "textures/");
	public static final ResourceLocation SHADER_FOLDER = new ResourceLocation(ASSETS_FOLDER, "shaders/");
	public static final ResourceLocation FONT_FOLDER = new ResourceLocation(ASSETS_FOLDER, "fonts/");
	public static final ResourceLocation LOGO_FOLDER = new ResourceLocation(TEXTURE_FOLDER, "guis/menu/title/");
	public static final String AUDIO_FOLDER = new String(ASSETS_FOLDER + "audio/");
	public static final ResourceLocation SOUND_FOLDER = new ResourceLocation(ASSETS_FOLDER + "audio/");
	
	public static String CRAFTIX_FOLDER;
	public static String RESOURCEPACKS_FOLDER;
	
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String FILE_SEPARATOR = "/";
	private String path;
	private String name;

	/**
	 * Set the path to appdata user ".craftix" folder.
	 */
	public static void setCraftixProfileFolder()
	{
		CRAFTIX_FOLDER = new String(userProfile.getUserAppdataFolder());
		RESOURCEPACKS_FOLDER = new String(CRAFTIX_FOLDER + "resourcepacks/");	
	}
	
	public String getResourcepacksFolder()
	{
		return RESOURCEPACKS_FOLDER;
	}
	
	/**
	 * Standard location for the files.
	 */
	public ResourceLocation(String path) 
	{
		this.path = FILE_SEPARATOR + path;
		String[] dirs = path.split(FILE_SEPARATOR);
		this.name = dirs[dirs.length - 1];
	}

	/**
	 * Maybe work with multipies string paths locations.
	 */
	public ResourceLocation(String... paths) 
	{
		this.path = "";
		for (String part : paths)
		{
			this.path += (FILE_SEPARATOR + part);
		}
		String[] dirs = path.split(FILE_SEPARATOR);
		this.name = dirs[dirs.length - 1];
	}

	public ResourceLocation(ResourceLocation file, String subFile) 
	{
		this.path = file.path + FILE_SEPARATOR + subFile;
		this.name = subFile;
	}

	public ResourceLocation(ResourceLocation file, String... subFiles) 
	{
		
		this.path = file.path;
		for (String part : subFiles) 
		{
			this.path += (FILE_SEPARATOR + part);
		}
		String[] dirs = path.split(FILE_SEPARATOR);
		this.name = dirs[dirs.length - 1];
	}

	public String getPath() 
	{
		return path;
	}

	@Override
	public String toString() 
	{
		return getPath();
	}

	public InputStream getInputStream() 
	{
		return Class.class.getResourceAsStream(path);
	}

	public BufferedReader getReader() throws Exception 
	{
		try
		{
			InputStreamReader isr = new InputStreamReader(getInputStream());
			BufferedReader reader = new BufferedReader(isr);
			return reader;
		} 
		catch (Exception e) 
		{
			LOGGER.error("Couldn't get reader for " + path);
			throw e;
		}
	}

	public String getName() 
	{
		return name;
	}

}
