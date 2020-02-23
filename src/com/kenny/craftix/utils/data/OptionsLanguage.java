package com.kenny.craftix.utils.data;

import java.util.Locale;

import com.kenny.craftix.client.settings.nbt.NBTTagCompound;

public class OptionsLanguage implements IFixableData
{
	public int getFixVersion()
	{
		return 816;
	}
	
	public NBTTagCompound fixTagCompound(NBTTagCompound nbt)
	{
		if(nbt.hasKey("lang", 8))
		{
			nbt.setString("lang", nbt.getString("lang").toLowerCase(Locale.ROOT));
		}
		
		return nbt;
	}
}
