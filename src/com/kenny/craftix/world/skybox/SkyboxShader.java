package com.kenny.craftix.world.skybox;

import static com.kenny.craftix.client.shaders.ShaderType.*;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.entity.EntityCamaraInMenu;
import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.shaders.ShaderProgram;
import com.kenny.craftix.client.shaders.UniformLoader;
import com.kenny.craftix.utils.Timer;
import com.kenny.craftix.utils.math.Maths;
 
public class SkyboxShader extends ShaderProgram
{
 
	private static final String VERTEX_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "skyboxVS" + VERTEX;
	private static final String FRAGMENT_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "skyboxFS" + FRAGMENT;
     
	/**This is a name of the shader for more information about, if happening error.*/
	private String shaderName = "[SkyboxShader]";
	
	/**Compile and load all type of varible into a uniform.*/
	private UniformLoader uniformLoader = new UniformLoader();
	
	/**This is rotate speed for skybox cube*/
	private static float ROTATE_SPEED = 0.3f;
	public Craftix cx;
	
	/**This is all uniforms contains in shader (txt) file in res folder*/
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_fogColour;
    private int location_cubeMap;
    private int location_cubeMap2;
    private int location_blendFactor;
    private int location_lowerLimit;
    private int location_upperLimit;
    
    /**This is a varible for current rotation the skybox cube*/
    private float rotation = 0;
    /**Get actually game time for rotation skybox cube*/
    private Timer timer = new Timer();
    
    public SkyboxShader() 
    {
        super(VERTEX_FILE_LOCATION, FRAGMENT_FILE_LOCATION);
    }
    
    @Override
    protected void getAllUniformLocation() 
    {
        this.location_projectionMatrix = super.getUniformLocation("projectionMatrix", shaderName);
        this.location_viewMatrix = super.getUniformLocation("viewMatrix", shaderName);
        this.location_fogColour = super.getUniformLocation("fogColour", shaderName);
        this.location_blendFactor = super.getUniformLocation("blendFactor", shaderName);
        this.location_cubeMap = super.getUniformLocation("cubeMap", shaderName);
        this.location_cubeMap2 = super.getUniformLocation("cubeMap2", shaderName);
        this.location_lowerLimit = super.getUniformLocation("lowerLimit", shaderName);
        this.location_upperLimit = super.getUniformLocation("upperLimit", shaderName);
        
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
    
    public void loadProjectionMatrix(Matrix4f matrix)
    {
        this.uniformLoader.loadMatrix(location_projectionMatrix, matrix);
    }
 
    public void loadViewMatrix(EntityCamera camera)
    {
        Matrix4f matrix = Maths.createViewMatrix(camera);
        matrix.m30 = 0;
        matrix.m31 = 0;
        matrix.m32 = 0;
        rotation += ROTATE_SPEED * this.timer.getFrameTimeSeconds();
        Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(0, 1, 0), matrix, matrix);
        this.uniformLoader.loadMatrix(location_viewMatrix, matrix);
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
   
    
    public void loadFogColour(float r, float g, float b)
    {
    	this.uniformLoader.loadVector3d(location_fogColour, new Vector3f(r, g, b));
    }
    
    public void connectTextureUnits(Craftix craftixIn)
    {
    	this.cx = craftixIn;
    	if(!this.cx.status.isGameMenu() && this.cx.status.isGameWorld())
    	{
    		this.uniformLoader.loadInt(location_cubeMap, 0);
        	this.uniformLoader.loadInt(location_cubeMap2, 1);
    	}
    	else
    	{
    		
    	}
    }
    
    public void loadBlendFactor(float blend)
    {
    	this.uniformLoader.loadFloat(location_blendFactor, blend);
    }
     
    public void loadLowerLimit(float lowerLimit)
    {
    	this.uniformLoader.loadFloat(location_lowerLimit, lowerLimit);
    }
    
    public void loadUpperLimit(float upperLimit)
    {
    	this.uniformLoader.loadFloat(location_upperLimit, upperLimit);
    }
 
    @Override
    protected void bindAllAttributes() 
    {
        super.bindAttribute(0, "position");
    }
 
}