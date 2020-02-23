package com.kenny.craftix.main;

import java.io.File;
import java.net.Proxy;

import com.kenny.craftix.client.settings.Session;

/**
 * It must be remembered that all the engine data is overwhelming to the 
 * repository, they do not set variables or values.
 */
public class GameConfiguration 
{
	public final GameConfiguration.FolderInformation folderInfo;
	public final GameConfiguration.UserInformation userInfo;
	public final GameConfiguration.GameEngineInformation engineInfo;
	public final GameConfiguration.DisplayInformation displayInfo;
	
	/**
	 * Connect all informations class for sets its data to main class how
	 * defaults parametrs.
	 */
	public GameConfiguration
			(GameConfiguration.FolderInformation folderInfoIn,
				GameConfiguration.UserInformation userInfoIn,
					GameConfiguration.GameEngineInformation engineInfoIn,
						GameConfiguration.DisplayInformation displayInfoIn)
	{
		this.folderInfo = folderInfoIn;
		this.userInfo = userInfoIn;
		this.engineInfo = engineInfoIn;
		this.displayInfo = displayInfoIn;
	}
	
	/**
	 * Information about the main engine folders where all files
	 *  are stored.
	 * @author Kenny
	 */
	public static class FolderInformation
	{
		public final File cxDataDir;
		public final File resourcepackDir;
		public final File assetsDir;
		
		public FolderInformation(File cxDataDirIn, File resourcepackDirIn, File assetsDirIn)
		{
			this.cxDataDir = cxDataDirIn;
			this.resourcepackDir = resourcepackDirIn;
			this.assetsDir = assetsDirIn;
		}
	}
	
	/**
	 * Information about the user and his game session.
	 * @author Kenny
	 */
	public static class UserInformation
	{
		public final Session session;
		public final Proxy proxy;
		
		public UserInformation(Session sessionIn, Proxy proxyIn)
		{
			this.session = sessionIn;
			this.proxy = proxyIn;
		}
	}
	
	/***
	 * Main Information about the game engine stuff. Like name, version...
	 * @author Kenny
	 */
	public static class GameEngineInformation
	{
		public final boolean isDemo;
		public final String engineVersion;
		public final String typeOfVersion;
		
		public GameEngineInformation(boolean demo, String engineVersionIn, String versionTypeIn)
        {
            this.isDemo = demo;
            this.engineVersion = engineVersionIn;
            this.typeOfVersion = versionTypeIn;
        }
	}
	
	/***
	 * Information about display when LWJGL connect to engine and create a display.
	 * @author Kenny
	 */
	public static class DisplayInformation
	{
		public final boolean fullscreen;
		public final int displayWidth;
		public final int displayHeight;
		
		public DisplayInformation(boolean fullscreenIn, int displayWidthIn, int displayHeightIn)
		{
			this.fullscreen = fullscreenIn;
			this.displayWidth = displayWidthIn;
			this.displayHeight = displayHeightIn;
		}
	}
}
