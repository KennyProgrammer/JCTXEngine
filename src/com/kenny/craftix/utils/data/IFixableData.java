package com.kenny.craftix.utils.data;

import com.kenny.craftix.client.settings.nbt.NBTTagCompound;

public interface IFixableData
{
    int getFixVersion();

    NBTTagCompound fixTagCompound(NBTTagCompound compound);
}