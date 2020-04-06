package CTXEngine.Graphics;

import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import org.joml.Matrix2f;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

/**
 * This class accesiable only on java version of the CTXEngine, because engine use
 * Lwjgl and memory controlling, and stored data to java buffers instead c/c++ pointers.
 * 
 */
public final class BufferHelper 
{
	public static FloatBuffer putMatrix4ftoFloatBuffer(FloatBuffer buffer, Matrix4f matrix)
	{
		buffer.put(matrix.m00()).put(matrix.m01()).put(matrix.m02()).put(matrix.m03());
		buffer.put(matrix.m10()).put(matrix.m11()).put(matrix.m12()).put(matrix.m13());
		buffer.put(matrix.m20()).put(matrix.m21()).put(matrix.m22()).put(matrix.m23());
		buffer.put(matrix.m30()).put(matrix.m31()).put(matrix.m32()).put(matrix.m33());
	
		return buffer;
	}
	
	public static FloatBuffer putMatrix3ftoFloatBuffer(FloatBuffer buffer, Matrix3f matrix)
	{
		buffer.put(matrix.m00()).put(matrix.m01()).put(matrix.m02());
		buffer.put(matrix.m10()).put(matrix.m11()).put(matrix.m12());
		buffer.put(matrix.m20()).put(matrix.m21()).put(matrix.m22());
		
		return buffer;
	}
	
	public static FloatBuffer putMatrix2ftoFloatBuffer(FloatBuffer buffer, Matrix2f matrix)
	{
		buffer.put(matrix.m00()).put(matrix.m01());
		buffer.put(matrix.m10()).put(matrix.m11());
		
		return buffer;
	}
	
	public static FloatBuffer putVector4ftoFloatBuffer(FloatBuffer buffer, Vector4f v)
	{
		buffer.put(v.x).put(v.y).put(v.z).put(v.w);
		return buffer;
	}
	
	public static FloatBuffer putVector3ftoFloatBuffer(FloatBuffer buffer, Vector3f v)
	{
		buffer.put(v.x).put(v.y).put(v.z);
		return buffer;
	}
	
	public static FloatBuffer putVector2ftoFloatBuffer(FloatBuffer buffer, Vector2f v)
	{
		buffer.put(v.x).put(v.y);
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Matrix4f matrix)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		BufferHelper.putMatrix4ftoFloatBuffer(buffer, matrix).flip();
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Matrix3f matrix)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(3 * 3);
		BufferHelper.putMatrix3ftoFloatBuffer(buffer, matrix).flip();
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Matrix2f matrix)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(2 * 2);
		BufferHelper.putMatrix2ftoFloatBuffer(buffer, matrix).flip();
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Matrix4f[] matrices)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4 * matrices.length);
		
		for (Matrix4f matrix : matrices)
		{
			BufferHelper.putMatrix4ftoFloatBuffer(buffer, matrix);
		}
		
		buffer.flip();
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Matrix3f[] matrices)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(3 * 3 * matrices.length);
		
		for (Matrix3f matrix : matrices)
		{
			BufferHelper.putMatrix3ftoFloatBuffer(buffer, matrix);
		}
		
		buffer.flip();
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Matrix2f[] matrices)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(2 * 2 * matrices.length);
		
		for (Matrix2f matrix : matrices)
		{
			BufferHelper.putMatrix2ftoFloatBuffer(buffer, matrix);
		}
		
		buffer.flip();
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Vector2f matrix)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(2 * 2);
		BufferHelper.putVector2ftoFloatBuffer(buffer, matrix).flip();
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Vector3f matrix)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(3 * 3);
		BufferHelper.putVector3ftoFloatBuffer(buffer, matrix).flip();
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Vector4f matrix)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		BufferHelper.putVector4ftoFloatBuffer(buffer, matrix).flip();
		return buffer;
	}
	
	public static ByteBuffer toByteBuffer(int size, int... data) 
	{
		return (ByteBuffer) memAlloc(Integer.BYTES * size).asIntBuffer().put(data).flip();
	}

	public static ByteBuffer toByteBuffer(int... data) 
	{
		return toByteBuffer(data.length, data);
	}
	
	public static ShortBuffer toShortBuffer(int size, short... data) 
	{					
		return (ShortBuffer) memAllocShort(Short.BYTES * size).put(data).flip();
	}
	
	public static ShortBuffer toShortBuffer(short... data) 
	{
		return toShortBuffer(data.length, data);
	}
	
	public static IntBuffer toIntBuffer(int size, int... data) 
	{						
		return (IntBuffer) memAllocInt(Integer.BYTES * size).put(data).flip();
	}
	
	public static IntBuffer toIntBuffer(int... data)
	{
		return toIntBuffer(data.length, data);
	}
											
	public static LongBuffer toLongBuffer(int size, long... data) 
	{
		return (LongBuffer) memAllocLong(Long.BYTES * size).put(data).flip();
	}
	
	public static LongBuffer toLongBuffer(long... data)
	{
		return toLongBuffer(data.length, data);
	}
	
	public static FloatBuffer toFloatBuffer(int size, float... data)
	{
		return (FloatBuffer) memAllocFloat(Float.BYTES * size).put(data).flip();
	}
	
	public static FloatBuffer toFloatBuffer(float... data)
	{
		return toFloatBuffer(data.length, data);
	}
	
	public static DoubleBuffer toDoubleBuffer(int size, double... data)
	{
		return (DoubleBuffer) memAllocDouble(Double.BYTES * size).put(data).flip();
	}
	
	public static DoubleBuffer toDoubleBuffer(double... data)
	{
		return toDoubleBuffer(data.length, data);
	}
}	
