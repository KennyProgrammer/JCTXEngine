package com.kenny.craftix.utils.data;

import com.kenny.craftix.client.settings.nbt.NBTTagCompound;

public class ForceVboOn implements IFixableData
{
	public int getFixVersion()
	{
		return 505;
	}
	
	public NBTTagCompound fixTagCompound(NBTTagCompound nbt)
	{
		nbt.setString("useVbo", "true");
		return nbt;
	}
}
