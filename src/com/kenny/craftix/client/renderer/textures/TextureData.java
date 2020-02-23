package com.kenny.craftix.client.renderer.textures;

import java.nio.ByteBuffer;

public class TextureData 
{
	/**This is a width of texture*/
	private int width;
	/**This is a height of texture*/
	private int height;
	private ByteBuffer buffer;
	
	public TextureData(ByteBuffer buffer, int width, int height)
	{
		this.buffer = buffer;
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() 
	{
		return width;
	}
		
	public int getHeight() 
	{
		return height;
	}
	
	public ByteBuffer getBuffer()
	{
		return buffer;
	}
	
	
}
