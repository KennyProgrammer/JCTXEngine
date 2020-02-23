package com.kenny.craftix.utils.data;

import com.kenny.craftix.client.settings.nbt.NBTTagCompound;

public interface IDataFixer
{
    NBTTagCompound process(IFixType type, NBTTagCompound compound, int versionIn);
}