package com.kenny.craftix.client.settings.nbt;

import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class NBTUtil
{
    public static final Logger LOGGER = LogManager.getLogger();


    public static boolean areNBTEquals(NBTBase nbt1, NBTBase nbt2, boolean compareTagList)
    {
        if (nbt1 == nbt2)
        {
            return true;
        }
        else if (nbt1 == null)
        {
            return true;
        }
        else if (nbt2 == null)
        {
            return false;
        }
        else if (!nbt1.getClass().equals(nbt2.getClass()))
        {
            return false;
        }
        else if (nbt1 instanceof NBTTagCompound)
        {
            NBTTagCompound nbttagcompound = (NBTTagCompound)nbt1;
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbt2;

            for (String s : nbttagcompound.getKeySet())
            {
                NBTBase nbtbase1 = nbttagcompound.getTag(s);

                if (!areNBTEquals(nbtbase1, nbttagcompound1.getTag(s), compareTagList))
                {
                    return false;
                }
            }

            return true;
        }
        else if (nbt1 instanceof NBTTagList && compareTagList)
        {
            NBTTagList nbttaglist = (NBTTagList)nbt1;
            NBTTagList nbttaglist1 = (NBTTagList)nbt2;

            if (nbttaglist.hasNoTags())
            {
                return nbttaglist1.hasNoTags();
            }
            else
            {
                for (int i = 0; i < nbttaglist.tagCount(); ++i)
                {
                    NBTBase nbtbase = nbttaglist.get(i);
                    boolean flag = false;

                    for (int j = 0; j < nbttaglist1.tagCount(); ++j)
                    {
                        if (areNBTEquals(nbtbase, nbttaglist1.get(j), compareTagList))
                        {
                            flag = true;
                            break;
                        }
                    }

                    if (!flag)
                    {
                        return false;
                    }
                }

                return true;
            }
        }
        else
        {
            return nbt1.equals(nbt2);
        }
    }

    /**
     * Creates a new NBTTagCompound which stores a UUID.
     */
    public static NBTTagCompound createUUIDTag(UUID uuid)
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setLong("M", uuid.getMostSignificantBits());
        nbttagcompound.setLong("L", uuid.getLeastSignificantBits());
        return nbttagcompound;
    }

    /**
     * Reads a UUID from the passed NBTTagCompound.
     */
    public static UUID getUUIDFromTag(NBTTagCompound tag)
    {
        return new UUID(tag.getLong("M"), tag.getLong("L"));
    }


   
}