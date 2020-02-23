package com.kenny.craftix.client.renderer.postEffects.colour;

import static com.kenny.craftix.client.shaders.ShaderType.*;

import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.shaders.ShaderProgram;
import com.kenny.craftix.client.shaders.UniformLoader;

public class ColourShader extends ShaderProgram 
{
	private static final String VERTEX_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "colourVS" + VERTEX;
	private static final String FRAGMENT_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "colourFS" + FRAGMENT;
	
	/**This is a name of the shader for more information about, if happening error.*/
	private String shaderName = "[ColourShader]";
	
	/**Compile and load all type of varible into a uniform.*/
	private UniformLoader uniformLoader = new UniformLoader();
	
	private int location_contrast;
	private int location_brightness;
	private int location_saturation;
	
	public ColourShader() 
	{
		super(VERTEX_FILE_LOCATION, FRAGMENT_FILE_LOCATION);
	}

	@Override
	protected void getAllUniformLocation() 
	{	
		this.location_contrast = super.getUniformLocation("contrast", shaderName);
		this.location_brightness = super.getUniformLocation("brightness", shaderName);
		this.location_saturation = super.getUniformLocation("saturation", shaderName);
	}

	@Override
	protected void bindAllAttributes() 
	{
		super.bindAttribute(0, "position");
	}
	
	public void loadContrast(float contrastIn)
	{
		this.uniformLoader.loadFloat(location_contrast, contrastIn);
	}
	
	public void loadBrightness(float brightnessIn)
	{
		this.uniformLoader.loadFloat(location_brightness, brightnessIn);
	}
	
	public void loadSaturation(float saturationIn)
	{
		this.uniformLoader.loadFloat(location_saturation, saturationIn);
	}

}
