package CTXEngine.Graphics;

import java.util.ArrayList;

import CTXEngine.Graphics.BufferObject.IndexBufferObject;
import CTXEngine.Graphics.BufferObject.VertexBufferObject;

import static CTXEngine.Core.CoreBase.*;

/**
 * A Vertex Array Object is an only OpenGL Object that stores all of the state needed to supply vertex
 * data (with one minor exception noted below). It stores the format of the vertex data as well as the
 * Buffer Objects providing the vertex data arrays. Note that Vao's do not copy, freeze
 * or store the contents of the referenced buffers - if you change any of the data in the buffers
 * referenced by an existing Vao, those changes will be seen by users of the Vao.
 */
public abstract class VertexArrayObject implements ArrayImpl
{

	/**
	 * Only private default constructor.
	 */
	public       VertexArrayObject() {}
	
	/**
	 * Only private default destructor. Not defenitly destructor just for memory managment.
	 */
	public void destroyVertexArrayObject() {}

	/**
	 * Creating vertex array object (list with other buffers).
	 */
	public static VertexArrayObject createReference()
	{
		switch (RenderEngine.getAPI().get())
		{
			case "none":
				CTX_ENGINE_ASSERT(false, "Can't create vertex buffer, because render api not define!");
					return null;
			case "opengl":
				return new CTXEngine.Graphics.GL.GLVertexArrayObject();
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
	 * Create array id's.
	 */
	@Override
	public abstract void create();
	
	/**
	 * Delete array id's.
	 */
	@Override
	public abstract void destroy();
	
	/**
	 * Bind this buffer.
	 */
	@Override
	public abstract void bind();
	
	/**
	 * UnBind this buffer.
	 */
	@Override
	public abstract void unBind();
	
	/**
	 * Put's the buffer to array.
	 */
	@Override
	public abstract void putBuffer(final BufferImpl buffer);

	/**
	 * Put's the vertex buffer to array.
	 */
	public abstract void putBuffer(final VertexBufferObject buffer);
	
	/**
	 * Put's the index buffer to array.
	 */
	public abstract void putBuffer(final IndexBufferObject buffer);

	/**
	 * Return the vector (list) with all vertex buffers used by this vertex
	 * array.
	 */
	public abstract ArrayList<VertexBufferObject> getVertexBuffers();
	
	/**
	 * Return the vector (list) with all index buffers used by this vertex
	 * array.
	 */
	public abstract ArrayList<IndexBufferObject> getIndexBuffers();
	
	/**
	 * Return the vector (list) with all buffers used by this vertex
	 * array.
	 */
	public abstract ArrayList<BufferImpl> getBuffers();

}
