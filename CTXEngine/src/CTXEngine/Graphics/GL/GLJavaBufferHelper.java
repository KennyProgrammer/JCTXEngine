package CTXEngine.Graphics.GL;

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
public final class GLJavaBufferHelper 
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
		GLJavaBufferHelper.putMatrix4ftoFloatBuffer(buffer, matrix).flip();
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Matrix3f matrix)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(3 * 3);
		GLJavaBufferHelper.putMatrix3ftoFloatBuffer(buffer, matrix).flip();
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Matrix2f matrix)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(2 * 2);
		GLJavaBufferHelper.putMatrix2ftoFloatBuffer(buffer, matrix).flip();
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Matrix4f[] matrices)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4 * matrices.length);
		
		for (Matrix4f matrix : matrices)
		{
			GLJavaBufferHelper.putMatrix4ftoFloatBuffer(buffer, matrix);
		}
		
		buffer.flip();
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Matrix3f[] matrices)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(3 * 3 * matrices.length);
		
		for (Matrix3f matrix : matrices)
		{
			GLJavaBufferHelper.putMatrix3ftoFloatBuffer(buffer, matrix);
		}
		
		buffer.flip();
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Matrix2f[] matrices)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(2 * 2 * matrices.length);
		
		for (Matrix2f matrix : matrices)
		{
			GLJavaBufferHelper.putMatrix2ftoFloatBuffer(buffer, matrix);
		}
		
		buffer.flip();
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Vector2f matrix)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(2 * 2);
		GLJavaBufferHelper.putVector2ftoFloatBuffer(buffer, matrix).flip();
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Vector3f matrix)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(3 * 3);
		GLJavaBufferHelper.putVector3ftoFloatBuffer(buffer, matrix).flip();
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Vector4f matrix)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		GLJavaBufferHelper.putVector4ftoFloatBuffer(buffer, matrix).flip();
		return buffer;
	}
	
	public static ByteBuffer toByteBuffer(int size, int... data) 
	{
									//sizeof(signed byte)
		ByteBuffer buffer = memAlloc(Integer.BYTES * size);
		IntBuffer intBuffer = buffer.asIntBuffer();
		intBuffer.put(data);
		intBuffer.flip();
		
		return buffer;
	}

	public static ByteBuffer toByteBuffer(int... data) 
	{
		return toByteBuffer(data.length, data);
	}
	
	
	public static ShortBuffer toShortBuffer(int size, short... data) 
	{
											//sizeof(signed int short)
		ShortBuffer buffer = memAllocShort(Short.BYTES * size);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	public static ShortBuffer toShortBuffer(short... data) 
	{
		return toShortBuffer(data.length, data);
	}
	
	public static IntBuffer toIntBuffer(int size, int... data) 
	{
										//sizeof(signed int)
		IntBuffer buffer = memAllocInt(Integer.BYTES * size);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	public static IntBuffer toIntBuffer(int... data)
	{
		return toIntBuffer(data.length, data);
	}
	
											//sizeof(int long long)
	public static LongBuffer toLongBuffer(int size, long... data) 
	{
		LongBuffer buffer = memAllocLong(Long.BYTES * size);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	public static LongBuffer toLongBuffer(long... data)
	{
		return toLongBuffer(data.length, data);
	}
	
	public static FloatBuffer toFloatBuffer(int size, float... data)
	{
		FloatBuffer buffer = memAllocFloat(Float.BYTES * size);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	public static FloatBuffer toFloatBuffer(float... data)
	{
		return toFloatBuffer(data.length, data);
	}
	
	public static DoubleBuffer toDoubleBuffer(int size, double... data)
	{
		DoubleBuffer buffer = memAllocDouble(Double.BYTES * size);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	public static DoubleBuffer toDoubleBuffer(double... data)
	{
		return toDoubleBuffer(data.length, data);
	}
	
	
}	
