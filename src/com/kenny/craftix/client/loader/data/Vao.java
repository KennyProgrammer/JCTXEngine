package com.kenny.craftix.client.loader.data;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;


import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.GlHelper.Texture;
import com.kenny.craftix.utils.DataUtils;

public class Vao 
{
	private List<Vbo> relatedVbos = new ArrayList<Vbo>();
	private Vbo indexBuffer;
	private List<Attribute> attributes = new ArrayList<Attribute>();

	public final int id;

	public static Vao create() 
	{
		int id = GlHelper.glGenVertexArrays();
		return new Vao(id);
	}

	private Vao(int id) 
	{
		this.id = id;
	}

	public void bind() 
	{
		GlHelper.glBindVertexArray(this.id);
	}

	public void unbind() 
	{
		GlHelper.glBindVertexArray(0);
	}

	public void enableAttributes() 
	{
		for (Attribute attribute : attributes) 
		{
			attribute.enable(true);
		}
	}

	public void disableAttribs(int... attribs) 
	{
		for (int i : attribs) {
			GlHelper.glDisableVertexAttribArray(i);
		}
	}

	public Vbo createDataFeed(int maxVertexCount, int usage, Attribute... newAttributes) 
	{
		int bytesPerVertex = getVertexDataTotalBytes(newAttributes);
		Vbo vbo = Vbo.create(Texture.ARRAY_BUFFER, usage);
		relatedVbos.add(vbo);
		vbo.allocateData(bytesPerVertex * maxVertexCount);
		linkAttributes(bytesPerVertex, newAttributes);
		vbo.unbind();
		return vbo;
	}

	public Vbo initDataFeed(FloatBuffer data, int usage, Attribute... newAttributes) 
	{
		int bytesPerVertex = getVertexDataTotalBytes(newAttributes);
		Vbo vbo = Vbo.create(Texture.ARRAY_BUFFER, usage);
		relatedVbos.add(vbo);
		vbo.allocateData(data.limit() * DataUtils.BYTES_IN_FLOAT);
		vbo.storeData(0, data);
		linkAttributes(bytesPerVertex, newAttributes);
		vbo.unbind();
		return vbo;
	}
	
	public Vbo initDataFeed(ByteBuffer data, int usage, Attribute... newAttributes) 
	{
		int bytesPerVertex = getVertexDataTotalBytes(newAttributes);
		Vbo vbo = Vbo.create(Texture.ARRAY_BUFFER, usage);
		this.relatedVbos.add(vbo);
		vbo.allocateData(data.limit());
		vbo.storeData(0, data);
		linkAttributes(bytesPerVertex, newAttributes);
		vbo.unbind();
		return vbo;
	}

	public void linkBoundVbo(Vbo vbo, Attribute... newAttributes) 
	{
		int bytesPerVertex = getVertexDataTotalBytes(newAttributes);
		linkAttributes(bytesPerVertex, newAttributes);
		this.relatedVbos.add(vbo);
	}

	public Vbo createIndexBuffer(IntBuffer indices) 
	{
		this.indexBuffer = Vbo.create(Texture.ELEMENT_ARRAY_BUFFER, Texture.STATIC_DRAW);
		this.indexBuffer.allocateData(indices.limit() * DataUtils.BYTES_IN_INT);
		this.indexBuffer.storeData(0, indices);
		return indexBuffer;
	}

	public void delete(boolean deleteVbos) 
	{
		GlHelper.glDeleteVertexArrays(this.id);
		if (deleteVbos) {
			for (Vbo vbo : relatedVbos) 
			{
				vbo.delete();
			}
		}
	}

	private void linkAttributes(int bytesPerVertex, Attribute... newAttributes) 
	{
		int offset = 0;
		for (Attribute attribute : newAttributes) 
		{
			attribute.link(offset, bytesPerVertex);
			offset += attribute.bytesPerVertex;
			attribute.enable(true);
			attributes.add(attribute);
		}
	} 
	
	private int getVertexDataTotalBytes(Attribute... newAttributes) 
	{
		int total = 0;
		for (Attribute attribute : newAttributes) 
		{
			total += attribute.bytesPerVertex;
		}
		return total;
	}

}
