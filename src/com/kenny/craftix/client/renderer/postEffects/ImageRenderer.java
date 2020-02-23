package com.kenny.craftix.client.renderer.postEffects;

import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.GlHelper.Texture;
import com.kenny.craftix.client.shaders.FrameBuffer;

public class ImageRenderer 
{
	/**Get framebuffer for this class like instance.*/
	private FrameBuffer framebuffer;

	public ImageRenderer(int width, int height) 
	{
		this.framebuffer = new FrameBuffer(width, height, FrameBuffer.NONE);
	}

	public ImageRenderer() {}

	/**
	 * Render a simple quad. And then effects render on him.
	 */
	public void renderQuad() 
	{
		if (this.framebuffer != null) 
		{
			this.framebuffer.bindFrameBuffer();
		}
		GlHelper.glClear(Texture.COLOR_BUFFER_BIT);
		GlHelper.glDrawArrays(Texture.TRIANGLE_STRIP, 0, 4);
		if (this.framebuffer != null) 
		{
			this.framebuffer.unbindFrameBuffer();
		}
	}

	public int getOutputTexture() 
	{
		return this.framebuffer.getColourTexture();
	}

	/**
	 * Clean up Framebuffer when we close the game. Or when FBO = null.
	 */
	public void cleanUp() 
	{
		if (this.framebuffer != null) 
		{
			this.framebuffer.cleanUp();
		}
	}

}
