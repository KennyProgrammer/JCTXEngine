package com.kenny.craftix.client.shaders;

import static com.kenny.craftix.client.shaders.ShaderType.*;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.entity.EntityCamaraInMenu;
import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.entity.Light;
import com.kenny.craftix.utils.math.Maths;

public class GameObjectShader extends ShaderProgram
{
	private static final String VERTEX_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "gameObjectVS" + VERTEX;
	private static final String FRAGMENT_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "gameObjectFS" + FRAGMENT;
	
	/**Compile and load all type of varible into a uniform.*/
	private UniformLoader uniformLoader = new UniformLoader();
	private boolean isSupportedVersion330;
	/**This is a name of the shader for more information about, if happening error.*/
	private String shaderName = "[GameObjectShader]";
	
	/**This is the maximum number of colors available in the world.*/
	private static final int MAX_LIGHTS = 4;
	
	/**This is all uniforms contains in shader ".vert" and ".frag" files in res folder.*/
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition[];
	private int location_lightColour[];
	private int location_attenuation[];
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_useFakeLighting;
	private int location_skyColour;
	private int location_numberOfRows;
	private int location_offset;
	private int location_blendFactor;
	private int location_plane;
	private int location_lightLevels;
	private int location_density;
	private int location_gradient;
	private int location_specularMap;
	@SuppressWarnings("unused")
	private int location_usesSpecularMap;
	private int location_textureSampler; /**Model Texture*/
	
	
	public GameObjectShader() 
	{
		super(VERTEX_FILE_LOCATION, FRAGMENT_FILE_LOCATION);
		
	}

	@Override
	protected void bindAllAttributes() 
	{
		/**
		 * If computer not supported shaders version 330 or highter.
		 */
		this.isSupportedVersion330 = true;
		if(!isSupportedVersion330)
		{
			super.bindFragOutput(0, "out_Colour");
			super.bindFragOutput(1, "out_BrightColour");
		}
	
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocation() 
	{
		this.location_transformationMatrix = getUniformLocation("transformationMatrix", shaderName);
		this.location_projectionMatrix = getUniformLocation("projectionMatrix", shaderName);
		this.location_viewMatrix = getUniformLocation("viewMatrix", shaderName);
		this.location_specularMap = getUniformLocation("specularMap", shaderName);
		this.location_usesSpecularMap = getUniformLocation("usesSpecularMap", shaderName);
		this.location_textureSampler = getUniformLocation("textureSampler", shaderName);
		this.location_shineDamper = getUniformLocation("shineDamper", shaderName);
		this.location_reflectivity = getUniformLocation("reflectivity", shaderName);
		this.location_useFakeLighting = getUniformLocation("useFakeLighting", shaderName);
		this.location_skyColour = getUniformLocation("skyColour", shaderName);
		this.location_numberOfRows = getUniformLocation("numberOfRows", shaderName);
		this.location_offset = getUniformLocation("offset", shaderName);
		this.location_blendFactor = getUniformLocation("blendFactor", shaderName);
		this.location_plane = getUniformLocation("plane", shaderName);
		this.location_lightLevels = getUniformLocation("levels", shaderName);
		this.location_density = getUniformLocation("density", shaderName);
		this.location_gradient = getUniformLocation("gradient", shaderName);
		
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
		//this.uniformLoader.loadInt(location_shadowMap, 5);
		this.uniformLoader.loadInt(location_textureSampler, 0);
		this.uniformLoader.loadInt(location_specularMap, 1);
	}
	
	
	public void loadUseSpecularMap(boolean usesSpecularMap)
	{
		this.uniformLoader.loadBoolean(location_specularMap, usesSpecularMap);
	}
	
	public void loadDensity(float density)
	{
		this.uniformLoader.loadFloat(location_density, density);
	}
	
	public void loadGradient(float gradient)
	{
		this.uniformLoader.loadFloat(location_gradient, gradient);
	}
	
	public void loadLightLevels(float levels)
	{
		this.uniformLoader.loadFloat(location_lightLevels, levels);
	}
	
	public void loadPlane(Vector4f plane)
	{
		this.uniformLoader.loadVector4d(location_plane, plane);
	}
	
	public void loadBlendFactor(float blendFactor)
	{
		this.uniformLoader.loadFloat(location_blendFactor, blendFactor);
	}
	
	public void loadNumberOfRows(int numberOfRows)
	{
		this.uniformLoader.loadFloat(location_numberOfRows, numberOfRows);
	}
	
	public void loadOffset(float x, float y)
	{
		this.uniformLoader.loadVector2d(location_offset, new Vector2f(x,y));
	}
	
	public void loadSkyColour(float r, float g, float b)
	{
		this.uniformLoader.loadVector3d(location_skyColour, new Vector3f(r, g, b));
	}
	
	public void loadFakeLightingVarible(boolean useFake)
	{
		this.uniformLoader.loadBoolean(location_useFakeLighting, useFake);
	}
	
	public void loadShineVaribles(float damper, float reflectivity)
	{
		this.uniformLoader.loadFloat(location_shineDamper, damper);
		this.uniformLoader.loadFloat(location_reflectivity, reflectivity);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix)
	{
		this.uniformLoader.loadMatrix(location_transformationMatrix, matrix);
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
	
	public void loadViewMatrix(EntityCamaraInMenu camera)
	{
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		this.uniformLoader.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection)
	{
		this.uniformLoader.loadMatrix(location_projectionMatrix, projection);
	}

}
