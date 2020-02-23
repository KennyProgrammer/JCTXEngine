package com.kenny.craftix.client.renderer.models;

public class ModelGui
{
	public float topLeftVertex_X =   -1F; 
	public float topLeftVertex_Y =    1F;
	public float downLeftVertex_X =  -1F;
	public float downLeftVertex_Y =  -1F;
	public float topRightVertex_X =   1F;
	public float topRightVertex_Y =   1F;
	public float downRightVertex_X =  1F;
	public float downRightVertex_Y = -1F;
	
	private float[] modelQuad;
	
	/**
	 * This constructor create a gui model form yours inputs floats.
	 */
	public ModelGui(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) 
	{
		this.modelQuad = new float[] {x1, y1, x2, y2, x3, y3, x4, y4};
	}
	
	/**
	 * This constructor create new gui quad model from position the vertices. Here we use
	 * triangles strips system of drawing the quad.
	 */
	public ModelGui()
	{
		this.modelQuad = new float[] 
				{this.topLeftVertex_X, this.topLeftVertex_Y, 
					this.downLeftVertex_X, this.downLeftVertex_Y,
						this.topRightVertex_X, this.topRightVertex_Y,
							this.downRightVertex_X, this.downRightVertex_Y};
	}
	
	/**
	 * Return the array of position vertices.
	 */
	public float[] getModelQuad()
	{
		return this.modelQuad;
	}
}	
