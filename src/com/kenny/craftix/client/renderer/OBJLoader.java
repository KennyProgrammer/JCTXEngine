package com.kenny.craftix.client.renderer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.CraftixOld;
import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.renderer.models.Model;

public class OBJLoader 
{
	public static final Logger LOGGER = LogManager.getLogger(CraftixOld.TITLE);
	
	/**
	 * Load a obj model. Connect texture coords with vertex positions, and write obj file
	 * and load it to the game.
	 */
	public static Model loadObjModel(String file, Loader loader)
	{	
		try 
		{
			InputStreamReader isr = new InputStreamReader(Class.class.getResourceAsStream
					(ResourceLocation.MODEL_FOLDER + file + ".obj"));
			BufferedReader reader = new BufferedReader(isr);
			
			String line;
			List<Vector3f> vertices = new ArrayList<Vector3f>();
			List<Vector2f> textures = new ArrayList<Vector2f>();
			List<Vector3f> normals = new ArrayList<Vector3f>();
			List<Integer> indices = new ArrayList<Integer>();
			float[] verticesArray = null;
			float[] normalsArray = null;
			float[] textureArray = null;
			int[] indicesArray = null;
			try
			{
				while(true)
				{
					line = reader.readLine();
					String[] currentLine = line.split(" ");
					if(line.startsWith("v "))
					{
						Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), 
								Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
						vertices.add(vertex);
					} else if(line.startsWith("vt ")) {
						Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
						textures.add(texture);
					} else if(line.startsWith("vn ")) {
						Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), 
								Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
						normals.add(normal);
					} else if(line.startsWith("f ")) {
						textureArray = new float[vertices.size() * 2];
						normalsArray = new float[vertices.size() * 3];
						break;
					}
					
					
				}
				
				while(line!= null)
				{
					if(!line.startsWith("f "))
					{
						line = reader.readLine();
						continue;
					}
					
					//String[] currentLine = line.split(" ");
					//String[] vertex1 = currentLine[1].split("/");
					//String[] vertex2 = currentLine[2].split("/");
					//String[] vertex3 = currentLine[3].split("/");
					
					String[] currentLine = line.split(" ");
					String[] vertex1 = currentLine[1].split("/");
					String[] vertex2 = currentLine[2].split("/");
					String[] vertex3 = currentLine[3].split("/");
					    
					if(vertex1[1].equalsIgnoreCase(""))
					      vertex1[1] = "1";
					if(vertex2[1].equalsIgnoreCase(""))
					       vertex2[1] = "2";
					if(vertex3[1].equalsIgnoreCase(""))
					       vertex3[1] = "3";
					
					processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
					processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
					processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
					line = reader.readLine();
		
				}
				
				reader.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			verticesArray = new float[vertices.size() * 3];
			indicesArray = new int[indices.size()];
			
			int vertexPointer = 0;
			for (Vector3f vertex : vertices) 
			{
				verticesArray[vertexPointer++] = vertex.x;
				verticesArray[vertexPointer++] = vertex.y;
				verticesArray[vertexPointer++] = vertex.z;
			}
			
			for (int i = 0; i < indices.size(); i++) 
			{
				indicesArray[i] = indices.get(i);
			}
			
			return loader.loadToVao(verticesArray, textureArray, normalsArray, indicesArray);
		} 
		catch (Exception e) 
		{
			Craftix.LOGGER.error("File not found! Check your resources folder. Maybe some file just gone.");
			return null;
		}
	
	}
	
	private static void processVertex(String[] vertexData, List<Integer> indices, 
			List<Vector2f> textures, List<Vector3f> normals, float[] textureArray, 
			float[] normalsArray)
	{
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		indices.add(currentVertexPointer);
		Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1])-1);
		textureArray[currentVertexPointer * 2] = currentTex.x;
		textureArray[currentVertexPointer * 2 + 1] = 1 - currentTex.y;
		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) -1);
		normalsArray[currentVertexPointer * 3] = currentNorm.x;
		normalsArray[currentVertexPointer * 3 + 1] = currentNorm.y;
		normalsArray[currentVertexPointer * 3 + 2] = currentNorm.z;
			
		
	}
}
