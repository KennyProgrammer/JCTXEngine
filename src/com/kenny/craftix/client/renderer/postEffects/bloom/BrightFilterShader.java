package com.kenny.craftix.client.renderer.postEffects.bloom;

import static com.kenny.craftix.client.shaders.ShaderType.*;

import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.shaders.ShaderProgram;
import com.kenny.craftix.client.shaders.UniformLoader;

public class BrightFilterShader extends ShaderProgram
{
	
	private static final String VERTEX_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "simpleVS" + VERTEX;
	private static final String FRAGMENT_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "brightFilterFS" + FRAGMENT;
	
	/**Compile and load all type of varible into a uniform.*/
	@SuppressWarnings("unused")
	private UniformLoader uniformLoader = new UniformLoader();
	
	public BrightFilterShader() 
	{
		super(VERTEX_FILE_LOCATION, FRAGMENT_FILE_LOCATION);
	}

	@Override
	protected void getAllUniformLocation() 
	{	
	}

	@Override
	protected void bindAllAttributes() 
	{
		super.bindAttribute(0, "position");
	}

}
