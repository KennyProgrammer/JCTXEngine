package CTXEngine.Core;

public interface CoreConfigurationsImpl 
{
	/**
		Get's engine name.
	*/
	String getEngineName();
	
	/**
		Get's engine version.
	*/
	String getEngineVersion();
	
	/**
		Get's application name, or window title on top of it.
	*/
	String getAppName();
	
	/**
		Get's application version.
	*/
	String getAppVersion();
	
	/**
		Get's width of current window.
	*/
	int getWindowWidth();
	
	/**
		Get's height of current window.
	*/
	int getWindowHeight();
	
	/**
		If is fullscreen then return true otherwise false.
	*/
	boolean isFullscreen();
	
	/**
		If is windowed then return true otherwise false.
	*/
	boolean isWindowed();
	
	/**
		If is Vsync mode then return true otherwise false.
	*/
	boolean isVSync();
	
	/**
		If is resized window then return true otherwise false.
	*/
	boolean isResized();
	
	/**
		Return true if render engine need to load otherwise false.
	*/
	static boolean isLoadRenderEngine() { return true; }
}
