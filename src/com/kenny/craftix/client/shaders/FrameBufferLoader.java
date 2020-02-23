package com.kenny.craftix.client.shaders;

import org.lwjgl.opengl.Display;


public class FrameBufferLoader 
{
	public FrameBuffer msFrameBuffer;
	public FrameBuffer frameBuffer1;
	public FrameBuffer frameBuffer2;
	
	public FrameBufferLoader(FrameBuffer id0, FrameBuffer id1, FrameBuffer id2)
	{
		this.msFrameBuffer = id0;
		this.frameBuffer1 = id1;
		this.frameBuffer2 = id2;
		
		id0 = new FrameBuffer(Display.getWidth(), Display.getHeight());
		id1 = new FrameBuffer(Display.getWidth(), Display.getHeight(), FrameBuffer.DEPTH_TEXTURE);
		id2 = new FrameBuffer(Display.getWidth(), Display.getHeight(), FrameBuffer.DEPTH_TEXTURE);
	}
	
}
