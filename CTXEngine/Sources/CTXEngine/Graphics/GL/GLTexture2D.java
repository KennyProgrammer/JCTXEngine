package CTXEngine.Graphics.GL;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import CTXEngine.Graphics.Texture2D;

import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.stb.STBImage.*;
import static CTXEngine.Core.CoreBase.*;
import static CTXEngine.Core.CoreUtils.*;

/**
 * Images in this texture all are 2-dimensional. They have width and
 * height, but no depth.
 */
public final class GLTexture2D extends Texture2D
{
	/**Id of this Open GL 2D texture.*/
	private int id;
	/**Type of this Open GL 2D texture.*/
	private int type;
	/**Width of this Open GL 2D texture.*/
	private int width;
	/**Height of this Open GL 2D texture.*/
	private int height;
	/**Format of this texture.*/
	private int format, internalFormat;
	/**Channels type on this texture.*/
	private int channels;
	/**Specific texture data.*/
	private ByteBuffer textureData; //or Buffer 
	/**Path of this texture.*/
	private final String resource;
	
	/**
	 * Create with GPU one texture with width and height, then build
	 * new GL_TEXTURE_2D texture.
	 */
	public GLTexture2D(int widthIn, int heightIn) 
	{
		this.id = 0;
		this.width = widthIn;
		this.height = heightIn;
		this.format = 0;
		this.internalFormat = 0;
		this.type = GL_TEXTURE_2D;
		this.resource = "";
		stbi_set_flip_vertically_on_load(true);
		
		String glv = GLGraphicsContext.OPEN_GL_VERSION;
		if(glv.contains("4.5") || glv.contains("4.6") || glv.contains("4.7"))
		{
			this.load1_wh();
		}
		else
		{
			this.load0_wh();
		}
	}
	
	/**
	 * Create, load and build new GL_TEXTURE_2D texture.
	 */
	public GLTexture2D(final String resource) 
	{
		this.id = 0;
		this.format = 0;
		this.textureData = null;
		this.channels = 0;
		this.internalFormat = 0;
		this.resource = resource;
		stbi_set_flip_vertically_on_load(true);
		
		String glv = GLGraphicsContext.OPEN_GL_VERSION;
		if(glv.contains("4.5") || glv.contains("4.6") || glv.contains("4.7"))
		{
			this.load1();
		}
		else
		{
			this.load0();
		}
	}
	
	/**
	 * Load OpenGL texture with functions 3.3 core and below.
	 */
	public void load0()
	{
		try 
		{
			IntBuffer w = BufferUtils.createIntBuffer(1);
			IntBuffer h = BufferUtils.createIntBuffer(1);
			IntBuffer chan = BufferUtils.createIntBuffer(1);			
			this.textureData =  stbi_load_from_memory(resourceToByteBuffer(this.resource), w, h, chan, 0);
			CTX_ENGINE_ASSERT(textureData == null, "Can't load image to texture!\n" + stbi_failure_reason());

			this.width = w.get();
			this.height = h.get();
			this.channels = chan.get();
		} 
		catch (IOException e) { e.printStackTrace(); }
		
		if (this.channels == 4)
		{
			this.internalFormat = GL_RGBA8;
			this.format = GL_RGBA;
		}
		else if (this.channels == 3)
		{
			this.internalFormat = GL_RGB;
			this.format = GL_RGB;
		}
		
		this.id = GLHelper.hglGenTextures();
		GLHelper.hglBindTexture(this.type, this.id);
		
		GLHelper.hglTexParameteri(this.type, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		GLHelper.hglTexParameteri(this.type, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		GLHelper.hglTexParameteri(this.type, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		GLHelper.hglTexParameteri(this.type, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
	
		GLHelper.hglTexImage2D(this.type, 0, this.internalFormat, this.width, this.height, 0, 
				this.format, GL_UNSIGNED_BYTE, this.textureData);

		GLHelper.hglBindTexture(this.type, 0);

		if (this.textureData != null)
			stbi_image_free(this.textureData);
	}
	
	/**
	 * Load OpenGL texture with new functions 4.5 core and above.
	 */
	public void load1()
	{
		try 
		{
			IntBuffer w = BufferUtils.createIntBuffer(1);
			IntBuffer h = BufferUtils.createIntBuffer(1);
			IntBuffer chan = BufferUtils.createIntBuffer(1);			
			this.textureData =  stbi_load_from_memory(resourceToByteBuffer(this.resource), w, h, chan, 0);
			CTX_ENGINE_ASSERT(textureData == null, "Can't load image to texture!\n" + stbi_failure_reason());

			this.width = w.get();
			this.height = h.get();
			this.channels = chan.get();
		} 
		catch (IOException e) { e.printStackTrace(); }
		
		int textureMinFilter = 0;
		int textureMagFilter = 0;
	
		if (this.width > 64 || this.height > 64)
		{
			textureMinFilter = GL_LINEAR;
			textureMagFilter = GL_LINEAR_MIPMAP_LINEAR;
		}
		else if (width <= 64 || this.height <= 64)
		{
			textureMinFilter = GL_LINEAR;
			textureMagFilter = GL_NEAREST;
		}
	
		if (this.channels == 4)
		{
			this.internalFormat = GL_RGBA8;
			this.format = GL_RGBA;
		}
		else if (this.channels == 3)
		{
			this.internalFormat = GL_RGB8;
			this.format = GL_RGB;
		}
		CTX_ENGINE_ASSERT((this.internalFormat & this.format) == 0, "Image Format is not supported!");
	
		this.id = GLHelper.hglCreateTextures(this.type);
		GLHelper.hglTextureStorage2D(this.id, 1, this.internalFormat, this.width, this.height);
	
		glGenerateMipmap(this.type);
	
		GLHelper.hglTextureParameteri(this.id, GL_TEXTURE_MIN_FILTER, textureMinFilter);
		GLHelper.hglTextureParameteri(this.id, GL_TEXTURE_MAG_FILTER, textureMagFilter);
		
		GLHelper.hglTextureParameteri(this.id, GL_TEXTURE_WRAP_S, GL_REPEAT);
		GLHelper.hglTextureParameteri(this.id, GL_TEXTURE_WRAP_T, GL_REPEAT);
	
		GLHelper.hglTextureSubImage2D(this.id, 0, 0, 0, this.width, this.height, this.format, 
				GL_UNSIGNED_BYTE, this.textureData);
	
		if (textureData != null)
			stbi_image_free(this.textureData);
	}
	
	/**
	 * Load OpenGL texture with functions 3.3 core and below.
	 */
	public void load0_wh()
	{
		this.internalFormat = GL_RGBA8;
		this.format = GL_RGBA;
	
		this.id = GLHelper.hglGenTextures();
		GLHelper.hglBindTexture(this.type, this.id);
	
		GLHelper.hglTexParameteri(this.type, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		GLHelper.hglTexParameteri(this.type, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		GLHelper.hglTexParameteri(this.type, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		GLHelper.hglTexParameteri(this.type, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
	
		GLHelper.hglTexImage2D(this.type, 0, this.internalFormat, this.width, this.height, 0,
			this.format, GL_UNSIGNED_BYTE, textureData);
	
		GLHelper.hglBindTexture(this.type, 0);
	}
	
	/**
	 * Load OpenGL texture with new functions 4.5 core and above.
	 */
	public void load1_wh()
	{
		this.internalFormat = GL_RGBA8;
		this.format = GL_RGBA;
	
		this.id = GLHelper.hglCreateTextures(this.type);
		GLHelper.hglTextureStorage2D(this.id, 1, this.internalFormat, this.width, this.height);
	
		GLHelper.hglTextureParameteri(this.id, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		GLHelper.hglTextureParameteri(this.id, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		GLHelper.hglTextureParameteri(this.id, GL_TEXTURE_WRAP_S, GL_REPEAT);
		GLHelper.hglTextureParameteri(this.id, GL_TEXTURE_WRAP_T, GL_REPEAT);
	}

	
	/**
	 * Delete texture data from memory after destroying.
	 */
	public void destroyGLTexture2D()
	{
		GLHelper.hglDeleteTextures(this.id);
		if(textureData != null)
			stbi_image_free(this.textureData);
	}
	
	/**
	 * Set texture data into void pointer block of memory (in Java with ByteBuffer) 
	 * with size.
	 * 
	 * @param texData - is C pointer block of memory in Java's ByteBuffer.
	 */
	@Override
	public void setTextureData(ByteBuffer texData, int size) 
	{
		int bytePerChannel = this.format == GL_RGBA ? 4 : 3;
		CTX_ENGINE_ASSERT(size == this.width * this.height * bytePerChannel,
			"Data must be entire texture!");
		GLHelper.hglTextureSubImage2D(this.id, 0, 0, 0, this.width, this.height,
				this.format, GL_UNSIGNED_BYTE, texData);
	}
	
	/**
	 * Set texture data into void pointer block of memory (in Java with ByteBuffer) 
	 * with size.
	 * 
	 * @param texData - is C pointer block of memory in Java's ByteBuffer.
	 */
	@Override
	public void setTextureData(IntBuffer texData, int size) 
	{
		int bytePerChannel = this.format == GL_RGBA ? 4 : 3;
		CTX_ENGINE_ASSERT(size == this.width * this.height * bytePerChannel,
			"Data must be entire texture!");
		GLHelper.hglTextureSubImage2D(this.id, 0, 0, 0, this.width, this.height,
				this.format, GL_UNSIGNED_BYTE, texData);
	}
	
	/**
	 * Bind texture id and prepare to use.
	 */
	@Override
	public void bind(int slot) 
	{
		GLHelper.hglBindTextureUnit(slot, this.id);
	}
	
	/**
	 * Bind texture id and prepare to use.
	 */
	@Override
	public void bind() 
	{
		this.bind(0);
	}
	
	/**
	 * Unbind texture id.
	 */
	@Override
	public void unBind(int slot) 
	{
		GLHelper.hglBindTextureUnit(slot, 0);
	}
	
	/**
	 * Unbind texture id.
	 */
	@Override
	public void unBind() 
	{
		this.unBind(0);
	}
	
	/**
	 * Return the id of this texture.
	 */
	@Override
	public final int getId() 
	{
		return this.id;
	}
	
	/*
	 * Return the type of this texture.
	 */
	@Override
	public final int getType() 
	{
		return this.type;
	}
	
	/**
	 * Return the width of this texture. 
	 */
	@Override
	public final int getWidth() 
	{
		return this.width;
	}
	
	/**
	 * Return the height of this texture. 
	 */
	@Override
	public final int getHeight() 
	{
		return this.height;
	}
	
	/**
	 * Return the internal format of this texture.
	 */
	@Override
	public final int getInternalFormat() 
	{
		return this.internalFormat;
	}
	
	/**
	 * Return the format of this texture.
	 */
	@Override
	public final int getFormat() 
	{
		return this.format;
	}
	
	/**
	 * Return the byte channels of this texture data.
	 */
	@Override
	public final int getChannels() 
	{
		return this.channels;
	}
	
	/**
	 * Return string resource / path where stored this texture.
	 */
	@Override
	public final String getResource() 
	{
		return this.resource;
	}
}
