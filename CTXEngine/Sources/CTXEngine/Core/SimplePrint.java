package CTXEngine.Core;

public class SimplePrint
{
	
	public static void CTX_ENGINE_INFO(String... args)
	{
		String finalStr = "";
		for (String arg : args) 
		{
			finalStr += arg;
		}
		System.out.println("[14:03:18]: [Thread 4364]: [CTXEngine/info]: " + finalStr);
	}
	
	public static void CTX_ENGINE_WARN(String... args)
	{
		String finalStr = "";
		for (String arg : args) 
		{
			finalStr += arg;
		}
		System.out.println("[14:03:18]: [Thread 4364]: [CTXEngine/warn]: " + finalStr);
	}
	
	public static void CTX_ENGINE_ERROR(String... args)
	{
		String finalStr = "";
		for (String arg : args) 
		{
			finalStr += arg;
		}
		System.err.println("[14:03:18]: [Thread 4364]: [CTXEngine/err]: " + finalStr);
	}
}
