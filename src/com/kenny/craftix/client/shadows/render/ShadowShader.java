package com.kenny.craftix.client.shadows.render;

import static com.kenny.craftix.client.shaders.ShaderType.*;

import org.lwjgl.util.vector.Matrix4f;

import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.shaders.ShaderProgram;
import com.kenny.craftix.client.shaders.UniformLoader;

public class ShadowShader extends ShaderProgram 
{
	private static final String VERTEX_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "shadowVS" + VERTEX;
	private static final String FRAGMENT_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "shadowFS" + FRAGMENT;
	
	/**This is a name of the shader for more information about, if happening error.*/
	private String shaderName = "[ShadowShader]";
	
	/**Compile and load all type of varible into a uniform.*/
	private UniformLoader uniformLoader = new UniformLoader();
	
	private int location_mvpMatrix;

	protected ShadowShader() 
	{
		super(VERTEX_FILE_LOCATION, FRAGMENT_FILE_LOCATION);
	}

	@Override
	protected void getAllUniformLocation() 
	{
		location_mvpMatrix = super.getUniformLocation("mvpMatrix", shaderName);
		
	}
	
	protected void loadMvpMatrix(Matrix4f mvpMatrix)
	{
		this.uniformLoader.loadMatrix(location_mvpMatrix, mvpMatrix);
	}

	@Override
	protected void bindAllAttributes() 
	{
		super.bindAttribute(0, "in_position");
		super.bindAttribute(1, "in_textureCoords");
	}

}
