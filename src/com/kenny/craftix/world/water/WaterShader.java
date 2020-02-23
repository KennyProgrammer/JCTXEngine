package com.kenny.craftix.world.water;

import static com.kenny.craftix.client.shaders.ShaderType.*;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.entity.Light;
import com.kenny.craftix.client.shaders.ShaderProgram;
import com.kenny.craftix.client.shaders.UniformLoader;
import com.kenny.craftix.utils.math.Maths;

public class WaterShader extends ShaderProgram 
{

	private static final String VERTEX_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "waterVS" + VERTEX;
	private static final String FRAGMENT_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "waterFS" + FRAGMENT;

	/**This is a name of the shader for more information about, if happening error.*/
	private String shaderName = "[WaterShader]";
	
	/**Compile and load all type of varible into a uniform.*/
	private UniformLoader uniformLoader = new UniformLoader();
	
	/**Its all uniforms from vertex and fragment shaders*/
	private int location_modelMatrix;
	private int location_viewMatrix;
	private int location_projectionMatrix;
	private int location_reflectionTexture;
	private int location_refractionTexture;
	private int location_dudvMap;
	private int location_moveFactor;
	private int location_cameraPosition;
	private int location_normalMap;
	private int location_lightColour;
	private int location_lightPosition;
	private int location_depthMap;
	private int location_skyColour;
	private int location_density;
	private int location_gradient;
	private int location_tiling;
	private int location_waveStrength;
	private int location_shineDamper;
	private int location_reflectivity;

	public WaterShader() 
	{
		super(VERTEX_FILE_LOCATION, FRAGMENT_FILE_LOCATION);
	}

	@Override
	protected void bindAllAttributes() 
	{
		bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocation() 
	{
		this.location_projectionMatrix = getUniformLocation("projectionMatrix" ,shaderName);
		this.location_viewMatrix = getUniformLocation("viewMatrix", shaderName);
		this.location_modelMatrix = getUniformLocation("modelMatrix", shaderName);
		this.location_reflectionTexture = getUniformLocation("reflectionTexture", shaderName);
		this.location_refractionTexture = getUniformLocation("refractionTexture", shaderName);
		this.location_dudvMap = getUniformLocation("dudvMap", shaderName);
		this.location_moveFactor = getUniformLocation("moveFactor", shaderName);
		this.location_cameraPosition = getUniformLocation("cameraPosition", shaderName);
		this.location_normalMap = getUniformLocation("normalMap", shaderName);
		this.location_lightColour = getUniformLocation("lightColour", shaderName);
		this.location_lightPosition = getUniformLocation("lightPosition", shaderName);
		this.location_depthMap = getUniformLocation("depthMap", shaderName);
		this.location_skyColour = getUniformLocation("skyColour", shaderName);
		this.location_density = getUniformLocation("density", shaderName);
		this.location_gradient = getUniformLocation("gradient", shaderName);
		this.location_tiling = getUniformLocation("tiling", shaderName);
		this.location_waveStrength = getUniformLocation("waveStrength", shaderName);
		this.location_shineDamper = getUniformLocation("shineDamper", shaderName);
		this.location_reflectivity = getUniformLocation("reflectivity", shaderName);
	}
	
	public void connectTextureUnits()
	{
		this.uniformLoader.loadInt(location_reflectionTexture, 0);
		this.uniformLoader.loadInt(location_refractionTexture, 1);
		this.uniformLoader.loadInt(location_dudvMap, 2);
		this.uniformLoader.loadInt(location_normalMap, 3);
		this.uniformLoader.loadInt(location_depthMap, 4);
	}
	
	public void loadGradient(float gradient)
	{
		this.uniformLoader.loadFloat(location_gradient, gradient);
	}
	
	public void loadDensity(float density)
	{
		this.uniformLoader.loadFloat(location_density, density);
	}
	
	public void loadSkyColour(float r, float g, float b)
	{
		this.uniformLoader.loadVector3d(location_skyColour, new Vector3f(r, g, b));
	}
	
	public void loadLight(Light sun)
	{
		this.uniformLoader.loadVector3d(location_lightColour, sun.getColour());
		this.uniformLoader.loadVector3d(location_lightPosition, sun.getPosition());
	}
	
	public void loadMoveFactor(float factor)
	{
		this.uniformLoader.loadFloat(location_moveFactor, factor);
	}


	public void loadTiling(float tiling)
	{
		this.uniformLoader.loadFloat(location_tiling, tiling);
	}
	
	public void loadWaveStrength(float waveStrength)
	{
		this.uniformLoader.loadFloat(location_waveStrength, waveStrength);
	}
	
	public void loadShineDamper(float shineDamper)
	{
		this.uniformLoader.loadFloat(location_shineDamper, shineDamper);
	}
	
	public void loadReflectivity(float reflectivity)
	{
		this.uniformLoader.loadFloat(location_reflectivity, reflectivity);
	}
	
	public void loadProjectionMatrix(Matrix4f projection) 
	{
		this.uniformLoader.loadMatrix(location_projectionMatrix, projection);
	}
	
	public void loadViewMatrix(EntityCamera camera)
	{
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		this.uniformLoader.loadMatrix(location_viewMatrix, viewMatrix);
		this.uniformLoader.loadVector3d(location_cameraPosition, camera.getPosition());
	}

	public void loadModelMatrix(Matrix4f modelMatrix)
	{
		this.uniformLoader.loadMatrix(location_modelMatrix, modelMatrix);
	}

}
