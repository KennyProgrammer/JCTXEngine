package com.kenny.craftix.client.particles.render;

import static com.kenny.craftix.client.shaders.ShaderType.*;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.shaders.ShaderProgram;
import com.kenny.craftix.client.shaders.UniformLoader;

public class ParticleShader extends ShaderProgram 
{

	private static final String VERTEX_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "particleVS" + VERTEX;
	private static final String FRAGMENT_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "particleFS" + FRAGMENT;

	/**This is a name of the shader for more information about, if happening error.*/
	private String shaderName = "[ParticleShader]";
	
	/**Compile and load all type of varible into a uniform.*/
	private UniformLoader uniformLoader = new UniformLoader();
	
	/**This is all uniforms contains in shader (txt) file in res folder*/
	private int location_transformationMatrix;
	private int location_numberOfRows;
	private int location_projectionMatrix;
	private int location_density;
	private int location_gradient;
	private int location_skyColour;

	public ParticleShader() 
	{
		super(VERTEX_FILE_LOCATION, FRAGMENT_FILE_LOCATION);
	}

	@Override
	protected void getAllUniformLocation() 
	{
		this.location_transformationMatrix = getUniformLocation("transformationMatrix", shaderName);
		this.location_numberOfRows = super.getUniformLocation("numberOfRows", shaderName);
		this.location_projectionMatrix = super.getUniformLocation("projectionMatrix", shaderName);
		this.location_skyColour = getUniformLocation("skyColour", shaderName);
		this.location_density = getUniformLocation("density", shaderName);
		this.location_gradient = getUniformLocation("gradient", shaderName);
	}

	@Override
	protected void bindAllAttributes() 
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "modelViewMatrix");
		super.bindAttribute(2, "texOffset");
		super.bindAttribute(3, "blendFactor");	
		
	}

	protected void loadNumberOfRows(float numberOfRows)
	{
		this.uniformLoader.loadFloat(location_numberOfRows, numberOfRows);
	}
	
	protected void loadProjectionMatrix(Matrix4f projectionMatrix) 
	{
		this.uniformLoader.loadMatrix(location_projectionMatrix, projectionMatrix);
	}
	
	public void loadDensity(float density)
	{
		this.uniformLoader.loadFloat(location_density, density);
	}
	
	public void loadGradient(float gradient)
	{
		this.uniformLoader.loadFloat(location_gradient, gradient);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix)
	{
		this.uniformLoader.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadSkyColour(float r, float g, float b)
	{
		this.uniformLoader.loadVector3d(location_skyColour, new Vector3f(r, g, b));
	}

}
