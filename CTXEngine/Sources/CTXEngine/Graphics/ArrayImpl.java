package CTXEngine.Graphics;

import org.lwjgl.system.NativeType;

/**
 * Contains main funtions to array system.
 */
public @NativeType("struct") interface ArrayImpl
{	
	/**Create buffer id's.*/
	public abstract void create();
	/**Delete buffer id's.*/
	public abstract void destroy();
	/**Bind this buffer.*/
	public abstract void bind();
	/**Unbind this buffer.*/
	public abstract void unBind();
	/**Return true if this buffer is selected.*/
	public abstract boolean isSelected();
	/**Put's the data to buffer from already class data with specific size.*/
	public abstract void putBuffer(final BufferImpl buffer);
}