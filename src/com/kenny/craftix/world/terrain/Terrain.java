package com.kenny.craftix.world.terrain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.MainLocation;
import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.client.renderer.textures.ModelTexture;
import com.kenny.craftix.utils.math.Maths;
import com.kenny.craftix.world.World;
import com.kenny.craftix.world.terrain.gen.HeightsGenerator;
@SuppressWarnings("unused")
public class Terrain 
{
	private static final Logger LOGGER = LogManager.getLogger("terrain");
	/**Its simple a size of terrain*/
	private static float maxPixelColour = 1024 * 1024 * 1024;
	/**Its a X coord of the terrain*/
	private float x;
	/**Its a Z coord of the terrain*/
	private float z;
	/**We get Model class for Terrain class. And get new Model for terrain*/
	private Model model;
	private TerrainTexturePack texturePack;
	/**Its a list rgb textures.*/
	private ModelTexture blendMap;
	protected Loader loader = new Loader();
	private HeightsGenerator terrainGenerator;
	protected Craftix cx;
	
	/**Double arrays for multiheihgt collision terrain*/
	private float[][] heights;
	
	public Terrain(Craftix cx, int gridX, int gridZ, TerrainTexturePack texturePack, 
			ModelTexture blendMap, String heightMap)
	{
		this.cx = cx;
		World worldIn = this.cx.getWorldScene().getWorld();
		this.texturePack = texturePack;
		this.blendMap = blendMap;
		this.x = gridX * World.terrainOptions.getXSize();
		this.z = gridZ * World.terrainOptions.getZSize();
		this.model = generateTerrain(heightMap, cx);
	}
	
	public Terrain() {}
	
	/**
	 * Its a generate terrain system. Generate a base flat terrain.
	 */
	private Model generateTerrain(String heightMap, Craftix cx)
	{
		World worldIn = this.cx.getWorldScene().getWorld();
		this.terrainGenerator = new HeightsGenerator(World.terrainOptions, cx);
		
		/**
		 * I not use this capuls lines of code because heights of terrain now generates
		 * randomly.
		 */
		
		/**
		BufferedImage image = null;
		try 
		{
			image = ImageIO.read(new File(ResourceLocation.TEXTURE_FOLDER + 
					heightMap + ".png"));
		} 
		catch (IOException e) 
		{
			LOGGER.info("Cannot read file: " + heightMap);
			e.printStackTrace();
		}
		*/
		int VERTEX_COUNT = 128;
		
		
		this.heights = new float[VERTEX_COUNT][VERTEX_COUNT];
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count * 2];
		int[] indices = new int[6*(VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
		int vertexPointer = 0;
		for(int i=0;i<VERTEX_COUNT;i++)
		{
			for(int j=0;j<VERTEX_COUNT;j++)
			{
				vertices[vertexPointer * 3] = (float)j/((float)VERTEX_COUNT - 1) * World.terrainOptions.getXSize();
				float height = getHeight(j, i, terrainGenerator);
				this.heights[j][i] = height;
				vertices[vertexPointer * 3 + 1] =  height;
				vertices[vertexPointer * 3 + 2] = (float)i/((float)VERTEX_COUNT - 1) * World.terrainOptions.getXSize();
				Vector3f normal = calculateNormal(j, i, terrainGenerator);
				normals[vertexPointer * 3] = normal.x;
				normals[vertexPointer * 3 + 1] = normal.y;
				normals[vertexPointer * 3 + 2] = normal.z;
				textureCoords[vertexPointer * 2] = (float) j / ((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer * 2 + 1] = (float) i / ((float)VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz = 0; gz < VERTEX_COUNT - 1; gz++)
		{
			for(int gx = 0; gx < VERTEX_COUNT - 1; gx++)
			{
				int topLeft = (gz * VERTEX_COUNT) + gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return this.loader.loadToVao(vertices, textureCoords, normals, indices);
	}
	
	private Vector3f calculateNormal(int x, int z, HeightsGenerator generator)
	{
		float heightL = getHeight(x - 1, z, generator);
		float heightR = getHeight(x + 1, z, generator);
		float heightD = getHeight(x, z - 1, generator);
		float heightU = getHeight(x, z + 1, generator);
		Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
		normal.normalise();
			return normal;
	}
	
	private float getHeight(int x, int z, HeightsGenerator generator)
	{
		return generator.generateHeight(x, z);
	}
	
	public float getHeightOfTerrain(float worldX, float worldZ)
	{
		World worldIn = this.cx.getWorldScene().world;
		float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		float gridSquareSize = World.terrainOptions.getXSize() / ((float) heights.length - 1);
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		if(gridX >= heights.length - 1 || gridZ >= heights.length - 1 
				|| gridX < 0 || gridZ < 0)
		{
			return 0;
		}
		float xCoord = (terrainX % gridSquareSize) / gridSquareSize;
		float zCoord = (terrainZ % gridSquareSize) / gridSquareSize;
		float answer;
		if (xCoord <= (1 - zCoord)) 
		{
			answer = Maths
					.barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), new Vector3f(1,
							heights[gridX + 1][gridZ], 0), new Vector3f(0,
							heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		} 
		else 
		{
			answer = Maths
					.barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(1,
							heights[gridX + 1][gridZ + 1], 1), new Vector3f(0,
							heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		}
		
		return answer;
	}
	public float getX() 
	{
		return x;
	}

	public float getZ()
	{
		return z;
	}

	public Model getModel() 
	{
		return model;
	}

	public TerrainTexturePack getTexturePack() 
	{
		return texturePack;
	}

	public ModelTexture getBlendMap() 
	{
		return blendMap;
	}
	
	public TerrainOptions getTerrainOptions()
	{
		return this.cx.getWorldScene().world.getTerrainOptions();
	}
	
	public HeightsGenerator getGenerator()
	{
		return this.terrainGenerator;
	}

	/**
	 * Set Z position on terrain. Only used for load data from ".world" file.
	 */
	public void setX(float x) 
	{
		this.x = x;
	}

	/**
	 * Set Z position on terrain. Only used for load data from ".world" file.
	 */
	public void setZ(float z) 
	{
		this.z = z;
	}

	public void setMaxPixelColour(float mpcIn)
	{
		this.maxPixelColour = mpcIn;
	}
	
}
