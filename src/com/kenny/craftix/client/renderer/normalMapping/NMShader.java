package com.kenny.craftix.client.renderer.normalMapping;

import static com.kenny.craftix.client.shaders.ShaderType.*;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.entity.Light;
import com.kenny.craftix.client.shaders.ShaderProgram;
import com.kenny.craftix.client.shaders.UniformLoader;
import com.kenny.craftix.utils.math.Maths;

public class NMShader extends ShaderProgram
{
	private static final int MAX_LIGHTS = 4;
	
	private static final String VERTEX_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "normalMapVS" + VERTEX;
	private static final String FRAGMENT_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "normalMapFS" + FRAGMENT;
	
	/**This is a name of the shader for more information about, if happening error.*/
	private String shaderName = "[NMShader]";
	
	/**Compile and load all type of varible into a uniform.*/
	private UniformLoader uniformLoader = new UniformLoader();
	
	/**This is all uniforms contains in shader (txt) file in res folder*/
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPositionEyeSpace[];
	private int location_lightColour[];
	private int location_attenuation[];
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_skyColour;
	private int location_numberOfRows;
	private int location_offset;
	private int location_plane;
	private int location_normalMap;
	private int location_specularMap;
	@SuppressWarnings("unused")
	private int location_usesSpecularMap;
	private int location_modelTexture;
	private int location_density;
	private int location_gradient;

	public NMShader() 
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
		super.bindAttribute(1, "textureCoordinates");
		super.bindAttribute(2, "normal");
		super.bindAttribute(3, "tangent");
	}

	@Override
	protected void getAllUniformLocation() 
	{
		this.location_transformationMatrix = super.getUniformLocation("transformationMatrix", shaderName);
		this.location_projectionMatrix = super.getUniformLocation("projectionMatrix", shaderName);
		this.location_viewMatrix = super.getUniformLocation("viewMatrix", shaderName);
		this.location_shineDamper = super.getUniformLocation("shineDamper", shaderName);
		this.location_reflectivity = super.getUniformLocation("reflectivity", shaderName);
		this.location_skyColour = super.getUniformLocation("skyColour", shaderName);
		this.location_numberOfRows = super.getUniformLocation("numberOfRows", shaderName);
		this.location_offset = super.getUniformLocation("offset", shaderName);
		this.location_plane = super.getUniformLocation("plane", shaderName);
		this.location_modelTexture = super.getUniformLocation("modelTexture", shaderName);
		this.location_specularMap = super.getUniformLocation("specularMap", shaderName);
		this.location_usesSpecularMap = super.getUniformLocation("usesSpecularMap", shaderName);
		this.location_normalMap = super.getUniformLocation("normalMap", shaderName);
		this.location_density = super.getUniformLocation("density", shaderName);
		this.location_gradient = super.getUniformLocation("gradient", shaderName);
		
		this.location_lightPositionEyeSpace = new int[MAX_LIGHTS];
		this.location_lightColour = new int[MAX_LIGHTS];
		this.location_attenuation = new int[MAX_LIGHTS];
		for(int i=0;i<MAX_LIGHTS;i++)
		{
			this.location_lightPositionEyeSpace[i] = super.getUniformLocation("lightPositionEyeSpace[" + i + "]", shaderName);
			this.location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]", shaderName);
			this.location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]", shaderName);
		}
	}
	
	protected void connectTextureUnits()
	{
		this.uniformLoader.loadInt(location_modelTexture, 0);
		this.uniformLoader.loadInt(location_normalMap, 1);
		this.uniformLoader.loadInt(location_specularMap, 2);
	}
	
	public void loadDensity(float density)
	{
		this.uniformLoader.loadFloat(location_density, density);
	}
	
	public void loadGradient(float gradient)
	{
		this.uniformLoader.loadFloat(location_gradient, gradient);
	}
	
	public void loadUseSpecularMap(boolean usesSpecularMap)
	{
		this.uniformLoader.loadBoolean(location_specularMap, usesSpecularMap);
	}
	
	protected void loadClipPlane(Vector4f plane)
	{
		this.uniformLoader.loadVector4d(location_plane, plane);
	}
	
	protected void loadNumberOfRows(int numberOfRows)
	{
		this.uniformLoader.loadFloat(location_numberOfRows, numberOfRows);
	}
	
	protected void loadOffset(float x, float y)
	{
		this.uniformLoader.loadVector2d(location_offset, new Vector2f(x,y));
	}
	
	public void loadSkyColour(float r, float g, float b)
	{
		this.uniformLoader.loadVector3d(location_skyColour, new Vector3f(r,g,b));
	}
	
	protected void loadShineVariables(float damper,float reflectivity)
	{
		this.uniformLoader.loadFloat(location_shineDamper, damper);
		this.uniformLoader.loadFloat(location_reflectivity, reflectivity);
	}
	
	protected void loadTransformationMatrix(Matrix4f matrix)
	{
		this.uniformLoader.loadMatrix(location_transformationMatrix, matrix);
	}
	
	protected void loadLights(List<Light> lights, Matrix4f viewMatrix)
	{
		for(int i=0;i<MAX_LIGHTS;i++)
		{
			if(i<lights.size())
			{
				this.uniformLoader.loadVector3d(location_lightPositionEyeSpace[i], getEyeSpacePosition(lights.get(i), viewMatrix));
				this.uniformLoader.loadVector3d(location_lightColour[i], lights.get(i).getColour());
				this.uniformLoader.loadVector3d(location_attenuation[i], lights.get(i).getAttenuation());
			}else{
				this.uniformLoader.loadVector3d(location_lightPositionEyeSpace[i], new Vector3f(0, 0, 0));
				this.uniformLoader.loadVector3d(location_lightColour[i], new Vector3f(0, 0, 0));
				this.uniformLoader.loadVector3d(location_attenuation[i], new Vector3f(1, 0, 0));
			}
		}
	}
	
	public void loadViewMatrix(Matrix4f viewMatrix)
	{
		this.uniformLoader.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void loadViewMatrix(EntityCamera camera)
	{
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		this.uniformLoader.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	protected void loadProjectionMatrix(Matrix4f projection)
	{
		this.uniformLoader.loadMatrix(location_projectionMatrix, projection);
	}
	
	private Vector3f getEyeSpacePosition(Light light, Matrix4f viewMatrix)
	{
		Vector3f position = light.getPosition();
		Vector4f eyeSpacePos = new Vector4f(position.x,position.y, position.z, 1f);
		Matrix4f.transform(viewMatrix, eyeSpacePos, eyeSpacePos);
		return new Vector3f(eyeSpacePos);
	}
	

}
