package com.kenny.craftix.client.text.render;

import static com.kenny.craftix.client.shaders.ShaderType.*;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.shaders.ShaderProgram;
import com.kenny.craftix.client.shaders.UniformLoader;

public class FontShader extends ShaderProgram
{

	private static final String VERTEX_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "fontVS" + VERTEX;
	private static final String FRAGMENT_FILE_LOCATION = 
			ResourceLocation.SHADER_FOLDER + "fontFS" + FRAGMENT;
	
	/**Compile and load all type of varible into a uniform.*/
	private UniformLoader uniformLoader = new UniformLoader();
	
	/**This is a name of the shader for more information about, if happening error.*/
	private String shaderName = "[FontShader]";
	
	/**This is all uniforms contains in shader (txt) file in res folder*/
	private int location_translation;
	private int location_colour;
	private int location_width;
	private int location_edge;
	private int location_borderWidth;
	private int location_borderEdge;
	private int location_outlineColour;
	private int location_offset;
	
	public FontShader() 
	{
		super(VERTEX_FILE_LOCATION, FRAGMENT_FILE_LOCATION);
	}

	@Override
	protected void getAllUniformLocation() 
	{
		this.location_colour = getUniformLocation("colour", shaderName);
		this.location_translation = getUniformLocation("translation", shaderName);
		this.location_width = getUniformLocation("width", shaderName);
		this.location_edge = getUniformLocation("edge", shaderName);
		this.location_borderWidth = getUniformLocation("borderWidth", shaderName);
		this.location_borderEdge = getUniformLocation("borderEdge", shaderName);
		this.location_outlineColour = getUniformLocation("outlineColour", shaderName);
		this.location_offset = getUniformLocation("offset", shaderName);
	}

	@Override
	protected void bindAllAttributes() 
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
	
	protected void loadOutlineColour(Vector3f outColour)
	{
		this.uniformLoader.loadVector3d(location_outlineColour, outColour);
	}
	
	protected void loadWidth(float width)
	{
		this.uniformLoader.loadFloat(location_width, width);
	}
	
	protected void loadEdge(float edge)
	{
		this.uniformLoader.loadFloat(location_edge, edge);
	}
	
	protected void loadOffset(Vector2f offset)
	{
		this.uniformLoader.loadVector2d(location_offset, offset);
	}
	
	protected void loadBorderWidth(float borderWidth)
	{
		this.uniformLoader.loadFloat(location_borderWidth, borderWidth);
	}
	
	protected void loadBorderEdge(float borderEdge)
	{
		this.uniformLoader.loadFloat(location_borderEdge, borderEdge);
	}
	
	protected void loadColour(Vector3f colour)
	{
		this.uniformLoader.loadVector3d(location_colour, colour);
	}
	
	protected void loadTranslation(Vector2f translation)
	{
		this.uniformLoader.loadVector2d(location_translation, translation);
	}
}
