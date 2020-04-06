package CTXEngine.Graphics.GL;

import CTXEngine.Graphics.BufferObject.BufferLayout;
import CTXEngine.Graphics.BufferObject.VertexBufferObject;

import CTXEngine.Graphics.GL.GLHelper;

import static CTXEngine.Core.SimplePrint.*;
import static CTXEngine.Core.CoreBase.*;
import static org.lwjgl.opengl.GL30.*; //temp

import org.lwjgl.system.NativeType;


/**
 * A vertex buffer object (Vbo) is an OpenGL feature that provides methods
 * for uploading vertex data (position, normal vector, color, etc.) to the
 * video device for non-immediate-mode rendering. Vbo's offer substantial
 * performance gains over immediate mode rendering primarily because the
 * data resides in the video device memory rather than the system memory
 * and so it can be rendered directly by the video device. These are equivalent
 * to vertex buffers in DirectX3D.
 */
public class GLVertexBufferObject extends VertexBufferObject
{
	/**@NativeType ("BufferData") <p> 
	 * This is data currently be stored into this vertex buffer.*/
	private float[] data; 
	/**@NativeType ("BufferSize") <p> 
	 * This is size of data of vertex buffer.*/
	private int size; 
	/** This is layout where store additional information about data
	from this buffer.*/
	private BufferLayout layout;
	/**This is basically id for current vertex buffer.*/
	private int id;
	/**This means how be used this vertex buffer.*/
	private int usage;
	/**This means how be used this vertex buffer.*/
	private int offset;
	
	/**Check if this buffer is bound / selected by open gl and can be used.*/
	protected boolean selected = false;

	/**
	 * Creating static vertex buffer object with data and size. Data can be float,
	 * because it's positions, colors, ... tangents can't be integer. Size its
	 * basically size of this input data in bytes of memory.
	 */
	public GLVertexBufferObject(@NativeType("BufferData") float[] dataIn, 
								@NativeType("BufferSize") int sizeIn, 
														  int usageIn, 
														  int offsetIn)
	{
		this.data = dataIn;
		this.size = sizeIn;
		this.usage = usageIn;
		this.offset = offsetIn;
		if (this.usage == 0)
			this.usage = GL_STATIC_DRAW;
		this.create();
		this.putData();
	}
	
	/**
	 * Creating dynamic vertex buffer object with data and size. Data can be float,
	 * because it's positions, colors, ... tangents can't be integer. Size its
	 * basically size of this input data in bytes of memory.
	 */
	public GLVertexBufferObject(@NativeType("BufferSize") int sizeIn, 
														  int usageIn, 
														  int offsetIn)
	{
		this.data = null;
		this.size = sizeIn;
		this.usage = usageIn;
		if (this.usage == 0)
			this.usage = GL_DYNAMIC_DRAW;
		this.offset = offsetIn;
		this.create();
		this.putData();
	}
	
	/**
	 * Delete n buffer names in vertexArrayId.
	 */
	@Override
	public void destroyVertexBufferObject()
	{
		this.destroy();
	}
	
	/**
	 * Creates vertex buffer id's.
	 */
	@Override
	public void create()
	{
		this.id = GLHelper.hglCreateBuffers();
		this.bind();
	}
	
	/**
	 * Deletes vertex buffer id's.
	 */
	@Override
	public void destroy()
	{
		this.unBind();
		GLHelper.hglDeleteBuffers(this.id);
	}
	
	/**
	 * Staticly put the data to vertex buffer. It's basic version of <em>putData</em>, without
	 * modification, in OpenGL directly call glBufferData. Recommends when 
	 * staticly put the data to vertex buffer.
	 * <p>
	 * Creates a new data store for the vertex buffer object currently bound to
	 * target. Any pre-existing data store is deleted.
	 */
	@Override
	public void putData()
	{
		CTX_ENGINE_INFO("Go.. 1");
		
		CTX_ENGINE_ASSERT(this.size == CTX_NULL ? false : true, "Buffer size is empty, because setting size is zero.");
		
		CTX_ENGINE_INFO("Go.. 2");
		
		GLHelper.hglBufferData(this.getType(), this.usage, this.size, this.data);
		
		CTX_ENGINE_INFO("Go.. 3");
	}
	
	/**
	 * Dynamicly put the data to this vertex Buffer with data and size, and then update, 
	 * in OpenGL directly call glbufferSubData. Recomends to use this function is when needs dynamiclly update buffer
	 * storage.
	 * <p>
	 * Redefines some or all of the data store for the buffer object current 
	 * lbound to target. Data starting at byte offset offset and extending for size
	 * bytes is copied to the data store from the memory pointed to by data.
	 */
	@Override
	public void putData(@NativeType("BufferData") float[] data, @NativeType("BufferSize") int size) 
	{
		this.bind();
		CTX_ENGINE_ASSERT(this.usage == GL_DYNAMIC_DRAW && this.data == null, "Buffer data is empty, because setting data is null.");
		GLHelper.hglBufferSubData(this.getType(), this.offset, size, data);
		this.unBind();
	}
	
	/**
	 * 	Bind the current vertex buffer, to begin use.
	 */
	@Override
	public void bind()
	{
		if (!this.isSelected())
		{
			GLHelper.hglBindBuffer(this.getType(), this.id);
			this.selected = true;
		}
	}
	
	/**
	 * Bind base the current vertex buffer, to begin use.
	 */
	public void bindBase(int indexIn)
	{
		GLHelper.hglBindBufferBase(this.getType(), indexIn, this.id);
	}
	
	/**
	 * Bind range of current vertex buffer, to end use.
	 */
	public final void bindRange(int indexIn, long offsetIn,
		long sizeIn) 
	{
		GLHelper.hglBindBufferRange(this.getType(), indexIn, this.id, offsetIn, sizeIn);
	}
	
	/**
	 * Unbind the current vertex buffer, to end use.
	 */
	public void unBind()
	{
		if (this.isSelected())
		{
			glBindBuffer(this.getType(), 0);
			this.selected = false;
		}
	}
	
	/**
	 * Bind base the current vertex buffer, to begin use.
	 */
	public final void unBindBase(int indexIn)
	{
		glBindBufferBase(this.getType(), indexIn, this.id);
	}
	
	/**
	 * Unbind range of current vertex buffer, to end use.
	 */
	public final void unBindRange(int indexIn, long offsetIn, long sizeIn) 
	{
		glBindBufferRange(this.getType(), indexIn, 0, offsetIn, sizeIn);
	}
	
	/*
	 * Return true if this buffer is selected.
	 */
	@Override
	public boolean isSelected()
	{
		return this.selected;
	}
	
	/**
	 * Add new data layout to this vertex buffer. Layout for example
	 * [layout (0) = v_position]. Basically it's just advanced and sorted
	 * glVertexArrtibPointer.
	 */
	@Override
	public void setLayout(final BufferLayout layout)
	{
		this.layout = layout;
	}

	/**
	 * Get's data layout from this vertex buffer.
	 */
	@Override
	public final BufferLayout getLayout()
	{
		return this.layout;
	}
	
	/**
	 * Return the size of data.
	 */
	@Override
	public final @NativeType("BufferSize") int getSize() 
	{
		if (this.data != null && this.getData().length != 0)
		{
			if (this.size != 0)
			{
				return this.size;
			}
			else
			{
				CTX_ENGINE_ERROR("Can't get size of data GLVertexBufferObject, because data is empty!");
					Runtime.getRuntime().exit(-1);
			}
	
		}
		else
		{
			CTX_ENGINE_ERROR("Can't get size of data GLVertexBufferObject, because data pointer not set!");
				Runtime.getRuntime().exit(-1);
		}
	
		return 0;
	}
	
	/**
	 * Return the float data from this buffer.
	 */
	@Override
	public @NativeType("BufferData") float[] getData()
	{
		if (this.data.length != 0)
		{
			return this.data;
		}
		else
		{
			CTX_ENGINE_ERROR("Can't get data of GLVertexBufferObject, because data not set!");
				Runtime.getRuntime().exit(-1);
		}
		return null;
	}
	
	/**
	 * Return id of GL Vertex Buffer Object.
	 */
	public final int getId()
	{
		if (this.id != 0)
		{
			return this.id;
		}
		else
		{
			CTX_ENGINE_ERROR("Can't get id of GLVertexBufferObject, because this object is not created!");
				Runtime.getRuntime().exit(-1);
		}
	
		return 0;
	}
	
	/**
	 * Get's the type from this buffer.
	 */
	@Override
	public int getType() 
	{
		return GL_ARRAY_BUFFER;
	}
}
