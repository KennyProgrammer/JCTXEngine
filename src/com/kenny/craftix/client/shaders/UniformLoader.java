package com.kenny.craftix.client.shaders;

import java.nio.FloatBuffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.resources.StillWorking;
import com.kenny.craftix.client.settings.console.ConsoleErrorConfig;

public class UniformLoader
{
	private static final Logger LOGGER = LogManager.getLogger();
	/**This is location of current uniform.*/
	public int location;
	protected boolean used = false;
	/**This is create a matrixBuffer for uniform*/
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	private static final int NOT_FOUND = -1;
	/**This is a main initialization for shaders.*/
	
	protected int getUniformLocation(String uniformName)
	{
		int programId = GlHelper.glCreateProgram();
		this.location = GlHelper.glGetUniformLocation(programId, uniformName);
		if(this.location == NOT_FOUND)
		{
			LOGGER.error("Not found uniform variable!");
		}
		return this.location;
	}
	
	@StillWorking
	protected void checkIfNotUse()
	{	
		if(ConsoleErrorConfig.errorShaderNoFoundUniform == true)
		{
			if(this.used = false)
			{
				LOGGER.warn("Uniform with name: " + this.getLocation() + "not use.");
			}
		}
	}
	
	public void loadFloat(int location, float value)
	{
		this.location = location;
		GlHelper.glUniform1f(location, value);
		this.used = true;
	}
	
	public void loadVector4d(int location, Vector4f vector)
	{
		this.location = location;
		GlHelper.glUniform4f(location, vector.x, vector.y, vector.z, vector.w);
		this.used = true;
	}
	
	public void loadVector3d(int location, Vector3f vector)
	{
		this.location = location;
		GlHelper.glUniform3f(location, vector.x, vector.y, vector.z);
		this.used = true;
	}
	
	public void loadVector2d(int location, Vector2f vector)
	{
		this.location = location;
		GlHelper.glUniform2f(location, vector.x, vector.y);
		this.used = true;
	}
	
	public void loadInt(int location, int value)
	{
		this.location = location;
		GlHelper.glUniform1i(location, value);
		this.used = true;
	}
	
	
	public void loadBoolean(int location, boolean value)
	{
		this.location = location;
		float toLoad = 0;
		if(value)
		{
			toLoad = 1;
		}
		GlHelper.glUniform1f(location, toLoad);
		this.used = true;
	}
	
	public void loadMatrix(int location, Matrix4f matrix)
	{
		this.location = location;
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GlHelper.glUniformMatrix4(location, false, matrixBuffer);
		this.used = true;
	}
	
	public void loadSampler2d(int location, int texUnit) 
	{
		this.location = location;
		GlHelper.glUniform1i(location, texUnit);
		this.used = true;
	}
		
	
	public int getLocation()
	{
		return this.location;
	}
}
