package com.kenny.craftix.client.renderer.textures;

public class ModelTexture 
{
	/**Id textures for the model*/
	private int textureId;
	/**A texture that describes the ability of a material to reflect.*/
	private int specularMap;
	/**Normal maps are commonly stored as regular RGB images where the RGB components 
	 * correspond to the X, Y, and Z coordinates, respectively, of the surface normal.*/
	private int normalMap;
	/**It's a shine damper. The greater the value of Thea the stronger
	 * the object will Shine from the light*/
	private float shineDumper = 1;
	/**It's the ability to reflect light. The larger the number the more 
	 * light will be reflected from the object*/
	private float reflectivity = 0;
	/**This is transparancy or (Прозрачность)*/
	private boolean hasTransparency = false;
	/**If we use fake light then normals going to up and light see brightness*/
	private boolean useFakeLighting = false;
	/**Simple boolean check if you use a specular map or not.*/
	private boolean hasSpecularMap = false;
	
	/**Number of row in the atlas*/
	private int numberOfRows = 1;
	
	public ModelTexture(int textureID)
	{
		this.textureId = textureID;
	}
	
	public ModelTexture setSpecularMap(int specMap)
	{
		this.specularMap = specMap;
		this.hasSpecularMap = true;
			return this;
	}
	
	public boolean hasSpecularMap() 
	{
		return hasSpecularMap;
	}
	
	public int getSpecularMap()
	{
		return specularMap;
	}

	public boolean isUseFakeLighting() 
	{
		return useFakeLighting;
	}

	public ModelTexture setUseFakeLighting(boolean useFakeLighting) 
	{
		this.useFakeLighting = useFakeLighting;
			return this;
	}

	public int getTextureId() 
	{
		return this.textureId;
	}

	public float getShineDumper() 
	{
		return shineDumper;
	}
	
	public int getNormalMap()
	{
		return normalMap;
	}
	
	public ModelTexture setNormalMap(int normalMap)
	{
		this.normalMap = normalMap;
			return this;
	}

	public ModelTexture setShineDumper(float shineDumper) 
	{
		this.shineDumper = shineDumper;
			return this;
	}

	public float getReflectivity() 
	{
		return reflectivity;
	}

	public ModelTexture setReflectivity(float reflectivity) 
	{
		this.reflectivity = reflectivity;
			return this;
	}

	public boolean isHasTransparency() 
	{
		return hasTransparency;
	}

	public ModelTexture setHasTransparency(boolean hasTransparency) 
	{
		this.hasTransparency = hasTransparency;
			return this;
	}

	public int getNumberOfRows() 
	{
		return numberOfRows;
	}

	public ModelTexture setNumberOfRows(int numberOfRows) 
	{
		this.numberOfRows = numberOfRows;
			return this;
	}

}
