package CTXEngine.Graphics.GL;

import static org.lwjgl.opengl.GL46C.*; // latest Open GL build
import static CTXEngine.Core.CAndCppOperations.cstdlib.*;
import static CTXEngine.Core.SimplePrint.*;
import static CTXEngine.Core.CoreBase.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.PointerBuffer;

import CTXEngine.Core.CAndCppOperations.CType;
import CTXEngine.Graphics.BufferHelper;
import CTXEngine.Graphics.BufferObject.ShaderDataType;

/**
 * GLHelper (HGL) is specific engine utitly class for Open GL Graphics API. Its contains
 * recalled functions with some modification to easy using in engine. Also contains
 * some different versions of origianl functions. HGL class created for easy access to
 * Open GL Api from all programming languages where this engine be witten, because has
 * the same base interface.
 * 
 * <p>To get all functions and methods you can by '<b>h</b>', '<b>hgl</b>' or '<b>hctx</b>'.
 */
public final class GLHelper 
{
	//C++ defines in Java methods
	public static final int  CTX_DEFAULT = 0;
	public static final void CTX_GL_ERROR    (boolean x, String s) { if(!x) { CTX_ENGINE_ERROR("Open Gl Error: " + s); }; }
	public static final void CTX_GL_GEN_ERROR(boolean x, String s) { if(!x) { CTX_ENGINE_ERROR("Open Gl Error: " + s); }; }
	public static final void CTX_GL_DEL_ERROR(boolean x, String s) { if(!x) { CTX_ENGINE_ERROR("Open Gl Error: " + s); }; }
	public static final void CTX_GL_ONE_ERROR(boolean x, String s) { if(x)    { CTX_ENGINE_ERROR("Open Gl Error: " + s); }; }
	
	//C++ const variables
	public static final int hDefaultWinPosX = 0;
	public static final int hDefaultWinPosY = 0;
	public static final int hDefaultVertexArrayGenCount = 1;
	public static final int hDefaultBuffersGenCount = 1;
	public static final int hDefaultTexturesGenCount = 1;
	public static final int hDefaultClearMask = GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT;
	public static final float hDefaultWinColR = 0.2f;
	public static final float hDefaultWinColG = 0.6f;
	public static final float hDefaultWinColB = 1.0f;
	public static final float hDefaultWinColA = 1.0f;
	
	//this variables has only in java version of the engine
	public static final int HGL_STATIC_DRAW = GL_STATIC_DRAW;
	public static final int HGL_DYNAMIC_DRAW = GL_DYNAMIC_DRAW;
	
	/**
	 * Enable specific Open Gl state.
	 */
	public static void hglEnable(int e)
	{
		glEnable(e);
	}

	/**
	 * Disable specific Open Gl state.
	 */
	public static void hglDisable(int e)
	{
		glDisable(e);
	}

	/**
	 * This method will be convert data to open gl.
	 */
	public static int hctxConvertData(ShaderDataType type)
	{
		switch (type.type)
		{
			case 5:     return GL_FLOAT;
			case 6:     return GL_FLOAT;
			case 7:     return GL_FLOAT;
			case 8:     return GL_FLOAT;
			case 1:     return GL_INT;
			case 2:     return GL_INT;
			case 3:     return GL_INT;
			case 4:     return GL_INT;
			case 9:     return GL_FLOAT;
			case 10:    return GL_FLOAT;
			case 11:    return GL_FLOAT;
			case 12:    return GL_BOOL;
			case 13:    return GL_FLOAT;
		}

		if (type.type == 0)
		{
			CTX_ENGINE_ERROR("Can't convert to open gl type, because input data is unknown!");
			Runtime.getRuntime().exit(-1);
		}
		return 0;
	}

	/**
	 * Return the shader type form string.
	 */
	public static int hctxShaderTypeFromString(final String type)
	{
		if (type == "vertex" || type == "vert")
		{
			return GL_VERTEX_SHADER;
		}

		if (type == "fragment" || type == "frag"
			|| type == "pixel" || type == "pix")
		{
			return GL_FRAGMENT_SHADER;
		}

		if (type == "geometry" || type == "geo")
		{
			return GL_GEOMETRY_SHADER;
		}

		if (type == "tessellationControl" || type == "tessC"
			|| type == "tcs")
		{
			return GL_TESS_CONTROL_SHADER;
		}

		if (type == "tessellationEvaluation" || type == "tessE"
			|| type == "tes")
		{
			return GL_TESS_EVALUATION_SHADER;
		}

		CTX_ENGINE_ASSERT(false,
			"\nInvalid shader type specifier!\n" +
			"Supported spcefiers: \n" +
			"'vertex', 'vert', 'fragment', 'frag', 'pixel', 'pix', 'geometry', 'geo'\n" +
			"'tessellationControl', 'tessC', 'tcs', 'tessellationEvaluation', 'tessE', 'tes'. \n");

			return 0;
	}

	/**
	 * Create GL vertex shader and return function with already stored
	 * id.
	 */
	public static int hctxCreateVertexShader()
	{
		return glCreateShader(GL_VERTEX_SHADER);
	}

	/**
	 * Create GL fragment shader and return function with already stored
	 * id.
	 */
	public static int hctxCreateFragmentShader()
	{
		return glCreateShader(GL_FRAGMENT_SHADER);
	}

	/**
	 * 	Create GL geometry shader and return function with already stored
	 *  id.
	 */
	public static int hctxCreateGeometryShader()
	{
		return glCreateShader(GL_GEOMETRY_SHADER);
	}

	/**
	 * Enable alpha blending parameter.
	 */
	public static void hctxEnableAlphaBlendig()
	{
		hglEnable(GL_BLEND);
		hglBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}

	/**
	 * Disable alpha blending parameter.
	 */
	public static void hctxDisableAlphaBlendig()
	{
		hglDisable(GL_BLEND);
	}

	/**
	 * Enable depth test parameter.
	 */
	public static void hctxEnableDepthTesting()
	{
		hglEnable(GL_DEPTH_TEST);
	}

	/**
	 * Enable depth test parameter.
	 */
	public static void hctxDisableDepthTesting()
	{
		hglDisable(GL_DEPTH_TEST);
	}

	//-----------------------------------------------
	
	/**
	 * Sets the biplane area of the window to values previously selected
	 * by hglClearColor, hglClearDepth, and hglClearStencil. Multiple color
	 * buffers can be cleared simultaneously by selecting more than
	 * one buffer at a time using hglDrawBuffer.
	 */
	@CType("void")
	public static void hglClear(int mask)
	{
		if (mask == 0)
		{
			mask = hDefaultClearMask;
		}
		glClear(mask);
	}

	/**
	 * Specifies the red, green, blue, and alpha values used by hglClear to clear
	 * the color buffers. Values specified by hglClearColor are clamped to
	 * the range [0,1].
	 */
	@CType("void")
	public static void hglClearColor(@CType("GLfloat") float r, @CType("GLfloat") float g, @CType("GLfloat") float b, @CType("GLfloat") float a)
	{
		glClearColor(r, g, b, a);
	}

	/**
	 * Specifies the red, green, blue, and alpha values used by hglClear to clear
	 * the color buffers. Values specified by hglClearColor are clamped to
	 * the range [0,1].
	 */
	@CType("void")
	public static void hglClearColor()
	{
		glClearColor(hDefaultWinColR, hDefaultWinColG, hDefaultWinColB, hDefaultWinColA);
	}

	/**
	 * Specifies the affine transformation of x and y from normalized
	 * device coordinates to window coordinates.
	 */
	@CType("void")
	public static void hglViewport(@CType("GLuint") int x, @CType("GLuint")int y, @CType("GLuint") int widthIn, @CType("GLuint")int heightIn)
	{
		glViewport(x, y, widthIn, heightIn);
	}

	/**
	 * Specifies the affine transformation of x and y from normalized
	 * device coordinates to window coordinates. Position of this viewport
	 * be stored in hDefault* varible.
	 */
	@CType("void")
	public static void hglViewport(@CType("GLuint") int widthIn, @CType("GLuint") int heightIn)
	{
		glViewport(0, 0, hDefaultWinPosX, hDefaultWinPosY);
	}
	
	/**
	 *  Returns n previously unused vertex array object names in arrays, each representing a 
	 *  new vertex array object initialized to the default state.
	 *  
	 *  @param n - number of vertex array objects to create.
	 *  @param arrays - specifies an array in which names of the new vertex array objects are stored.
	 */
	@CType("void")
	public static int[] hglCreateVertexArrays(@CType("GLsizei") int n, @CType("GLuint") int... arrays)
	{
		IntBuffer ip = callocIntEmpty(n);
		nglCreateVertexArrays(n, pointer(ip));
		arrays = new int[n];
		for(int i = 0; i < n; i++) arrays[i] = ip.get(i);
			return arrays;
	}
	
	/**
	 *  Returns n previously unused vertex array object names in arrays, each representing a 
	 *  new vertex array object initialized to the default state.
	 *  
	 *  @param n - number of vertex array objects to create.
	 *  @param arrays - specifies an array in which names of the new vertex array objects are stored.
	 */
	@CType("void")
	public static int[] hglGenVertexArrays(@CType("GLSizei") int n, @CType("GLuint") int[] arrays)
	{
		IntBuffer ip = callocIntEmpty(n);
		nglGenVertexArrays(n, pointer(ip));
		arrays = new int[n];
		for(int i = 0; i < n; i++) arrays[i] = ip.get(i);
			return arrays;
	}
	
	/**
	 * Deletes n vertex array objects whose names are stored in the array addressed 
	 * by arrays. Once a vertex array object is deleted it has no contents and
	 * its name is again unused.
	 * 
	 * @param n - number of vertex array objects to delete.
	 * @param arrays - specifies the address of an array containing the n names of the objects to be deleted.
	 */
	@CType("void")
	public static void hglDeleteVertexArrays(@CType("GLSizei") int n, @CType("GLuint") int... arrays)
	{
        nglDeleteVertexArrays(n, pointer(mallocInt(n, arrays)));   
	}
	
	/**
	 * Binds the vertex array object with name array.
	 */
	@CType("void")
	public static void hglBindVertexArray(@CType("GLSizei") int array)
	{
		glBindVertexArray(array);
	}
	
	/**
	 * Returns n previously unused buffer names in buffers, each representing a 
	 * new buffer object initialized as if it had been bound to an unspecified 
	 * target.
	 * 
	 * @param n - specifies the number of buffer objects to create.
	 * @param buffers - specifies an array in which names of the new buffer objects are stored.
	 */
	@CType("void")
	public static int[] hglCreateBuffers(@CType("GLSizei") int n, @CType("GLuint") int... buffers)
	{
		IntBuffer ip = callocIntEmpty(n);
		nglCreateBuffers(n, pointer(ip));
		buffers = new int[n];
		for(int i = 0; i < n; i++) buffers[i] = ip.get(i);
			return buffers;
	}
	
	/**
	 * Returns n previously unused buffer names in buffers, each representing a 
	 * new buffer object initialized as if it had been bound to an unspecified 
	 * target.
	 * 
	 * @param n - specifies the number of buffer objects to create.
	 * @param buffers - specifies an array in which names of the new buffer objects are stored.
	 */
	@CType("void")
	public static int[] hglGenBuffers(@CType("GLSizei") int n, @CType("GLuint") int... buffers)
	{
		IntBuffer ip = callocIntEmpty(n);
		nglGenBuffers(n, pointer(ip));
		buffers = new int[n];
		for(int i = 0; i < n; i++) buffers[i] = ip.get(i);
			return buffers;
	}
	
	/**
	 * Deletes n buffer objects named by the elements of the array buffers. After a 
	 * buffer object is deleted, it has no contents, and its name is free for reuse.
	 * 
	 * @param n - number of buffer to delete.
	 * @param arrays - specifies the address of an array containing the n names of the objects to be deleted.
	 */
	@CType("void")
	public static void hglDeleteBuffers(@CType("GLSizei") int n, @CType("GLuint") int... arrays)
	{
       		nglDeleteBuffers(n, pointer(mallocInt(n, arrays)));   
	}
	
	/**
	 * Binds the buffer object with name buffer.
	 */
	@CType("void")
	public static void hglBindBuffer(@CType("GLenum") int target, @CType("GLuint") int buffer)
	{
		glBindBuffer(target, buffer);
	}
	
	/**
	 * Creates and initializes a buffer object's data store.
	 * 
	 * @param target - buffer type.
	 * @param size - size of data in bytes.
	 * @param data - data to this buffer. Native is <code>const void*</code>, here is <code>int[]</code>.
	 * @param usage - type of usage, GL_STATIC_DRAW, GL_DYNAMIC_DRAW and else.
	 */
	@CType("void")
	public static void hglBufferData(@CType("GLenum") int target, @CType("GLsizeiptr") int size, @CType("const void*") int[] data, @CType("GLenum") int usage)
	{
		IntBuffer ip = (IntBuffer) mallocInt(size, data);
		nglBufferData(target, Integer.toUnsignedLong(ip.remaining()) << 2, pointer(ip), usage);
	}
	
	/**
	 * Creates and initializes a buffer object's data store.
	 * 
	 * @param target - buffer type.
	 * @param size - size of data in bytes.
	 * @param data - data to this buffer. Native is <code>const void*</code>, here is <code>float[]</code>.
	 * @param usage - type of usage, GL_STATIC_DRAW, GL_DYNAMIC_DRAW and else.
	 */
	@CType("void")
	public static void hglBufferData(@CType("GLenum") int target, @CType("GLsizeiptr") int size, @CType("const void*") float[] data, @CType("GLenum") int usage)
	{
		FloatBuffer fp = (FloatBuffer) mallocFloat(size, data);
		nglBufferData(target, Integer.toUnsignedLong(fp.remaining()) << 2, pointer(fp), usage);
	}
	
	
	/**
	 * Enable generic vertex attribute array.
	 */
	@CType("void")
	public static void hglEnableVertexAttribArray(@CType("GLuint") int index)
	{
		glEnableVertexAttribArray(index);
	}
	
	/**
	 * Disable generic vertex attribute array.
	 */
	@CType("void")
	public static void hglDisableVertexAttribArray(@CType("GLuint") int index)
	{
		glDisableVertexAttribArray(index);
	}
	
	/**
	 * Define an array of generic vertex attribute data.
	 * 
	 * @param index - index of generic vertex attribute
	 * @param size - count elements to one attribute, like three {0.4f, 0.2f, 0.1f}
	 * @param type - type of attribute.
	 * @param stride - specifies the byte offset between consecutive generic vertex attributes. 
	 * @param offset - specifies a offset of the first component of the first generic vertex attribute 
	 * in the array in the data store of the buffer currently bound to the GL_ARRAY_BUFFER target.
	 */
	@CType("void")
	public static void hglVertexAttribPointer(@CType("GLuint") int index, @CType("GLint") int size, @CType("GLenum") int type, @CType("GLboolean") boolean normalized,
			@CType("GLuint") int stride, @CType("const void*") int offset)
	{
		nglVertexAttribPointer(index, size, type, normalized, stride, offset == 0 ? offset : pointer(offset)); //maybe usage for pointer.
	}
	
	/**
	 * See {@link hglVertexAttribPointer} upper.
	 * Define an array of generic vertex attribute data.
	 * <p>
	 * This method automaticlly convert input data to open gl data, GL_FLOAT, GL_INT.
	 */
	@CType("void")
	public static void hglVertexAttribPointer(int index, int size, ShaderDataType type, boolean normalized, int stride, long pointer)
	{
		nglVertexAttribPointer(index, size, hctxConvertData(type), normalized, stride, pointer);
	}
	
	/**
	 * Specifies multiple geometric primitives with very few subroutine calls. Instead of
	 * calling a GL procedure to pass each individual vertex, normal, texture coordinate, edge flag,
	 * or color, you can prespecify separate arrays of vertices, normals,
	 * and colors and use them to construct a sequence of primitives
	 * with a single call to glDrawArrays.
	 */
	@CType("void")
	public static void hglDrawArrays(int mode, int first, int count)
	{
		glDrawArrays(mode, first, count);
	}

	/**
	 * Specifies multiple geometric primitives with very few subroutine calls. Instead of
	 * calling a GL function to pass each individual vertex, normal,
	 * texture coordinate, edge flag, or color, you can prespecify
	 * separate arrays of vertices, normals, and so on, and use them to
	 * construct a sequence of primitives with a single call to glDrawElements.
	 */
	@CType("void")
	public static void hglDrawElements(int mode, int count, int type, @CType("const void*")long indices)
	{
		nglDrawElements(mode, count, type, indices);
	}
	
	//-----------------------------------------------


	/**
	 * Binds the buffer object buffer to the binding point at index index of the
	 * array of targets specified by target. Each target represents an indexed array
	 * of buffer binding points, as well as a single general binding point that can
	 * be used by other buffer manipulation functions such as glBindBuffer or glMapBuffer.
	 */
	public static void hglBindBufferBase(int type, int index, int bufferId)
	{
		CTX_GL_ERROR((bufferId != 0 && bufferId > 0) ? true : false, "Can't bind vertex array, because it null or not created!");
			glBindBufferBase(type, index, bufferId);
	}

	/**
	 * Binds a range the buffer object buffer represented by offset and size to the binding
	 * point at index index of the array of targets specified by target. Each target represents
	 * an indexed array of buffer binding points, as well as a single general binding point that
	 * can be used by other buffer manipulation functions such as glBindBuffer or glMapBuffer.
	 */
	public static void hglBindBufferRange(int type, int index, int bufferId,
		long offset, long size)
	{
		CTX_GL_ERROR((bufferId != 0 && bufferId > 0) ? true : false, "Can't bind vertex array, because it null or not created!");
			glBindBufferRange(type, index, bufferId, offset, size);
	}

	
	// C/C++ style (original example)
	/**
	 * Creates a new data store for the buffer object currently bound to
	 * target. Any pre-existing data store is deleted.
	 */
	//public static void hglBufferData(int bufferType, GLsizeiptr size, const void* data, int usage)
	//{
	//	glBufferData(bufferType, size, data, usage);
	//}
	
	/**
	 * Creates a new data store for the buffer object currently bound to
	 * target. Any pre-existing data store is deleted.
	 */
	public static void hglBufferSubData(int bufferType, long offset, int[] data)
	{
		glBufferSubData(bufferType, offset, BufferHelper.toIntBuffer(data));
	}
	
	/**
	 * Creates a new data store for the buffer object currently bound to
	 * target. Any pre-existing data store is deleted.
	 */
	public static void hglBufferSubData(int bufferType, long offset, float[] data)
	{
		glBufferSubData(bufferType, offset, BufferHelper.toFloatBuffer(data));
	}
	
	/**
	 * Creates a new data store for the buffer object currently bound to
	 * target. Any pre-existing data store is deleted.
	 */
	public static void hglBufferSubData(int bufferType, long offset, double[] data)
	{
		glBufferSubData(bufferType, offset, BufferHelper.toDoubleBuffer(data));
	}
	
	/**
	 * Creates a new data store for the buffer object currently bound to
	 * target. Any pre-existing data store is deleted.
	 */
	public static void hglBufferSubData(int bufferType, long offset, int size, int[] data)
	{
		glBufferSubData(bufferType, offset, BufferHelper.toIntBuffer(size, data));
	}
	
	/**
	 * Creates a new data store for the buffer object currently bound to
	 * target. Any pre-existing data store is deleted.
	 */
	public static void hglBufferSubData(int bufferType, long offset, int size, float[] data)
	{
		glBufferSubData(bufferType, offset, BufferHelper.toFloatBuffer(size, data));
	}
	
	/**
	 * Creates a new data store for the buffer object currently bound to
	 * target. Any pre-existing data store is deleted.
	 */
	public static void hglBufferSubData(int bufferType, long offset, int size, double[] data)
	{
		glBufferSubData(bufferType, offset, BufferHelper.toDoubleBuffer(size, data));
	}

	//C/C++ style original example
	/*
		Redefines some or all of the data store for the buffer object currently bound to
		target. Data starting at byte offset offset and extending for size bytes is copied
		to the data store from the memory pointed to by data.
	*/
	//public static void hglBufferSubData(GLenum bufferType, GLintptr offset, GLsizeiptr size, const void* data)
	//{
	//	glBufferSubData(bufferType, offset, size, data);
	//}



	/**
	 * Modifies the rate at which generic vertex attributes advance when rendering multiple
	 * instances of primitives in a single draw call.
	 */
	public static void hglVertexAttribDivisor(int id, int divisor)
	{
		glVertexAttribDivisor(id, divisor);
	}



	/**
	 * This creates an empty shader object for the shader stage given by given shaderType. The shader
	 * type must be one of GL_VERTEX_SHADER, GL_TESS_CONTROL_SHADER, GL_TESS_EVALUATION_SHADER, GL_GEOMETRY_SHADER,
	 * GL_FRAGMENT_SHADER, or GL_COMPUTE_SHADER.
	 */
	public static int hglCreateShader(int shaderType)
	{
		int shaderId = glCreateShader(shaderType);
		return shaderId;
	}

	/**
	 * Frees the memory and invalidates the name associated with the shader object specified by
	 * shader. This command effectively undoes the effects of a
	 * call to glCreateShader.
	 */
	public static void hglDeleteShader(int shaderType)
	{
		glDeleteShader(shaderType);
	}

	/**
	 * Sets the source code in shader to the source code in the array of strings specified by
	 * string. Any source code previously stored in the shader object is completely replaced.
	 */
	public static void hglShaderSource(int shaderType, CharSequence... strings)
	{
		glShaderSource(shaderType, strings);
	}
	
	/**
	 * Sets the source code in shader to the source code in the array of strings specified by
	 * string. Any source code previously stored in the shader object is completely replaced.
	 */
	public static void hglShaderSource(int shaderType, PointerBuffer strings, IntBuffer length)
	{
		glShaderSource(shaderType, strings, length);
	}
	
	/**
	 * Sets the source code in shader to the source code in the array of strings specified by
	 * string. Any source code previously stored in the shader object is completely replaced.
	 */
	public static void hglShaderSource(int shaderType, PointerBuffer strings, int... length)
	{
		glShaderSource(shaderType, strings, length);
	}

	/**
	 * 	Compiles the source code strings that have been stored in the shader object specified by shader.
	 */
	public static void hglCompileShader(int shaderType)
	{
		glCompileShader(shaderType);
	}
	
	/**
	 * Returns a parameter from a shader object.
	 */
	public static int hglGetShaderi(int shaderType, int pname)
	{
		return glGetShaderi(shaderType, pname);
	}

	/**
	 * Check the shaders on occurs error.
	 */
	public static void hglGetShaderiv(int shaderType, int pname, int... params)
	{
		glGetShaderiv(shaderType, pname, params);
	}
	
	/**
	 * Check the shaders on occurs error.
	 */
	public static void hglGetShaderiv(int shaderType, int pname, IntBuffer params)
	{
		glGetShaderiv(shaderType, pname, params);
	}

	/**
	 * Creates an empty program object and returns a non-zero value by which it can be referenced. A program object is
	 * an object to which shader objects can be attached. This provides a mechanism to specify the shader objects
	 * that will be linked to create a program.
	 */
	public static int hglCreateProgram()
	{
		int programId = glCreateProgram();
		return programId;
	}

	/**
	 * Links the program object specified by program. In other words just
	 * connect shader with usable shader program.
	 */
	public static void hglLinkProgram(int programId)
	{
		glLinkProgram(programId);
	}

	/**
	 * Installs the program object specified by program as part of current rendering state. One
	 * or more executables are created in a program object by successfully attaching shader objects
	 * to it with glAttachShader, successfully compiling the shader objects with glCompileShader,
	 * and successfully linking the program object with glLinkProgram.
	 */
	public static void hglUseProgram(int programId)
	{
		glUseProgram(programId);
	}

	/**
	 * Creates an empty program object and free memory.
	 */
	public static void hglDeleteProgram(int programId)
	{
		glDeleteProgram(programId);
	}

	/**
	 * Returns a parameter from a program object.
	 */
	public static int hglGetProgrami(int programId, int pname)
	{
		return glGetProgrami(programId, pname);
	}
	
	/**
	 * Check the program on occurs error.
	 */
	public static void hglGetProgramiv(int programId, int pname, int... params)
	{
		glGetProgramiv(programId, pname, params);
	}
	
	/**
	 * Check the program on occurs error.
	 */
	public static void hglGetProgramiv(int programId, int pname, IntBuffer params)
	{
		glGetProgramiv(programId, pname, params);
	}

	/**
	 * In order to create a complete shader program, there must be a way
	 * to specify the list of things that will be linked together. Program
	 * objects provide this mechanism. Shaders that are to be linked together
	 * in a program object must first be attached to that program object.
	 */
	public static void hglAttachShader(int programId​, int shaderId​)
	{
		glAttachShader(programId​, shaderId​);
	}

	/**
	 * Basically detach shader from input shader program.
	 */
	public static void hglDetachShader(int programId​, int shaderId)
	{
		glDetachShader(programId​, shaderId);
	}

	/**
	 * Returns the information log for the specified shader object. The information
	 * log for a shader object is modified when the shader is compiled. The string that
	 * is returned will be null terminated.
	 */
	public static String hglGetShaderInfoLog(int shaderId)
	{
		return glGetShaderInfoLog(shaderId);
	}
	
	/**
	 * Returns the information log for the specified shader object. The information
	 * log for a shader object is modified when the shader is compiled. The string that
	 * is returned will be null terminated.
	 */
	public static String hglGetShaderInfoLog(int shaderId, int length)
	{
		return glGetShaderInfoLog(shaderId, length);
	}
	
	/**
	 * Returns the information log for the specified shader object. The information
	 * log for a shader object is modified when the shader is compiled. The string that
	 * is returned will be null terminated.
	 */
	public static void hglGetShaderInfoLog(int shaderId, IntBuffer length, ByteBuffer infoLog)
	{
		glGetShaderInfoLog(shaderId, length, infoLog);
	}

	/**
	 * Returns the information log for the specified program object. The information log for aprogram object is modified when the program object is linked or validated. The string that
	 * is returned will be null terminated.
	 */
	public static void hglGetProgramInfoLog(int programId​, IntBuffer length, ByteBuffer infoLog)
	{
		glGetProgramInfoLog(programId​, length, infoLog);
	}
	
	/**
	 * Returns the information log for the specified program object. The information log for a
	 * program object is modified when the program object is linked or validated. The string that
	 * is returned will be null terminated.
	 */
	public static String hglGetProgramInfoLog(int programId​)
	{
		return glGetProgramInfoLog(programId​);
	}
	
	/**
	 * Returns the information log for the specified program object. The information log for a
	 * program object is modified when the program object is linked or validated. The string that
	 * is returned will be null terminated.
	 */
	public static String hglGetProgramInfoLog(int programId​, int l)
	{
		return glGetProgramInfoLog(programId​, l);
	}

	/**
	 * Specify the value of a uniform variable for the current program object.
	 * 							START
	 */

	public static void hglUniformMatrix4fv(int location, boolean transpose, float... value)
	{
		glUniformMatrix4fv(location, transpose, value);
	}
	
	public static void hglUniformMatrix4fv(int location, boolean transpose, FloatBuffer value)
	{
		glUniformMatrix4fv(location, transpose, value);
	}

	public static void hglUniformMatrix3fv(int location, boolean transpose, float... value)
	{
		glUniformMatrix3fv(location, transpose, value);
	}
	
	public static void hglUniformMatrix3fv(int location, boolean transpose, FloatBuffer value)
	{
		glUniformMatrix3fv(location, transpose, value);
	}

	public static void hglUniform1fv(int location, float... value)
	{
		glUniform1fv(location, value);
	}
	
	public static void hglUniform1fv(int location, FloatBuffer value)
	{
		glUniform1fv(location, value);
	}

	public static void hglUniform1f(int location, final float value)
	{
		glUniform1f(location, value);
	}

	public static void hglUniform2fv(int location, float... value)
	{
		glUniform2fv(location, value);
	}
	
	public static void hglUniform2fv(int location, FloatBuffer value)
	{
		glUniform2fv(location, value);
	}

	public static void hglUniform2f(int location,
		final float x, final float y)
	{
		glUniform2f(location, x, y);
	}

	public static void hglUniform3fv(int location, float... value)
	{
		glUniform3fv(location, value);
	}
	
	public static void hglUniform3fv(int location, FloatBuffer value)
	{
		glUniform3fv(location, value);
	}

	public static void hglUniform3f(int location,
		final float x, final float y, final float z)
	{
		glUniform3f(location, x, y, z);
	}

	public static void hglUniform4fv(int location, float... value)
	{
		glUniform4fv(location, value);
	}
	
	public static void hglUniform4fv(int location, FloatBuffer value)
	{
		glUniform4fv(location, value);
	}

	public static void hglUniform4f(int location,
		final float x, final float y, final float z, final float w)
	{
		glUniform4f(location, x, y, z, w);
	}

	public static void hglUniform1iv(int location, final int... value)
	{
		glUniform1iv(location, value);
	}
	
	public static void hglUniform1iv(int location, final IntBuffer value)
	{
		glUniform1iv(location, value);
	}

	public static void hglUniform1i(int location, final int x)
	{
		glUniform1i(location, x);
	}

	public static void hglUniform2iv(int location, final int... value)
	{
		glUniform2iv(location, value);
	}
	
	public static void hglUniform2iv(int location, final IntBuffer value)
	{
		glUniform2iv(location, value);
	}

	public static void hglUniform2i(int location,
		final int x, final int y)
	{
		glUniform2i(location, x, y);
	}

	public static void hglUniform3iv(int location, final int... value)
	{
		glUniform3iv(location, value);
	}
	
	public static void hglUniform3iv(int location, final IntBuffer value)
	{
		glUniform3iv(location, value);
	}

	public static void hglUniform3i(int location,
		final int x, final int y, final int z)
	{
		glUniform3i(location, x, y, z);
	}

	public static void hglUniform4iv(int location, final int... value)
	{
		glUniform4iv(location, value);
	}
	
	public static void hglUniform4iv(int location, final IntBuffer value)
	{
		glUniform4iv(location, value);
	}

	public static void hglUniform4i(int location,
		final int x, final int y, final int z, final int w)
	{
		glUniform4i(location, x, y, z, w);
	}

	/*                END                                 */

	/**
	 * Returns an integer that represents the location of a specific uniform variable
	 * within a program object. name must be a null terminated string that contains
	 * no white space. name must be an active uniform variable name in program that
	 * is not a structure, an array of structures, or a subcomponent of a vector or a matrix.
	 */
	public static int hglGetUniformLocation(int program, CharSequence name)
	{
		return glGetUniformLocation(program, name);
	}
	
	/**
	 * Returns an integer that represents the location of a specific uniform variable
	 * within a program object. name must be a null terminated string that contains
	 * no white space. name must be an active uniform variable name in program that
	 * is not a structure, an array of structures, or a subcomponent of a vector or a matrix.
	 */
	public static int hglGetUniformLocation(int program, ByteBuffer name)
	{
		return glGetUniformLocation(program, name);
	}

	/**
	 * Returns n texture names in textures. There is no guarantee that the names
	 * form a contiguous set of integers; however, it is guaranteed that none of t
	 * he returned names was in use immediately before the call to glGenTextures.
	 */
	public static int hglGenTextures()
	{
		return glGenTextures();
	}
	
	/**
	 * Returns n texture names in textures. There is no guarantee that the names
	 * form a contiguous set of integers; however, it is guaranteed that none of t
	 * he returned names was in use immediately before the call to glGenTextures.
	 */
	public static void hglGenTextures(int... ids)
	{
		glGenTextures(ids);
	}

	/**
	 * Returns n previously unused texture names in textures, each representing a
	 * new texture object of the dimensionality and type specified by target and
	 * initialized to the default values for that texture type.
	 */
	public static int hglCreateTextures(int type)
	{
		return glCreateTextures(type);
	}
	
	/**
	 * Returns n previously unused texture names in textures, each representing a
	 * new texture object of the dimensionality and type specified by target and
	 * initialized to the default values for that texture type.
	 */
	public static void hglCreateTextures(int type, int... ids)
	{
		glCreateTextures(type, ids);
	}

	/**
	 * Deletes n textures named by the elements of the array textures.
	 */
	public static void hglDeleteTextures(final int textureId)
	{
		glDeleteTextures(textureId);
	}
	
	/**
	 * Deletes n textures named by the elements of the array textures.
	 */
	public static void hglDeleteTextures(final int... textureIds)
	{
		glDeleteTextures(textureIds);
	}

	/**
	 * Lets you create or use a named texture. Calling glBindTexture with target set
	 * to GL_TEXTURE_1D, GL_TEXTURE_2D, GL_TEXTURE_3D, GL_TEXTURE_1D_ARRAY,
	 * GL_TEXTURE_2D_ARRAY, GL_TEXTURE_RECTANGLE, GL_TEXTURE_CUBE_MAP,
	 * GL_TEXTURE_CUBE_MAP_ARRAY, GL_TEXTURE_BUFFER, GL_TEXTURE_2D_MULTISAMPLE or
	 * GL_TEXTURE_2D_MULTISAMPLE_ARRAY and texture set to the name of the new texture
	 * binds the texture name to the target. When a texture is bound to a target, the
	 * previous binding for that target is automatically broken.
	*/
	public static void hglBindTexture(int type, int textureId)
	{
		glBindTexture(type, textureId);
	}
	
	/**
	 * Lets you create or use a named texture.
	 */
	public static void hglBindTextures(int type, int... textureIds)
	{
		glBindTextures(type, textureIds);
	}
	
	/**
	 * Lets you create or use a named texture.
	 */
	public static void hglBindTextures(int type, IntBuffer textureIds)
	{
		glBindTextures(type, textureIds);
	}

	/**
	 * Binds an existing texture object to the texture unit numbered unit.
	 */
	public static void hglBindTextureUnit(int unit, int textureId)
	{
		glBindTextureUnit(unit, textureId);
	}
	
	/**
	 * Specifies a two-dimensional texture image.
	 */
	public static void hglTexImage2D(int target, int level, int internalformat, int width, int height, int border,
			int format, int type, long pixels)
	{
		glTexImage2D(target, level, internalformat, width, height, border,
			format, type, pixels);
	}
	
	/**
	 * Specifies a two-dimensional texture image.
	 */
	public static void hglTexImage2D(int target, int level, int internalformat, int width, int height, int border,
			int format, int type, ByteBuffer pixels)
	{
		glTexImage2D(target, level, internalformat, width, height, border,
			format, type, pixels);
	}

	/**
	 * Simultaneously specify storage for all levels of a two-dimensional or one-dimensional
	 * array texture.
	 */
	public static void hglTextureStorage1D(int texture, int levels, int internalformat,
			int width)
	{
		glTextureStorage1D(texture, levels, internalformat, width);
	}

	/**
	 * Simultaneously specify storage for all levels of a two-dimensional or one-dimensional
	 * array texture.
	 */
	public static void hglTextureStorage2D(int texture, int levels, int internalformat,
			int width, int height)
	{
		glTextureStorage2D(texture, levels, internalformat, width, height);
	}

	/*
	 * Simultaneously specify storage for all levels of a two-dimensional or one-dimensional
	 * array texture.
	 */
	public static void hglTextureStorage3D(int texture, int levels, int internalformat,
			int width, int height, int depth)
	{
		glTextureStorage3D(texture, levels, internalformat, width, height, depth);
	}

	/**
	 * Texturing maps a portion of a specified texture image onto each graphical primitive for which
	 * texturing is enabled. This redefine a contiguous subregion of an existing two-dimensional or one-dimensional array texture image.
	 * The texels referenced by pixels replace the portion of the existing texture array with x indices xoffset and xoffset+width−1,
	 * inclusive, and y indices yoffset and yoffset+height−1, inclusive. This region may not include any texels outside
	 * the range of the texture array as it was originally specified. It is not an error to specify a subtexture with
	 * zero width or height, but such a specification has no effect.
	 */
	public static void hglTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width,
		int height, int format, int type, @CType("void const *") long pixels)
	{
		glTextureSubImage2D(texture, level, xoffset, yoffset, width, height,
			format, type, pixels);
	}
	
	/**
	 * IntBuffer version of {@link GLHelper.hglTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width,
	 * int height, int format, int type, long pixels)}
	 */
	public static void hglTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width,
		int height, int format, int type, @CType("void const *") IntBuffer pixels)
	{
		glTextureSubImage2D(texture, level, xoffset, yoffset, width, height,
			format, type, pixels);
	}
	
	/**
	 * 
	 * Byte buffer version of {@link GLHelper.hglTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width,
	 * int height, int format, int type, long pixels)}
	 */
	public static void hglTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width,
			int height, int format, int type, @CType("void const *") ByteBuffer pixels)
	{
		glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
	}

	/**
	 * Selects which texture unit subsequent texture state calls
	 * will affect.
	 */
	public static void hglActiveTexture(int type)
	{
		glActiveTexture(type);
	}

	/**
	 * Assign the value or values in params to the texture parameter specified
	 * as pname.
	*/
	public static void hglTexParameterf(int type, int pname, float param)
	{
		glTexParameterf(type, pname, param);
	}

	/**
	 * Assign the value or values in params to the texture parameter specified
	 * as pname.
	 */
	public static void hglTexParameteri(int type, int pname, int param)
	{
		glTexParameteri(type, pname, param);
	}

	/**
	 * Assign the value or values in params to the texture parameter specified
	 * as pname.
	 */
	public static void hglTextureParameterf(int type, int pname, float param)
	{
		glTextureParameterf(type, pname, param);
	}

	/**
	 * 	Assign the value or values in params to the texture parameter specified
	 * as pname.
	 */
	public static void hglTextureParameteri(int type, int pname, int param)
	{
		glTextureParameteri(type, pname, param);
	}

	/*
	 * Assign the value or values in params to the texture parameter specified
	 * as pname.
	 */
	public static void hglTexParameteriv(int type, int pname, int... params)
	{
		glTexParameteriv(type, pname, params);
	}
	
	/*
	 * Assign the value or values in params to the texture parameter specified
	 * as pname.
	 */
	public static void hglTexParameteriv(int type, int pname, IntBuffer params)
	{
		glTexParameteriv(type, pname, params);
	}

	/**
	 * Assign the value or values in params to the texture parameter specified
	 * as pname.
	 */
	public static void hglTexParameterfv(int type, int pname, float... params)
	{
		glTexParameterfv(type, pname, params);
	}
	
	/**
	 * Assign the value or values in params to the texture parameter specified
	 * as pname.
	 */
	public static void hglTexParameterfv(int type, int pname, FloatBuffer params)
	{
		glTexParameterfv(type, pname, params);
	}

	/**
	 * Defines the operation of blending for all draw buffers when it is enabled.
	 */
	public static void hglBlendFunc(int sfactor, int dfactor)
	{
		glBlendFunc(sfactor, dfactor);
	}
}
