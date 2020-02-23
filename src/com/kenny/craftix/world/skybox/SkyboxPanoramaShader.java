package com.kenny.craftix.world.skybox;

import static com.kenny.craftix.client.shaders.ShaderType.*;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.entity.EntityCamaraInMenu;
import com.kenny.craftix.client.shaders.ShaderProgram;
import com.kenny.craftix.client.shaders.UniformLoader;
import com.kenny.craftix.utils.Timer;
import com.kenny.craftix.utils.math.Maths;

public class SkyboxPanoramaShader extends ShaderProgram
{
	private static final String VERTEX_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "panoramaVS" + VERTEX;
	private static final String FRAGMENT_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "panoramaFS" + FRAGMENT;
	
	/**Compile and load all type of varible into a uniform.*/
	private UniformLoader uniformLoader = new UniformLoader();
	
	/**This is rotate speed for skybox-panorama cube*/
	private static float ROTATE_SPEED = 0.3f;
	
	/**This is all uniforms contains in shader (txt) file in res folder*/
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_fogColour;
    private int location_cubePanorama;
    private int location_lowerLimit;
    private int location_upperLimit;
	
    /**This is a varible for current rotation the skybox-panorama cube*/
    private float rotation = 0;
    /**Get actually game time for rotation skybox cube*/
    private Timer timer = new Timer();
    
    /**This is a name of the shader for more information about, if happening error.*/
	private String shaderName = "[PanoramaShader]";
    
	public SkyboxPanoramaShader() 
	{
		super(VERTEX_FILE_LOCATION, FRAGMENT_FILE_LOCATION);
	}

	@Override
	protected void getAllUniformLocation() 
	{
		this.location_projectionMatrix = super.getUniformLocation("projectionMatrix", shaderName);
        this.location_viewMatrix = super.getUniformLocation("viewMatrix", shaderName);
        this.location_fogColour = super.getUniformLocation("fogColour", shaderName);
        this.location_cubePanorama = super.getUniformLocation("cubeMapPanorama", shaderName);
        this.location_lowerLimit = super.getUniformLocation("lowerLimit", shaderName);
        this.location_upperLimit = super.getUniformLocation("upperLimit", shaderName);
	}
	
	public void setRotationSpeedSkybox(float rotationSpeedSkybox)
	{
		ROTATE_SPEED = rotationSpeedSkybox;
	}
	 
	public void loadProjectionMatrix(Matrix4f projectionMatrix)
	{
		this.uniformLoader.loadMatrix(location_projectionMatrix, projectionMatrix);
	}
	
	public void loadViewMatrix(EntityCamaraInMenu camera)
	{
		 Matrix4f matrix = Maths.createViewMatrix(camera);
	     matrix.m30 = 0;
	     matrix.m31 = 0;
	     matrix.m32 = 0;
	     rotation += ROTATE_SPEED * this.timer.getFrameTimeSeconds();
	     Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(0, 1, 0), matrix, matrix);
	     this.uniformLoader.loadMatrix(location_viewMatrix, matrix);
	}
	
	public void loadLowerLimit(float lowerLimit)
	{
		this.uniformLoader.loadFloat(location_lowerLimit, lowerLimit);
	}
	
	public void loadUpperLimit(float upperLimit)
	{
		this.uniformLoader.loadFloat(location_upperLimit, upperLimit);
	}
	
	public void loadFogColour(float r, float g, float b)
	{
		this.uniformLoader.loadVector3d(location_fogColour, new Vector3f(r, g, b));
	}
	
	public void connectTextureUnits() 
	{
		this.uniformLoader.loadInt(location_cubePanorama, 0);
	}

	@Override
	protected void bindAllAttributes() 
	{
		super.bindAttribute(0, "position");
	}

}
