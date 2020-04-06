package CTXEngine.Graphics;

import static CTXEngine.Core.CoreBase.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector4f;
import org.joml.Vector4i;

import CTXEngine.Graphics.GL.GLShader;

/**
 * In computer graphics, a shader is a type of computer program that was originallyused for shading (the production of appropriate levels of light, darkness, and color
 * within an image), but which now performs a variety of specialized functions in various
 * fields of computer graphics special effects, or does video post-processing unrelated
 * to shading, or even performs functions unrelated to graphics at all.
 */
public abstract class Shader 
{	
	/**
	 * Shader library or Shader Manager, little provider to easy create and load
	 * shaders for game programmers.
	 */
	static class ShaderLibrary
	{
		/**Map with shaders, stored in kinda this library.*/
		private Map<String, Shader> shaderMap = new LinkedHashMap<String, Shader>();
		
		/**
		 * Add new shader object to shader map.
		 */
		public void add(final Shader shader)
		{
			String name = shader.getName();
			this.add(name, shader);
		}
		
		/**
		 * Add new shader object to shader map.
		 */
		public void add(final String name, final Shader shader)
		{
			CTX_ENGINE_ASSERT(!this.isExists(name),
					"This shader with name " + name + " already exist!");
			this.shaderMap.put(name, shader);
		}
		
		/**
		 * Load added shaders.
		 */
		public Shader load(final String path)
		{
			Shader shader = Shader.create(path);
			this.add(shader);
			return shader;
		}
		
		/**
		 * Load added shaders, with name and path.
		 */
		public Shader load(final String name, final String path)
		{
			Shader shader = Shader.create(path);
			this.add(name, shader);
			return shader;
		}

		/**
		 * Return name of shader from map.
		 */
		public Shader getShaderByName(final String name)
		{
			CTX_ENGINE_ASSERT(this.isExists(name),
					"This shader with name " + name + " not exist!");
			return this.shaderMap.get(name);
		}
		
		/**
		 * Returns true if shader with name 'name' is contains in map.
		 */
		public final boolean isExists(final String name)
		{
			return this.shaderMap.containsKey(name);
		}
	};
	
	/**
	 * Create new shader with input only one.glsl / .hlsl file where be stored
	 * all vertex, fragment, or geometry shaders, after it load string and link
	 * program.
	 */
	public static Shader create(final String glslSrc)
	{
		switch (RenderEngine.getAPI().get())
		{
			case "none":
				CTX_ENGINE_ASSERT(false, "Can't create shader, because render api not define!");
					return null;
			case "opengl":
				return new GLShader(glslSrc);
			case "vulcan":
				CTX_ENGINE_ASSERT(false, "Can't create shader, because vulcan api not support yet!");
					return null;
			case "directx":
				CTX_ENGINE_ASSERT(false, "Can't create shader, because directx api not support yet!");
					return null;
		}

		return null;
	}
	
	/**
	 * Create new shader with input only one .glsl / .hlsl file where be stored
	 * all vertex, fragment, or geometry shaders, after it load string and link
	 * 	program.
	 */
	public static Shader create(final String nameIn, final String glslSrc)
	{
		switch (RenderEngine.getAPI().get())
		{
			case "none":
				CTX_ENGINE_ASSERT(false, "Can't create shader, because render api not define!");
					return null;
			case "opengl":
				return new GLShader(nameIn, glslSrc);
			case "vulcan":
				CTX_ENGINE_ASSERT(false, "Can't create shader, because vulcan api not support yet!");
					return null;
			case "directx":
				CTX_ENGINE_ASSERT(false, "Can't create shader, because directx api not support yet!");
					return null;
		}

		return null;
	}
	
	/**
	 * Create new shader with input vertex and fragment shaders
	 * after it load string and link program.
	 */
	public static Shader create(final String nameIn, final String vsSrc, 
			final String  fsSrc)
	{
		switch (RenderEngine.getAPI().get())
		{
			case "none":
				CTX_ENGINE_ASSERT(false, "Can't create shader, because render api not define!");
					return null;
			case "opengl":
				return new GLShader(nameIn, vsSrc, fsSrc);
			case "vulcan":
				CTX_ENGINE_ASSERT(false, "Can't create shader, because vulcan api not support yet!");
					return null;
			case "directx":
				CTX_ENGINE_ASSERT(false, "Can't create shader, because directx api not support yet!");
					return null;
		}

		return null;
	}
	
	/**Start to use program.*/
	public abstract void bind();
	/**Stop to use program.*/
	public abstract void unBind();
	/**Load matrix 4x4 into to shader uniform.*/
	public abstract void setUniformMat4(final String name, final Matrix4f matrix4x4);
	/**Load matrix 3x3 into to shader uniform.*/
	public abstract void setUniformMat3(final String name, final Matrix3f matrix3x3);
	/**Load vector 4 into to shader uniform.*/
	public abstract void setUniformVec4(final String name, final Vector4f vector4);
	/**Load vector 3 into to shader uniform.*/
	public abstract void setUniformVec3(final String name, final Vector3f vector3);
	/**Load vector 2 into to shader uniform.*/
	public abstract void setUniformVec2(final String name, final Vector2f vector2);
	/**Load vector 1 into to shader uniform.*/
	public abstract void setUniformVec1(final String name, final float vector);
	/**Load float into to shader uniform.*/
	public abstract void setUniformFloat(final String name, float value);
	/**Load float 2 into to shader uniform.*/
	public abstract void setUniformFloat2(final String name, float x, float y);
	/**Load float 2 from vec2 into shader uniform.*/
	public abstract void setUniformFloat2(final String name, final Vector2f values);
	/**Load float 3  into to shader uniform.*/
	public abstract void setUniformFloat3(final String name, float x, float y, float z);
	/**Load float 3 from vec2 into shader uniform.*/
	public abstract void setUniformFloat3(final String name, final Vector3f values);
	/** float 4  into to shader uniform.*/
	public abstract void setUniformFloat4(final String name, final float x, final float y, final float z, final float w);
	/**Load float 2 from vec4 into shader uniform.*/
	public abstract void setUniformFloat4(final String name, final Vector4f values);
	/**Load int from vec4 into shader uniform.*/
	public abstract void setUniformInt(final String name, int value);
	/**Load int from vec4 into shader uniform.*/
	public abstract void setUniformIntv(final String name, int size, int value);
	/**Load int 2 from vec4 into shader uniform.*/
	public abstract void setUniformInt2(final String name, final Vector2i values);
	/**Load int 2 into shader uniform.*/
	public abstract void setUniformInt2(final String name, int x, int y);
	/**Load int 3 from vec4 into shader uniform.*/
	public abstract void setUniformInt3(final String name, final Vector3i values);
	/**Load int 3 into shader uniform.*/
	public abstract void setUniformInt3(final String name, int x, int y, int z);
	/**Load int 4 from vec4 into shader uniform.*/
	public abstract void setUniformInt4(final String name, final Vector4i values);
	/**Load int 4 into shader uniform.*/
	public abstract void setUniformInt4(final String name, int x, int y, int z, int w);
	/**Load boolean into to shader uniform.*/
	public abstract void setUniformBoolean(final String name, final boolean value);
	/**Return name of this shader.*/
	public abstract String getName();

}
