package com.kenny.craftix.utils.data;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kenny.craftix.client.settings.nbt.NBTTagCompound;
import com.kenny.craftix.utils.Util;

public class DataFixer implements IDataFixer
{

	private static final Logger LOGGER = LogManager.getLogger("DataFixer");
	private final Map<IFixType, List<IDataWalker>> walkerMap = Maps.<IFixType, List<IDataWalker>>newHashMap();
	private final Map<IFixType, List<IFixableData>> fixMap = Maps.<IFixType, List<IFixableData>>newHashMap();
	public final int version;
	
	public DataFixer(int versionIn) 
	{
		this.version = versionIn;
	}
	
	public NBTTagCompound process(IFixType type, NBTTagCompound nbt) 
	{
		int i = nbt.hasKey("DataVersion", 99) ? nbt.getInteger("DataVerison") : -1;
			return i >= 0001 ? nbt : this.process(type, nbt, i);
			
	}

	@Override
	public NBTTagCompound process(IFixType type, NBTTagCompound nbt, int versionIn) 
	{
		if(versionIn < this.version)
		{
			nbt = this.processFixes(type, nbt, versionIn);
			nbt = this.processWalkers(type, nbt, versionIn);
		}
		return nbt;
	}
	
	@SuppressWarnings("unchecked")
	private NBTTagCompound processFixes(IFixType type, NBTTagCompound nbt, int versionIn)
    {
        @SuppressWarnings("rawtypes")
		List<IFixableData> list = (List)this.fixMap.get(type);
        if (list != null)
        {
            for (int i = 0; i < list.size(); ++i)
            {
                IFixableData ifixabledata = list.get(i);

                if (ifixabledata.getFixVersion() > versionIn)
                {
                    nbt = ifixabledata.fixTagCompound(nbt);
                }
            }
        }

        return nbt;
    }
	
	@SuppressWarnings("unchecked")
	private NBTTagCompound processWalkers(IFixType type, NBTTagCompound nbt, int versionIn)
	{
		@SuppressWarnings("rawtypes")
		List<IDataWalker> list = (List)this.walkerMap.get(type);
		
		if (list != null)
		{
			for (int i = 0; i < list.size(); ++i)
			{
				nbt =((IDataWalker) list.get(i)).process(this, nbt, versionIn);
			}
		}
		
		return nbt;
	}
	
	public void registerWalker(FixTypes type, IDataWalker walker)
    {
        this.registerVanillaWalker(type, walker);
    }

    /**
     * Do not invoke this method, use registerWalker instead. It is expected to be removed in future versions.
     */
    public void registerVanillaWalker(IFixType type, IDataWalker walker)
    {
        this.getTypeList(this.walkerMap, type).add(walker);
    }

    public void registerFix(IFixType type, IFixableData fixable)
    {
        List<IFixableData> list = this.<IFixableData>getTypeList(this.fixMap, type);
        int i = fixable.getFixVersion();

        if (i > this.version)
        {
            LOGGER.warn("Ignored fix registered for version: {} as the DataVersion of the game is: {}", Integer.valueOf(i), Integer.valueOf(this.version));
        }
        else
        {
            if (!list.isEmpty() && ((IFixableData)Util.getLastElement(list)).getFixVersion() > i)
            {
                for (int j = 0; j < list.size(); ++j)
                {
                    if (((IFixableData)list.get(j)).getFixVersion() > i)
                    {
                        list.add(j, fixable);
                        break;
                    }
                }
            }
            else
            {
                list.add(fixable);
            }
        }
    }

    @SuppressWarnings("rawtypes")
	private <V> List<V> getTypeList(Map<IFixType, List<V>> map, IFixType type)
    {
        @SuppressWarnings("unchecked")
		List<V> list = (List)map.get(type);

        if (list == null)
        {
            list = Lists.<V>newArrayList();
            map.put(type, list);
        }

        return list;
    }
	
}
