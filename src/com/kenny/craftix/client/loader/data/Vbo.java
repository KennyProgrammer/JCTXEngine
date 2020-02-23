package com.kenny.craftix.client.loader.data;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.kenny.craftix.client.renderer.GlHelper;

public class Vbo 
{
	
	private final int vboId;
	private final int type;
	private final int usage;
	
	private Vbo(int vboId, int type, int usage)
	{
		this.vboId = vboId;
		this.type = type;
		this.usage = usage;
		this.bind();
	}
	
	/**
	 * Create a new Vertex Buffer Object (Vbo)
	 *
	 * @return - the new created "Vbo"
	 */
	public static Vbo create(int type, int usage)
	{
		int id = GlHelper.glGenBuffers();
		return new Vbo(id, type, usage);
	}
	
	/**
	 * Bind the current Vbo.
	 */
	public void bind()
	{
		GlHelper.glBindBuffer(type, vboId);
	}
	
	/**
	 * UnBind the current Vbo.
	 */
	public void unbind()
	{
		GlHelper.glBindBuffer(type, 0);
	}
	
	public void allocateData(long sizeInBytes)
	{
		GlHelper.glBufferData(type, sizeInBytes, usage);
	}
	
	public void storeData(long startInBytes, IntBuffer data)
	{
		GlHelper.glBufferSubData(type, startInBytes, data);
	}
	
	public void storeData(long startInBytes, FloatBuffer data)
	{
		GlHelper.glBufferSubData(type, startInBytes, data);
	}
	
	public void storeData(long startInBytes, ByteBuffer data)
	{
		GlHelper.glBufferSubData(type, startInBytes, data);
	}
	
	public void delete()
	{
		GlHelper.glDeleteVertexBuffers(vboId);
	}

}
