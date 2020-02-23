package com.kenny.craftix.utils.data;

import com.kenny.craftix.client.settings.nbt.NBTTagCompound;

public interface IDataWalker
{
    NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn);
}