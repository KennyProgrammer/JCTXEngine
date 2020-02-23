package com.kenny.craftix.client.audio.ogg;

import java.nio.ByteBuffer;

public abstract interface IDataStream 
{
	public abstract int getAllFormat();
	
	public abstract int getSampleRate();
	  
	public abstract void setStartPoint(int paramInt);
	  
	public abstract ByteBuffer loadNextData();
	  
	public abstract boolean hasEnded();
	  
	public abstract void close();

	public abstract int getAlFormat();
}
