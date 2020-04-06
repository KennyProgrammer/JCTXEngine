package CTXEngine.Graphics;

import org.lwjgl.system.NativeType;

/**
 * Contains main funtions to all graphics api buffers.
 */
public @NativeType("struct") interface BufferImpl
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
	public abstract void putData();
	/**Get's the data from this buffer.*/
	public abstract @NativeType("BufferSize") int getSize();
	/*Get's the type of this buffer.*/
	public abstract @NativeType("BufferType") int getType();
	
	/**
	 * Contains main funtions to float version of buffer because java doenst have void*.
	 */
	public static interface BufferFloatImpl extends BufferImpl
	{	
		/**Put's the data to buffer with specific size.*/
		public abstract void putData(@NativeType("BufferData") float[] data, @NativeType("BufferSize") int size);
		/**Get's the data from this buffer.*/
		public abstract @NativeType("BufferData") float[] getData();
	}
	
	/**
	 * Contains main funtions to int version of buffer because java doenst have void*.
	 */
	public static interface BufferIntImpl extends BufferImpl
	{	
		/**Put's the data to buffer with specific size.*/
		public abstract void putData(@NativeType("BufferData") int[] data, @NativeType("BufferSize") int size);
		/**Get's the data from this buffer.*/
		public abstract @NativeType("BufferData") int[] getData();
	}
}