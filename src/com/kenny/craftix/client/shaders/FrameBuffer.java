package com.kenny.craftix.client.shaders;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;

import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.GlHelper.Depth;
import com.kenny.craftix.client.renderer.GlHelper.Framebuffer;
import com.kenny.craftix.client.renderer.GlHelper.Texture;
import com.kenny.craftix.client.renderer.GlHelperError;
import com.kenny.craftix.client.renderer.textures.TextureManager;

public class FrameBuffer 
{
	protected static final int MY_FRAME_DISPLAY = 100;
	protected static final int MY_FRAME_START_DISPLAY_POINT = 50;
	private static final Logger LOGGER = LogManager.getLogger();
	public static final int NONE = 0;
	public static final int DEPTH_TEXTURE = 1;
	public static final int DEPTH_RENDER_BUFFER = 2;
	/**This is a reflection height and height*/
    protected static final int REFLECTION_WIDTH = 320;
    private static final int REFLECTION_HEIGHT = 180;
     /**This is a refraction width and height*/
    protected static final int REFRACTION_WIDTH = 1280;
    private static final int REFRACTION_HEIGHT = 768;
    /**This is a simple framebuffer constant.*/
    private int framebuffer;
	/**Its a width for framebuffer.*/
	private int width;
	/**Its a height for framebuffer.*/
	private int height;
	/**It is a reflection framebuffer, texture and depth
	 * buffer of the world in water.*/
    private int reflectionFrameBuffer;
    private int reflectionTexture;
    private int reflectionDepthBuffer;
    /**It is a refraction framebuffer, texture and depth
	 * buffer of the world in water.*/
    private int refractionFrameBuffer;
    private int refractionTexture;
    private int refractionDepthTexture;
    /**This is a colour texture for framebuffer.*/
    private int colourTexture;
    /**This is a depth texture for framebuffer.*/
    private int depthTexture;
    
    /**These variables are responsible for the colors in the world that are stored in the buffer.*/
    /**This is a normal depth buffer.*/
    private int depthBuffer;
    /**This is normal colors in the world.*/
	private int colourBuffer;
	/**This is a special/edit or unverse colors in the world.*/
	private int colourBuffer2;
	
	/**False to water fbo and true for standard fbo.*/
	private boolean waterBuffer = false;
	private boolean multisampleAndMultiTarget = false;
	
	/**
	 * Create a Framebuffer of a specified width and height, with the desired type of 
	 * depth buffer attacment.
	 * 
	 * @param width - width of the framebuffer.
	 * @param height - height of the framebuffer.
	 * @param depthBufferType - ant int indificate the type of depth buffer attachment that
	 * 			this framebuffer should use.
	 */
	public FrameBuffer(int width, int height, int depthBufferType)
	{
		this.width = width;
		this.height = height;
		this.initFrameBuffer(depthBufferType);
	}
	
	public FrameBuffer(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.multisampleAndMultiTarget = true;
		this.initFrameBuffer(DEPTH_RENDER_BUFFER);
	}
	
	/**
	 * Load the water framebuffers. Ant initalize reflection and refraction
	 * effects texture.
	 */
	public FrameBuffer()
	{
		this.waterBuffer = true;
		this.initReflectionFrameBuffer();
	    this.initRefractionFrameBuffer();
	}
	
	/**
	 * Here a gl generate framebuffers, bind itself, and draw this buffers.
	 * One word is this method create framebuffer.
	 *  
	 * @return - a new framebuffer object.
	 */
	
	private void createFrameBuffer()
	{
		if(GlHelperError.isFramebufferSupported())
		{
			try
			{
				this.framebuffer = GlHelper.glGenFramebuffers();
				GlHelper.glBindFramebuffers(Framebuffer.FRAMEBUFFER, framebuffer);
				this.determineDrawBuffer();
			}		
			catch(Exception e)
			{
				LOGGER.error("Cannot create framebuffer!");
				e.printStackTrace();
			}
		}
		else
		{
			LOGGER.fatal("Framebuffer object not supported on your computer!");
		}
		
	}
	
	private int createWaterFrameBuffer()
	{
		if(GlHelperError.isFramebufferSupported())
		{
			try
			{
				this.framebuffer = GlHelper.glGenFramebuffers();
				GlHelper.glBindFramebuffers(Framebuffer.FRAMEBUFFER, this.framebuffer);
				if(this.waterBuffer)
				{
					GlHelper.glDrawBuffers(Framebuffer.COLOR_ATTACHMENT0);
				}
			}
			catch(Exception e)
			{
				LOGGER.error("Cannot create water framebuffer!");
				e.printStackTrace();
			}
		}
		else
		{
			LOGGER.fatal("Framebuffer object not supported on your computer!");
		}
		
		return framebuffer;
	}
	
	/**
	 * Creates the FrameBuffer along with a colour buffer texture attachment, and
	 * possibly a depth buffer.
	 */
	private void initFrameBuffer(int type)
	{
		if(GlHelperError.isFramebufferSupported())
		{
			this.createFrameBuffer();
			if(this.multisampleAndMultiTarget)
			{
				this.colourBuffer = createMultisampleColourAttachment(Framebuffer.COLOR_ATTACHMENT0);
				this.colourBuffer2 = createMultisampleColourAttachment(Framebuffer.COLOR_ATTACHMENT1);
			}
			else
			{
				this.createTextureAttachment();
			}
			if(type == DEPTH_RENDER_BUFFER)
			{
				this.createDepthBufferAttachment();
			} else if (type == DEPTH_TEXTURE)
			{
				this.createDepthTextureAttachment();
			}
			this.unbindFrameBuffer();
		}
	}
	
	private void initReflectionFrameBuffer()
	{
		if(GlHelperError.isFramebufferSupported())
		{
			this.reflectionFrameBuffer = createWaterFrameBuffer();
	        this.reflectionTexture = createTextureAttachment(REFLECTION_WIDTH,REFLECTION_HEIGHT);
	        this.reflectionDepthBuffer = createDepthBufferAttachment(REFLECTION_WIDTH,REFLECTION_HEIGHT);
	        this.unbindFrameBuffer();
		}
	}
	
	private void initRefractionFrameBuffer()
	{
		if(GlHelperError.isFramebufferSupported())
		{
			this.refractionFrameBuffer = createWaterFrameBuffer();
			this.refractionTexture = createTextureAttachment(REFRACTION_WIDTH,REFRACTION_HEIGHT);
			this.refractionDepthTexture = createDepthTextureAttachment(REFRACTION_WIDTH,REFRACTION_HEIGHT);
			this.unbindFrameBuffer();
		}
	}
	
	/**
	 * Binds the frame buffer, setting it as the current render target. Anything
	 * rendered after this will be rendered to this FBO, and not to the screen.
	 */
	public void bindFrameBuffer(int framebuffer, int width, int height)
	{
		if(GlHelperError.isFramebufferSupported())
		{
			if(this.waterBuffer)
			{
				TextureManager.bindTexture2d(0);
				GlHelper.glBindFramebuffers(Framebuffer.FRAMEBUFFER, framebuffer);
				GlHelper.glViewport(0, 0, width, height);
			}
		}
	}
	
	public void bindFrameBuffer() 
	{
		if(GlHelperError.isFramebufferSupported())
		{
			GlHelper.glBindFramebuffers(Framebuffer.DRAW_FRAMEBUFFER, framebuffer);
			GlHelper.glViewport(0, 0, width, height);
		}
	}
	
	public void bindReflectionFrameBuffer() 
    {
    	/**Call before rendering to this FBO.*/
		this.waterBuffer = true;
        this.bindFrameBuffer(reflectionFrameBuffer, REFLECTION_WIDTH,REFLECTION_HEIGHT);
    }
     
    public void bindRefractionFrameBuffer() 
    {
    	/**Call before rendering to this FBO.*/
    	this.waterBuffer = true;
        this.bindFrameBuffer(refractionFrameBuffer, REFRACTION_WIDTH,REFRACTION_HEIGHT);
    }
	
    /**
	 * Binds the current framebuffer to be read from.
	 */
    public void bindToRead()
    {
    	TextureManager.bindTexture2d(0);
    	GlHelper.glBindFramebuffers(Framebuffer.READ_FRAMEBUFFER, framebuffer);
    	GlHelper.glReadBuffer(Framebuffer.COLOR_ATTACHMENT0);
    }
    
	/**
	 * Unbinds the frame buffer, setting the default frame buffer as the current
	 * render target. Anything rendered after this will be rendered to the
	 * screen, and not this FBO.
	 */
	public void unbindFrameBuffer()
	{
		GlHelper.glBindFramebuffers(Framebuffer.FRAMEBUFFER, 0);
		GlHelper.glViewport(0, 0, Display.getWidth(), Display.getHeight());
	}
	

	/**
	 * If you use resolve to framebuffer, you can resize display in real time.
	 * 
	 * @param outputFramebuffer - output current fbo.
	 */
	public void resolveToFrameBuffer(int readBuffer, FrameBuffer outputFrameBuffer)
	{
		GlHelper.glBindFramebuffers(Framebuffer.DRAW_FRAMEBUFFER, outputFrameBuffer.framebuffer);
		GlHelper.glBindFramebuffers(Framebuffer.READ_FRAMEBUFFER, this.framebuffer);
		GlHelper.glReadBuffer(readBuffer);
		//GlHelper.glBlitFramebuffer(0, 0, width, height, 0, 0, outputFrameBuffer.width, outputFrameBuffer.height,
				//Texture.COLOR_BUFFER_BIT | Depth.DEPTH_BUFFER_BIT, Texture.NEAREST);
		GlHelper.glBlitFramebuffer(0, 0, width, height, 0, 0, outputFrameBuffer.width, outputFrameBuffer.height,
				Texture.COLOR_BUFFER_BIT | Depth.DEPTH_BUFFER_BIT, Texture.NEAREST);
		this.unbindFrameBuffer();
		
	}
	
	/**
	 * If you use resolve to screen, you can't resize display in real time. You just see a black
	 * glitch screen.
	 */
	public void resolveToScreen()
	{
		GlHelper.glBindFramebuffers(Framebuffer.DRAW_FRAMEBUFFER, 0);
		GlHelper.glBindFramebuffers(Framebuffer.READ_FRAMEBUFFER, this.framebuffer);
		GlHelper.glDrawBuffers(Texture.BACK);
		GlHelper.glBlitFramebuffer(0, 0, width, height, 0, 0, Display.getWidth(), Display.getHeight(), 
				Texture.COLOR_BUFFER_BIT, Texture.NEAREST);
		this.unbindFrameBuffer();
	}
	
	/**
	 * This method choose when color attachment game has be loaded.
	 */
	private void determineDrawBuffer()
	{
		IntBuffer drawBuffers = BufferUtils.createIntBuffer(2);
		drawBuffers.put(Framebuffer.COLOR_ATTACHMENT0);
		if(this.multisampleAndMultiTarget)
		{
			drawBuffers.put(Framebuffer.COLOR_ATTACHMENT1);
		}
		drawBuffers.flip();
		GlHelper.glDrawBuffers(drawBuffers);
	}
	
	/**
	 * Creates a texture and sets it as the colour buffer attachment for this
	 * framebuffer.
	 */
	private void createTextureAttachment()
	{
		this.colourTexture = GlHelper.glGenTextures();
		TextureManager.bindTexture2d(this.colourTexture);
		GlHelper.glTexImage2D(Texture.TEXTURE_2D, 0, Texture.RGBA8, width, height, 0, Texture.RGBA, Texture.UNSIGNED_BYTE, //or int
				(ByteBuffer) null);
		GlHelper.glTexParametri(Texture.TEXTURE_MAG_FILTER, Texture.LINEAR);
		GlHelper.glTexParametri(Texture.TEXTURE_MIN_FILTER, Texture.LINEAR);
		GlHelper.glTexParametri(Texture.TEXTURE_WRAP_S, Texture.CLAMP_TO_EDGE);
		GlHelper.glTexParametri(Texture.TEXTURE_WRAP_T, Texture.CLAMP_TO_EDGE);
		GlHelper.glFramebufferTexture2D(Framebuffer.FRAMEBUFFER, Framebuffer.COLOR_ATTACHMENT0, Texture.TEXTURE_2D, colourTexture,
				0);
	}
	
	private int createTextureAttachment(int width, int height) 
    {
        int texture = GlHelper.glGenTextures();
        TextureManager.bindTexture2d(texture);
        GlHelper.glTexImage2D(Texture.TEXTURE_2D, 0, Texture.RGB, width, height,
                0, Texture.RGB, Texture.UNSIGNED_BYTE, (ByteBuffer) null);
        GlHelper.glTexParametri(Texture.TEXTURE_MAG_FILTER, Texture.LINEAR);
        GlHelper.glTexParametri(Texture.TEXTURE_MIN_FILTER, Texture.LINEAR);
        GlHelper.glFramebufferTexture(Framebuffer.FRAMEBUFFER, Framebuffer.COLOR_ATTACHMENT0,
                texture, 0);
        return texture;
    }
	
	/**
	 * Adds a depth buffer to the FBO in the form of a render buffer. This can't
	 * be used for sampling in the shaders.
	 */
	private void createDepthBufferAttachment()
	{
		this.depthBuffer = GlHelper.glGenRenderbuffers();
		GlHelper.glBindRenderbuffers(Framebuffer.RENDERBUFFER, depthBuffer);
		if(!multisampleAndMultiTarget)
		{
			GlHelper.glRenderbufferStorage(Framebuffer.RENDERBUFFER, Depth.DEPTH_COMPONENT24, width, height);
		}
		else
		{
			GlHelper.glRenderbufferStorageMultisample(Framebuffer.RENDERBUFFER, 4, Depth.DEPTH_COMPONENT24, width, height);
		}
		GlHelper.glFramebufferRenderbuffer(Framebuffer.FRAMEBUFFER, Depth.DEPTH_ATTACHMENT, Framebuffer.RENDERBUFFER,
				depthBuffer);
	}
	
	/**
	 * Create a depth buffer for a water frame buffer. With width and height inside.
	 */
	private int createDepthBufferAttachment(int width, int height) 
    {
        int depthBuffer = GlHelper.glGenRenderbuffers();
        GlHelper.glBindRenderbuffer(depthBuffer);
        GlHelper.glRenderbufferStorage(Framebuffer.RENDERBUFFER, Depth.DEPTH_COMPONENT, width,
                height);
        GlHelper.glFramebufferRenderbuffer(Framebuffer.FRAMEBUFFER, Depth.DEPTH_ATTACHMENT,
        		Framebuffer.RENDERBUFFER, depthBuffer);
        return depthBuffer;
    }
	
	
	private int createMultisampleColourAttachment(int attachment)
	{
		int colourBuffer = GlHelper.glGenRenderbuffers();
		GlHelper.glBindRenderbuffers(Framebuffer.RENDERBUFFER, colourBuffer);
		GlHelper.glRenderbufferStorageMultisample(Framebuffer.RENDERBUFFER, 4, Texture.RGBA8, width, height);
		GlHelper.glFramebufferRenderbuffer(Framebuffer.FRAMEBUFFER, attachment, Framebuffer.RENDERBUFFER,
				colourBuffer);
			return colourBuffer;
	
	}
	
	/**
	 * Adds a depth buffer to the FBO in the form of a texture, which can later
	 * be sampled.
	 */
	private void createDepthTextureAttachment()
	{
		this.depthTexture = GlHelper.glGenTextures();
		TextureManager.bindTexture2d(this.depthTexture);
		GlHelper.glTexImage2D(Texture.TEXTURE_2D, 0, Depth.DEPTH_COMPONENT24, width, height, 0, Depth.DEPTH_COMPONENT,
				Texture.FLOAT, (ByteBuffer) null);
		GlHelper.glTexParametri(Texture.TEXTURE_MAG_FILTER, Texture.LINEAR);
		GlHelper.glTexParametri(Texture.TEXTURE_MIN_FILTER, Texture.LINEAR);
		GlHelper.glFramebufferTexture2D(Framebuffer.FRAMEBUFFER, Depth.DEPTH_ATTACHMENT, Texture.TEXTURE_2D, depthTexture, 0);
	}
	
	/**
	 * Adds a water depth buffer to the FBO in the form of a texture, which can later
	 * be sampled.
	 */
	 private int createDepthTextureAttachment(int width, int height)
	 {
		 int texture = GlHelper.glGenTextures();
		 TextureManager.bindTexture2d(texture);
	     GlHelper.glTexImage2D(Texture.TEXTURE_2D, 0, Depth.DEPTH_COMPONENT32, width, height,
	             0, Depth.DEPTH_COMPONENT, Texture.FLOAT, (ByteBuffer) null);
	     GlHelper.glTexParametri(Texture.TEXTURE_MAG_FILTER, Texture.LINEAR);
	     GlHelper.glTexParametri(Texture.TEXTURE_MIN_FILTER, Texture.LINEAR);
	     GlHelper.glFramebufferTexture(Framebuffer.FRAMEBUFFER, Depth.DEPTH_ATTACHMENT,
	             texture, 0);
	     	return texture;
	 }
	
	/**
	 * Clean up base framebuffer or water framebuffer when user closed the game.
	 */
	public void cleanUp()
	{
		if(this.waterBuffer)
		{
			GlHelper.glDeleteFramebuffers(this.reflectionFrameBuffer);
			GlHelper.glDeleteTextures(this.reflectionTexture);
			GlHelper.glDeleteRenderbuffers(this.reflectionDepthBuffer);
			GlHelper.glDeleteFramebuffers(this.refractionFrameBuffer);
			GlHelper.glDeleteTextures(this.refractionTexture);
			GlHelper.glDeleteRenderbuffers(this.refractionDepthTexture);
		}
		
			GlHelper.glDeleteFramebuffers(framebuffer);
			GlHelper.glDeleteTextures(colourTexture);
			GlHelper.glDeleteTextures(depthTexture);
			GlHelper.glDeleteRenderbuffers(depthBuffer);
			GlHelper.glDeleteRenderbuffers(colourBuffer);
			GlHelper.glDeleteFramebuffers(colourBuffer2);

	}
	
	/**
	 * Clean up absulutly all framebuffers when we close the game.
	 */
	public void cleanUpAll()
	{
		GlHelper.glDeleteFramebuffers(this.reflectionFrameBuffer);
		GlHelper.glDeleteTextures(this.reflectionTexture);
		GlHelper.glDeleteRenderbuffers(this.reflectionDepthBuffer);
		GlHelper.glDeleteFramebuffers(this.refractionFrameBuffer);
		GlHelper.glDeleteTextures(this.refractionTexture);
		GlHelper.glDeleteRenderbuffers(this.refractionDepthTexture);
		GlHelper.glDeleteFramebuffers(this.framebuffer);
		GlHelper.glDeleteTextures(this.colourTexture);
		GlHelper.glDeleteTextures(this.depthTexture);
		GlHelper.glDeleteRenderbuffers(this.depthBuffer);
		GlHelper.glDeleteRenderbuffers(this.colourBuffer);
		GlHelper.glDeleteFramebuffers(this.colourBuffer2);
	}
	
	/**
	 * Get the resulting "Reflection" texture.
	 */
	public int getReflectionTexture()
	{
		return this.reflectionTexture;
	}
	
	/**
	 * Get the resulting "Refraction" texture.
	 */
	public int getRefractionTexture()
	{
		return this.refractionTexture;
	}
	
	public int getRefractionDepthTexture()
	{
		return this.refractionDepthTexture;
	}
	
	/**
	 * Get the ID of the texture containing the colour buffer of the FBO.
	 */
	public int getColourTexture()
	{
		return this.colourTexture;
	}
	
	/**
	 * Get the texture containing the FBOs depth buffer.
	 */
	public int getDepthTexture()
	{
		return this.depthTexture;
	}
	
}
	
