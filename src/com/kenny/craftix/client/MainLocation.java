package com.kenny.craftix.client;

public class MainLocation
{
	/**
	 * This is OLD Verison of the Resource Location path loader. This class never be use in future, and
	 * maybe be deleted.
	 */
	public static final String MAIN_LOCATION = 
			"/com/eclipseX/craftix/";
	
	public static final String RESOURCE_LOCATION = 
		"res/assets/";
	
	public static final String SHADERS_LOCATION = 
			"res/assets/shaders/";
	
	public static final String RESOURCE_LOCATION_S = 
			"/assets/";
	
	public static final String RESOURCE_LOCATION_I = 
			"res/assets/";
	
	
	public static final String LIBRARY_LOCATION =
			"";
	
	public static String ResourceLocation(String file)
	{
		return RESOURCE_LOCATION + file + ".png";
	}
	
	public static String ResourceLocationFont(String file)
	{
		return RESOURCE_LOCATION + "fonts/" + file + ".fnt";
	}
			
}
