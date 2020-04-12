package CTXEngine.Core;

import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public final class CAndCppOperations
{
	public static int sizeof(String _type) 
	{ 
		switch (_type) 
		{
			case "byte": return 1; 
			case "char": return 1; 
			case "short": return 2; 
			case "int": return 4; 
			case "long": return 8; 
			case "float": return 4; 
			case "double": return 8; 
		}
		return 1; 
    }
	 
    public static int  sizeof(byte   n) { return sizeof("byte")   *        n; }
	public static int  sizeof(short  n) { return sizeof("short")  *        n; }
	public static int  sizeof(int    n) { return sizeof("int")    *        n; }
	public static long sizeof(long   n) { return sizeof("long")   *        n; }
	public static int  sizeof(float  n) { return sizeof("float")  *  (int) n; }
	public static long sizeof(double n) { return sizeof("double") *  (long)n; }

	/** 
	 * Annotates parameter and return types in Java bindings with the C type as defined in the native function.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE,
		ElementType.FIELD})
	public static @interface CType 
	{
	    String value();
	}
	
	/**
	 * This class file defines several General-purpose functions, including dynamic 
	 * memory management, random number generation, integer arithmetic, search, sorting,
	 * and data type conversion.
	 */
	public static class stdlib
	{
		/**
		 * Standard 'C' calloc function. 
		 * <p>
		 * Allocates a block of {@code size} bytes of memory on the stack. The content of the 
		 * newly allocated block of memory is not initialized, remaining with
         * indeterminate values.
         * <p>
		 * CallocInt and return pointer with all zero values. <p>
		 * <b>'C'</b> example:
		 * <p>
		 * <code>int* ip = calloc(size, sizeof(int); </code> <p>
		 * <b>'C++'</b> example:
		 * <p>
		 * <code>int* ip = new int[]; </code>
		 *  
		 * @param number - number elements of array to be allocated memory.
		 * @param size - <code>(number * size)</code> size one element in bytes. (already calculated)
		 * 
		 * @return allocated int in {@link IntBuffer}.
		 */
		public static IntBuffer callocIntEmpty(int number)
		{
			return stackGet().callocInt(number);
		}
		
		/**
		 * Standard 'C' calloc function. 
		 * <p>
		 * Float version of {@link callocInt}. <p>
		 * <b>'C'</b> example:
		 * <p>
		 * <code>float* fp = calloc(size, sizeof(float); </code> <p>
		 * <b>'C++'</b> example:
		 * <p>
		 * <code>float* fp = new float[]; </code>
		 *  
		 * @param number - number elements of array to be allocated memory.
		 * @param size - <code>(number * size)</code> size one element in bytes. (already calculated)
		 * 
		 * @return allocated float in {@link FloatBuffer}.
		 */
		public static FloatBuffer callocFloatEmpty(int number)
		{
			return stackGet().callocFloat(number);
		}
		
		/**
		 * Allocates {@link callocInt} bytes of memory.
		 * <p>
		 * CallocInt and put <code>arrays</code> to IntBuffer. <p>
		 * <b>'C'</b> example:
		 * <p>
		 * <code>int* ip = calloc(size, sizeof(int); </code> <p>
		 * <b>'C++'</b> example:
		 * <p>
		 * <code>int* ip = new int[]; </code>
		 *  
		 * @param number - number elements of array to be allocated memory.
		 * @param arrays - basically int array data to be pushed on buffer and returned as pointer.
		 *
		 * @return allocated int in {@link IntBuffer}. 
		 */
		public static IntBuffer callocInt(int number, int[] values)
		{
			return (IntBuffer) callocIntEmpty(number != values.length ? number : values.length).put(values).flip();
		}
		
		/**
		 * Allocates {@link callocFloat} bytes of memory.
		 * <p>
		 * CallocFloat and put <code>arrays</code> to FloatBuffer. <p>
		 * <b>'C'</b> example:
		 * <p>
		 * <code>float* fp = calloc(size, sizeof(float); </code> <p>
		 * <b>'C++'</b> example:
		 * <p>
		 * <code>float* fp = new float[]; </code>
		 *  
		 * @param number - number elements of array to be allocated memory.
		 * @param arrays - basically int array data to be pushed on buffer and returned as pointer.
		 *
		 * @return allocated float in {@link FloatBuffer}. 
		 */
		public static FloatBuffer callocFloat(int number, float... values)
		{
			return (FloatBuffer) callocFloatEmpty(number != values.length ? number : values.length).put(values).flip();
		}
		
		/**
		 * Standard 'C' malloc function. 
		 * <p>
	     * Allocates a block of {@code size} bytes of memory on the stack. The content of the 
	     * newly allocated block of memory is not initialized, remaining with
	     * indeterminate values.
	     *
	     * @param number - the allocation size
	     * @param values - values to be allocated.
	     *
	     * @return allocated int values in {@link IntBuffer}.
	     */
		public static IntBuffer mallocInt(int number, int... values)
		{
			return (IntBuffer) stackGet().mallocInt(number).put(values).flip();
		}
		
		/**
		 * Standard 'C' malloc function. 
		 * <p>
	     * Float version of {@link mallocInt}.
	     *
	     * @param number - the allocation size
	     * @param values - values to be allocated.
	     *
	     * @return allocated float values in {@link FloatBuffer}.
	     */
		public static FloatBuffer mallocFloat(int number, float[] values)
		{
			return (FloatBuffer) stackGet().mallocFloat(number == values.length ? number : values.length).put(values).flip();
		}
	
		/**
		 * Return the pointer from int's as memory address.
		 */
		public static long pointer(int... ints)
		{
			return memAddress(mallocInt(ints.length, ints));
		}
		
		/**
		 * Return the pointer from float's as memory address.
		 */
		public static long pointer(float... floats)
		{
			return memAddress(mallocFloat(floats.length, floats));
		}
		
		/**
		 * Return the pointer from {@link callocInt} as memory address.
		 * 
		 * @param calloc_malloc - {@link callocInt} or {@link mallocInt} function or 
		 * java custom {@link FloatBuffer}.
		 */
		public static long pointer(IntBuffer calloc_malloc)
		{
			return memAddress(calloc_malloc);
		}
		
		/**
		 * Return the pointer from {@link callocFloat} as memory address.
		 * 
		 * @param calloc_malloc - {@link callocFloat} or {@link mallocFloat} function or custom {@link FloatBuffer}.
		 */
		public static long pointer(FloatBuffer calloc_malloc)
		{
			return memAddress(calloc_malloc);
		}
		
		/**
		 * Sets the current stack pointer. 
		 * <p>
		 * This method directly manipulates the stack pointer. Using it irresponsibly 
		 * may break the internal state of the stack. It should only be used in rare cases 
		 * or in auto-generated code
		 */
		public static void setCurrentStackPointer()
		{
			stackGet().setPointer(stackGet().getPointer());
		}
		
		/**
		 * Sets the pointer to current stack. 
		 * <p>
		 * This method directly manipulates the stack pointer. Using it irresponsibly 
		 * may break the internal state of the stack. It should only be used in rare cases 
		 * or in auto-generated code
		 */
		public static void setPointer(int pointer)
		{
			stackGet().setPointer(pointer);
		}
	}
	
	
	public static class cstdlib extends stdlib
	{
		
	}
}
