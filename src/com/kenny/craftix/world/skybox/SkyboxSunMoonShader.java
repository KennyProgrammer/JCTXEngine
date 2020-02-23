package com.kenny.craftix.world.skybox;

import static com.kenny.craftix.client.shaders.ShaderType.FRAGMENT;
import static com.kenny.craftix.client.shaders.ShaderType.VERTEX;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.shaders.ShaderProgram;
import com.kenny.craftix.client.shaders.UniformLoader;
import com.kenny.craftix.utils.Timer;
import com.kenny.craftix.utils.math.Maths;

public class SkyboxSunMoonShader extends ShaderProgram
{

	private static final String VERTEX_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "sunMoonVS" + VERTEX;
	private static final String FRAGMENT_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "sunMoonFS" + FRAGMENT;
	
	/**This is a name of the shader for more information about, if happening error.*/
	private String shaderName = "[SkyboxSunMoon]";
	public Craftix cx;
	
	/**Compile and load all type of varible into a uniform.*/
	private UniformLoader uniformLoader = new UniformLoader();
	
	/**This is rotate speed for skybox cube*/
	private static float ROTATE_SPEED = 0.3f;

	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_fogColour;
	private int location_sunMoonCube;
	
    /**This is a varible for current rotation the skybox cube*/
    private float rotation = 0;
    /**Get actually game time for rotation skybox cube*/
    private Timer timer = new Timer();
	
	public SkyboxSunMoonShader() 
	{
		super(VERTEX_FILE_LOCATION, FRAGMENT_FILE_LOCATION);
	}

	@Override
	protected void getAllUniformLocation() 
	{
		this.location_projectionMatrix = super.getUniformLocation("projectionMatrix", shaderName);
		this.location_viewMatrix = super.getUniformLocation("viewMatrix", shaderName);
		this.location_fogColour = super.getUniformLocation("fogColour", shaderName);
		this.location_sunMoonCube = super.getUniformLocation("sunMoonCube", shaderName);
		//this.location_lowerLimit = super.getUniformLocation("lowerLimit", shaderName);
		//this.location_upperLimit = super.getUniformLocation("upperLimit", shaderName);
		
	}
	
	public void loadProjectionMatrix(Matrix4f projectionMatrix)
	{
		this.uniformLoader.loadMatrix(location_projectionMatrix, projectionMatrix);
	}
	
	public void loadViewMatrix(EntityCamera cameraIn) 
	{
		Matrix4f viewMatrix = Maths.createViewMatrix(cameraIn);
		 viewMatrix.m30 = 0;
	     viewMatrix.m31 = 0;
	     viewMatrix.m32 = 0;
	     rotation += ROTATE_SPEED * this.timer.getFrameTimeSeconds();
	     Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
	     this.uniformLoader.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void loadFogColour(float r, float g, float b)
    {
    	this.uniformLoader.loadVector3d(location_fogColour, new Vector3f(r, g, b));
    }
	
	public void connectTextureUnits(Craftix craftixIn)
	{
		this.uniformLoader.loadFloat(location_sunMoonCube, 0);
	}

	public void setRotationSpeedSkybox(float rotationSpeedSkybox)
    {
    	ROTATE_SPEED = rotationSpeedSkybox;
    }
    
    public void skyboxInPause(Craftix craftixIn)
    {
    	this.cx = craftixIn;
    	if(!this.cx.isGamePause)
    	{
    		this.setRotationSpeedSkybox(0.3f);
    	}
    	else
    	{
    		this.setRotationSpeedSkybox(0f);
    	}
    }
	
	@Override
	protected void bindAllAttributes() 
	{
		super.bindAttribute(0, "position");
	}

}
