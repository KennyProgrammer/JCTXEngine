package com.kenny.craftix.client.renderer.models;

public class Model 
{
	/**In Vao containce alll Vbo ist(indices, normal, coord, etc).*/
	private int vaoID;
	/**This is count of vertices in model*/
	private int vertexCount;
	
	public Model(int vaoID, int vertexCount)
	{
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}
	
	public int getVaoID() 
	{
		return vaoID;
	}

	public int getVertexCount() 
	{
		return vertexCount;
	}
}
