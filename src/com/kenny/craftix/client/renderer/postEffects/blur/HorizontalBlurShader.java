package com.kenny.craftix.client.renderer.postEffects.blur;

import static com.kenny.craftix.client.shaders.ShaderType.*;

import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.shaders.ShaderProgram;
import com.kenny.craftix.client.shaders.UniformLoader;

public class HorizontalBlurShader extends ShaderProgram 
{

	private static final String VERTEX_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "horizontalBlurVS" + VERTEX;
	private static final String FRAGMENT_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "blurFS" + FRAGMENT;
	
	/**This is a name of the shader for more information about, if happening error.*/
	private String shaderName = "[HorizontalBlurShader]";
	
	/**Compile and load all type of varible into a uniform.*/
	private UniformLoader uniformLoader = new UniformLoader();
	
	private int location_targetWidth;
	
	protected HorizontalBlurShader() 
	{
		super(VERTEX_FILE_LOCATION, FRAGMENT_FILE_LOCATION);
	}

	protected void loadTargetWidth(float width)
	{
		this.uniformLoader.loadFloat(location_targetWidth, width);
	}
	
	@Override
	protected void getAllUniformLocation() 
	{
		location_targetWidth = super.getUniformLocation("targetWidth", shaderName);
	}

	@Override
	protected void bindAllAttributes() 
	{
		super.bindAttribute(0, "position");
	}
	
}
