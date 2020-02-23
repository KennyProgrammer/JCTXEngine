package com.kenny.craftix.client.text;

/**
 * Stores the vertex data for all the quads on which a text will be rendered.
 */
public class TextMeshData 
{
    
	/**
	 * This is a simple array of vertex position.
	 */
    private float[] vertexPositions;
    /**
	 * This is a simple array of texture coordinates.
	 */
    private float[] textureCoords;
     
    protected TextMeshData(float[] vertexPositions, float[] textureCoords)
    {
        this.vertexPositions = vertexPositions;
        this.textureCoords = textureCoords;
    }
 
    public float[] getVertexPositions() 
    {
        return vertexPositions;
    }
 
    public float[] getTextureCoords() 
    {
        return textureCoords;
    }
 
    public int getVertexCount() 
    {
        return vertexPositions.length / 2;
    }
 
}