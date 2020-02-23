package com.kenny.craftix.client.audio.ogg;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.ResourceLocation;

public class OggDataStream implements IDataStream 
{
	public static final Logger LOGGER = LogManager.getLogger(Craftix.LOGGER_INSTANCE);
	private final int alFormat;
	private final int sampleRate;
	private final int chunckSize;
	private final OggInputStream audioStream;
	private final ByteBuffer buffer;
	
	public OggDataStream(OggInputStream audioStream, int chunckSizeBytes) 
	{
		this.audioStream = audioStream;
		this.chunckSize = (chunckSizeBytes - chunckSizeBytes % 8);
		this.alFormat = this.audioStream.getFormat();
		this.buffer = BufferUtils.createByteBuffer(this.chunckSize);
		this.sampleRate = audioStream.getRate();
	}
	
	/**
	 * Set the start point for bytes.
	 */
	public void setStartPoint(int startBytes)
	{
		try
		{
			if(startBytes > this.chunckSize)
			{
				this.audioStream.read(new byte[startBytes], 0, startBytes);
			}
			else
			{
				this.buffer.clear();
				this.audioStream.read(this.buffer, 0, startBytes);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public int getAlFormat() 
	{
		return alFormat;
	}

	public int getSampleRate() 
	{
		return sampleRate;
	}
	
	/**
	 * Load the next data, and read more byte from audio stream.
	 */
	public ByteBuffer loadNextData()
	{
		try
		{
			this.buffer.clear();
			int bytesRead = this.audioStream.read(this.buffer, 0, this.chunckSize);
			this.buffer.flip();
			if(bytesRead <= 0)
			{
				return null;
			}
			return this.buffer;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			LOGGER.error("Couldn't read more byte from audio stream!");
		}
		return null;
	}
	
	public boolean hasEnded()
	{
	    return this.audioStream.isEndOfStream();
	}
	  
	public void close()
	{
	    try
	    {
	      this.audioStream.close();
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	}
	  
	public static OggDataStream openOggStream(ResourceLocation oggFile, int chunkSizeBytes) throws Exception
	{
	    OggInputStream stream = new OggInputStream(oggFile.getInputStream());
	    return new OggDataStream(stream, chunkSizeBytes);
	}

	@Override
	public int getAllFormat() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
