package com.kenny.craftix.client.shaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.settings.console.ConsoleErrorConfig;

public abstract class ShaderProgram
{
	public static final Logger LOGGER = LogManager.getLogger(Craftix.getTitle());
	protected static final int NOT_FOUND = -1;
	/**Its a program ID for fragment and vertex shader*/
	public  int programID;
	/**This is a vertex shader ID*/
	private int vertexShaderID;
	/**This is a geometry shader ID*/
	public int geometryShaderID;
	/**This is a fragment shader ID*/
	private int fragmentShaderID;
	protected UniformLoader uniformLoader = new UniformLoader();

	/**
	 * Creates a Shader Program and attaching them with this program as an ID.
	 */
	public ShaderProgram(String vertexFile, String fragmentFile)
	{
		/**Here we load the shader and get varible type of shader*/
		this.vertexShaderID = loadShader(vertexFile, GlHelper.vertexShader());
		this.fragmentShaderID = loadShader(fragmentFile, GlHelper.fragmentShader());
		/**This we create a program for shaders*/
		this.programID = GlHelper.glCreateProgram();
		GlHelper.glAttachShader(programID, vertexShaderID);
		GlHelper.glAttachShader(programID, fragmentShaderID);
		this.bindAllAttributes();
		GlHelper.glLinkProgram(programID);
		GlHelper.glValidateProgram(programID);
		this.getAllUniformLocation();
	}
	
	protected int getUniformLocation(String uniformName, String shaderName)
	{
		this.uniformLoader.location = GlHelper.glGetUniformLocation(programID, uniformName);
		if(ConsoleErrorConfig.errorShaderNoFoundUniform == true)
		{
			if(this.uniformLoader.location == NOT_FOUND)
			{
				LOGGER.error("Not found uniform variable: " + uniformName + 
						", in shader: " + shaderName + ", Id: " + programID);
			}
		}
		return this.uniformLoader.location;
	}
	
	/**
	 * When we call this method we start use the shader program and can drawing,
	 * rendering all what we have.
	 */
	public void start()
	{
		GlHelper.glUseProgram(programID);
	}
	
	/**
	 * When we call this method we stop use the shader program.
	 */
	public void stop()
	{
		GlHelper.glUseProgram(0);
	}
	
	public void cleanUp()
	{
		this.stop();
		GlHelper.glDetachShader(programID, vertexShaderID);
		//GlHelper.glDetachShader(programID, geometryShaderID);
		GlHelper.glDetachShader(vertexShaderID, fragmentShaderID);
		GlHelper.glDeleteShader(vertexShaderID);
		//GlHelper.glDeleteShader(geometryShaderID);
		GlHelper.glDeleteShader(fragmentShaderID);
		GlHelper.glDeleteProgram(programID);
	}
	
	protected abstract void getAllUniformLocation();
	
	protected abstract void bindAllAttributes();
	
	/**
	 * If computer not supported shader version 330 and highter..
	 */
	protected void bindFragOutput(int attachment, String varibleName)
	{
		GL30.glBindFragDataLocation(programID, attachment, varibleName);
	}
	
	protected void bindAttribute(int attribute, String variableName)
	{
		GlHelper.glBindAttribLocation(programID, attribute, variableName);
	}
	
	
	/**
	 * Read shader from file and load it to the game.
	 * 
	 * @param file - shader file name.
	 * @param type - type our shader.
	 */
	 private static int loadShader(String file, int type)
	 {
	        StringBuilder shaderSource = new StringBuilder();
	        try
	        {
	        	InputStream in = Class.class.getResourceAsStream(file);
	            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	            String line;
	            while((line = reader.readLine())!=null)
	            {
	                shaderSource.append(line).append("//\n");
	            }
	            reader.close();
	        }
	        catch(IOException e)
	        {
	        	LOGGER.error("This file: " + file + "cound be read!");
	            e.printStackTrace();
	            System.exit(-1);
	        }
	        int shaderID = GlHelper.glCreateShader(type);
	        GlHelper.glShaderSource(shaderID, shaderSource);
	        GlHelper.glCompileShader(shaderID);
	        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
	        {
	            LOGGER.info(GlHelper.glGetShaderInfoLog(shaderID, 500));
	            LOGGER.error("Could not compile shader: " + file);
	            System.exit(-1);
	        }
	        return shaderID;
	    }
}
