package com.kenny.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Splitter;
import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.settings.nbt.NBTTagCompound;
import com.kenny.craftix.utils.data.FixTypes;

public class UsernameManager 
{
	public static final Splitter COLON_SPLITTER = Splitter.on(':');
	public static final Logger LOGGER = LogManager.getLogger();
	private File userdataFile;
	private Craftix cx;
	
	public UsernameManager(Craftix cxIn, File userdataFileIn) 
	{
		this.cx = cxIn;
		this.userdataFile = new File(userdataFileIn, "userdata.json");
		this.loadUsername(cxIn);
		this.saveUsername(cxIn);
	}
	
	@SuppressWarnings("deprecation")
	public void saveUsername(Craftix craftix)
	{
		PrintWriter printWriter = null;
		try
		{
			printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.userdataFile), StandardCharsets.UTF_8));
			printWriter.println("Username:" + craftix.getSession().getUsername());
			printWriter.println("PlayerId:" + craftix.getSession().getPlayerId());
			printWriter.println("SessionId:" + craftix.getSession().getSessionId());
	
		}
		catch(Exception e)
		{
			LOGGER.error("Failed to save userdata!", (Throwable)e);
			e.printStackTrace();
		}
		finally
		{
			IOUtils.closeQuietly((Writer)printWriter);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void loadUsername(Craftix craftix)
	{
		try
		{
			List<String> list = IOUtils.readLines(new FileInputStream(this.userdataFile));
			NBTTagCompound nbt = new NBTTagCompound();
			
			for(String s: list)
			{
				try
				{
					Iterator<String> iterator = COLON_SPLITTER.omitEmptyStrings().limit(2).split(s).iterator();
					nbt.setString(iterator.next(), iterator.next());
				}
				catch(Exception e)
				{
					LOGGER.warn("Skipping bad userdata: {}", (Object)s);
				}
			}
			
			nbt = this.dataFix(nbt);
			
			for(String s1 : nbt.getKeySet())
			{
				String s2 = nbt.getString(s1);
				
				if("Username".equals(s1))
				{
					craftix.getSession().username = s2;
				}
				
				if("PlayerId".equals(s1))
				{
					String s = craftix.getSession().getPlayerId();
					s = s2;
				}
				
				if("SessionId".equals(s1))
				{
					String s = craftix.getSession().getSessionId();
					s = s2;
				}
			}
		}
		catch (Exception e) 
		{
			LOGGER.error("Failed to load userdata", (Throwable)e);
			
		}
		
		
	}
	
	private NBTTagCompound dataFix(NBTTagCompound p_189988_1_)
    {
        int i = 0;

        try
        {
            i = Integer.parseInt(p_189988_1_.getString("craftixVersion"));
        }
        catch (RuntimeException var4)
        {
            ;
        }
        	return this.cx.getDataFixer().process(FixTypes.OPTIONS, p_189988_1_, i);
    }
}
