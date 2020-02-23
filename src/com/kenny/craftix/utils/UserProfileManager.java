package com.kenny.craftix.utils;

import java.io.File;

import com.google.common.base.Splitter;
import com.kenny.craftix.client.Craftix;


public class UserProfileManager 
{
	public static final Splitter COLON_SPLITTER = Splitter.on('=');
	public String userProfile;
	public static String appdataFolder;
	protected Craftix cx;
	
	private String hardDriveCFolder = "C:\\";
	private String hardDriveDFolder = "D:\\";
	private String appdata;
	public static File dataFile;
	
	public UserProfileManager() 
	{
	}
	
	
	public String getUserAppdataFolder()
	{
		this.setUserFolder("Kenny");
		this.appdata = this.getCFolder() + "\\Users\\" + 
				this.userProfile + "\\AppData\\Roaming\\.craftix\\";
		this.appdata = this.getCFolder() + "\\.craftix\\";
		return this.appdata;
	}
	
	public void setUserFolder(String userProfileIn)
	{ 
		this.userProfile = userProfileIn;
	}
	
	private String getCFolder()
	{
		return hardDriveCFolder;
	}
	
	public String getDFolder()
	{
		return hardDriveDFolder;
	}
}
