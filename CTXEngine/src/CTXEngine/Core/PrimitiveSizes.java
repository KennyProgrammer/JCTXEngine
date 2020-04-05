package CTXEngine.Core;

/**
 * C++ sizeof operations.
 */
public final class PrimitiveSizes 
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
}
