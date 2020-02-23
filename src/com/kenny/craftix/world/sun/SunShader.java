package com.kenny.craftix.world.sun;

import static com.kenny.craftix.client.shaders.ShaderType.FRAGMENT;
import static com.kenny.craftix.client.shaders.ShaderType.VERTEX;

import org.lwjgl.util.vector.Matrix4f;

import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.shaders.ShaderProgram;
import com.kenny.craftix.client.shaders.UniformLoader;

public class SunShader extends ShaderProgram
{
	private static final String VERTEX_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "sunVS" + VERTEX;
	private static final String FRAGMENT_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "sunFS" + FRAGMENT;

	/**This is a name of the shader for more information about, if happening error.*/
	private String shaderName = "[SunShader]";
	
	/**Compile and load all type of varible into a uniform.*/
	private UniformLoader uniformLoader = new UniformLoader();
	
	/**Its all uniforms from vertex and fragment shaders*/
	private int location_mvpMatrix;
	private int location_sunTexture;

	public SunShader() 
	{
		super(VERTEX_FILE_LOCATION, FRAGMENT_FILE_LOCATION);
	}

	@Override
	protected void getAllUniformLocation() 
	{
		this.location_mvpMatrix = super.getUniformLocation("mvpMatrix", shaderName);
		this.location_sunTexture = super.getUniformLocation("sunTexture", shaderName);
	}

	@Override
	protected void bindAllAttributes() 
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
	
	public void connectTextureUnits()
	{
		this.loadSunTexture(0);
	}
	
	public void loadMvpMatrix(Matrix4f mvpMatrix)
	{
		this.uniformLoader.loadMatrix(location_mvpMatrix, mvpMatrix);
	}
	
	public void loadSunTexture(int texUnit)
	{
		this.uniformLoader.loadSampler2d(location_sunTexture, texUnit);
	}

}
