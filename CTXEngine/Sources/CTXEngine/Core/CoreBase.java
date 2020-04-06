package CTXEngine.Core;

import static CTXEngine.Core.SimplePrint.*;
import static CTXEngine.Core.PrimitiveSizes.*;

import CTXEngine.Core.InputSystem.WinInput;

public final class CoreBase 
{
	public static int CTX_NULL = 0;
	
	public static byte CTX_ENABLE_ASSETS = 1;
	
	public static int CTX_PLATFORM_WINDOWS = 0x1;
	public static int CTX_PLATFORM_IOS = 0x2;
	public static int CTX_PLATFORM_MACOS = 0x3;
	public static int CTX_PLATFORM_ANDROID = 0x4;
	public static int CTX_PLATFORM_LINUX = 0x5;
	public static int CTX_PLATFORM_CURRENT = CTX_PLATFORM_WINDOWS;
	
	public static String CTX_API_GL   = "Open Gl";    //string type of api
	public static String CTX_API_VK   = "Vulcan";
	public static String CTX_API_DX3D = "DirectX 3D";
	public static String CTX_API_CURRENT = CTX_API_GL;
	public static int CTX_GL = 1723;    //integral type of api
	public static int CTX_VK = 1724;
	public static int CTX_DX = 1725;
	public static int CTX_RENDER_CONTEXT = CTX_GL;
	public static int CTX_WINDOW_SYS_GL   = 0,
	                  CTX_WINDOW_SYS_VK   = 0,
	                  CTX_WINDOW_SYS_DX3D = 0;
	
	static
	{
		if(CTX_PLATFORM_WINDOWS == 0 && CTX_PLATFORM_IOS == 0 && CTX_PLATFORM_MACOS == 0
				&& CTX_PLATFORM_ANDROID == 0 && CTX_PLATFORM_LINUX == 0)
		{
			CTX_ENGINE_INFO("Unknown platform!");
			System.exit(-1);
		}
		
		if(CTX_PLATFORM_CURRENT == CTX_PLATFORM_WINDOWS)
		{
			if(CTX_RENDER_CONTEXT == CTX_GL)
			{
				CTX_WINDOW_SYS_GL = 1;
			}
			else if(CTX_RENDER_CONTEXT == CTX_VK)
			{
				CTX_WINDOW_SYS_VK = 1;
			}
			else if(CTX_RENDER_CONTEXT == CTX_DX)
			{
				CTX_WINDOW_SYS_DX3D = 1;
			}
			
			new WinInput();
		}
		else if(CTX_PLATFORM_CURRENT == CTX_PLATFORM_IOS)
		{
			CTX_ENGINE_INFO("CTXEngine not support IOS!");
			System.exit(-1);
		}
		else if(CTX_PLATFORM_CURRENT == CTX_PLATFORM_MACOS)
		{
			CTX_ENGINE_INFO("CTXEngine not support MacOs!");
			System.exit(-1);
		}
		else if(CTX_PLATFORM_CURRENT == CTX_PLATFORM_ANDROID)
		{
			CTX_ENGINE_INFO("Unknown Android platform!");
			System.exit(-1);
		}
		else if(CTX_PLATFORM_CURRENT == CTX_PLATFORM_LINUX)
		{
			CTX_ENGINE_INFO("Unknown Linux platform!");
			System.exit(-1);
		}
							
	}
	
	public static void CTX_ENGINE_ASSERT(boolean b, String s) 
	{ 
		if(CTX_ENABLE_ASSETS == 1) 
		{
			if(!b) {CTX_ENGINE_ERROR("Assertion failed: " + s); Runtime.getRuntime().exit(-1); } 
		} 
	}
	
	public static byte BIT(byte x) 
	{ 
		return (byte) (1 << x); 
	}
	
	public static int CTX_COUNT_I(int x) 
	{
		//mem size of element / mem size of data type
		return sizeof(x) / sizeof("int");
	}
	
	public static int CTX_COUNT_F(float x) 
	{
		//mem size of element / mem size of data type
		return sizeof(x) / sizeof("float");
	}
	
	public static long CTX_COUNT_D(double x) 
	{
		//mem size of element / mem size of data type
		return sizeof(x) / sizeof("double");
	}
	
	public static int CTX_SIZE_I(int... x) 
	{	
		int countElemInArr = 0;
		for(Integer i : x)
			countElemInArr = countElemInArr + (sizeof(i) / sizeof("int"));
		//count elements in array    *    memory size of this elements
		return countElemInArr * sizeof("int");
	}
	
	public static int CTX_SIZE_F(float... x) 
	{
		int countElemInArr = 0;
		for(Float i : x)
			countElemInArr = countElemInArr + (sizeof(i) / sizeof("float"));
		//count elements in array    *    memory size of this elements
		return countElemInArr * sizeof("float");
	}
	
	public static long CTX_SIZE_D(double... x) 
	{
		long countElemInArr = 0;
		for(Double i : x)
			countElemInArr = countElemInArr + (sizeof(i) / sizeof("double"));
		//count elements in array    *    memory size of this elements
		return countElemInArr * sizeof("double");
	}
	
	public static int CTX_STRIDE_I(int x) 
	{
		//count elements in array    *    memory size of this elements
		return CTX_SIZE_I(x);
	}
	
	public static int CTX_STRIDE_F(float x) 
	{
		//count elements in array    *    memory size of this elements
		return CTX_SIZE_F(x);
	}
	
	public static CoreBase init()
	{
		return new CoreBase();
	}
}
