package com.kenny.craftix.world;

import java.util.Random;

import com.kenny.craftix.client.scenes.WorldScene;

public class CreatingWorld 
{
	/**This is temp world name, before this world will be created.*/
	public String tempWorldName;
	/**Temp user input seed, which will generate a map.*/
	public int tempSeed;
	/**Temp user choose gamemode for the player.*/
	public int tempGamemode;
	/**Temp user choose world type, which will be generated.*/
	public int tempWorldType;
	/**Return the string to gamemode's insted int value.*/
	public String stringModes;
	/**Return the string to world type's insted int value.*/
	public String stringTypes;
	public Random rand = new Random();
	public InputingText inputText = new InputingText();

	public CreatingWorld() 
	{
		this.resetWorldSettings();
		this.parseIntToStringTags();
	}
	
	/**
	 * Reloads the parameters of the created world when pressed back on the button.
	 */
	public void resetWorldSettings()
	{
		if(!WorldScene.isLoadWorld)
		{
			this.setTempWorldName("New World_" + this.rand.nextInt(10));
			this.setTempGamemode(0);
			this.setTempSeed(this.rand.nextInt(1000000));
			this.setTempWorldType(1);
		}
	}
	
	/**
	 * Converts the int variables to string.
	 */
	public void parseIntToStringTags()
	{
		String s1, s2;
		s1 = "Survavial";
		s2 = "Inlimited";
		if(this.tempGamemode == 0)
		{
			this.stringModes = s1;
		}
		else if(this.tempGamemode == 1)
		{
			this.stringModes = s2;
		}
		else
			this.stringModes = "unknown";
		
		String s3, s4, s5;
		s3 = "Hills";
		s5 = "Plains";
		s4 = "Flat";
		if(this.tempWorldType == 0)
		{
			this.stringTypes = s3;
		}
		else if(this.tempWorldType == 1)
		{
			this.stringTypes = s5;
		}
		else if(this.tempWorldType == 2)
		{
			this.stringTypes = s4;
		}
		else
			this.stringTypes = "unknown";
	}
	
	
	public String getTempWorldName()
	{
		return this.tempWorldName;
	}
	
	public void setTempWorldName(String name)
	{
		this.tempWorldName = name;
	}
	
	public int getTempSeed() 
	{
		return tempSeed;
	}

	public void setTempSeed(int tempSeed) 
	{
		this.tempSeed = tempSeed;
	}

	public int getTempGamemode() 
	{
		return tempGamemode;
	}

	public void setTempGamemode(int tempGamemode) 
	{
		this.tempGamemode = tempGamemode;
	}

	public int getTempWorldType() 
	{
		return tempWorldType;
	}

	public void setTempWorldType(int tempWorldType) 
	{
		this.tempWorldType = tempWorldType;
	}
}
