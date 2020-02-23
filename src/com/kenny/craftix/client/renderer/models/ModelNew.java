package com.kenny.craftix.client.renderer.models;

import com.kenny.craftix.client.loader.data.Vao;

public class ModelNew 
{
	/**In Vao containce alll Vbo ist(indices, normal, coord, etc).*/
	private Vao vaoID;
	/**This is count of vertices in model*/
	private int vertexCount;
	
	public ModelNew(Vao vaoID, int vertexCount)
	{
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}
	
	public Vao getVaoID() 
	{
		return vaoID;
	}

	public int getVertexCount() 
	{
		return vertexCount;
	}
}