package CTXEngine.Graphics.GL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector4f;
import org.joml.Vector4i;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL40.*;

import CTXEngine.Graphics.BufferHelper;
import CTXEngine.Graphics.Shader;

import static CTXEngine.Core.CoreBase.*;
import static CTXEngine.Core.SimplePrint.*;

/**
 * A Program Object represents fully processed executable code, in the OpenGL
 * Shading Language, for one or more Shader stages. A program object is an
 * object in OpenGL, but it does not use the standard OpenGL Object model.
 */
public class GLShader extends Shader
{
	/**Name of this shader.*/
	private String name;
	/**This is id of shader / shader program.*/
	private int id;
	/**This is id of created vertex shader.*/
	private int vertexId;
	/**This is id of created fragment shader.*/
	private int fragmentId;
	/**This is id of created geometry shader.*/
	private int geometryId;
	/**This is id of created tess control shader.*/
	private int tessCId;
	/**This is id of created tess e shader.*/
	private int tessEId;

	private boolean bound;
	
	/**Send if uniform not used or not bound to this shader.*/
	protected  boolean sendMsgWarnFloat, sendMsgWarnFloat2, sendMsgWarnFloat3, sendMsgWarnFloat4,
		sendMsgWarnFloatv2, sendMsgWarnFloatv3, sendMsgWarnFloatv4,
		sendMsgWarnMat2, sendMsgWarnMat3, sendMsgWarnMat4,
		sendMsgWarnBoolean;

	/**
	 * Create new shader with input only one .glsl file where be stored
	 * all vertex, fragment, or geometry shaders, after it load string and link
	 * program.
	 */
	public GLShader(final String glslSrc) 
	{
		String shaderData = this.readFile(glslSrc);
		Map<Integer, String> shaderSources = this.preProcess(shaderData);
		this.compile(shaderSources);
		
		this.name = "unknown";
	}
	
	/**
	 * Create new shader with input only one .glsl file where be stored
	 * all vertex, fragment, or geometry shaders, after it load string and link
	 * program.
	 */
	public GLShader(final String name, final String glslSrc)
	{
		this(glslSrc);
		this.name = name;
	}
	
	/**
	 * This constructor basically create GLShader.
	 */
	public GLShader(final String name, final String vsSrc, final String fsSrc)
	{
		this.name = name;
		Map<Integer, String> sources = new HashMap<Integer, String>();
		sources.put(1, this.readFile(vsSrc));
		sources.put(2, this.readFile(fsSrc));
		this.compile(sources);
	}
	
	/**
	 * This constructor basically create GLShader.
	 */
	public GLShader(final String name, final String vsSrc,
		final String fsSrc, final String gsSrc)
	{
		this.name = name;
		Map<Integer, String> sources = new HashMap<Integer, String>();
		sources.put(1, this.readFile(vsSrc));
		sources.put(2, this.readFile(fsSrc));
		sources.put(3, this.readFile(gsSrc));
		this.compile(sources);
		
		//doesn't work, because method compile() now support only 2 shaders.
	}
	
	/**
	 *	Basic Java version of C++ de constructor.
	 */
	public void destroyGLShader()
	{
		GLHelper.hglDetachShader(this.id, this.vertexId);
		GLHelper.hglDeleteShader(this.vertexId);

		if (this.fragmentId != 0)
		{
			GLHelper.hglDetachShader(this.id, this.fragmentId);
			GLHelper.hglDeleteShader(this.fragmentId);
		}

		if (this.geometryId != 0)
		{
			GLHelper.hglDetachShader(this.id, this.geometryId);
			GLHelper.hglDeleteShader(this.geometryId);
		}

		if (this.tessCId != 0)
		{
			GLHelper.hglDetachShader(this.id, this.tessCId);
			GLHelper.hglDeleteShader(this.tessCId);
		}

		if (this.tessEId != 0)
		{
			GLHelper.hglDetachShader(this.id, this.tessEId);
			GLHelper.hglDeleteShader(this.tessEId);
		}

		GLHelper.hglDeleteProgram(this.id);
	}
	
	/**
	 * Pre processor checks custom commands starts with # . Check what type of Open GL 
	 * shader is readed. For example in one .glsl file may
	 * be 4 different types of shaders 'Vertex, Fragment, Geometry, Tessaletion'.
	 */
	public Map<Integer, String> preProcess(final String path)
	{
		Map<Integer, String> shaders = new HashMap<Integer, String>();
		int shaderCount = 2, currentShaderCount = 0;
		String[] derectiveTypes = {"#type", "#endtype"};
		int derectiveTypeLengths[] = { derectiveTypes[0].length(), derectiveTypes[1].length()};
		int pos = -1;

		//while cycle when runs all read and save code for 'shaderCount' shaders.
		while(currentShaderCount < shaderCount)
		{
			pos = path.indexOf(derectiveTypes[0], pos + 1);
			currentShaderCount++;
			int spaces = 0;
			char ch = path.charAt(pos + derectiveTypeLengths[0] + spaces);
			spaces++;
			while(ch == ' ')
			{
				ch = path.charAt(pos + derectiveTypeLengths[0] + spaces);
				spaces++;
				
				if(ch != ' ') spaces--;
			}
			pos = pos + spaces + derectiveTypeLengths[0]; //posType
			char ch2 = path.charAt(pos);
			int nextLinePos = pos + 7;
			CTX_ENGINE_ASSERT((ch == 'v' && path.charAt(pos + 6) == ' ') || (ch == 'g' || ch == 'f') && path.charAt(pos + 8) == ' ',
					"After shader word type should not be spaces!");
			if     (ch2 == 'v')               
			{ 
				pos = path.indexOf('#', nextLinePos + 1);
				shaders.put(1, path.substring(nextLinePos, pos));
				pos = path.indexOf('#', pos);
				CTX_ENGINE_ASSERT((pos + 8) == ' ', "After '" + derectiveTypes[1] + "' should not be spaces!");
				nextLinePos = pos + 8;
			} else if(ch2 == 'f') {
				nextLinePos = pos + 9;
				pos = path.indexOf('#', nextLinePos + 1);
				shaders.put(2, path.substring(nextLinePos, pos));
				pos = path.indexOf('#', pos);
				CTX_ENGINE_ASSERT((pos + 10) == ' ', "After '" + derectiveTypes[1] + "' should not be spaces!");
				nextLinePos = pos + 10;
			} else if(ch == 'g') {} 
			  else if(ch == 't') {}
			pos = nextLinePos; // + 1 = '#;
		}
		
		return shaders;
	}
	
	/**
	 * Read shader file.
	 */
	public String readFile(final String file)
	{
		boolean appendSlashes = false;
		boolean returnInOneLine = false;
		StringBuilder shaderSource = new StringBuilder();
        try
        {
        	InputStream in = Class.class.getResourceAsStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = reader.readLine())!=null)
            {
                shaderSource.append(line);
                
                if(appendSlashes)    shaderSource.append("//");
                if(!returnInOneLine) shaderSource.append("\n");
            }
            reader.close();
            
            return shaderSource.toString();
        }
        catch(IOException e)
        {
        	System.out.println("This file '" + file + "' cound be read!");
            e.printStackTrace();
            Runtime.getRuntime().exit(-1);
        }
        
        return "[Reading Error]: This file '" + file + "' cound be read!";
	}

	/**
	 * Compile this open gl shader source.
	 */
	public void compile(Map<Integer, String> shaderSources)
	{
		int program = GLHelper.hglCreateProgram();

		List<Integer> glShaderIds = new ArrayList<Integer>(2);
		int shaderIdIdxs = 1;
		CTX_ENGINE_ASSERT(shaderSources.size() <= 2, "Engine support only 2 compare shaders!");
		
		for(int i = 0; i < shaderSources.size(); i++)
		{
			int type = i == 0 ? GL_VERTEX_SHADER : i == 1 ? GL_FRAGMENT_SHADER : -1;
			final String source = shaderSources.get(shaderIdIdxs);

			int shader = GLHelper.hglCreateShader(type);
			GLHelper.hglShaderSource(shader, source);
			GLHelper.hglCompileShader(shader);
			
			int isCompiled = 0;
			isCompiled = GLHelper.hglGetShaderi(shader, GL_COMPILE_STATUS);
			if(isCompiled == GL_FALSE)
			{
				int maxLength = 0;
				maxLength = GLHelper.hglGetShaderi(shader, GL_INFO_LOG_LENGTH);
				
				String infoLog = "";
				infoLog = GLHelper.hglGetShaderInfoLog(shader, maxLength);
				GLHelper.hglDeleteShader(shader);
				
				String sType = "";
				if (type == GL_VERTEX_SHADER) sType = "Vertex Shader";
				if (type == GL_FRAGMENT_SHADER) sType = "Fragment Shader";
				if (type == GL_GEOMETRY_SHADER) sType = "Geometry Shader";
				if (type == GL_TESS_CONTROL_SHADER) sType = "Tessellation Control Shader";
				if (type == GL_TESS_EVALUATION_SHADER) sType = "Tessellation Evaluation Shader";
			
				CTX_ENGINE_ERROR(sType + " can't compiled! \n" + infoLog);
				Runtime.getRuntime().exit(-1);
			}
			
			if (type == GL_VERTEX_SHADER) this.vertexId = GL_VERTEX_SHADER;
			if (type == GL_FRAGMENT_SHADER) this.fragmentId = GL_FRAGMENT_SHADER;
			if (type == GL_GEOMETRY_SHADER) this.geometryId = GL_GEOMETRY_SHADER;
			if (type == GL_TESS_CONTROL_SHADER) this.tessCId = GL_TESS_CONTROL_SHADER;
			if (type == GL_TESS_EVALUATION_SHADER) this.tessEId = GL_TESS_EVALUATION_SHADER;
			
			CTX_ENGINE_ASSERT(this.vertexId != GL_VERTEX_SHADER ? false : true, "Open cannot be without vertex shader!");

			GLHelper.hglAttachShader(program, shader);
			shaderIdIdxs++;
		}
		
		GLHelper.hglLinkProgram(program);

		int isLinked = 0;
		isLinked = GLHelper.hglGetProgrami(program, GL_LINK_STATUS);
		if(isLinked == GL_FALSE)
		{
			int maxLength = 0;
			maxLength = GLHelper.hglGetProgrami(program, GL_INFO_LOG_LENGTH);
			String infoLog = "";
			infoLog = GLHelper.hglGetProgramInfoLog(program, maxLength);
			
			GLHelper.hglDeleteProgram(program);

			for (int shaderId : glShaderIds)
			{
				GLHelper.hglDeleteShader(shaderId);
			}
			
			CTX_ENGINE_ERROR("Shader Program can't compiled! \n{0}", infoLog);
			Runtime.getRuntime().exit(-1);
		}
		
		for (int shaderId : glShaderIds)
		{
			GLHelper.hglDetachShader(program, shaderId);
		}

		this.id = program; //may be all program
	}
	
	/**
	 * 	Start to use program, after this command can add uniforms,
	 *  and change the value in runtime.
	 */
	@Override
	public void bind() 
	{
		GLHelper.hglUseProgram(this.id);
		this.bound = true;
	}

	/**
	 * Stops to use program, after this command can't be change shaders.
     */
	@Override
	public void unBind() 
	{
		GLHelper.hglUseProgram(0);
		this.bound = false;
	}

	/**
	 * Load matrix 4x4 into to shader uniform.
	 */
	@Override
	public void setUniformMat4(String name, Matrix4f matrix4x4) 
	{	
		int location = GLHelper.hglGetUniformLocation(this.id, name);
		GLHelper.hglUniformMatrix4fv(location, false, BufferHelper.createFlippedBuffer(matrix4x4));
	}

	/**
	 * Load matrix 3x3 into to shader uniform.
	 */
	@Override
	public void setUniformMat3(String name, Matrix3f matrix3x3) 
	{
		int location = GLHelper.hglGetUniformLocation(this.id, name);
		GLHelper.hglUniformMatrix3fv(location, false, BufferHelper.createFlippedBuffer(matrix3x3));
	}

	/**
	 * Load vector 4x4 into to shader uniform.
	 */
	@Override
	public void setUniformVec4(String name, Vector4f vector4) 
	{
		int location = GLHelper.hglGetUniformLocation(this.id, name);
		GLHelper.hglUniform4fv(location, BufferHelper.createFlippedBuffer(vector4));
	}

	/**
	 * Load vector 3x3 into to shader uniform.
	 */
	@Override
	public void setUniformVec3(String name, Vector3f vector3) 
	{
		int location = GLHelper.hglGetUniformLocation(this.id, name);
		GLHelper.hglUniform3fv(location, BufferHelper.createFlippedBuffer(vector3));
	}

	/**
	 * Load vector 2x2 into to shader uniform.
	 */
	@Override
	public void setUniformVec2(String name, Vector2f vector2) 
	{
		int location = GLHelper.hglGetUniformLocation(this.id, name);
		GLHelper.hglUniform2fv(location, BufferHelper.createFlippedBuffer(vector2));
	}

	/**
	 * Load float into to shader uniform.
	 */
	@Override
	public void setUniformVec1(String name, float vector) 
	{
		int location = GLHelper.hglGetUniformLocation(this.id, name);
		GLHelper.hglUniform1fv(location, BufferHelper.toFloatBuffer(vector));
	}

	/**
	 * Load float into to shader uniform.
	 */
	@Override
	public void setUniformFloat(String name, float value) 
	{
		int location = GLHelper.hglGetUniformLocation(this.id, name);
		GLHelper.hglUniform1f(location, value);
	}

	/**
	 * Load 2 floats into to shader uniform.
	 */
	@Override
	public void setUniformFloat2(String name, float x, float y)
	{
		int location = GLHelper.hglGetUniformLocation(this.id, name);
		GLHelper.hglUniform2f(location, x, y);
	}

	/**
	 * Load 2 floats into to shader uniform.
	 */
	@Override
	public void setUniformFloat2(String name, Vector2f values) 
	{
		int location = GLHelper.hglGetUniformLocation(this.id, name);
		GLHelper.hglUniform2f(location, values.x, values.y);
	}

	/**
	 * Load 3 floats into to shader uniform.
	 */
	@Override
	public void setUniformFloat3(String name, float x, float y, float z) 
	{
		int location = GLHelper.hglGetUniformLocation(this.id, name);
		GLHelper.hglUniform3f(location, x, y, z);
	}

	/**
	 * Load 3 floats into to shader uniform.
	 */
	@Override
	public void setUniformFloat3(String name, Vector3f values) 
	{
		int location = GLHelper.hglGetUniformLocation(this.id, name);
		GLHelper.hglUniform3f(location, values.x, values.y, values.z);	
	}

	/**
	 * Load 4 floats into to shader uniform.
	 */
	@Override
	public void setUniformFloat4(String name, float x, float y, float z, float w) 
	{
		int location = GLHelper.hglGetUniformLocation(this.id, name);
		GLHelper.hglUniform4f(location, x, y, z, w);
	}

	/**
	 * Load 4 floats into to shader uniform.
	 */
	@Override
	public void setUniformFloat4(String name, Vector4f values) 
	{
		int location = GLHelper.hglGetUniformLocation(this.id, name);
		GLHelper.hglUniform4f(location, values.x, values.y, values.z, values.w);
	}

	/**
	 * Load int into to shader uniform.
	 */
	@Override
	public void setUniformInt(String name, int value) 
	{
		int location = GLHelper.hglGetUniformLocation(this.id, name);
		GLHelper.hglUniform1i(location, value);
	}

	/**
	 * Load int... into to shader uniform.
	 */
	@Override
	public void setUniformIntv(String name, int size, int value) 
	{
		int location = GLHelper.hglGetUniformLocation(this.id, name);
		GLHelper.hglUniform1iv(location, size, value);
	}

	/**
	 * Load 2 ints into to shader uniform.
	 */
	@Override
	public void setUniformInt2(String name, Vector2i values) 
	{
		int location = GLHelper.hglGetUniformLocation(this.id, name);
		GLHelper.hglUniform2iv(location, values.x, values.y);
	}

	/**
	 * Load 2 ints into to shader uniform.
	 */
	@Override
	public void setUniformInt2(String name, int x, int y)
	{
		int location = GLHelper.hglGetUniformLocation(this.id, name);
		GLHelper.hglUniform2iv(location, x, y);
	}

	/**
	 * Load 3 ints into to shader uniform.
	 */
	@Override
	public void setUniformInt3(String name, Vector3i values) 
	{
		int location = GLHelper.hglGetUniformLocation(this.id, name);
		GLHelper.hglUniform3iv(location, values.x, values.y, values.z);
	}

	/**
	 * Load 3 ints into to shader uniform.
	 */
	@Override
	public void setUniformInt3(String name, int x, int y, int z) 
	{
		int location = GLHelper.hglGetUniformLocation(this.id, name);
		GLHelper.hglUniform3iv(location, x, y, z);
	}
	
	/**
	 * Load 4 ints into to shader uniform.
	 */
	@Override
	public void setUniformInt4(String name, Vector4i values)
	{
		int location = GLHelper.hglGetUniformLocation(this.id, name);
		GLHelper.hglUniform4iv(location, values.x, values.y, values.z, values.w);
	}

	/**
	 * Load 4 ints into to shader uniform.
	 */
	@Override
	public void setUniformInt4(String name, int x, int y, int z, int w) 
	{
		int location = GLHelper.hglGetUniformLocation(this.id, name);
		GLHelper.hglUniform4iv(location, x, y, z, w);
	}

	/**
	 * Load boolean into to shader uniform.
	 */
	@Override
	public void setUniformBoolean(String name, boolean value) 
	{
		int location = GLHelper.hglGetUniformLocation(this.id, name);
		GLHelper.hglUniform1iv(location, 1, value ? 1 : 0);
	}

	/**
	 * Return this name.
	 */
	@Override
	public String getName() 
	{
		return this.name;
	}
	
	/**
	 * Return id of this shader or 'Shader Program'.
	 */
	public int getId()
	{
		return this.id;
	}
	
	/**
	 * Return id of this vertex shader.
	 */
	public int getVertexId()
	{
		return this.vertexId;
	}
	
	/**
	 * Return id of this fragment shader.
	 */
	public int getFragmentId()
	{
		return this.fragmentId;
	}
	
	/**
	 * Return id of this geometry shader.
	 */
	public int getGeometryId()
	{
		return this.geometryId;
	}
	
	/**
	 * Return id of this fragment shader.
	 */
	public int getTessCId()
	{
		return this.tessCId;
	}
	
	/**
	 * Return id of this geometry shader.
	 */
	public int getTessEId()
	{
		return this.tessEId;
	}
	
	public boolean isBound()
	{
		return this.bound;
	}

}
