package com.kenny.craftix.client.renderer.postEffects.blur;

import static com.kenny.craftix.client.shaders.ShaderType.*;

import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.shaders.ShaderProgram;
import com.kenny.craftix.client.shaders.UniformLoader;

public class VerticalBlurShader extends ShaderProgram
{

	private static final String VERTEX_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "verticalBlurVS" + VERTEX;
	private static final String FRAGMENT_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "blurFS" + FRAGMENT;
	
	/**This is a name of the shader for more information about, if happening error.*/
	private String shaderName = "[VertcalBlurShader]";
	
	/**Compile and load all type of varible into a uniform.*/
	private UniformLoader uniformLoader = new UniformLoader();
	
	private int location_targetHeight;
	
	protected VerticalBlurShader() 
	{
		super(VERTEX_FILE_LOCATION, FRAGMENT_FILE_LOCATION);
	}
	
	protected void loadTargetHeight(float height)
	{
		this.uniformLoader.loadFloat(location_targetHeight, height);
	}

	@Override
	protected void getAllUniformLocation() 
	{	
		location_targetHeight = super.getUniformLocation("targetHeight", shaderName);
	}

	@Override
	protected void bindAllAttributes() {
		super.bindAttribute(0, "position");
	}
}
