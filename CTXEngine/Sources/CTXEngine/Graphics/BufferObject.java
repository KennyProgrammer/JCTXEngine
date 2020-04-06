package CTXEngine.Graphics;

import static CTXEngine.Core.SimplePrint.*;
import static CTXEngine.Core.CoreBase.*;
import static CTXEngine.Graphics.GL.GLHelper.HGL_STATIC_DRAW;
import static CTXEngine.Graphics.GL.GLHelper.HGL_DYNAMIC_DRAW;

import java.util.ArrayList;

import org.lwjgl.system.NativeType;

import CTXEngine.Graphics.BufferImpl.BufferFloatImpl;
import CTXEngine.Graphics.BufferImpl.BufferIntImpl;
import CTXEngine.Graphics.GL.GLIndexBufferObject;
import CTXEngine.Graphics.GL.GLVertexBufferObject;

public final class BufferObject 
{

	/**
	 * This is type of data, like a vec2, vec3, mat4.
	 */
	public static enum ShaderDataType
	{
		none(0),
		t_int(1),       t_int2(2),     t_int3(3),      t_int4(4),
		t_float(5),     t_float2(6),   t_float3(7),    t_float4(8),
		t_mat2(9),      t_mat3(10),    t_mat4(11),
		t_boolean(12),
		t_struct(13);
		
		public int type;
		ShaderDataType(int typeIn) { this.type = typeIn; }
	};
	
	public static int ShaderDataTypeSize(ShaderDataType type)
	{
		switch (type.type)
		{
			case 5:     return 4;
			case 6:     return 4 * 2;
			case 7:     return 4 * 3;
			case 8:     return 4 * 4;
			case 1:     return 4;
			case 2:     return 4 * 2;
			case 3:     return 4 * 3;
			case 4:     return 4 * 4;
			case 9:     return 4 * 2 * 2;
			case 10:    return 4 * 3 * 3;
			case 11:    return 4 * 4 * 4;
			case 12:    return 4;
			case 13:    return 1;
		}

		if (type.type == 0)
		{
			CTX_ENGINE_ERROR("Shader data type is unknown!");
				Runtime.getRuntime().exit(-1);
		}
		return 0;
	}
	
	public static final class VertexAttribute
	{
		/**This is name of this buffer element.*/
		private String name;
		/**Type of data in shader.*/
		private ShaderDataType type;
		/**This is offset of current buffer element.*/
		private int offset;
		/**This is size of current buffer element.*/
		private int size;
		/**Check if this element in buffer is normalized.*/
		private boolean normalized;
		
		/**
			Default constructor.
		 */
		VertexAttribute() {}
		
		/**
			Creates new vertex attribute element with type, size, offset and name.
			
			@typeIn - is type of data in actual shader .glsl or .hlsl code.
			@nameIn - is name of current attribute like 'v_position'.
			@normalizedIn - specifies whether fixed-point data values should be normalized 
				(GL_TRUE) or converted directly as fixed-point values (GL_FALSE) 
				when they are accessed.
		*/
		public VertexAttribute(ShaderDataType typeIn, String nameIn, 
				boolean normalizedIn)
		{
			this.name = nameIn;
			this.type = typeIn;
			this.normalized = normalizedIn;
			this.size = ShaderDataTypeSize(this.type);
			this.offset = 0;
		}
		
		/**
			Creates new vertex attribute element with type, size, offset and name.
			
			@typeIn - is type of data in actual shader .glsl or .hlsl code.
			@nameIn - is name of current attribute like 'v_position'.
			@normalizedIn - specifies whether fixed-point data values should be normalized 
				(GL_TRUE) or converted directly as fixed-point values (GL_FALSE) 
				when they are accessed.
		*/
		public VertexAttribute(ShaderDataType typeIn, String nameIn)
		{
			this(typeIn, nameIn, false);
		}
		
		/**
		 * Return the element count from element (attribute id in vertex array object),
		 * in other words its just coordinate size of object. For example t_float3 has 3
		 * axis / or directions and it's actual x, y, z our coordinate size.
		 * Or t_int2 has 2 axis and actual x, y our coordinates.
		 */
		public final int getElementAttribSize()
		{
			switch (type.type)
			{
				case 5:  return 1;
				case 6:  return 2;
				case 7:  return 3;
				case 8:  return 4;
				case 1:  return 1;
				case 2:  return 2;
				case 3:  return 3;
				case 4:  return 4;
				case 9:  return 2 * 2;
				case 10: return 3 * 3;
				case 11: return 4 * 4;
				case 12: return 1;
				case 13: return 1;
			}
	
			if (type.type == 0)
			{
				CTX_ENGINE_ERROR("Shader data type is unknown!");
					Runtime.getRuntime().exit(-1);
			}
	
			return 0;
		}
		
		/**
		 * Return the name of this vertex attribute element use getter.
		 */
		public String getName()
		{
			return this.name;
		}
	
		/**
		 * Return the offset of this vertex attribute element use getter.
		 */
		public int getOffset()
		{
			return this.offset;
		}
	
		/**
		 * Return the pointer of this vertex attribute element.
		 */
		public final int getPointer()
		{
			return (this.offset);
		}
	
		/**
		 * Return the size of this vertex attribute element use getter.
		 */
		public final int getSize()
		{
			return this.size;
		}
	
		/**
		 * Return the type of shader.
		 */
		public final ShaderDataType getType()
		{
			return this.type;
		}
	
		/**
		 * Return true if this vertex attribute is normalized, else false.
		 */
		public final boolean isNormalized() 
		{
			return this.normalized;
		}
	}
	
	/**
	 * Buffer layout is representation of layout's in rendering api's. Some
	 * layout can which can store some kind of data, say vertices, and for them
	 * it will be possible to determine some other parameters, for example, types
	 * of vertices, name, id, and so on.
     */
	public static class BufferLayout
	{
		/*This is actual list with all buffer elements.*/
		private ArrayList<VertexAttribute> attributes;
		/*Stride.*/
		private int stride = 0;
		
		/**
		 * Default constructor.
		 */
		public BufferLayout() {};
		
		/**
		 * Create buffer layout use initializer list witch takes just reference
		 * to 'elementsIn'.
		 */
		public BufferLayout(final VertexAttribute... elementsIn)
		{
			this.attributes = new ArrayList<VertexAttribute>();
			for (VertexAttribute vertexAttribute : elementsIn) 
			{
				this.attributes.add(vertexAttribute);
			}
			this.calcOffsetAndStride();
		}
		
		/**
		 * Return list with buffer elements.
		 */
		public final ArrayList<VertexAttribute> getAttributes()
		{
			return this.attributes;
		}
	
		/**
		 * Return the stride of this buffer element.
		 */
		public final int getStride()
		{
			return this.stride;
		}
		
		/**
		 * Calculate offset and stride of this buffer element in buffer
		 * layout.
		 */
		private void calcOffsetAndStride()
		{
			int offset = 0;
			stride = 0;
			for (VertexAttribute attribute : this.attributes)
			{
				attribute.offset = offset;
				offset += attribute.size;
				stride += attribute.size;
			}
		}
		
		/**
		 * This is begin point of element iterator.
		 */
		public <T> T begin(final Iterable<T> elements) 
		{
		    return elements.iterator().next();
		}

		/**
		 * This is end point of element iterator.
		 */
		public <T> T end(final Iterable<T> elements) 
		{
		    T lastElement = null;

		    for (T element : elements) 
		    {
		        lastElement = element;
		    }

		    return lastElement;
		}
	}
	
	/**
	 * Vertex buffer object is specific graphical buffer where stores
	 * data about single vertex or vertices. Data can be anything from
	 * position to bi-tangent.
	 */
	public static abstract class VertexBufferObject implements BufferFloatImpl
	{
		/**
		 * Creating vertex buffer object with data and size specific on usable render
		 * API. Data can be float. because it's positions, colours, ... tangents
		 * can't be integer. Size its basically size of this input data in
		 * bytes of memory.
		 */
		public static VertexBufferObject createReferenceStatic(float data[], int size)
		{
			switch (RenderEngine.getAPI().get())
			{
				case "none":
					CTX_ENGINE_ASSERT(false, "Can't create vertex buffer, because render api not define!");
						return null;
				case "opengl":
					return new GLVertexBufferObject(data, size, HGL_STATIC_DRAW, 0);
				case "vulcan":
					CTX_ENGINE_ASSERT(false, "Can't create vertex buffer, because vulcan api not support yet!");
						return null;
				case "directx":
					CTX_ENGINE_ASSERT(false, "Can't create vertex buffer, because directx api not support yet!");
						return null;
			}

			return null;
		}
		
		/**
		 * Creating vertex buffer object with data and size specific on usable render
		 * API. Data can be float. because it's positions, colours, ... tangents
		 * can't be integer. Size its basically size of this input data in
		 * bytes of memory.
		 */
		public static VertexBufferObject createReferenceDynamic(float[] data, int size)
		{
			switch (RenderEngine.getAPI().get())
			{
				case "none":
					CTX_ENGINE_ASSERT(false, "Can't create vertex buffer, because render api not define!");
						return null;
				case "opengl":
					return new GLVertexBufferObject(data, size, HGL_DYNAMIC_DRAW, 0);
				case "vulcan":
					CTX_ENGINE_ASSERT(false, "Can't create vertex buffer, because vulcan api not support yet!");
						return null;
				case "directx":
					CTX_ENGINE_ASSERT(false, "Can't create vertex buffer, because directx api not support yet!");
						return null;
			}

			return null;
		}
		
		/**
		 * Basic constructor.
		 */
		public VertexBufferObject() {};
		
		/**
		 * Basic destructor.
		 */
		public abstract void destroyVertexBufferObject();
		
		/**
		 * Bind this buffer.
		 */
		@Override
		public abstract void bind();
		
		/**
		 * Unbind this buffer.
		 */
		@Override
		public abstract void unBind();
		
		/**
		 * Return true if this buffer is selected.
		 */
		@Override
		public abstract boolean isSelected();
		
		/**
		 * Put's the data to buffer from already class data with specific size.
		 */
		@Override
		public abstract void putData();
		
		/**
		 * Put's the data to buffer with specific size.
		 */
		@Override
		public abstract void putData(@NativeType("BufferData") float[] data, @NativeType("BufferSize") int size);
		
		/**
		 * Get's the data from this buffer.
		 */
		@Override
		public abstract @NativeType("BufferData") float[] getData();
		
		/**
		 * Get's the data from this buffer.
		 */
		@Override
		public abstract @NativeType("BufferSize") int getSize();
		
		/**
		 * Get's the type from this buffer.
		 */
		@Override
		public abstract @NativeType("BufferType") int getType();
	
		/**
		 * Get's data layout from this vertex buffer.
		 */
		public abstract BufferLayout getLayout();

		/**
		 * Add new data layout to this vertex buffer.
		 */
		public abstract void setLayout(BufferLayout layout);
	
	}
		
	/**
	 * Index Buffer object is specific object where renderer indices about
	 * position of vertices is stored.
	 */
	public static abstract class IndexBufferObject implements BufferIntImpl
	{
		/**
		 * Creating buffer object with data and size.
		 */
		public static IndexBufferObject createReference(int data[], int size)
		{
			switch (RenderEngine.getAPI().get())
			{
				case "none":
					CTX_ENGINE_ASSERT(false, "Can't create index buffer, because render api not define!");
						return null;
				case "opengl":
					return new GLIndexBufferObject(data, size);
				case "vulcan":
					CTX_ENGINE_ASSERT(false, "Can't create index buffer, because vulcan api not support yet!");
						return null;
				case "directx":
					CTX_ENGINE_ASSERT(false, "Can't create index buffer, because directx api not support yet!");
						return null;
						
			}

			return null;
			
		}
		
		/**
		 * Creating buffer object with data and size.
		 */
		public static IndexBufferObject createReference(int data[], int size, int countAlreadyCalc)
		{
			switch (RenderEngine.getAPI().get())
			{
				case "none":
					CTX_ENGINE_ASSERT(false, "Can't create index buffer, because render api not define!");
						return null;
				case "opengl":
					return new GLIndexBufferObject(data, size, countAlreadyCalc);
				case "vulcan":
					CTX_ENGINE_ASSERT(false, "Can't create index buffer, because vulcan api not support yet!");
						return null;
				case "directx":
					CTX_ENGINE_ASSERT(false, "Can't create index buffer, because directx api not support yet!");
						return null;
						
			}

			return null;
			
		}
		
		/**
		 * Basic constructor.
		 */
		public IndexBufferObject() {};
		
		/**
		 * Basic destructor.
		 */
		public abstract void destroyIndexBufferObject();
		
		/**
		 * Bind this buffer.
		 */
		@Override
		public abstract void bind();
		
		/**
		 * Unbind this buffer.
		 */
		@Override
		public abstract void unBind();
		
		/**
		 * Return true if this buffer is selected.
		 */
		@Override
		public abstract boolean isSelected();
		
		/**
		 * Put's the data to buffer from already class data with specific size.
		 */
		@Override
		public abstract void putData();
		
		/**
		 * Put's the data to buffer with specific size.
		 */
		@Override
		public abstract void putData(@NativeType("BufferData") int[] data, @NativeType("BufferSize") int size);
		
		/**
		 * Get's the data from this buffer.
		 */
		@Override
		public abstract @NativeType("BufferData") int[] getData();
		
		/**
		 * Get's the data from this buffer.
		 */
		@Override
		public abstract @NativeType("BufferSize") int getSize();
		
		/**
		 * Get's the type from this buffer.
		 */
		@Override
		public abstract @NativeType("BufferType") int getType();
		
		/**
		 * Equivalent {@link getSize()}
		 */
		public abstract int getCount();
		
		/**
		 * Equivalent {@link getData()}
		 */
		public abstract int[] getIndices();
		
	}
}
