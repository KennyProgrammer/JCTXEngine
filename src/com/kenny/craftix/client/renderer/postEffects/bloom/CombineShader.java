package com.kenny.craftix.client.renderer.postEffects.bloom;

import static com.kenny.craftix.client.shaders.ShaderType.*;

import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.shaders.ShaderProgram;
import com.kenny.craftix.client.shaders.UniformLoader;

public class CombineShader extends ShaderProgram 
{

	private static final String VERTEX_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "simpleVS" + VERTEX;
	private static final String FRAGMENT_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "combineFS" + FRAGMENT;
	
	/**This is a name of the shader for more information about, if happening error.*/
	private String shaderName = "[CombineShader]";
	
	/**Compile and load all type of varible into a uniform.*/
	private UniformLoader uniformLoader = new UniformLoader();
	
	/**Location all uniform varibles.*/
	private int location_colourTexture;
	private int location_highlightTexture;
	
	protected CombineShader() 
	{
		super(VERTEX_FILE_LOCATION, FRAGMENT_FILE_LOCATION);
	}
	
	@Override
	protected void getAllUniformLocation() 
	{
		location_colourTexture = super.getUniformLocation("colourTexture", shaderName);
		location_highlightTexture = super.getUniformLocation("highlightTexture", shaderName);
	}
	
	protected void connectTextureUnits()
	{
		this.uniformLoader.loadInt(location_colourTexture, 0);
		this.uniformLoader.loadInt(location_highlightTexture, 1);
	}

	@Override
	protected void bindAllAttributes() 
	{
		super.bindAttribute(0, "position");
	}
	
}
