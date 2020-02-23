package com.kenny.craftix.world.terrain.gen;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.world.terrain.TerrainOptions;

public class HeightsGenerator 
{
	public TerrainOptions terrainOptions;
	public static final Logger LOGGER = LogManager.getLogger("generator");
	/***An instance of this class is used to generate a stream of pseudorandom 
	 * numbers. The class uses a 48-bit seed, which is modified using a linear 
	 * congruential formula.*/
	private Random random = new Random();
	/**This is a seed generation map.*/
	private int seed;
	private int xOffset = 0;
	private int zOffset = 0;
	
	public HeightsGenerator(TerrainOptions terrainOptionsIn, Craftix craftixIn)
	{
		this.terrainOptions = terrainOptionsIn;
		if(craftixIn.status.isServer())
		{
			int serverSeed = craftixIn.getWorldScene().worldSeed;
			this.seed = serverSeed;
		}
		else
		{
			this.seed = 4354454;//craftixIn.getWorldScene().world.getWorldSeed();
		}
	}
	
	/**
	 * This is same smooth noicse but more interpreted.
	 * @return
	 */
	private float getInterpolatedNoise(float x, float z)
	{
		int intX = (int) x;
		int intZ = (int) z;
		float fracX = x - intX;
		float fracZ = z - intZ;
		
		float v1 = getSmoothNoise(intX, intZ);
		float v2 = getSmoothNoise(intX + 1, intZ);
		float v3 = getSmoothNoise(intX, intZ + 1);
		float v4 = getSmoothNoise(intX + 1, intZ + 1);
		float i1 = interpolate(v1, v2, fracX);
		float i2 = interpolate(v3, v4, fracX);
			return interpolate(i1, i2, fracZ);
	}
	
	
	private float interpolate(float a, float b, float blend)
	{
		double theta = blend * Math.PI;
		float f = (float) (1f - Math.cos(theta)) * 0.5f;
			return a * (1f - f) + b * f;
		
	}
	
	/**
	 * Its a generation map vertices height.
	 */
	public float generateHeightOld(int x, int z)
	{
		float total = getInterpolatedNoise(x / this.terrainOptions.getSmooth(), z / this.terrainOptions.getSmooth()) * 
				this.terrainOptions.getAmplitude();
		total += getInterpolatedNoise(x / this.terrainOptions.getSmoothNext(), z / this.terrainOptions.getSmoothNext()) * 
				this.terrainOptions.getAmplitude() / 3f;
		total += getInterpolatedNoise(x , z) * this.terrainOptions.getAmplitude() / 9f;
			return total;
	}
	
	/**
	 * Only works with POSITIVE gridX and gridZ values!
	 */
    public HeightsGenerator(int gridX, int gridZ, int vertexCount, int seed) 
    {
        this.seed = seed;
        xOffset = gridX * (vertexCount - 1);
        zOffset = gridZ * (vertexCount - 1);
    }
	
	 public float generateHeight(int x, int z) 
	 {
	        float total = 0;
	        float d = (float) Math.pow(2, this.terrainOptions.getOctaves() - 1);
	        for(int i = 0; i < this.terrainOptions.getOctaves(); i++)
	        {
	            float freq = (float) (Math.pow(2, i) / d);
	            float amp = (float) Math.pow(this.terrainOptions.getRoughness(), i) * this.terrainOptions.getAmplitude();
	            total += getInterpolatedNoise((x + xOffset) * freq, (z + zOffset) * freq) * amp;
	        }
	        return total;
	    }
	
	/**
	 * This is that same noise but litte be random, smooth.
	 * @return
	 */
	private float getSmoothNoise(int x, int z)
	{
		float corners = (getNoise(x - 1, z - 1) + getNoise(x + 1, z - 1) + 
				getNoise(x - 1, z + 1) + getNoise(x + 1, z + 1)) / 16f;
		float sides = (getNoise(x - 1, z) + getNoise(x + 1, z) + 
				getNoise(x, z - 1) + getNoise(x, z + 1)) / 8f;
		float center = getNoise(x, z) / 4f;
			return corners + sides + center;
	}
	
	/**
	 * This method will give different seeds values from up to.
	 * @param x - X coord on the terrain.
	 * @param z - Z coord on the terrain.
	 */
	public float getNoise(int x, int z)
	{
		this.random.setSeed(x * 49632 + z * 325176 + this.seed); 
		return this.random.nextFloat() * 2f - 1f;
	}
	
	/**
	 * Return the seed of generated world. 
	 */
	public int getSeed()
	{
		return this.seed;
	}
	
	
}
