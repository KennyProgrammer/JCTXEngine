package com.kenny.craftix.client.loader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;


import com.kenny.craftix.client.Craftix;

import com.kenny.craftix.client.loader.data.Vao;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.GlHelper.Texture;
import com.kenny.craftix.client.renderer.models.Model;

@SuppressWarnings({"rawtypes", "unchecked" })
public class Loader 
{
	public static final Logger LOGGER = LogManager.getLogger(Craftix.LOGGER_INSTANCE);
	/**List of vaos and vbos*/
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	/**List Vao*/
	public List<Vao> vao = new ArrayList<Vao>();

	private static Map<Integer, List<Integer>> vaoCache = new HashMap();
	
	/**
	 * Create a Vertex Arrays Objects (VAO) and return Vertex Buffer Objects (VBO) ids.
	 */
	private int createVao() 
	{
		int vaoID = GlHelper.glGenVertexArrays();
		GlHelper.glBindVertexArray(vaoID);
		List<Integer> associatedVbos = new ArrayList();
		vaoCache.put(vaoID, associatedVbos);
			return vaoID;
		
	}
	
	/**
	 * Load all raw models in VAO lists.
	 */
	public Model loadToVao(float[] positions, float[] textureCoords, float[] normals,
			int[] indices)
	{
		int vaoID = createVao();
		this.vaos.add(vaoID);
		this.bindIndicesBuffer(vaoID,indices);
		this.storeDataInAttributeList(0, 3, positions);
		this.storeDataInAttributeList(1, 2, textureCoords);
		this.storeDataInAttributeList(2, 3, normals);
		this.unbindVAO();
			return new Model(vaoID, indices.length);
	}
	
	/**
	 * Load all verices of each text character.
	 */
	public int loadToVao(float[] positions, float[] textureCoords)
	{
		int vaoID = createVao();
		this.vaos.add(vaoID);
		this.storeDataInAttributeList(0, 2, positions);
		this.storeDataInAttributeList(1, 2, textureCoords);
		this.unbindVAO();
			return vaoID;
	}
	
	/**
	 * Load all raw models in VAO lists with tangents for normal map effect.
	 */
	public Model loadToVao(float[] positions, float[] textureCoords, float[] normals,
		float[] tangents, int[] indices)
	{
		int vaoID = createVao();
		this.vaos.add(vaoID);
		this.bindIndicesBuffer(vaoID, indices);
		this.storeDataInAttributeList(0, 3, positions);
		this.storeDataInAttributeList(1, 2, textureCoords);
		this.storeDataInAttributeList(2, 3, normals);
		this.storeDataInAttributeList(3, 3, tangents);
		this.unbindVAO();
			return new Model(vaoID, indices.length);
	}
	
	public Model loadToVao(float[] position, int dimensions)
	{
		int vaoID = createVao();
		this.storeDataInAttributeList(0, dimensions, position);
		this.unbindVAO();
			return new Model(vaoID, position.length / dimensions);
	}
	
	
	
	public int createEmptyVbo(int floatCount)
	{
		int vbo = GlHelper.glGenBuffers();
		//vaoCache.get(vao).add(vbo);
		this.vbos.add(vbo);
		GlHelper.glBindBuffer(Texture.ARRAY_BUFFER, vbo);
		GlHelper.glBufferData(Texture.ARRAY_BUFFER, floatCount * 4, Texture.STREAM_DRAW);
		GlHelper.glBindBuffer(Texture.ARRAY_BUFFER, 0);
			return vbo;
	}
	
	public void addInstancedAttribute(int vao, int vbo, int attribute, int dataSize, 
			int instancedDataLength, int offset)
	{
		GlHelper.glBindBuffer(Texture.ARRAY_BUFFER, vbo);
		GlHelper.glBindVertexArray(vao);
		GlHelper.glVertexAttribPointer(attribute, dataSize, Texture.FLOAT, false, 
				instancedDataLength * 4, offset * 4);
		GlHelper.glVertexAttribDivisor(attribute, 1);
		GlHelper.glBindBuffer(Texture.ARRAY_BUFFER, 0);
		GlHelper.glBindVertexArray(0);
	}
	
	public void updateVbo(int vbo, float[] data, FloatBuffer buffer)
	{
		buffer.clear();
		buffer.put(data);
		buffer.flip();
		GlHelper.glBindBuffer(Texture.ARRAY_BUFFER, vbo);
		GlHelper.glBufferData(Texture.ARRAY_BUFFER, buffer.capacity(), Texture.STREAM_DRAW);
		GlHelper.glBufferSubData(Texture.ARRAY_BUFFER, 0, buffer);
		GlHelper.glBindBuffer(Texture.ARRAY_BUFFER, 0);
	}
	

	
	/**
	 * Stores the position data of the vertices into attribute 0 of the VAO. To
	 * do this the positions must first be stored in a VBO. You can simply think
	 * of a VBO as an array of data that is stored in memory on the GPU for easy
	 * access during rendering.
	 */
	
	private void storeDataInAttributeList(int attributeNumber, int coordsSize, float[] data)
	{
		int vboID = GlHelper.glGenBuffers();
		this.vbos.add(vboID);
		GlHelper.glBindBuffer(Texture.ARRAY_BUFFER, vboID);
		FloatBuffer buffer = this.storeDataInFloatBuffer(data);
		GlHelper.glBufferData(Texture.ARRAY_BUFFER, buffer, Texture.STATIC_DRAW);
		GlHelper.glVertexAttribPointer(attributeNumber, coordsSize, Texture.FLOAT, false, 0, 0);
		GlHelper.glBindBuffer(Texture.ARRAY_BUFFER, 0);
		
	}
	
	/**
	 * This method unbind all vertecies in VAO.
	 */
	private void unbindVAO()
	{
		GlHelper.glBindVertexArray(0);
	}
	
	private void bindIndicesBuffer(int vaoId, int [] indices)
	{
		IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
	    indicesBuffer.put(indices);
	    indicesBuffer.flip();
	    int indicesBufferId = GlHelper.glGenBuffers();
		GlHelper.glBindBuffer(Texture.ELEMENT_ARRAY_BUFFER, indicesBufferId);
		IntBuffer buffer = this.storeDataInIntBuffer(indices);
		((List)vaoCache.get(Integer.valueOf(vaoId))).add(Integer.valueOf(indicesBufferId));
		GlHelper.glBufferData(Texture.ELEMENT_ARRAY_BUFFER, buffer, Texture.STATIC_DRAW);
	}
	
	public static int createIndicesVBO(int vaoID, int[] indices)
	{
	    IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
	    indicesBuffer.put(indices);
	    indicesBuffer.flip();
	    int indicesBufferId = GlHelper.glGenBuffers();
	    ((List)vaoCache.get(Integer.valueOf(vaoID))).add(Integer.valueOf(indicesBufferId));
	    GlHelper.glBindBuffer(34963, indicesBufferId);
	    GlHelper.glBufferData(34963, indicesBuffer, 35044);
	    return indicesBufferId;
	} 
	
	private IntBuffer storeDataInIntBuffer(int[] data)
	{
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
			return buffer;
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
			return buffer;
	}
	
	/**
	 * Deletes all the VAOs and VBOs when the game is closed. VAOs and VBOs are
	 * located in video memory.
	 */
	public void cleanUpOpenGlDataObjects()
	{
		for (int vao : vaos) 
		{
			GlHelper.glDeleteVertexArrays(vao);
		}
		for (int vbo : vbos) 
		{
			GlHelper.glDeleteVertexBuffers(vbo);
		}
	}
	
	public void cleanUpDataVbo()
	{
		for (int vbo : vbos) 
		{
			GlHelper.glDeleteVertexBuffers(vbo);
		}
	}
	
	public static void cleanUpModelMemory()
	 {
	    GlHelper.glDisableVertexAttribArray(0);
	    GlHelper.glBindBuffer(34962, 0);
	    GlHelper.glBindVertexArray(0);
	    
	    for (Iterator localIterator1 = vaoCache.keySet().iterator(); localIterator1.hasNext();) { int vaoID = ((Integer)localIterator1.next()).intValue();
	      List<Integer> vbos = (List)vaoCache.get(Integer.valueOf(vaoID));
	      for (Iterator localIterator2 = vbos.iterator(); localIterator2.hasNext();) { int vbo = ((Integer)localIterator2.next()).intValue();
	        GlHelper.glDeleteVertexBuffers(vbo);
	      }
	      GlHelper.glDeleteVertexArrays(vaoID);
	    }
	    vaoCache.clear();
	  }
	
	/**
	 * Delete Open Gl objects from system memory and clear the cache.
	 */
	public static void deleteVaoFromCache(int vao)
	{
		List<Integer> vbos = vaoCache.remove(vao);
		for (int vbo : vbos) 
		{
			GlHelper.glDeleteVertexBuffers(vbo);
		}
		GlHelper.glDeleteVertexArrays(vao);
		
	}
}
