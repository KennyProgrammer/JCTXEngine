package com.kenny.craftix.client.shaders;

import static com.kenny.craftix.client.shaders.ShaderType.*;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.entity.Light;
import com.kenny.craftix.utils.math.Maths;

public class TerrainShader extends ShaderProgram implements IShader
{
	private static final String VERTEX_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "terrainVS" + VERTEX;
	private static final String FRAGMENT_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "terrainFS" + FRAGMENT;
	
	/**This is a name of the shader for more information about, if happening error.*/
	private String shaderName = "[TerrainShader]";
	
	/**Compile and load all type of varible into a uniform.*/
	private UniformLoader uniformLoader = new UniformLoader();
	
	/**This is the maximum number of colors available in the world.*/
	private static final int MAX_LIGHTS = 4;
	
	/**This is all uniforms contains in shader (txt) file in res folder*/
	private int location_trasformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition[];
	private int location_lightColour[];
	private int location_attenuation[];
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_skyColour;
	private int location_backgroundTexture;
	private int location_rTexture;
	private int location_gTexture;
	private int location_bTexture;
	private int location_blendMap;
	private int location_plane;
	private int location_toShadowMapSpace;
	private int location_shadowMap;
	private int location_density;
	private int location_gradient;
	private int location_shadowDistance;
	private int location_transitionDistance;
    private int location_pcfCount;
    private int location_totalTexels;
	
	public TerrainShader() 
	{
		super(VERTEX_FILE_LOCATION, FRAGMENT_FILE_LOCATION);
	}

	@Override
	protected void bindAllAttributes() 
	{
		/**
		 * If computer not supported shaders version 330 or highter.
		 * 
		 * super.bindFragOutput(0, "out_Colour");
		 * super.bindFragOutput(1, "out_BrightColour");
		 */
		
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocation() 
	{
		this.location_trasformationMatrix = super.getUniformLocation("trasformationMatrix", shaderName);
		this.location_projectionMatrix = super.getUniformLocation("projectionMatrix", shaderName);
		this.location_viewMatrix = super.getUniformLocation("viewMatrix", shaderName);
		this.location_shineDamper = super.getUniformLocation("shineDamper", shaderName);
		this.location_reflectivity = super.getUniformLocation("reflectivity", shaderName);
		this.location_skyColour = super.getUniformLocation("skyColour", shaderName);
		this.location_backgroundTexture = super.getUniformLocation("backgroundTexture", shaderName);
		this.location_rTexture = super.getUniformLocation("rTexture", shaderName);
		this.location_gTexture = super.getUniformLocation("gTexture", shaderName);
		this.location_bTexture = super.getUniformLocation("bTexture", shaderName);
		this.location_blendMap = super.getUniformLocation("blendMap", shaderName);
		this.location_plane = super.getUniformLocation("plane", shaderName);
		this.location_toShadowMapSpace = super.getUniformLocation("toShadowMapSpace", shaderName);
		this.location_shadowMap = super.getUniformLocation("shadowMap", shaderName);
		this.location_density = super.getUniformLocation("density", shaderName);
		this.location_gradient = super.getUniformLocation("gradient", shaderName);
		this.location_shadowDistance = super.getUniformLocation("shadowDistance", shaderName);
		this.location_transitionDistance = super.getUniformLocation("transitionDistance", shaderName);
		this.location_pcfCount = super.getUniformLocation("pcfCount", shaderName);
		this.location_totalTexels = super.getUniformLocation("totalTexels", shaderName);
		
		this.location_lightPosition = new int[MAX_LIGHTS];
		this.location_lightColour = new int[MAX_LIGHTS];
		this.location_attenuation = new int[MAX_LIGHTS];
		for(int i = 0; i < MAX_LIGHTS; i++)
		{
			this.location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]", shaderName);
			this.location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]", shaderName);
			this.location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]", shaderName);
		}
	}
	
	public void connectTextureUnits()
	{
		this.uniformLoader.loadInt(location_backgroundTexture, 0);
		this.uniformLoader.loadInt(location_rTexture, 1);
		this.uniformLoader.loadInt(location_gTexture, 2);
		this.uniformLoader.loadInt(location_bTexture, 3);
		this.uniformLoader.loadInt(location_blendMap, 4);
		this.uniformLoader.loadInt(location_shadowMap, 5);
	}
	
	public void loadPcfCount(int pcfCount)
	{
		this.uniformLoader.loadInt(location_pcfCount, pcfCount);
	}
	    
	public void loadTotalTexels(float totalTexels)
	{
		this.uniformLoader.loadFloat(location_totalTexels, totalTexels);
	}
	
	public void loadPlane(Vector4f plane)
	{
		this.uniformLoader.loadVector4d(location_plane, plane);
	}
	
	public void loadSkyColour(float r, float g, float b)
	{
		this.uniformLoader.loadVector3d(location_skyColour, new Vector3f(r, g, b));
	}
	
	public void loadShineVaribles(float damper, float reflectivity)
	{
		this.uniformLoader.loadFloat(location_shineDamper, damper);
		this.uniformLoader.loadFloat(location_reflectivity, reflectivity);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix)
	{
		this.uniformLoader.loadMatrix(location_trasformationMatrix, matrix);
	}
	
	public void loadToShadowMapSpace(Matrix4f mat)
	{
		this.uniformLoader.loadMatrix(location_toShadowMapSpace, mat);
	}
	
	public void loadDensity(float density)
	{
		this.uniformLoader.loadFloat(location_density, density);
	}
	
	public void loadGradient(float gradient)
	{
		this.uniformLoader.loadFloat(location_gradient, gradient);
	}
	
	public void loadShadowDistance(float shadowDistance)
	{
		this.uniformLoader.loadFloat(location_shadowDistance, shadowDistance);
	}
	
	public void loadTransitionDistance(float transitionDistance)
	{
		this.uniformLoader.loadFloat(location_transitionDistance, transitionDistance);
	}
	
	public void loadLights(List<Light> lights)
	{
		for (int i = 0; i < MAX_LIGHTS; i++) 
		{
			if(i < lights.size())
			{
			
				this.uniformLoader.loadVector3d(location_lightPosition[i], lights.get(i).getPosition());
				this.uniformLoader.loadVector3d(location_lightColour[i], lights.get(i).getColour());
				this.uniformLoader.loadVector3d(location_attenuation[i], lights.get(i).getAttenuation());
				
			}else
			{
				this.uniformLoader.loadVector3d(location_lightPosition[i], new Vector3f(0,0,0));
				this.uniformLoader.loadVector3d(location_lightColour[i], new Vector3f(0,0,0));
				this.uniformLoader.loadVector3d(location_attenuation[i], new Vector3f(1,0,0));
			}
		}
	}
	
	public void loadViewMatrix(EntityCamera camera)
	{
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		this.uniformLoader.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection)
	{
		this.uniformLoader.loadMatrix(location_projectionMatrix, projection);
	}
	
	
}
